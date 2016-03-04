package hypergraph;

/** A vertex in the hyper graph. It is characterized by its stored element and the degree, i.e. the number of
 * {@link HyperEdge} objects that contain this vertex. It also implements a simple checking mechanism, i.e. a flag
 * useful if an algorithm needs to check the vertices of a hypergraph.
 * 
 * @note Equivalence checking between two HyperGraphVertex objects is equal to checking equivalence between the stored
 * objects (i.e. <code>v1.equals(v2)</code> yields the same result as <code>v1.value().equals(v2.value())</code>).
 * 
 * @author Matthias Kampmann (kampmams)
 */
public class HyperGraphVertex<T> {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Constructor.
	 * 
	 * @param value The value to store.
	 */
	public HyperGraphVertex(T value) {
		m_value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof HyperGraphVertex) {
			HyperGraphVertex<?> p = (HyperGraphVertex<?>)obj;
			
			if(!m_value.equals(p.m_value)) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return m_value.hashCode();
	}
	
	@Override
	public String toString() {
		return m_value.toString() + "(" + m_degree + ")";
	}
	
	/** Gets the stored vertex value.
	 * 
	 * @return m_value
	 */
	public T value() {
		return m_value;
	}
	
	/** Gets the degree of this point.
	 * 
	 * @return m_degree
	 */
	public int degree() {
		return m_degree;
	}
	
	/** Checks whether the vertex should be checked during the course of some algorithm. 
	 * 
	 * @return m_needsChecking
	 */
	public boolean needsChecking() {
		return m_needsChecking;
	}
	
	/** Sets whether this object should be checked during the course of some algorithm.
	 * 
	 * @param value True, if the vertex should be checked.
	 */
	public void setNeedsChecking(boolean value) {
		m_needsChecking = value;
	}
	
	/** Increments the degree of this point. */
	public void incrementDegree() {
		m_degree++;
	}
	
	/** Decrements the degree of this point. */
	public void decrementDegree() {
		m_degree--;
	}

	//------------------------------------------------------------------------------------------------------------------
	// Protected members.
	//------------------------------------------------------------------------------------------------------------------
	
	/** Sets the degree to the specified value.
	 * 
	 * @param degree The new degree of the vertex.
	 */
	protected void setDegree(int degree) {
		m_degree = degree;
	}

	//------------------------------------------------------------------------------------------------------------------
	// Private members.
	//------------------------------------------------------------------------------------------------------------------
	/** The stored point value. */
	private T m_value;
	
	/** If this point is used in a hyper graph, the degree denotes the number of hyper edges that contain this point. */
	private int m_degree = 0;
	
	/** Flag indicating whether the vertex should be checked. */
	private boolean m_needsChecking = true;
}
