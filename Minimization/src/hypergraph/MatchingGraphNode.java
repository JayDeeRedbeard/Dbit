package hypergraph;


/** Node for the {@link UndirectedGraph} class. Stores the edge it represents plus an unique identifier.
 * 
 * @author Matthias Kampmann (kampmams)
 *
 */
public class MatchingGraphNode<T> {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Constructor.
	 * 
	 * @param id The unique identifier of this vertex.
	 * @param edge The edge that is represented by this vertex.
	 */
	public MatchingGraphNode(int id, HyperEdge<T> edge) {
		m_edge = edge;
		m_id = id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MatchingGraphNode) {
			MatchingGraphNode<?> n = (MatchingGraphNode<?>)obj;
			
			if(m_id != n.m_id) {
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return m_edge.hashCode();
	}
	
	@Override
	public String toString() {
		return "MatchingGraphNode(" + Integer.toString(m_id) + ")";
	}
	
	/** Gets the stored hyperedge.
	 * 
	 * @return m_edge
	 */
	public HyperEdge<T> edge() {
		return m_edge;
	}

	//------------------------------------------------------------------------------------------------------------------
	// Private members.
	//------------------------------------------------------------------------------------------------------------------
	/** The hyperedge that is represented by this vertex. */
	private HyperEdge<T> m_edge;
	
	/** Unique identifier for this vertex. */
	private int m_id;
}
