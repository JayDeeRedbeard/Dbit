package hypergraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/** Provides the minimum hitting set algorithm based on hypergraphs.
 * 
 * @author Matthias Kampmann
 *
 */
public class MinimumHittingSet {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Implements the minimum hitting set search for the given graph. This is a recursive function which tries to
	 * minimize the graph and then searching for a hitting set on the minimized graph. If no minimization is possible,
	 * a branching rule is used to check whether a specific vertex should be included in the hitting set.
	 * 
	 * @param graph The hypergraph to perform the algorithm on.
	 * 
	 * @note Although this algorithm is fast, it still has exponential runtime, keep this in mind for extremely large
	 * problem instances.
	 * 
	 * @return The optimal solution for the minimum hitting set represented as the given graph.
	 */
	public static <T> ArrayList<T> mhs(HyperGraph<T> graph) throws Exception {
		Stack<HyperGraph<T>> stack = new Stack<HyperGraph<T>>();
		stack.push(graph);
		
		boolean checkVertexDominance = true;
		ArrayList<T> hitSet = new ArrayList<T>();
		
		// Try to minimize the graph in an iterative loop as much as possible. Only use recursion on the branching rule.
		while(!stack.isEmpty()) {
			HyperGraph<T> current = stack.pop();
		
			// Abortion rules.
			if(current.vertexSet().size() == 0) {
				break;
			} else if(current.vertexSet().size() == 1) {
				for(HyperGraphVertex<T> vertex : current.vertexSet()) {
					hitSet.add(vertex.value());
					break;
				}
				
				break;
			} else if(current.edgeSet().size() == 0) {
				break;
			} else if(current.edgeSet().size() == 1) {
				for(HyperEdge<T> edge : current.edgeSet()) {
					for(HyperGraphVertex<T> vertex : edge) {
						hitSet.add(vertex.value());
						break;
					}
					break;
				}
				
				break;
			} else if(current.maxDegree() == 2) {
				HyperGraphVertex<T> singlePoint = applyUnitHyperEdgeRules(current);
				while(null != singlePoint) {
					current.removeEdgesContaining(singlePoint);
					current.removeVertex(singlePoint);
					
					hitSet.add(singlePoint.value());
					singlePoint = applyUnitHyperEdgeRules(current);
				}
				
				if(current.edgeSet().size() > 0) {
//					s_log.info("Starting edge cover search...");
					hitSet.addAll(mhsByMaxMatching(current));
				}
				
				break;
			}
			
			// Apply vertex domination rule.
			if(checkVertexDominance) {
				if(applyVertexDominationRules(current, true)) {
					stack.push(current);
					
					// Do not check for vertex dominance on the next run, since nothing has changed in the graph in the
					// meantime. Instead, directly check for hyperedge dominance.
					checkVertexDominance = false;
					continue;
				}
			}
			
			// In the next iteration, check for vertex dominance again, since the following methods possibly modify the
			// graph.
			checkVertexDominance = true;
			
			// Apply hyper edge domination rule.
			if(applyHyperEdgeDominationRules(current)) {
//			if(HyperGraphHelpers.applyAgressiveHyperEdgeDominationRules(current)) {
				stack.push(current);
				continue;
			}

			// Apply unit hyperedge rule.
			HyperGraphVertex<T> singlePoint = applyUnitHyperEdgeRules(current);
			if(null != singlePoint) {
				current.removeEdgesContaining(singlePoint);
				current.removeVertex(singlePoint);
				
				hitSet.add(singlePoint.value());

				stack.push(current);
				continue;
			}
			
			// Apply degree 2 branching rule
			HyperGraphVertex<T> toInclude = applyDegree2BranchingRule(current);
			if(null != toInclude) {
				current.removeVertex(toInclude);
				current.removeEdgesContaining(toInclude);
				
				hitSet.add(toInclude.value());
				
				stack.push(current);
				continue;
			}
			
			// Apply branching heuristic. Here, we fetch the vertex with the largest degree (i.e. the point that
			// currently hits the most intervals) and then ask whether it is a good idea to include the point in the
			// hitting set. To answer this, we split this problem into two subproblems. The first is the graph without
			// the point and the second one is the graph that would be created if we choose the point to be in our
			// hitting set. Then we calculate for each graph the hitting set and decide based on the size of each set
			// which solution we should take.
//			s_log.info("Applying branching heuristic.");
			HyperGraphVertex<T> pointWithMaxDegree = graph.getVertexWithMaxDegree();
			if(null == pointWithMaxDegree) {
				throw new NullPointerException();
			}
			
//			s_log.info("Branching on vertex {}", pointWithMaxDegree);
			HyperGraph<T> g1 = current.cloneAndExclude(pointWithMaxDegree);
			HyperGraph<T> g2 = current.cloneExcludeAndRemoveEdges(pointWithMaxDegree);
			
			// Recursive branching.
			ArrayList<T> mhs1 = mhs(g1);
			ArrayList<T> mhs2 = mhs(g2);
			
			if(mhs1.size() < (mhs2.size() + 1)) {
				hitSet.addAll(mhs1);
			} else {
				hitSet.addAll(mhs2);
				hitSet.add(pointWithMaxDegree.value());
			}
		}
		
		return hitSet;
	}
	
	/** Returns a hitting set for a hypergraph H with maximum degree 2. This is done by firstly converting the into
	 * another "classic" graph G with the following properties:
	 * <ul>
	 * 	<li> Every hyperedge of H is a vertex in G.
	 *  <li> If two hyperedges in H intersect, the corresponding vertices in G are connected by an edge. 
	 * </ul>
	 * 
	 * Then, a minimum edge cover for G is constructed, which is equivalent to the minimum hitting set for H. To do
	 * this, a maximum matching is constructed by using Edmonds's algorithm (which runs in polynomial time) and then (if
	 * the matching is not perfect) the matching is expanded until every vertex in G is connected to at least one edge.
	 * 
	 * @param graph The hypergraph for which the hitting set should be computed. The degree of the graph must be <= 2.
	 * 
	 * @return A minimum hitting set for the given hypergraph.
	 * 
	 * @see EdmondsMatching
	 */
	private static <T> ArrayList<T> mhsByMaxMatching(HyperGraph<T> graph) {
		// Convert the hypergraph to the matching graph (on which the matching needs to be found) such that a hyperedge
		// in the graph becomes vertex in our matching graph and two vertices in the matching graph are connected if
		// the corresponding hyperedges share vertices.
		// This way, we reduce the hypergraph to a "normal" graph and can apply algorithms to find the maximum matching
		// on it.
		UndirectedGraph<MatchingGraphNode<T>> convertedGraph = new UndirectedGraph<MatchingGraphNode<T>>();
		ArrayList<T> result = new ArrayList<T>();
		
		// Add the vertices. We convert the hash set to an array list in order to obtain indices for the edges.
		ArrayList<HyperEdge<T>> edgeSet = new ArrayList<HyperEdge<T>>();
		edgeSet.addAll(graph.edgeSet());
		
		if(edgeSet.size() == 1) {
			// There is only a single edge in the graph. Return the first vertex.
			HyperEdge<T> edge = edgeSet.get(0);
			
			for(HyperGraphVertex<T> vertex : edge) {
				result.add(vertex.value());
				break;
			}

//			s_log.info("Max matching: {} points", result.size());
			return result;
		}
		
		for(int idx = 0; idx < edgeSet.size(); idx++) {
			HyperEdge<T> edge = edgeSet.get(idx);
			boolean added = false;
			
			// Only add edges, if they contain nodes with degree 2.
			for(HyperGraphVertex<T> vertex : edge) {
				if(vertex.degree() == 2) {
					MatchingGraphNode<T> node = new MatchingGraphNode<T>(idx, edge);
					convertedGraph.addNode(node);
					added = true;
					break;
				}
			}
			
			if(!added) {
				// If the edge only contains vertices of degree 1, choose one of the vertices in order to cover that
				// edge.
				for(HyperGraphVertex<T> vertex : edge) {
					result.add(vertex.value());
					break;
				}
			}
		}
		
		// Connect the vertices. Two vertices are connected if they have a common vertex in the hypergraph of degree 2
		// (i.e. the hypergraph vertex is contained in exactly the two hyperedges that should be connected in the
		// converted graph).
		for(MatchingGraphNode<T> vertex : convertedGraph) {
			HyperEdge<T> e = vertex.edge();
			
			for(HyperGraphVertex<T> v : e) {
				if(v.degree() == 2) {
					// Find the hyperedge that is also connected to v. Then create an edge between the corresponding
					// edges in the converted graph for the matching.
					ArrayList<HyperEdge<T>> edges = new ArrayList<HyperEdge<T>>();
					edges.addAll(graph.getHyperEdgesContaining(v));
					for(int idx = 0; idx < edgeSet.size(); idx++) {
						HyperEdge<T> e2 = edgeSet.get(idx);
						if(e2.equals(e)) {
							continue;
						} else if(edges.contains(e2)) {
							// This is the target edge. Note that addEdge will be called twice (once for vertex and
							// once for toConnect), however this is handled by the UndirectedGraph class since it does
							// not allow multiple equal edges.
							MatchingGraphNode<T> toConnect = new MatchingGraphNode<T>(idx, e2);
							convertedGraph.addEdge(vertex, toConnect);
						}
					}
				}
			}
		}

		ArrayList<HyperGraphVertex<T>> hittingSet = new ArrayList<HyperGraphVertex<T>>();
		if(!convertedGraph.isEmpty()) {
			UndirectedGraph<MatchingGraphNode<T>> matching = EdmondsMatching.maximumMatching(convertedGraph);
		
			// Check if the matching is also a valid edge cover. If not, extend the matching (possibly destroying it)
			// until all nodes in the matching graph are covered by at least one edge.
			ArrayList<MatchingGraphNode<T>> uncoveredNodes = new ArrayList<MatchingGraphNode<T>>();
			for(MatchingGraphNode<T> node : matching) {
				Set<MatchingGraphNode<T>> edges = matching.edgesFrom(node);
				if(0 == edges.size()) {
					uncoveredNodes.add(node);
				}
			}
	
			if(0 != uncoveredNodes.size()) {
//				s_log.info("DEBUG: Completing edge cover...");
				// We have found a maximum matching, but the matching is not perfect. Therefore, some nodes are not
				// matched (i.e. the resulting hitting set does not hit all intervals). We need to extend our matching
				// to a smallest edge cover, which solves our problem.
				while(!uncoveredNodes.isEmpty()) {
					MatchingGraphNode<T> toConnect = uncoveredNodes.get(0);
					boolean connected = false;
					
					// First, check if the unconnected node is connected to some other unconnected node in the original
					// graph.
					// FIXME: This should not happen since in this case, both nodes would be connected by the matching
					// algorithm?
					for(int idx = 1; idx < uncoveredNodes.size(); idx++) {
						MatchingGraphNode<T> other = uncoveredNodes.get(idx);
						if(convertedGraph.edgeExists(toConnect, other)) {
							matching.addEdge(toConnect, other);
							uncoveredNodes.remove(idx);
							uncoveredNodes.remove(0);
							connected = true;
							break;
						}
					}
					
					if(connected) {
						continue;
					}
					
					// We need to connect this node to some node in the matching that shares a connection with this node
					// in the original graph.					
					for(MatchingGraphNode<T> matchedNode : matching) {
						if(uncoveredNodes.contains(matchedNode)) {
							continue;
						}
						
						if(convertedGraph.edgeExists(toConnect, matchedNode)) {
							matching.addEdge(toConnect, matchedNode);
							uncoveredNodes.remove(toConnect);
							break;
						}
					}
				}
			}
			
			// Get the edges.
//			s_log.info("DEBUG: Fetching hitting set from edge cover...");
			HashSet<MatchingGraphNode<T>> coveredNodes = new HashSet<MatchingGraphNode<T>>();
			for(MatchingGraphNode<T> node : matching) {
				if(coveredNodes.contains(node)) {
					continue;
				}
				HyperEdge<T> first = node.edge();
				Set<MatchingGraphNode<T>> edges = matching.edgesFrom(node);
				
				for(MatchingGraphNode<T> other : edges) {
					HyperEdge<T> second = other.edge();
					
					HashSet<HyperGraphVertex<T>> intersection = first.getIntersection(second);
					
					// Since it does not matter which point we take, let's take the first one.
					for(HyperGraphVertex<T> p : intersection) {
						if(!hittingSet.contains(p)) {
							hittingSet.add(p);
							break;
						}
					}
					
					coveredNodes.add(other);
				}
				
				coveredNodes.add(node);
			}
		}
		
		// Add the degree-1 edges
		for(HyperEdge<T> edge : graph.edgeSet()) {
			if(edge.size() == 1) {
				for(HyperGraphVertex<T> vertex : edge) {
					if(!hittingSet.contains(vertex)) {
						hittingSet.add(vertex);
					}
				}
			}
		}
		
		for(HyperGraphVertex<T> point : hittingSet) {
			result.add(point.value());
		}
		
//		s_log.info("Max matching: {} points", result.size());
		return result;
	}
	
	/** Check for each vertex in the hypergraph H=(V,E) if it is dominated by some other vertex. Formally: Let u and v
	 * be two different vertices in V. If every hyperedge e in E that contains u also contains v, u is said to be
	 * dominated by v. 
	 * <p>
	 * In the context of the hitting set problem, vertex domination means the following. Let u and v be some points that
	 * hit some sets. v dominates u if v hits all sets that u hits, plus some more. In that case, it would not make
	 * sense to choose u as a hit point, since v will hit more sets, possibly leading to a smaller hitting set.
	 * <p>
	 * If a vertex v dominates some vertex u, u is removed from H.
	 * 
	 * @param graph The graph to check. If dominance rules are applicable, this object is manipulated.
	 * @param aggressive True if aggressive checking should be used, i.e. the algorithm does not stop after the first
	 * dominance was found.
	 * 
	 * @return True, if vertex domination was found (then, graph is changed).
	 */
	public static <T> boolean applyVertexDominationRules(HyperGraph<T> graph, boolean aggressive) {
		boolean foundDominance = false;
		
		Set<HyperGraphVertex<T>> vertexSet = new HashSet<HyperGraphVertex<T>>();
		vertexSet.addAll(graph.vertexSet());
		Iterator<HyperGraphVertex<T>> iterator = vertexSet.iterator();
		HyperEdgeComparator<T> comparator = new HyperEdgeComparator<T>();
		
		while(iterator.hasNext()) {
			// We check here whether vertex1 is dominated by some other vertex. This is easier to check than to look for
			// some vertex that is dominated by vertex1.
			HyperGraphVertex<T> vertex1 = iterator.next();
//			if(!vertex1.needsChecking()) {
//				continue;
//			} else if(vertex1.degree() == 0) {
			
			ArrayList<HyperEdge<T>> edges = new ArrayList<HyperEdge<T>>();
			edges.addAll(graph.getHyperEdgesContaining(vertex1));
			Collections.sort(edges, comparator);
			
			if(0 == edges.size()) {
				continue;
			}
			
			// For vertex domination it actually suffices to check the first edge of the edge set. If there is a vertex
			// in that edge (that is not vertex1) that is also included in every other edge, that vertex dominates
			// vertex1. If no such vertex is found, vertex1 cannot be dominated by any vertex since that vertex must
			// have been included in the first vertex already.
			HyperEdge<T> toCheck = edges.get(0);
			
			boolean dominates = true;
			if(toCheck.size() == 1) {
				dominates = false;
			} else {
				for(HyperGraphVertex<T> vertex2 : toCheck) {
					if(vertex2.degree() < vertex1.degree()) {
						// vertex2 cannot dominate vertex1 since it is contained in fewer edges than vertex1.
						dominates = false;
						continue;
					} else if(vertex2.equals(vertex1)) {
						continue;
					}
					
					dominates = true;
					for(int jdx = 1; jdx < edges.size(); jdx++) {
						if(!edges.get(jdx).contains(vertex2)) {
							dominates = false;
							break;
						}
					}
					
					if(dominates) {
						foundDominance = true;
						break;
					}
				}
			}
			
			if(dominates) {
				// Some vertex dominates vertex1 -> remove vertex1.
				foundDominance = true;
				graph.removeVertex(vertex1);
			} else {
				vertex1.setNeedsChecking(false);
			}
			
			if(!aggressive && foundDominance) {
				break;
			}
		}
		
		return foundDominance;
	}
	
	/** Check for each hyperedge in the hypergraph H=(V,E) if it is dominated by some other edge. Formally: Let e1 and 
	 * e2 be two different hyperedges in E. If e1 is a subset of e2, then e2 dominates e1.
	 * <p>
	 * In the context of the hitting set problem, hyperedge domination means the following. Let e1 and e2 be some sets
	 * that need to be hit. e2 dominates e1 if e2 contains all elements that e1 contains plus some more. In that case,
	 * Every point that hits e1 will also hit e2, but not vice versa. Therefore, e2 can be neglected in the search for
	 * a minimum hitting set.
	 * <p>
	 * If an hyperedge e2 dominates some hyperedge e1, e2 is removed from H. This function will stop on the first found
	 * dominance and return true. This is different from vertex domination checking, since removing a hyperedge from the
	 * graph might cause vertices to dominate other vertices (which they did not do before).
	 * 
	 * @param graph The graph to check.
	 * 
	 * @return True, if hyperedge dominance was found.
	 */
	public static <T> boolean applyHyperEdgeDominationRules(HyperGraph<T> graph) {
		boolean foundDominance = false;
		
		ArrayList<HyperEdge<T>> list = new ArrayList<HyperEdge<T>>();
		list.addAll(graph.edgeSet());
		
		// To avoid checking combinations multiple times, we use two indices to determine the elements we should check
		// next. Since we remove elements from list if dominance was detected, we need to update our indices
		// accordingly.
		int idx = 0;
		while(idx < list.size()) {
			HyperEdge<T> edge1 = list.get(idx);			
			if(edge1.size() == 1) {
				idx++;
				continue;
			}
			
			int jdx = 0;
			while(jdx < list.size()) {
				if(idx == jdx) {
					jdx++;
					continue;
				}

				HyperEdge<T> edge2 = list.get(jdx);
				
				if(edge1.size() > edge2.size()) {
					// edge2 cannot dominate edge1 since it contains less vertices.
					jdx++;
					continue;
				}
				
				if(edge2.containsAll(edge1)) {
					// edge2 dominates edge1 -> remove edge2 (every point that hits the smaller set edge1 also hits the
					// larger set edge2).
					graph.removeEdge(edge2);
					foundDominance = true;
					
					if(graph.maxDegree() == 2) {
						break;
					}
					
//					list.remove(jdx);
					
					// Adjust indices.
//					if(jdx < idx) {
//						idx--;
//					}
//					jdx--;
					
					break;
				}
				
				jdx++;
			}
			
			if(foundDominance) {
				break;
			}
			
			idx++;
		}
		
		return foundDominance;
	}
	
	/** Applies the unit hyperedge rule to the graph. This means that the graph is checked whether hyperedges of degree
	 * 1 exist. In that case, the element covered by this hyperedge must be contained in the hitting set, otherwise the
	 * set induced by the hyperedge would not be hit.
	 * 
	 * @param graph The graph to check.
	 * 
	 * @return The first vertex for which a hyperedge exists that only contains this vertex.
	 */
	public static <T> HyperGraphVertex<T> applyUnitHyperEdgeRules(HyperGraph<T> graph) {
		for(HyperEdge<T> edge : graph.edgeSet()) {			
			if(edge.size() == 1) {
				for(HyperGraphVertex<T> p : edge) {
					return p;
				}
			}
		}
		
		return null;
	}
	
	/** This applies an advanced branching rule to the minimum hitting set algorithm. For each vertex v in the graph,
	 * the delta2-number is calculated. This counts the number of hyperedges that contain v and are of degree 2. Next,
	 * the degree-2-neighborset B of v is calculated. This is the set of all vertices that share a degree-2-hyperedge
	 * with v (including v). It holds <code>|B| = delta2 + 1</code>.
	 * <p>
	 * Now we check, whether it is wise to include v in the hitting set or not. If we include v in the set, we would hit
	 * at least delta2 sets. If not, we must choose all vertices from <code>B \ {v}</code> to hit the delta2 sets. But
	 * then, we would hit m more sets, because these other vertices might hit sets that do not contain v. 
	 * <p>
	 * This boils down to the question whether m (the benefit of not choosing v) is smaller than delta2. If it is, it
	 * is preferred to not include v in the hitting set in order to minimize it.
	 * <p>
	 * This function therefore checks for each vertex in the set whether this inequation holds. If it holds for some
	 * vertex (i.e. we should include that vertex in the hitting set), that vertex is returned. Otherwise, null is
	 * returned.
	 * 
	 * @param graph The graph to check.
	 * 
	 * @return Either a vertex to include in the hitting set or null if the branching rule does not apply to any vertex
	 * in the set.
	 */
	public static <T> HyperGraphVertex<T> applyDegree2BranchingRule(HyperGraph<T> graph) {
		HyperGraphVertex<T> point = null;
		
		for(HyperGraphVertex<T> p : graph.vertexSet()) {
			int delta2 = graph.numEdges(p, 2);
			if(0 == delta2) {
				continue;
			}
			
			Set<HyperGraphVertex<T>> neighborhood = graph.neighborSet(p, 2);
			neighborhood.remove(p);
			
			// Find the sets hit by the B\{v} vertices and get m.
			ArrayList<HyperEdge<T>> targetEdgeSet = new ArrayList<HyperEdge<T>>();
			for(HyperGraphVertex<T> toCheck : neighborhood) {
				Set<HyperEdge<T>> edgeSet = graph.getHyperEdgesContaining(toCheck);
				for(HyperEdge<T> e : edgeSet) {
//					if(!e.contains(p) && !targetEdgeSet.contains(e)) {
						targetEdgeSet.add(e);
//					}
				}
			}
			
			if(targetEdgeSet.size() < delta2) {
				point = p;
				break;
			}
		}
		
		return point;
	}
}
