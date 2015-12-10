package removingBits;

import java.util.ArrayList;

public class heuristic {
	public static ArrayList<ArrayList<Integer>> numberOfTruesRow(ArrayList<ArrayList<Boolean>> tmp){
		ArrayList<ArrayList<Integer>> mem= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp1 = new ArrayList<Integer>();
		mem.add(tmp1);
		mem.add(tmp1);
		int counter=0;
		for (int x=0; x<tmp.size();x++){
			for (int i =0; i<tmp.get(0).size();i++){
				if(tmp.get(x).get(i))
					counter++;
			}
			mem.get(0).add(x);
			mem.get(1).add(counter);
			counter=0;
		}
		return mem;
	}
	public static ArrayList<ArrayList<Boolean>> removeBitWithMostTrues(ArrayList<ArrayList<Boolean>> tmp){
		ArrayList<ArrayList<Integer>> mem= numberOfTruesRow(tmp);
		int temp1, temp2;
		if(!tmp.isEmpty()){
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
				//System.out.println(mem.get(i)+ mem.get(i));   
			}///BubbleSort End
			tmp=removingBits.removeOneRowTrueColumns(tmp, mem.get(0).get(mem.get(0).size()-1));
		}
		return tmp;
	}
}
