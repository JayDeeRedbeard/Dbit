package removingBits;

import java.io.IOException;
import java.util.ArrayList;

public class essentialBits {
	public static ArrayList<ArrayList<Boolean>> removeAllEssential(ArrayList<ArrayList<Boolean>> tmp)throws IOException{
		/** Es werden alle essentiellen D-Bits zur Lösung hinzugefügt und danach gelöscht( mit Spalten)
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@return													3D-ArrayList ohne die essentiellen Bits (Überdeckungstabelle)
		*/
		ArrayList<Boolean> essent1D= essential1D(tmp);
		
			for(int row =tmp.size()-1;row>=0; row--){
				//System.out.println("Row: "+row);
				if(essent1D.get(row)){
					//System.out.println("Row: "+row);
					tmp= removingBits.removeOneRowTrueColumns(tmp, row);
					
					
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
		
		
		//Falls ein Pattern schon die richtige Lösung hat, muss das jeweilige Pattern gelöscht werden
			
		return tmp;
	}
	public static ArrayList<Boolean> BooleanAry1DToArrayList(boolean[] tmp){
		ArrayList<Boolean> k = new ArrayList<Boolean>();
		for(int i = 0; i<tmp.length; i++){
			k.add(tmp[i]);
		}
		return k;
	}
	public static ArrayList<Boolean> essential1D(ArrayList<ArrayList<Boolean>> tmp) {
		/** Findet heraus, welches D-Bit essentiell ist und gibt dementsprechend eine Arraylist zurück
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@return													3D-ArrayList ohne die essentiellen Bits (Überdeckungstabelle)
		*/
		boolean[] essentialAry = new boolean[tmp.size()];		
		int tmp1=0;
		int counter=0;
		if(!tmp.isEmpty()){
			for (int column = 0; column<tmp.get(0).size(); column++){
				for (int row=0; row<tmp.size(); row++){
					if(tmp.get(row).get(column)){
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
			}
		}
		return BooleanAry1DToArrayList(essentialAry);
	}
	
	
}
