package TestHypergraph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import hypergraph.*;

public class minimumhittingSetHyperGraph {

	public static void main(String[] args) {
		
	}
	public static ArrayList<Integer> mhsHyperGraphdbits(ArrayList<ArrayList<Long>> tmp) throws IOException{
		
		
		ArrayList<Integer> dbits = new ArrayList<>();
		for(int i =0; i<tmp.size(); i++){
			dbits.add(i);
		}
		ArrayList<ArrayList<Integer>> faults = readdataHyperGraph(tmp);
		
		HyperGraph<Integer> graph = new IncidenceHyperGraph<Integer>();
		Set<HyperGraphVertex<Integer>> vertexSet = graph.vertexSet();
	
		// VERTICES
		for(int i = 0; i < dbits.size(); i++) {
			HyperGraphVertex<Integer> v = new HyperGraphVertex<Integer>(dbits.get(i));
			vertexSet.add(v);
		}
	
		// EDGES
		for(int i = 0; i < faults.size(); i++) {
			HyperEdge<Integer> edge = new HyperEdge<>();
			for(int j = 0; j < faults.get(i).size(); j++) {
				for(HyperGraphVertex<Integer> v : vertexSet) {
					if(v.value() == faults.get(i).get(j) ) {
						edge.add(v);
					}
				}
			}
			
			graph.addEdge(edge);
		}
	
		try {
			ArrayList<Integer> hittingSet = MinimumHittingSet.mhs(graph);
			System.out.println(hittingSet);
			return hittingSet;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log4j zum Loggen+
		return null;
	}
	public static ArrayList<ArrayList<Integer>> readdataHyperGraph(ArrayList<ArrayList<Long>> tmp)throws IOException{
		
		ArrayList<ArrayList<Integer>> b= new ArrayList<ArrayList<Integer>>();
		int c=0;
		for (int d=0; d<tmp.get(0).size();){
			b.add(new ArrayList<>());
			for (int k=0; k<tmp.size();k++){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1){
					b.get(d).add(k);
				}
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
		}
		return b;
	}
}
