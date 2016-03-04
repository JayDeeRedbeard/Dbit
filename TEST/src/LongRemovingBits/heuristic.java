package LongRemovingBits;

import java.util.ArrayList;
import java.util.*;

import readdata.DBit;
import readdata.longData;
import readdata.readingdata;

public class heuristic {
	/** Loescht das D-Bit mit den meisten Trues bzw. ein D-Bit, dass mit am meisten "Trues" hat.
	 * 
	 * @param tmp 	Bekommt die 2D-ArrayList uebergeben
	 */
	public static void removeBitWithMostTrues(ArrayList<DBit> tmp){
//		ArrayList<ArrayList<Integer>> mem= new ArrayList<ArrayList<Integer>>();
//		mem.add(new ArrayList<Integer>());
//		mem.add(new ArrayList<Integer>());
//		
//		for(int i =0; i<tmp.size(); i++){
//			mem.get(0).add(i);
//			mem.get(1).add(readdata.make1DatafileLong.numberOfTruesInRow.get(i));
//		}
//		int a=mem.get(0).size()-1;
//		
//		Integer [][] mem=new Integer[2][tmp.size()];
//		for(int i =0; i<tmp.size(); i++){
//			mem[0][i]=i;
//			mem[1][i]=readdata.make1DatafileLong.numberOfTruesInRow.get(i);
//		}
		boolean counter = false;
//		int a= mem[0].length-1;
//		
		if(!removingBits.validFalse(tmp)){
//			
			//Sortierung der ArrayList. Ganz vorne in der ArrayList befindet sich das D-Bit mit den meisten "Trues".
//			if(removingBits.stopdomination){
//				qSort(mem.get(1), mem.get(0), 0, tmp.size()-1);
//			}else{
//				if(tmp.size()<60000 || tmp.get(0).getList().size()<50){
//					qSort(mem.get(1), mem.get(0), 0, tmp.size()-1);
//				}
//			}
//			while(!counter && !mem.isEmpty() && a>=0){
//				if(tmp.get(mem.get(0).get(a)).getValid() && readdata.make1DatafileLong.numberOfTruesInRow.get(mem.get(0).get(a))>0){
//					
//					LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, mem.get(0).get(a));
//					System.out.println("remove: "+mem.get(0).get(a));
//					LongRemovingBits.removingBits.counterremoveRows++;
//					counter=true;
//				} else{
//					a--;
//				}
//			}
			int max=0;
			int index=0;
			for (int row=0; row<tmp.size();row++){
				if(tmp.get(row).getValid()){
					if(readdata.make1DatafileLong.numberOfTruesInRow.get(row)>max){
						max=readdata.make1DatafileLong.numberOfTruesInRow.get(row);
						index=row;
					}
				}
			}	
			LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, index);
			System.out.println("remove: "+index);
			LongRemovingBits.removingBits.counterremoveRows++;
			
//			System.out.println();
			
			
//			System.out.println(mem.get(1));
//			System.out.println(mem.get(0));
//			mem=mysort(mem);
//			
//			while(!counter && mem[0].length>0 && a>=0){
//				if(tmp.get(mem[0][a]).getValid() && readdata.make1DatafileLong.numberOfTruesInRow.get(mem[0][a])>0){
//					
//					LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, mem[0][a]);
//					System.out.println("remove: "+mem[0][a]);
//					LongRemovingBits.removingBits.counterremoveRows++;
//					counter=true;
//				} else{
//					a--;
//				}
//			}
//			Object [] temp = mem.toArray();
//			Arrays.sort(temp, new Comparator<Object>(){ 
//				@Override
//		        public int compare(Object o1, Object o2) {
//					ArrayList<Integer> temp1 = (ArrayList<Integer>) o1;
//			        ArrayList<Integer> temp2 = (ArrayList<Integer>) o2;
//
//			        // Then compare those inner arrays' indexes
//			        return temp1.get(1).compareTo(temp2.get(1));
//		        }               
//			});
//			mem.clear();
//
//			for(Object o: temp)
//			{
//			    mem.add((ArrayList<Integer>) o);
//			}
			
			
		}
	}
//	
//	 public static Integer[][] mysort(Integer[][] ar) {
//	        Arrays.sort(ar, new Comparator<Integer[]>() {
//	            @Override
//	            public int compare(Integer[] int1, Integer[] int2) {
//	                Integer numOfKeys1 = int1[1];
//	                Integer numOfKeys2 = int2[1];
//	                return numOfKeys1.compareTo(numOfKeys2);
//	            }
//	        });
//	        return ar;
//	    }
	/**
	 * http://www.java-uni.de/index.php?Seite=86
	 * @param x
	 * @param y
	 * @param links
	 * @param rechts
	 */
	public static void qSort(ArrayList<Integer> x,ArrayList<Integer> y, int links, int rechts) {
	      if (links < rechts) {
	         int i = partition(x,y,links,rechts);
	         qSort(x,y,links,i-1);
	         qSort(x,y,i+1,rechts);
	      }
	   }
	    
	   public static int partition(ArrayList<Integer> x,ArrayList<Integer> y, int links, int rechts) {
	      int pivot, i, j, help,help1;
	      pivot = x.get(rechts);               
	      i     = links;
	      j     = rechts-1;
	      while(i<=j) {
	         if (x.get(i) > pivot) {     
	            // tausche x[i] und x[j]
	            help = x.get(i); 
	            help1= y.get(i);
	            x.set(i, x.get(j)); 
	            y.set(i, y.get(j));
	            x.set(j, help);
	            y.set(j, help1);
	            j--;
	         } else i++;            
	      }
	      // tausche x[i] und x[rechts]
	      help      = x.get(i);
	      help1=y.get(i);
	      x.set(i, x.get(rechts));
	      y.set(i, y.get(rechts));
	      x.set(rechts, help);
	      y.set(rechts, help1);
	      return i;
	   }
}