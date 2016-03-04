package hypergraph;

import java.util.HashSet;

/** A hyper edge is an edge in a hyper graph. Hyper edges can connect multiple points (therefore 'hyper' edge).
 * 
 * @author Matthias Kampmann (kampmams)
 *
 */
public class HyperEdge<T> extends HashSet<HyperGraphVertex<T>> {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Constructor. Creates an empty hyper edge. */
	public HyperEdge() {
	}
	
	/** Copy constructor. Copies all points from the given edge that are also included in the ground set (excludes all
	 * points from copy that are not in withSet).
	 * 
	 * @param copy The edge to copy.
	 * @param withSet Reference ground set. Any point in copy that is not in this set is excluded from the newly created
	 * edge.
	 */
	public HyperEdge(HyperEdge<T> copy, HashSet<HyperGraphVertex<T>> withSet) {
		for(HyperGraphVertex<T> p : withSet) {
			if(copy.contains(p)) {
				add(p);
//				p.incrementDegree();
			}
		}
	}
	
	/** Checks if this edge intersects with another edge (i.e. both this and the other edge share at least one vertex).
	 * 
	 * @param edge The edge to check with.
	 * 
	 * @return True if this edge and the given edge share at least one vertex.
	 */
	public boolean intersects(HyperEdge<T> edge) {
		for(HyperGraphVertex<T> p : this) {
			if(edge.contains(p)) {
				return true;
			}
		}
		
		return false;
	}
	
	/** Checks if this edge is a subset of the given edge, i.e. all vertices in this edge are also included in the other
	 * edge.
	 * 
	 * @param edge The edge to check.
	 * 
	 * @return True if all vertices from this edge are also included in the given edge.
	 */
	public boolean isSubsetOf(HyperEdge<T> edge) {
		return edge.containsAll(this);
	}
	
	/** Calculates and returns the intersection vertices, i.e. all vertices that are included in this edge and the
	 * given edge. If <code>intersects(edge)</code> returns <code>false</code>, this method returns an empty list.
	 * 
	 * @param edge The edge to intersect with.
	 * 
	 * @return A set of ground points that are shared between this edge and the given edge.
	 */
	public HashSet<HyperGraphVertex<T>> getIntersection(HyperEdge<T> edge) {
		HashSet<HyperGraphVertex<T>> intersection = new HashSet<HyperGraphVertex<T>>();
		for(HyperGraphVertex<T> p : this) {
			if(edge.contains(p)) {
				intersection.add(p);
			}
		}
		
		return intersection;
	}
	
	@Override
	public void clear() {
		for(HyperGraphVertex<T> element : this) {
			element.decrementDegree();
		}
		
		super.clear();
	}
	
	@Override
	public int hashCode() {
		// Only calculate the hash code for the edge if its size has changed. Otherwise, return the hash code directly.
		if(size() != m_lastSize) {
			m_lastSize = size();
			m_hashCode = 0;
			for(HyperGraphVertex<T> v : this) {
				m_hashCode += v.hashCode();
			}
		}
		
		return m_hashCode;
	}
	
	@Override
	public boolean remove(Object obj) {
		if(super.remove(obj)) {
			if(size() == 0) {
				System.out.println("Edge size == 0!");
			} else {
				// Enable the checking flag for the vertices, since their degree changed.
				for(HyperGraphVertex<T> vertex : this) {
					vertex.setNeedsChecking(true);
				}
			}
			return true;
		}
		return false;
	}

	//------------------------------------------------------------------------------------------------------------------
	// Private members.
	//------------------------------------------------------------------------------------------------------------------	
	/** UID for serialization purposes. */
	private static final long serialVersionUID = -5983514278523692623L;
	
	/** Hash code for this edge. */
	private int m_hashCode = 0;
	
	/** Stores the size of the edge since the last computation of the hash code. */
	private int m_lastSize = 0;
}
