package LongRemovingBits;

import java.util.ArrayList;

import readdata.longData;

public class falseRowsAndColumns {
	/**
	 * Loescht alle Reihen die aus komplett False bestehen.
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return													2D-ArrayList ohne Reihen, die aus False bestehen.
	*/
	public static void RemoveFalseRows(ArrayList<ArrayList<Long>> tmp){
		
		int j =0;
			while(j<tmp.size()){					
				//System.out.println(rowAllFalse(tmp,j) + " " +j);	//zum Testen
				if (rowAllFalse(tmp,j)){
					//System.out.println( " j"+  j);				//zum Testen
					removingBits.removeRow(tmp,j,false);
				}
			j++;
			}
	}
	/** Gibt zurueck, ob eine Reihe komplett FALSE ist.
	@param ArrayList<ArrayList<Long>> tmp					Bekommt die 2D-ArrayList uebergeben	
	@param int row											Hier muss die zu ueberpruefende Reihe eingetragen werden.	
	@return													Gibt in Boolean zurück, ob die Reihe komplett False ist oder nicht.
	*/
	public static boolean rowAllFalse(ArrayList<ArrayList<Long>> tmp, int row){
		boolean counter=false;	
		int c=0;
		for(int d = 0;d<tmp.get(0).size();){
			if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1 && 
					stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zuruechgegeben
				counter= true;
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
		}
		if(counter==true){
			return false;
		}
		else{
			return true;
		}
	}
	/** Berechnung einer ArrayList in der keine Spalte aus False besteht.
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return													2D-ArrayList ohne Spalte, die aus False bestehen
	*/
	public static void RemoveFalseColumn(ArrayList<ArrayList<Long>> tmp){
		int j=0;
		int d=0;
		int c=0;
		if(!removingBits.validRowAllFalse()){
			while(j<tmp.get(0).size()*64 && d<tmp.get(0).size()){					//AUFGEPASST HIER get(0) nicht get(j), weil j abhaengig sonst von j
				if(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
					if (columnAllFalse(tmp,d,c)){
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
	@param d		Welche Spalte soll getestet werden: d steht für das ansprechen eines Longs in der ArrayList. 	
	@param c		c steht für das ansprechen eines Bits in dem jeweiligen Long.
	@return			Gibt in Boolean zurück, ob die Spalte komplett False ist oder nicht.
	*/
	public static boolean columnAllFalse(ArrayList<ArrayList<Long>> tmp, int d, int c){
		boolean counter=false;
		for(int i = 0; i<tmp.size(); i++){
			if(longData.validRow.get(i)){
				//System.out.println("i= "+ i +" d= "+d + " c="+ c);
				//System.out.println("tmp.get(0).size(): "+tmp.get(0).size() );
				//System.out.println("tmp.size(): "+tmp.size() );
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(i).get(d), c)==1){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zuruechgegeben
					counter= true;
				}
			}
		}
		if(counter){
			return false;
		}
		else{
			return true;
		}
	}
}
