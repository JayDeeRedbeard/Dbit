package LongRemovingBits;

import java.util.ArrayList;

import readdata.longData;

public class heuristic {
	/**Gibt eine ArrayList zurueck in der die Anzahl der Trues pro Reihe drin steht 
	 * @param tmp	Bekommt die 2D-ArrayList uebergeben	
	 * @return		Gibt eine ArrayList zurueck. 0.Spalte: Zeilennummer; 1.Spalte: Anzahl der Trues
	 */
	public static ArrayList<ArrayList<Integer>> numberOfTruesRow(ArrayList<ArrayList<Long>> tmp){
		ArrayList<ArrayList<Integer>> mem= new ArrayList<ArrayList<Integer>>();
		mem.add(new ArrayList<Integer>());
		mem.add(new ArrayList<Integer>());
		int counter=0;
		int c=0;
		for (int x=0; x<tmp.size();x++){
			if(longData.validRow.get(x)){
				for (int d =0; d<tmp.get(0).size();){
					if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(x).get(d), c)==1 && stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1 )
						counter++;
					c++;
					if(c==64){
						d++;
						c=0;
					}
				}
				if(counter>0){
					mem.get(0).add(x);
					mem.get(1).add(counter);
				}
				counter=0;
			}
		}
		return mem;
	}
	/** Loescht das D-Bit mit den meisten Trues bzw. ein D-Bit, dass mit am meisten "Trues" hat.
	 * 
	 * @param tmp 	Bekommt die 2D-ArrayList uebergeben
	 */
	public static void removeBitWithMostTrues(ArrayList<ArrayList<Long>> tmp){
		ArrayList<ArrayList<Integer>> mem= numberOfTruesRow(tmp);
		boolean counter = false;
		int a= mem.get(0).size()-1;
		int temp1, temp2;
		if(!removingBits.validRowAllFalse()){
			//Sortierung der ArrayList. Ganz vorne in der ArrayList befindet sich das D-Bit mit den meisten "Trues".
			//Beginn BubbleSort
			for(int i=0; i<mem.get(0).size(); i++){
					for(int k= i+1; k <mem.get(0).size(); k++){
							if((mem.get(1).get(k)).compareTo(mem.get(1).get(i)) < 0){
								temp1 = mem.get(1).get(i);
								temp2 = mem.get(0).get(i);
								mem.get(1).set(i, mem.get(1).get(k));
								mem.get(0).set(i, mem.get(0).get(k));
								mem.get(1).set(k,temp1);
								mem.get(0).set(k,temp2);
							}
					}
				System.out.println(mem.get(0).get(i)+ " "+mem.get(1).get(i));   
			}//BubbleSort End
			while(!counter && !mem.isEmpty() && a>=0){
				if(longData.validRow.get(mem.get(0).get(a)) ){
					LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, mem.get(0).get(a));
					counter=true;
				} else{
					a--;
				}
			}
		}
	}
	
}
