package TestHypergraph;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import LongRemovingBits.removingBits;
import hypergraph.*;
import readdata.longData;
import readdata.make1DatafileLong;

public class minimumhittingSetHyperGraph {

	public static void main(String[] args) throws IOException {
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
		ArrayList<ArrayList<Long>> tmp; 
		ArrayList<Integer> mhs ;
		File f = new File(removingBits.circuits+"Schaltungen/");
		for(File files : f.listFiles()){
			tmp= new ArrayList<ArrayList<Long>>();
			mhs=new ArrayList<Integer>();
			System.out.println(files.getName());
			longData.testpfad= removingBits.circuits+"Schaltungen/"+files.getName();
			longData.protokoll= removingBits.circuits + "logs/"+files.getName();
			longData.results= removingBits.circuits + "results/"+files.getName();
//		
//		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
//		ArrayList<ArrayList<Long>> tmp;
//		ArrayList<Integer> mhs ;
//		File f = new File(removingBits.circuits+"TEST/Circuits/");
//		for(File files : f.listFiles()){
//			tmp= new ArrayList<ArrayList<Long>>();
//			System.out.println(files.getName());
//			longData.testpfad= removingBits.circuits+"TEST/Circuits/"+files.getName();
//			longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
//			longData.results= removingBits.circuits + "TEST/results/"+files.getName();
			longData.validRow=new ArrayList<Boolean>();
			longData.validColumn=new ArrayList<Long>();
			longData.validRowZwischenspeicher=new ArrayList<Boolean>();
			make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
			
			tmp=make1DatafileLong.returnbigList();

			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
			
//			LongRemovingBits.falseRowsAndColumns.RemoveFalseColumn(tmp);
//			LongRemovingBits.domColumn.dominatingColumns(tmp);
//			LongRemovingBits.falseRowsAndColumns.RemoveFalseRows(tmp);
//			longData.printLongPatternwithoutEmptySpace(tmp);
			mhs=mhsHyperGraphdbits(tmp);
			
			outputData.printData.ausgabeindateimhsHyperGraph(mhs);
			
			System.out.println();
			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
			System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
			System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse());
			System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
		
			
		}
	}
	public static ArrayList<Integer> mhsHyperGraphdbits(ArrayList<ArrayList<Long>> tmp) throws IOException{
		ArrayList<Integer> dbits = new ArrayList<>();
		for(int i =0; i<tmp.size(); i++){
			dbits.add(i);
		}
		ArrayList<ArrayList<Integer>> faults = readdataHyperGraph(tmp);
		
		System.out.println(faults);
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
		int counter=0;
		for (int d=0; d<tmp.get(0).size();){
			if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1){
			b.add(new ArrayList<>());
			for (int k=0; k<tmp.size();k++){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1){
					b.get(counter).add(k);
				}
			}
			if(!b.get(counter).isEmpty())
				counter++;
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
