package removingBits;

import java.util.ArrayList;

public class falseRowsAndColumns {
	public static ArrayList<ArrayList<Boolean>> RemoveFalseRows(ArrayList<ArrayList<Boolean>> tmp){
		/**
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													2D-ArrayList ohne Reihen die aus False bestehen
		*/
		int save=0;
		int j =0;
			while(j<tmp.size()){					
				//System.out.println(rowAllFalse(tmp,j) + " " +j);	//zum Testen
				if (rowAllFalse(tmp,j)){
					//System.out.println( " j"+  j);				//zum Testen
					removingBits.removeRow(tmp,j,false);
					if(j<tmp.size())						//Nach jeden Schritt wo eine Reihe entfernt wurde muss geguckt 
						j--;									//werden ob die nachger�ckte Spalte auch komplett False ist 
				}
			j++;
			}
		return tmp;
	}
	public static boolean rowAllFalse(ArrayList<ArrayList<Boolean>> tmp, int row){
		/** Gibt zur�ck, ob eine Reihe komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	
		@param int row											Welche Reihe soll getestet werden	
		@return													Boolean, row False oder True
		*/
		boolean counter=false;									
		for(int i = 0; i<tmp.get(0).size(); i++){
			if(tmp.get(row).get(i)==true){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zur�chgegeben
				counter= true;
			}
		}
		if(counter==true){
			return false;
		}
		else{
			return true;
		}
	}
	public static ArrayList<ArrayList<Boolean>> RemoveFalseColumn(ArrayList<ArrayList<Boolean>> tmp){
		/** Berechnung einer ArrayList in der keine Spalte aus False besteht.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne Spalte die aus False bestehen
		*/
		/*
		 *  Testmuster um diese Methode zu testen
		  	readdata.readingdata.print2DTEST(tmp);
			tmp=RemoveFalseColumn(tmp);
			readdata.readingdata.print2DTEST(tmp);
		 * */
		int j =0;
			while(j<tmp.get(0).size()){					//AUFGEPASST HIER get(0) nicht get(j), weil j abh�ngig sonst von j
				//System.out.println(columnAllFalse(tmp,i,j) + " "+i +" " +j);
				if (columnAllFalse(tmp,j)){
					//System.out.println( "Pattern: " + i + "     j"+  j);
					for(int k = 0; k<tmp.size(); k++){
						tmp.get(k).remove(j);
					}
					if(j<tmp.get(0).size())				//Nach jeden Schritt wo eine Spalte entfernt wurde muss geguckt 
						j--;									//werden ob die nachger�ckte Spalte auch komplett False ist 
				}
			j++;
			}
		return tmp;
	}
	public static boolean columnAllFalse(ArrayList<ArrayList<Boolean>> tmp, int column){
		/** Gibt zur�ck, ob eine Spalte komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	
		@param int pattern										In welches Testmuster soll getestet werden	
		@param int column										Welche Spalte soll getestet werden	
		@return													Boolean, Spalte False oder True
		*/
		boolean counter=false;									
		for(int i = 0; i<tmp.size(); i++){
			//System.out.println("Ausgabe= "+tmp.get(pattern).get(i).get(column));
			if(tmp.get(i).get(column)==true){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zur�chgegeben
				counter= true;
			}
		}
		if(counter==true){
			return false;
		}
		else{
			return true;
		}
	}
}
