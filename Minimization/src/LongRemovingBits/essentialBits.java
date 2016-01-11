package LongRemovingBits;

import java.io.IOException;
import java.util.ArrayList;

public class essentialBits {
	/** Es werden alle essentiellen D-Bits zur Loesung hinzugefuegt und danach geloescht(sowohl Zeilen als auch Spalten)
	@author Jan Dennis Reimer		
	@version1.0
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	*/
	public static void removeAllEssential(ArrayList<ArrayList<Long>> tmp)throws IOException{
		
		ArrayList<Boolean> essent1D= essential1D(tmp);
		
			for(int row =tmp.size()-1;row>=0; row--){
				//System.out.println("Row: "+row);
				if(essent1D.get(row)){
					System.out.println("Row: "+row +" ist essentiell");
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
	}
	/** Umwandelung von boolean Array zur ArrayList
	@param boolean[] tmp									Bekommt ein boolean Array übergeben.
	@return													Gibt eine Boolean ArrayList zurück.
	*/
	public static ArrayList<Boolean> BooleanAry1DToArrayList(boolean[] tmp){
		ArrayList<Boolean> k = new ArrayList<Boolean>();
		for(int i = 0; i<tmp.length; i++){
			k.add(tmp[i]);
		}
		return k;
	}
	/** Findet heraus, welches D-Bit essentiell ist und gibt dementsprechend eine Arraylist zurueck
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return													Gibt eine ArrayList zurück, die essentielle D-Bits enthält.
	*/
	public static ArrayList<Boolean> essential1D(ArrayList<ArrayList<Long>> tmp) {
		//Start Initialisieren
		boolean[] essentialAry = new boolean[tmp.size()];		
		int tmp1=0;
		int counter=0;
		int c=0;
		//Ende Initialisieren
		if(!removingBits.validRowAllFalse()){
			for (int d = 0; d<tmp.get(0).size();){
				for (int row=0; row<tmp.size(); row++){
					if (readdata.longData.validRow.get(row)){
						if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1 && 
								stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c)==1){
							counter++;
							tmp1= row;
						}
						//System.out.println(row + " " + counter + " " );
					}
				}
				if(counter==1){
					essentialAry[tmp1]= true;
					//System.out.println("Reihe "+tmp1 + "ist essentiell true");
				}
				counter=0;
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
