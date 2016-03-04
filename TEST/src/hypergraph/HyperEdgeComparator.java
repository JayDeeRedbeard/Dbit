package hypergraph;

import java.util.Comparator;

/** Implements hyper edge comparison by looking at their respective sizes.
 * 
 * @author Matthias Kampmann
 *
 * @param <T> The vertex type the hyper edges store.
 */
public class HyperEdgeComparator<T> implements Comparator<HyperEdge<T>> {

	@Override
	public int compare(HyperEdge<T> arg0, HyperEdge<T> arg1) {
		if(arg0.size() > arg1.size()) {
			return 1;
		} else if(arg1.size() > arg0.size()) {
			return -1;
		}
		return 0;
	}

}
