package removingBits;

import java.util.ArrayList;

public class heuristic {
	public static ArrayList<Integer> numberOfTruesRow(ArrayList<ArrayList<Boolean>> tmp){
		ArrayList<Integer> mem= new ArrayList<Integer>();
		int counter=0;
		for (int x=0; x<tmp.size();x++){
			for (int i =0; i<tmp.get(0).size();i++){
				if(tmp.get(x).get(i))
					counter++;
			}	
			mem.add(counter);
			counter=0;
		}
		return mem;
	}
	public static ArrayList<ArrayList<Boolean>> removeBitWithMostTrues(ArrayList<ArrayList<Boolean>> tmp){
		ArrayList<Integer> mem= numberOfTruesRow(tmp);
		int temp1;
		if(!tmp.isEmpty()){
			for(int i=0; i<mem.size(); i++){
					for(int k= i+1; k <mem.size(); k++){
							if((mem.get(k)).compareTo(mem.get(i)) < 0){
								temp1 = mem.get(i);
								mem.set(i, mem.get(k));
								mem.set(k,temp1);
							}
					}
				//System.out.println(mem.get(i)+ mem.get(i));   
			}///BubbleSort End
			tmp=removingBits.removeOneRowTrueColumns(tmp, mem.size()-1);
		}
		return tmp;
	}
}
