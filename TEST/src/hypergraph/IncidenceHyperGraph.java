package hypergraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/** A hypergraph that uses an incidence list for storing the elements. This means the vertices and edges are stored in
 * different lists.
 * 
 * @author Matthias Kampmann (kampmams)
 *
 * @param T The objects that are stored in the vertices of the graph.
 */
public class IncidenceHyperGraph<T> extends HyperGraph<T> {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Default constructor. */
	public IncidenceHyperGraph() {
		
	}
	
	/** Copy constructor. Creates a deep copy of the given graph.
	 * 
	 * @param other The graph to copy.
	 */
	public IncidenceHyperGraph(IncidenceHyperGraph<T> other) throws Exception {
		m_vertexSet = new HashSet<HyperGraphVertex<T>>();
		
		for(HyperGraphVertex<T> vertex : other.m_vertexSet) {
			HyperGraphVertex<T> copy = new HyperGraphVertex<T>(vertex.value());
			
			// No need to copy the degree of the vertex, this is done when the edges are added. Besides, if the degrees
			// of the original and cloned vertices don't match, a bug is present that is detectable by that.
			m_vertexSet.add(copy);
		}
		
		for(HyperEdge<T> edge : other.m_edgeSet) {
			HyperEdge<T> toAdd = new HyperEdge<T>(edge, m_vertexSet);
			if(toAdd.size() > 0) {
//				m_edgeSet.add(toAdd);
				addEdge(toAdd);
			}
		}
	}
	
	@Override
	public IncidenceHyperGraph<T> cloneAndExclude(HyperEdge<T> edge) throws Exception {
		IncidenceHyperGraph<T> graph = new IncidenceHyperGraph<T>(this);
		graph.removeEdge(edge);

		return graph;
	}
	
	@Override
	public IncidenceHyperGraph<T> cloneAndExclude(HyperGraphVertex<T> vertex) throws Exception {
		IncidenceHyperGraph<T> graph = new IncidenceHyperGraph<T>(this);
		
		HyperGraphVertex<T> copy = null;
		HyperGraphVertex<T> bestFit = null;
		for(HyperGraphVertex<T> v : graph.vertexSet()) {
			if(v.equals(vertex)) {
				if(v.degree() == vertex.degree()) {
					copy = v;
				} else {
					bestFit = v;
				}
				break;
			}
		}
		
		if(null != copy) {
			graph.removeVertex(copy);
		} else {			
			// Check whether the clone and this graph have an equal edge set.
			Set<HyperEdge<T>> clonedEdgeSet = graph.edgeSet();
		}

		return graph;
	}
	
	@Override
	public IncidenceHyperGraph<T> cloneExcludeAndRemoveEdges(HyperGraphVertex<T> vertex) throws Exception {
		IncidenceHyperGraph<T> graph = new IncidenceHyperGraph<T>(this);
		
		HyperGraphVertex<T> copy = null;
		HyperGraphVertex<T> bestFit = null;
		for(HyperGraphVertex<T> v : graph.vertexSet()) {
			if(v.equals(vertex)) {
				if(v.degree() == vertex.degree()) {
					copy = v;
				} else {
					bestFit = v;
				}
				break;
			}
		}
		
		if(null != copy) {
			graph.removeEdgesContaining(copy);
			graph.m_vertexSet.remove(copy);
		} else {
			// Check whether the clone and this graph have an equal edge set.
			Set<HyperEdge<T>> clonedEdgeSet = graph.edgeSet();
		}

		return graph;
	}
	
	@Override
	public void removeVertex(HyperGraphVertex<T> vertex) {
		if(containsVertex(vertex)) {
			m_vertexSet.remove(vertex);
			
			// Collect all edges that contain the given vertex, remove them from the set, adjust them (remove vertex
			// from them) and re-add them to the set.
			// This seems to be dumb, but avoids a subtle bug:
			// Suppose we remove a vertex and consequently adjust all edges that contained it. The hashCode() function
			// of the edges will now return a different result, as the content of the edge (on which the hash code is
			// based) has changed. Since a hash map is used to store the edges, checking whether an edge that previously
			// contained the given vertex is present in the graph will fail, although the edge is actually present (as
			// it is now stored somewhere else).
			HashSet<HyperEdge<T>> toChange = new HashSet<HyperEdge<T>>();
			Iterator<HyperEdge<T>> iterator = m_edgeSet.iterator();
			while(iterator.hasNext()) {
				HyperEdge<T> e = iterator.next();
				if(e.contains(vertex)) {
					toChange.add(e);
				}
			}
			
			for(HyperEdge<T> edge : toChange) {
				m_edgeSet.remove(edge);
				edge.remove(vertex);
				if(0 < edge.size()) {
					// Note: The edge might not always be re-added since it might be that there is already the same edge
					// in the graph.
					///// DEBUG
					if(!m_edgeSet.add(edge)) {
						for(HyperGraphVertex<T> v : edge) {
							v.decrementDegree();
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean containsVertex(HyperGraphVertex<T> vertex) {
		return m_vertexSet.contains(vertex);
	}
	
	@Override
	public int numEdges(HyperGraphVertex<T> vertex, int degree) {
		int result = 0;
		
		for(HyperEdge<T> edge : m_edgeSet) {
			if(edge.contains(vertex) && edge.size() == degree) {
				result++;
			}
		}
		
		return result;
	}
	
	@Override
	public Set<HyperGraphVertex<T>> neighborSet(HyperGraphVertex<T> vertex, int degree) {
		HashSet<HyperGraphVertex<T>> result = new HashSet<HyperGraphVertex<T>>();
		
		for(HyperEdge<T> edge : m_edgeSet) {
			if(edge.size() != degree) {
				continue;
			}
			
			if(edge.contains(vertex)) {
				for(HyperGraphVertex<T> p : edge) {
					if(!result.contains(p)) {
						result.add(p);
					}
				}
			}
		}
		
		return result;
	}
	
	@Override
	public int maxDegree() {
		int degree = -1;
		for(HyperGraphVertex<T> vertex : m_vertexSet) {
			if(vertex.degree() > degree) {
				degree = vertex.degree();
			}
		}
		
		return degree;
	}
	
	@Override
	public void removeEdge(HyperEdge<T> edge) {
//		while(m_edgeSet.contains(edge)) {
		if(m_edgeSet.remove(edge)) {
			for(HyperGraphVertex<T> p : edge) {
				p.decrementDegree();
				if(p.degree() <= 0) {
					// Remove p directly since it cannot be contained in other edges anymore.
					m_vertexSet.remove(p);
				} else {
					// Enable dominance checking for this vertex since it might be possible that p is now dominated by
					// some other vertex.
					p.setNeedsChecking(true);
				}
			}
		}
//		}
	}
	
	@Override
	public void removeEdgesContaining(HyperGraphVertex<T> vertex) {
		HashSet<HyperEdge<T>> toRemove = new HashSet<HyperEdge<T>>();
		
		for(HyperEdge<T> edge : m_edgeSet) {
			if(edge.contains(vertex)) {
				toRemove.add(edge);
			}
		}
		
		for(HyperEdge<T> edge : toRemove) {
			removeEdge(edge);
		}
	}
	
	@Override
	public boolean addEdge(HyperEdge<T> edge) {
		if(m_edgeSet.add(edge)) {
			for(HyperGraphVertex<T> vertex : edge) {
				vertex.incrementDegree();
				vertex.setNeedsChecking(true);
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public HyperGraphVertex<T> getVertexWithMaxDegree() {
		HyperGraphVertex<T> result = null;
		int maxDegree = -1;
		
		for(HyperGraphVertex<T> vertex : m_vertexSet) {
			if(vertex.degree() > maxDegree) {
				result = vertex;
				maxDegree = vertex.degree();
			}
		}
		
		return result;
	}
	
	@Override
	public Set<HyperEdge<T>> getHyperEdgesContaining(HyperGraphVertex<T> vertex) {
		HashSet<HyperEdge<T>> result = new HashSet<HyperEdge<T>>();
		
		for(HyperEdge<T> edge : m_edgeSet) {
			if(edge.contains(vertex)) {
				result.add(edge);
			}
		}
		
		return result;
	}
	
	/**
	 * @return m_edgeSet
	 */
	@Override
	public HashSet<HyperEdge<T>> edgeSet() {
		return m_edgeSet;
	}
	
	/**
	 * @return m_vertexSet
	 */
	@Override
	public Set<HyperGraphVertex<T>> vertexSet() {
		return m_vertexSet;
	}
	
	/** Remove all vertices from the graph that have a degree of 0 (i.e. they are not connected to any hyper edge). */
	public void removeZeroDegreeVertices() {
		Iterator<HyperGraphVertex<T>> iterator = m_vertexSet.iterator();
		
		int count = 0;
		while(iterator.hasNext()) {
			HyperGraphVertex<T> vertex = iterator.next();
			if(vertex.degree() == 0) {
				iterator.remove();
				count++;
			}
		}
	}

	//------------------------------------------------------------------------------------------------------------------
	// Private members.
	//------------------------------------------------------------------------------------------------------------------
	/** The vertex set of this graph. */
	private HashSet<HyperGraphVertex<T>> m_vertexSet = new HashSet<HyperGraphVertex<T>>();
	
	/** The edge set of this graph. */
	private HashSet<HyperEdge<T>> m_edgeSet = new HashSet<HyperEdge<T>>();
}
