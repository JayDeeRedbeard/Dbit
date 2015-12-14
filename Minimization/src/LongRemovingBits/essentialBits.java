package LongRemovingBits;

import java.io.IOException;
import java.util.ArrayList;

import removingBits.removingBits;

public class essentialBits {
	public static void removeAllEssential(ArrayList<ArrayList<Long>> tmp)throws IOException{
		/** Es werden alle essentiellen D-Bits zur Loesung hinzugefuegt und danach geloescht( mit Spalten)
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
		@return													3D-ArrayList ohne die essentiellen Bits (ueberdeckungstabelle)
		*/
		ArrayList<Boolean> essent1D= essential1D(tmp);
		
			for(int row =tmp.size()-1;row>=0; row--){
				//System.out.println("Row: "+row);
				if(essent1D.get(row)){
					//System.out.println("Row: "+row);
					LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, row);
					//Test
						/*for(int y = 0; y<tmp.size(); y++){
							for(int z = 0; z<tmp.get(y).size(); z++){
								System.out.print(tmp.get(y).get(z)+ "\t");
							}
							System.out.println();	
							
						}
						System.out.println();	
					*/
					
				}
			}
		
		
		//Falls ein Pattern schon die richtige Loesung hat, muss das jeweilige Pattern geloescht werden
			
	}
	public static ArrayList<Boolean> BooleanAry1DToArrayList(boolean[] tmp){
		ArrayList<Boolean> k = new ArrayList<Boolean>();
		for(int i = 0; i<tmp.length; i++){
			k.add(tmp[i]);
		}
		return k;
	}
	public static ArrayList<Boolean> essential1D(ArrayList<ArrayList<Long>> tmp) {
		/** Findet heraus, welches D-Bit essentiell ist und gibt dementsprechend eine Arraylist zurueck
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
		@return													3D-ArrayList ohne die essentiellen Bits (ueberdeckungstabelle)
		*/
		boolean[] essentialAry = new boolean[tmp.size()];		
		int tmp1=0;
		int counter=0;
		int c=0;
		if(!tmp.isEmpty()){
			for (int d = 0; d<tmp.get(0).size();){
				for (int row=0; row<tmp.size(); row++){
					if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1){
						counter++;
						tmp1= row;
					}
					//System.out.println(row + " " + counter + " " );
				}
				if(counter==1){
					essentialAry[tmp1]= true;
					
					//System.out.println("Reihe "+tmp1 + " true");
					counter=0;
				} else{
					counter=0;
				}
				c++;
				if(c==64){
					d++;
					c=0;
				}
			}
		}
		return BooleanAry1DToArrayList(essentialAry);
	}
	
	
}
