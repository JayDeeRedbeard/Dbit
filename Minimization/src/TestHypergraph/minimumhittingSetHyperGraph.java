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
		removingBits.circuits = "/home/dj0804/workspace/Minimization/";
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
//		

//		ArrayList<DBit> tmp;
//		ArrayList<Integer> mhs ;
//		File f = new File(removingBits.circuits+"TEST/Circuits/");
//		for(File files : f.listFiles()){
//			tmp= new ArrayList<DBit>();
//			System.out.println(files.getName());
//			longData.testpfad= removingBits.circuits+"TEST/Circuits/"+files.getName();
//			longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
//			longData.results= removingBits.circuits + "TEST/results/"+files.getName();
			longData.validColumn=new ArrayList<Long>();
//			longData.validRowZwischenspeicher=new ArrayList<Boolean>();
			make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
			
			tmp=make1DatafileLong.returnbigList();
			
			LongRemovingBits.falseRowsAndColumns.RemoveFalseColumn(tmp);
//			LongRemovingBits.domColumn.dominatingColumns(tmp);
//			LongRemovingBits.falseRowsAndColumns.RemoveFalseRows(tmp);
//			LongRemovingBits.domRows.dominatingRows(tmp);
//			longData.printLongPatternwithoutEmptySpace(tmp);
			mhs=mhsHyperGraphdbits(tmp);
			
			
			for(int i=0; i<tmp.size();i++){
				if(mhs.contains(i)){
					removingBits.solution.set(tmp.get(i).getValue(),true);
				}
			}
			outputData.printData.ausgabeindatei();
			System.out.println();
			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecoveredHypergraph(tmp));	
			System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
		 	System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse(tmp));
//			System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
		
			
		}
	}
	public static ArrayList<Integer> mhsHyperGraphdbits(ArrayList<DBit> tmp) throws IOException{
		
		ArrayList<Integer> dbits = new ArrayList<>();
		for(int i =0; i<tmp.size(); i++){
			dbits.add(i);
		}
		System.out.println(dbits);
		ArrayList<ArrayList<Integer>> faults = faults(tmp);
		
		HyperGraph<Integer> graph = new IncidenceHyperGraph<Integer>();
		Set<HyperGraphVertex<Integer>> vertexSet = graph.vertexSet();
	
		// VERTICES
		
		for(int i = 0; i < dbits.size(); i++) {
			HyperGraphVertex<Integer> v = new HyperGraphVertex<Integer>(dbits.get(i));
			vertexSet.add(v);
		}
//		int counter=0;
		// EDGES
//		System.out.println("faults.size():"+faults.size());
		for(int i = 0; i < faults.size(); i++) {
			HyperEdge<Integer> edge = new HyperEdge<Integer>();
			for(int j = 0; j < faults.get(i).size(); j++) {	
				for(HyperGraphVertex<Integer> v : vertexSet) {
//					System.out.println("Knoten: "+v.value());
					if((int)v.value() == faults.get(i).get(j) ) {
						edge.add(v);
//						System.out.println("Wurde gefunden");
//						counter++;
						break;
					} 
//					System.out.println("(int)v.value() == faults.get(i).get(j)"+ ((int)v.value() + " "+faults.get(i).get(j)) + " j="+ j);
				}
//				System.out.println(counter);
//				if(counter==0){
//					System.out.println("nicht gefunden!!!");
//					System.out.println(faults.get(i).get(j));
//				}
//				counter=0;
				
			}
			if(edge.size() != faults.get(i).size()) {
				System.out.println("Edgesize: "+edge.size());
				System.err.println("Edge not built correctly");
			}
			
			if(!edge.isEmpty()) {
				graph.addEdge(edge);
			}
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
		int counttrueA=0;
//		boolean entprell=false;
		System.out.println("# D-Bits: " + tmp.size());
		//Gehe jede Spalte in der Liste durch und kontrolliere ob die Spalte Valid ist
		for (int d = 0; d < tmp.get(0).getList().size();) {
			if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1){
				ArrayList<Integer> f = new ArrayList<>();
				//Suche in jeder Spalte nach "trues"
				for (int k = 0; k < tmp.size(); k++) {
					if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1) {
//						b.get(counter).add(k);
						f.add(k);
						
						counttrueA++;
						if (readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c) == counttrueA) {
							break;
						}
					}
				}
				//Nur wenn die vorherige Int.List leer ist soll zur n�chsten liste gegangen werden
//				if (!b.get(counter).isEmpty()){
//					counter++;
//				}
				counttrueA=0;
				System.out.println("dbits for fault " + (d * 64 + c) + ": " + f);
				if(!f.isEmpty()) {
					b.add(f);
				}
			}
			c++;
			if (c == 64) {
				d++;
				c = 0;
			}
		}
		return b;
	}
}
