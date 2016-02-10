package LongRemovingBits;

import java.util.ArrayList;

import readdata.DBit;

public class heuristic {
	/** Loescht das D-Bit mit den meisten Trues bzw. ein D-Bit, dass mit am meisten "Trues" hat.
	 * 
	 * @param tmp 	Bekommt die 2D-ArrayList uebergeben
	 */
	public static void removeBitWithMostTrues(ArrayList<DBit> tmp){
		ArrayList<ArrayList<Integer>> mem= new ArrayList<ArrayList<Integer>>();
		mem.add(new ArrayList<Integer>());
		mem.add(new ArrayList<Integer>());
		for(int i =0; i<tmp.size(); i++){
			mem.get(0).add(i);
			mem.get(1).add(readdata.make1DatafileLong.numberOfTruesInRow.get(i));
		}
		boolean counter = false;
		int a= mem.get(0).size()-1;
		int temp1, temp2;
		if(!removingBits.validRowAllFalse(tmp)){
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
//				System.out.println(mem.get(0).get(i)+ " "+mem.get(1).get(i));   
			}//BubbleSort End
			while(!counter && !mem.isEmpty() && a>=0){
				if(tmp.get(mem.get(0).get(a)).getValid() ){
					LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, mem.get(0).get(a));
					LongRemovingBits.removingBits.counterremoveRows++;
					counter=true;
				} else{
					a--;
				}
			}
		}
	}
	
}
