package LongRemovingBits;

import java.util.ArrayList;

import readdata.DBit;
import readdata.longData;

public class falseRowsAndColumns {
	/**
	 * Loescht alle Reihen die aus komplett False bestehen.
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return													2D-ArrayList ohne Reihen, die aus False bestehen.
	*/
	public static void RemoveFalseRows(ArrayList<DBit> tmp){
		int row = 0;
		while (readdata.make1DatafileLong.numberOfTruesInRow.contains(0) && removingBits.stopdomination) {
			row = readdata.make1DatafileLong.numberOfTruesInRow.indexOf(0);
			if (tmp.get(row).getValid()) {
				LongRemovingBits.removingBits.removeRow(tmp, row, false);
			}
			readdata.make1DatafileLong.numberOfTruesInRow.set(row,
					(readdata.make1DatafileLong.numberOfTruesInRow.get(row) - 1));
		}
	}
	/** Berechnung einer ArrayList in der keine Spalte aus False besteht.
	@param  tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return			2D-ArrayList ohne Spalte, die aus False bestehen
	*/
	public static void RemoveFalseColumn(ArrayList<DBit> tmp){
		int j=0;
		int d=0;
		int c=0;
		if(!removingBits.validFalse(tmp) /*&& removingBits.stopdomination*/){
			while(j<tmp.get(0).getList().size()*64 && d<tmp.get(0).getList().size()){					//AUFGEPASST HIER get(0) nicht get(j), weil j abhaengig sonst von j
				if(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
//					if (readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)==0){
					if(columnAllFalse(tmp,d,c)){
						//System.out.println("Column All false: "+ columnAllFalse(tmp,c,d) + " " +d+ " "+c);
						removingBits.removeColumn(tmp, d,c);
					}
				}
				c++;
				if(c==64){
					d++;
					c=0;
				}
			j++;
			}
		}
	}
	/** Gibt zurueck, ob eine Spalte komplett FALSE ist.
	@param tmp		Bekommt die 2D-ArrayList uebergeben	
	@param d		Welche Spalte soll getestet werden: d steht fuer das ansprechen eines Longs in der ArrayList. 	
	@param c		c steht fuer das ansprechen eines Bits in dem jeweiligen Long.
	@return			Gibt in Boolean zurueck, ob die Spalte komplett False ist oder nicht.
	*/
	public static boolean columnAllFalse(ArrayList<DBit> tmp, int d, int c){
//		boolean counter=false;
//		for(int i = 0; i<tmp.size(); i++){
//			if(tmp.get(i).getValid()){
				//System.out.println("i= "+ i +" d= "+d + " c="+ c);
				//System.out.println("tmp.get(0).getList().size(): "+tmp.get(0).getList().size() );
				//System.out.println("tmp.size(): "+tmp.size() );
//				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(i).getList().get(d), c)==1){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zuruechgegeben
//					counter= true;
//				}
//			}
//		}
		if(/*counter*/ !(readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)==0)){
			return false;
		}
		else{
			return true;
		}
	}
}