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
		int column=0;
		for(int d =0; d<tmp.get(0).size();d++){
			while(readdata.make1DatafileLong.numberOfTruesInColumn.get(d).contains(1) ){
				column=readdata.make1DatafileLong.numberOfTruesInColumn.get(d).indexOf(1);
				if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), column)==1){
					for(int row =0; row<tmp.size();row++){
						if (readdata.longData.validRow.get(row)){
							if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), column)==1){
								System.out.println("Row: "+row +" ist essentiell");
								LongRemovingBits.removingBits.removeOneRowTrueColumns(tmp, row);
							}
						}
					}
				}
			}
		}
			
	}
}
