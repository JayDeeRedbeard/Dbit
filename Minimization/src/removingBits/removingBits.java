package removingBits;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import readdata.*;

public class removingBits {
	
	public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
	public static ArrayList<Boolean> saveRow = new ArrayList<Boolean>(); //Zur sp�teren Bestimmung bei den Dominanzen Speicher Reihe die weg ist aber nicht zur L�sung geh�rt(e.g. Allfalse Reihen
	public static ArrayList<Boolean> patternEmpty = new ArrayList<Boolean>();
	
	public static void main (String [] args) throws IOException{
		ArrayList<ArrayList<Boolean>> tmp=readdata.readingdata.testpatternOneData();
		//ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();		
		//Initialisierung von solution und saveRow; patternEmpty
		initsolution(tmp);
		initsaveRow();
		//initpatternEmpty();
		//Ausgabe der ungefilterterten Daten
		//print.arrayList.print2DTEST(tmp);		
		
		System.out.println("Remove all False Columns: ");
		tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
		print.arrayList.print2DTEST(tmp);
		
		System.out.println("Remove all EssentialBits: ");
		tmp=essentialBits.removeAllEssential(tmp);
		print.arrayList.print2DTEST(tmp);
		
		System.out.println("Remove all Equal Columns: ");
		tmp=removeEqualColumns(tmp);											
		print.arrayList.print2DTEST(tmp);
		System.out.println("Remove False Rows: ");
		tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
		print.arrayList.print2DTEST(tmp);
		System.out.println("Remove Equal Rows: ");
		tmp= removeEqualRows(tmp);
		print.arrayList.print2DTEST(tmp);
		
		System.out.println("Remove Equal Rows: ");
		tmp= removeEqualRows(tmp);
		print.arrayList.print2DTEST(tmp);
		
 		System.out.println("Remove NOT dominating Rows: ");
 		tmp=removeNotDominatingRows(tmp);
 		print.arrayList.print2DTEST(tmp);
 		
 		System.out.println("Remove all EssentialBits: ");
 		tmp=essentialBits.removeAllEssential(tmp);
 		print.arrayList.print2DTEST(tmp);
 		
		//Vorl�ufiges Ergebnis
		System.out.println("saveRowAllTrue: "+saveRowAllTrue());
		System.out.println("numberOfFalseinSolution "+numberOfFalseinSolution()+" ALLE D-Bits: " + solution.size());
		
		System.out.println();
		
	}
	public static int numberOfFalseinSolution(){
		/** Gibt die Anzahl der false in solution zur�ck
		@author Jan Dennis Reimer		
		@version1.0
		@return													Anzahl der D-Bits
		*/
		int counter=0;
		for(int j=0;j<solution.size();j++)
			if(solution.get(j)==false)
				counter++;
		System.out.println("D-Bits: " + counter);
		return counter;
	}
	
	/*public static void initpatternEmpty(){ // wird zurzeit nicht gebraucht.
		/** initialisierung von patternEmpty
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		/
		for (int i=0 ; i<solution.size(); i++)
			patternEmpty.add(true);
	}*/
	
	public static void initsaveRow(){
		/** Initialisierung von saveRow. Hier werden alle Reihen gespeichert die gestrichen wurden, um einen �berblick zu behalten. 
		 *  Es soll jederzeit gewissheit herrschen, um welches DBit es sich handelt
		@author Jan Dennis Reimer		
		@version1.0
		@return						-
		*/
		//Es sind immer nur die �brigen D-Bits aktiv(true)
		for(int row =0;row<solution.size(); row++)
			saveRow.add(row, false);
	}
	public static void initsolution(ArrayList<ArrayList<Boolean>> tmp){
		/**  Initialisierung von solution: da zuerst die essentiellen D-Bits zur L�sung hinzugef�gt werden, wird solution direkt hiermit initialisiert
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													-
		*/
		solution=essentialBits.essential1D(tmp);
	}
	public static boolean saveRowAllTrue(){
		/**  �berpr�fung ob in saveRow nur true drin steht. Wenn ja, gebe true zur�ck
		@author Jan Dennis Reimer		
		@version1.0
		@return									alles true gebe true zur�ck, else false				-
		*/
		int counter=0;
		for (int i=0; i<saveRow.size();i++){
			counter++;
		}
		if(saveRow.size()==counter)
			return true;
		else 
			return false;
			
	}
	public static ArrayList<ArrayList<Boolean>> removeDominatingRows(ArrayList<ArrayList<Boolean>> tmp){
		/**  Remove all NOT dominating Rows.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													
		*/
		
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeNotDominatingRows(ArrayList<ArrayList<Boolean>> tmp){
		/**  Remove all NOT dominating Rows.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													2D-ArrayList ohne die Zeilen, die dominiert wurden, also (hier) nur 2.Spalte l�schen.
		*/
		ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();
		for (int x=tmp.size()-1; x>=0;x--){
			tmp1=dominatingRows(tmp,x);									//Die �bergebene ArrayList hat 2 Spalten (beide nicht sortiert, 
																		//sowie mit m�glich doppelten Eintr�gen)
			
			// Sortieralgorithmus Bubble Sort start:
			int temp1, temp2;
			for(int i=0; i<tmp1.get(0).size(); i++){
			        for(int k= i+1; k <tmp1.get(0).size(); k++){
			                if((tmp1.get(1).get(k)).compareTo(tmp1.get(1).get(i)) < 0){
			                	 temp1 = tmp1.get(1).get(i);
			                	 temp2 = tmp1.get(0).get(i);
			                     tmp1.get(1).set(i, tmp1.get(1).get(k));
			                     tmp1.get(0).set(i, tmp1.get(0).get(k));
			                     tmp1.get(1).set(k,temp1);
			                     tmp1.get(0).set(k,temp2);	
			                }
			        }
			        System.out.println(tmp1.get(0).get(i)+ tmp1.get(1).get(i));   
			}///BubbleSort End
			
			//Removing the NOT dominating Rows
			for(int y=tmp1.get(0).size()-1;y>=0;y--){
				removeRow(tmp,tmp1.get(1).get(y),false);
			}
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Integer>> dominatingRows(ArrayList<ArrayList<Boolean>> tmp, int row){
		/**  Finde alle Reihen die dominiert werden von int row.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int row											Es wird jeweils �berpr�ft, ob diese Reihe irgendeine andere Reihe dominiert
		@return													Es wird eine ArrayList zur�ckgegeben, wo die erste Spalte jeweils die dominierende Reihe ist.
																Die 2.Spalte ist dann die nicht dominierende Spalte
		*/
		/* Zum Testen in Main einf�gen.
		for(int j=0;j<tmp1.get(0).size();j++)
		System.out.println(tmp1.get(0).get(j)+ " "+ tmp1.get(1).get(j));
		 * */
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();						//tmp2 und 3 zum initailisieren von tmp1.
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();						 
		tmp1.add(tmp2);tmp1.add(tmp3);	
		int counttrue=0;
		boolean isdominated= true;
		for (int k=0; k<tmp.size(); k++){							
			for(int z=0; z<tmp.get(0).size() && isdominated&& k!=row; z++){
				if( !(tmp.get(row).get(z).equals(false) && tmp.get(k).get(z).equals(true)) ){	//Entscheidendes Kriterium!!
					//System.out.println("row= "+row + " z= "+z+ "k= "+k);
					//System.out.println(tmp.get(row).get(z)+ "\t"+ tmp.get(k).get(z));
					if(tmp.get(row).get(z)){													//counttrue muss gr��er als 1 sein,
						counttrue++;															//da es sonst keine dominierens Zeile sein kann
					}
				}
				else{
						//System.out.println(" Reihe "+ row + " ist nicht dominiernd auf "+k);
						isdominated=false;
					}
				//System.out.println("counttrue= " + counttrue );	
			}
			if(counttrue>1 && isdominated)	{
				tmp1.get(0).add(row);
				tmp1.get(1).add(k);
			}
			isdominated=true;
			counttrue=0;
		}
		return tmp1;
	}
	public static ArrayList<ArrayList<Boolean>> removeEqualColumns(ArrayList<ArrayList<Boolean>> tmp){
		/** L�schen von gleichen Spalten aus der �berdeckungstabelle
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		*/
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();						//Brauche 2 verschiedene tmp ArrayList um zu initialisieren, 
		tmp1.add(tmp2);tmp1.add(tmp3);											//ob sich im L�sungsarray tmp1 schon die gesuchte Spalte befindet
		int counter=0;															//k und z sind die Laufindizes ,um jede Spalte durchzugehen						
			for (int k=0; k<tmp.get(0).size(); k++){							//j - Reihe
				for(int z=0; z<tmp.get(0).size(); z++){				
					for(int j =0;j<tmp.size(); j++){						
						//System.out.println("Spalte k ="+k+" j= "+j+" z="+z );											//Zum Testen
						//System.out.println("jk "+(tmp.get(j).get(k))+ " \t jz "+ tmp.get(j).get(z));					//Zum Testen
					
						if(tmp.get(j).get(k).equals(tmp.get(j).get(z)) && z!=k){
							counter++;
						}
						//System.out.println("Z�hler "+counter);														//Zum Testen
					}
					//System.out.println("Z�hler "+counter + " =? "+ tmp.size());										//Zum Testen
					if(counter==tmp.size()){									//Es m�ssen alle Zeilen in einer Spalten �bereinstimmen
							tmp1.get(0).add(z);
							tmp1.get(1).add(k);
					}
					counter=0;	
				}
		}
			for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){					//Folgender Block l�scht jeweils die zu viel hinzugef�gten Zwischenergebnisse
				for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hiernach sind keine gespiegelten Zwischenergebnisse mehr vorhanden.
					if(tmp1.get(0).get(idx).equals(tmp1.get(1).get(idy)) && tmp1.get(1).get(idx).equals(tmp1.get(0).get(idy))&& idx!=idy ){
						tmp1.get(0).remove(idy);
						tmp1.get(1).remove(idy);
						idx--;
						
					}
				}
			}
			for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){
				for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hier werden nun die Zwischenergebnisse aussortiert sodass in '1' jeweils die zu l�schenden Spalten sich befinden
					if(tmp1.get(1).get(idx).equals(tmp1.get(1).get(idy))&& idx!=idy ){
						tmp1.get(0).remove(idy);
						tmp1.get(1).remove(idy);
						idx--;
						
					}
				}
			}
			for(int x=tmp1.get(0).size()-1; x>=0;x--){							//L�schen von Spalte 1 (tmp1)
				//System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));	//Zum Testen
				removeColumn(tmp,tmp1.get(1).get(x));
			}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeEqualRows(ArrayList<ArrayList<Boolean>> tmp){
		/** L�schen von gleichen Reihen aus der �berdeckungstabelle
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Reihen
		*/
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();	
		tmp1.add(tmp2);tmp1.add(tmp3);	
		int counter= 0;
		int save=0;
		for(int j =0;j<tmp.size(); j++){
			for (int k=0; k<tmp.size(); k++){							
				for(int z=0; z<tmp.get(0).size(); z++){
					//System.out.println("j= "+j + " z= "+z+ "k= "+k);
					//System.out.println(tmp.get(j).get(z)+ "\t"+ tmp.get(k).get(z));
					if(tmp.get(j).get(z).equals(tmp.get(k).get(z)) && j!=k){
						counter++;
					}
				}
				//System.out.println("Z�hler "+counter + " =? "+ tmp.get(0).size());	
				if (counter==tmp.get(0).size()){
					tmp1.get(0).add(k);
					tmp1.get(1).add(j);
				}
				counter=0;
			}
		}
		for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){					//Folgender Block l�scht jeweils die zu viel hinzugef�gten Zwischenergebnisse
			for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hiernach sind keine gespiegelten Zwischenergebnisse mehr vorhanden.
				if(tmp1.get(0).get(idx).equals(tmp1.get(1).get(idy)) && tmp1.get(1).get(idx).equals(tmp1.get(0).get(idy))&& idx!=idy ){
					tmp1.get(0).remove(idy);
					tmp1.get(1).remove(idy);
					idx--;
					
				}
			}
		}
		for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){
			for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hier werden nun die Zwischenergebnisse aussortiert sodass in '1' jeweils die zu l�schenden Spalten sich befinden
				if(tmp1.get(1).get(idx).equals(tmp1.get(1).get(idy))&& idx!=idy ){
					tmp1.get(0).remove(idy);
					tmp1.get(1).remove(idy);
					idx--;
					
				}
			}
		}
		for(int x=tmp1.get(1).size()-1; x>=0;x--){
			System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));//Zum Testen
			removeRow(tmp,tmp1.get(1).get(x),false);	
			for(int j=0; j<removingBits.saveRow.size() && j<=tmp1.get(1).get(x);j++){			//Um die richtigen Zeilen zu speichern
				if(removingBits.saveRow.get(j))
					save++;
			}
			removingBits.saveRow.set(tmp1.get(1).get(x)+save, true);
			save=0;
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeOneRowTrueColumns(ArrayList<ArrayList<Boolean>> tmp, int Row){
		/** Die �bergebene Reihe(Row) soll zur L�sung hinzugef�gt werden. L�schen von einer Reihe sowie jeweils die dazugeh�rigen Spalten.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int Row											Die Reihe, die zur L�sung hinzugef�gt werden soll. Und au�erdem gel�scht werden soll
		@return													3D-ArrayList ohne diese Reihe und Spalten
		*/
		for(int i= tmp.get(Row).size()-1; i>=0; i--){
			
			if(tmp.get(Row).get(i)==true){
				tmp=removeColumn(tmp,i);
			}
		}
		tmp=removeRow(tmp, Row,true);
		return tmp;
	}
	
	
	public static ArrayList<ArrayList<Boolean>> removeColumn(ArrayList<ArrayList<Boolean>> tmp, int column){
		/** Spalte die gel�scht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int Column										Spalte column wird gel�scht.
		@return													3D-ArrayList ohne diese Spalte
		*/
		for(int k = 0; k<tmp.size(); k++){
			tmp.get(k).remove(column);
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeRow (ArrayList<ArrayList<Boolean>> tmp, int row, boolean solutionBit){
		/** Reihe die gel�scht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int row											Reihe row wird gel�scht.
		@param boolean solutionBit								Gibt an, ob es sich um ein Bit handelt, dass auch zur L�sung hinzugef�gt werden muss.
		@return													3D-ArrayList ohne diese Spalte
		*/
		tmp.remove(row);
		
		int save=0;
		for(int j=0; j<removingBits.saveRow.size() && j<=row+save;j++){			//Um die richtigen Zeilen zu speichern
			if(removingBits.saveRow.get(j))										//Berechnung von save, bzw Berechnung wie viele trues schon in saveRow gespeichert sind
				save++;
		}
		removingBits.saveRow.set(row+save, true);
		if (solutionBit)
			removingBits.solution.set(row+save, true);
		save=0;
		return tmp;
	}
	
}
