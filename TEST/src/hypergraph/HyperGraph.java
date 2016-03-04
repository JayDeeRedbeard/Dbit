package hypergraph;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Set;

/** Defines a hypergraph with vertices that contain objects of class <code>T</code>. In hyper graphs, edges might
 * connect more than two vertices. More formally, if an edge is defined as a set of vertices, in a classic graph the
 * cardinality of an edge is <= 2 whereas in hypergraphs the cardinality is not restricted.  
 * <p>
 * This is the abstract base classes for general hypergraphs. Subclasses must implement their own data structure to
 * store the elements of the graph.
 * 
 * @author Matthias Kampmann (kampmams)
 *
 * @param T The objects that are stored in the vertices of the graph.
 */
public abstract class HyperGraph<T> {
	/** Creates a new hyper graph with equal vertex set excluding the given vertex. The edge set is also cloned from
	 * this graph but the edges do not contain the given vertex anymore. More formally, if this graph has vertex set V
	 * and edge set E, then the new graph has:
	 * <pre>
	 * V' = V \ {vertex}
	 * E' = E
	 * </pre>
	 * 
	 * @param vertex The vertex to exclude in the copy.
	 * 
	 * @return A new hyper graph with equal vertex and edge set excluding the given vertex.
	 */
	public abstract HyperGraph<T> cloneAndExclude(HyperGraphVertex<T> vertex) throws Exception;
	
	/** Creates a new hyper graph with the same vertex set as this graph and the same edge set as this graph excluding
	 * the given edge. More formally, if this graph has vertex set V and edge set E, then the new graph has:
	 * <pre>
	 * V' = V
	 * E' = E \ {edge}
	 * </pre>
	 * 
	 * @param edge The edge to exclude in the copy.
	 * 
	 * @return A new hyper graph with equal vertex and edge set excluding the given edge.
	 */
	public abstract HyperGraph<T> cloneAndExclude(HyperEdge<T> edge) throws Exception;
	
	/** Creates a new hyper graph with equal vertex set excluding the given vertex. All edges that contain the vertex
	 * in this graph are also removed from the newly created graph. More formally, if this graph has vertex set V and
	 * edge set E, then the new graph has:
	 * <pre>
	 * V' = V \ {vertex}
	 * E' = {e | e in E, vertex not in e}
	 * </pre>
	 * 
	 * @param vertex The vertex to exclude.
	 * 
	 * @return
	 */
	public abstract HyperGraph<T> cloneExcludeAndRemoveEdges(HyperGraphVertex<T> vertex) throws Exception;
	
	/** Removes the given edge from this graph. Does nothing, if the edge is not present. Updates the degree of all
	 * vertices that were in the hyperedge.
	 * 
	 * @param edge The edge to remove.
	 */
	public abstract void removeEdge(HyperEdge<T> edge);
	
	/** Removes a vertex from this graph. The edges are updated accordingly.
	 * 
	 * @param vertex The vertex to remove.
	 */
	public abstract void removeVertex(HyperGraphVertex<T> vertex);
	
	/** Checks if the given vertex is contained in this graph.
	 * 
	 * @param vertex The vertex to check.
	 * 
	 * @return True if this graph contains the given vertex.
	 */
	public abstract boolean containsVertex(HyperGraphVertex<T> vertex);
	
	/** Gets the number of hyperedges in this graph that contain the given vertex and have the given degree.
	 * 
	 * @param vertex The vertex to look for.
	 * @param degree The degree the hyperedges should have.
	 * 
	 * @return The number of hyperedges with the given degree that contain the given vertex.
	 */
	public abstract int numEdges(HyperGraphVertex<T> vertex, int degree);
	
	/** Gets the 'neighbor' set of the given vertex. This is the set of vertices that share at least one hyperedge with
	 * the given vertex and degree.
	 * 
	 * @param vertex The vertex to look for.
	 * @param degree The degree the edges should have to include the vertices in the set.
	 * 
	 * @return The set of vertices (including the given vertex) that share at least one hyperedge with the given vertex.
	 */
	public abstract Set<HyperGraphVertex<T>> neighborSet(HyperGraphVertex<T> vertex, int degree);
	
	/** Gets the maximum degree of a vertex in this set.
	 * 
	 * @return The maximum degree of all vertices in this set.
	 */
	public abstract int maxDegree();
	
	/** Removes all edges from the graph that contain the given vertex. Note that this does not remove the vertex from
	 * the graph.
	 * 
	 * @param vertex The vertex to look for.
	 */
	public abstract void removeEdgesContaining(HyperGraphVertex<T> vertex);
	
	/** Adds an edge to the set, if it is not already present.
	 * 
	 * @param edge The edge to add.
	 * 
	 * @return True if the edge was actually added, that is, if the edge was not already added.
	 * 
	 * @see HashSet#add(Object)
	 */
	public abstract boolean addEdge(HyperEdge<T> edge);
	
	/** Gets the vertex with the largest degree. If several vertices share the largest degree, calling this function
	 * multiple times might yield different vertices.
	 * 
	 * @return The first found vertex with the largest degree.
	 */
	public abstract HyperGraphVertex<T> getVertexWithMaxDegree();
	
	/** Gets all hyperedges in this graph that contain the given vertex.
	 * 
	 * @param vertex The vertex to look for.
	 * 
	 * @return A set of hyperedges that contain the given vertex.
	 */
	public abstract Set<HyperEdge<T>> getHyperEdgesContaining(HyperGraphVertex<T> vertex);
	
	/** Gets the edge set. Manipulating the return value will also manipulate this object.
	 * 
	 * @return The edge set of the graph.
	 */
	public abstract Set<HyperEdge<T>> edgeSet();
	
	/** Gets the vertex set. Manipulating the return value will also manipulate this object.
	 * 
	 * @return The vertex set of the graph.
	 */
	public abstract Set<HyperGraphVertex<T>> vertexSet();
	
	/** Dumps the contents of this graph to a file.
	 * 
	 * @param toFile The file to write to.
	 * 
	 * @throws FileNotFoundException The file could not be opened.
	 * @throws IOException General write exception.
	 */
	public void dumpGraph(String toFile) throws FileNotFoundException, IOException {
		OutputStream outStream = null;
		OutputStreamWriter outStreamWriter = null;
		BufferedWriter writer = null;
		
		try {
			outStream = new FileOutputStream(toFile);
			outStreamWriter = new OutputStreamWriter(outStream);
			writer = new BufferedWriter(outStreamWriter);
			
			writer.write("# Hypergraph vertices:\n");
			
			Set<HyperGraphVertex<T>> vertexSet = vertexSet();
			for(HyperGraphVertex<T> vertex : vertexSet) {
				writer.write(vertex.toString() + "\n");
			}
			
			writer.write("\n# Hypergraph edges:\n");
			Set<HyperEdge<T>> hyperEdges = edgeSet();
			for(HyperEdge<T> edge : hyperEdges) {
				writer.write(edge.toString() + "\n");
			}
		} finally {
			if(null != writer) {
				writer.flush();
				writer.close();
				writer = null;
			}
			if(null != outStreamWriter) {
				outStreamWriter.close();
				outStreamWriter = null;
			}
			if(null != outStream) {
				outStream.close();
				outStream = null;
			}
		}
	}
	
	/** Implements simple sanity checking by comparing the degrees of the vertices and and the actual number of edges
	 * they are located in.
	 * 
	 * @throws Exception Checking failed.
	 */
	public void checkGraph() throws Exception {
		for(HyperGraphVertex<T> vertex : vertexSet()) {
			Set<HyperEdge<T>> edges = getHyperEdgesContaining(vertex);
			if(edges.size() != vertex.degree()) {
				throw new Exception("Vertex " + vertex + " is contained in " + edges.size() + " edges!");
			}
		}
	}
}
