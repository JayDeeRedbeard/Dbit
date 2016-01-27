package TestHypergraph;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import LongRemovingBits.removingBits;
import hypergraph.*;
import readdata.DBit;
import readdata.longData;
import readdata.make1DatafileLong;

public class minimumhittingSetHyperGraph {

	public static void main(String[] args) throws IOException {
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
		ArrayList<DBit> tmp; 
		ArrayList<Integer> mhs ;
		File f = new File(removingBits.circuits+"Schaltungen/");
		for(File files : f.listFiles()){
			tmp= new ArrayList<DBit>();
			mhs=new ArrayList<Integer>();
			System.out.println(files.getName());
			longData.testpfad= removingBits.circuits+"Schaltungen/"+files.getName();
			longData.protokoll= removingBits.circuits + "logs/"+files.getName();
			longData.results= removingBits.circuits + "results/"+files.getName();
		
//		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
//		ArrayList<DBit> tmp;
//		ArrayList<Integer> mhs ;
//		File f = new File(removingBits.circuits+"TEST/Circuits/");
//		for(File files : f.listFiles()){
//			tmp= new ArrayList<DBit>();
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
			
			LongRemovingBits.falseRowsAndColumns.RemoveFalseColumn(tmp);
//			LongRemovingBits.domColumn.dominatingColumns(tmp);
//			LongRemovingBits.falseRowsAndColumns.RemoveFalseRows(tmp);
//			LongRemovingBits.domRows.dominatingRows(tmp);
			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
//			longData.printLongPatternwithoutEmptySpace(tmp);
			mhs=mhsHyperGraphdbits(tmp);
//			mhs.remove(mhs.size()-1);
//			outputData.printData.ausgabeindateimhsHyperGraph(mhs);
			
			for(int i=0; i<removingBits.solution.size();i++){
				if(mhs.contains(i)){
					removingBits.solution.set(i,true);
				}
			}
			outputData.printData.ausgabeindatei();
			System.out.println();
			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecoveredHypergraph(tmp));	
			System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
		 	System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse(tmp));
			System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
		
			
		}
	}
	public static ArrayList<Integer> mhsHyperGraphdbits(ArrayList<DBit> tmp) throws IOException{
		
		ArrayList<Integer> dbits = new ArrayList<>();
		for(int i =0; i<tmp.size(); i++){
			dbits.add(i);
		}
		ArrayList<ArrayList<Integer>> faults = faults(tmp);
		
		System.out.println(faults);
		HyperGraph<Integer> graph = new IncidenceHyperGraph<Integer>();
		Set<HyperGraphVertex<Integer>> vertexSet = graph.vertexSet();
	
		// VERTICES
		for(int i = 0; i < dbits.size(); i++) {
//			HyperGraphVertex<Integer> v = new HyperGraphVertex<Integer>(dbits.get(i));
			vertexSet.add(new HyperGraphVertex<Integer>(dbits.get(i)));
		}
	
		// EDGES
		for(int i = 0; i < faults.size(); i++) {
			HyperEdge<Integer> edge = new HyperEdge<Integer>();
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
	public static ArrayList<ArrayList<Integer>> faults(ArrayList<DBit> tmp)throws IOException{
		
		ArrayList<ArrayList<Integer>> b= new ArrayList<ArrayList<Integer>>();
		int c=0;
		int counter=0;
		int row=0;
		int counttrueA=0;
		boolean entprell=false;
//		b.add(new ArrayList<>());
		for (int d = 0; d < tmp.get(0).getList().size();) {
			if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1){
				b.add(new ArrayList<>());
				for (int k = 0; k < tmp.size() && !entprell; k++){
					if (longData.validRow.get(k)){
						if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1) {
							b.get(counter).add(row);
							counttrueA++;
							if(readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)==counttrueA){
								entprell=true;
							}
						}
						row++;
					}
				}
				entprell=false;
				if (!b.get(counter).isEmpty()){
					counter++;
				}
			}
			row = 0;
			c++;
			if (c == 64) {
				d++;
				c = 0;
			}
		}
		return b;
	}
}
