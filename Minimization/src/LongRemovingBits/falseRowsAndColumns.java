package LongRemovingBits;

import java.util.ArrayList;

import readdata.longData;

public class falseRowsAndColumns {
	public static void RemoveFalseRows(ArrayList<ArrayList<Long>> tmp){
		/**
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
		@return													2D-ArrayList ohne Reihen die aus False bestehen
		*/
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
	public static boolean rowAllFalse(ArrayList<ArrayList<Long>> tmp, int row){
		/** Gibt zurueck, ob eine Reihe komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList uebergeben	
		@param int row											Welche Reihe soll getestet werden	
		@return													Boolean, row False oder True
		*/
		boolean counter=false;	
		int c=0;
		for(int d = 0;d<tmp.get(0).size();){
			if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1 && stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zuruechgegeben
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
	public static void RemoveFalseColumn(ArrayList<ArrayList<Long>> tmp){
		/** Berechnung einer ArrayList in der keine Spalte aus False besteht.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
		@return													3D-ArrayList ohne Spalte die aus False bestehen
		*/
		int j=0;
		int d=0;
		int c=0;
		if(!removingBits.validRowAllFalse()){
			while(j<tmp.get(0).size()*64 && d<tmp.get(0).size()){					//AUFGEPASST HIER get(0) nicht get(j), weil j abhaengig sonst von j
					if (columnAllFalse(tmp,d,c) && stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1 ){
						//System.out.println("Column All false: "+ columnAllFalse(tmp,c,d) + " " +d+ " "+c);
						removingBits.removeColumn(tmp, d,c);
						
					}
					c++;
					if(c==64){
						d++;
						c=0;
					}
				}
			}
	}
	public static boolean columnAllFalse(ArrayList<ArrayList<Long>> tmp, int d, int c){
		/** Gibt zurueck, ob eine Spalte komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	
		@param int pattern										In welches Testmuster soll getestet werden	
		@param int column										Welche Spalte soll getestet werden	
		@return													Boolean, Spalte False oder True
		*/
		boolean counter=false;
		for(int i = 0; i<tmp.size(); i++){
			//System.out.println("d= "+d + " c="+ c);
			
			if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(i).get(d), c)==1 && longData.validRow.get(i)){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zuruechgegeben
				counter= true;
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
