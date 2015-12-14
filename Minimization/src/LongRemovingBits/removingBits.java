package LongRemovingBits;

import java.io.IOException;
import java.util.ArrayList;
import readdata.longData;
import readdata.readingdata;

public class removingBits {
		public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
		
		public static void main (String [] args) throws IOException{
			ArrayList<ArrayList<Long>> tmp=longData.pattern(readingdata.testfile+".txt");
			
			longData.printLongPattern(tmp);	

			System.out.println("RemoveFalseColumn: ");
			falseRowsAndColumns.RemoveFalseColumn(tmp);
			longData.printLongPattern(tmp);	
			
			for(int i=0; i<64; i++)
				System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(0), i)+" ");
			System.out.println();
			
			System.out.println("RemoveFalseRows: ");
			falseRowsAndColumns.RemoveFalseRows(tmp);
			longData.printLongPattern(tmp);	
			
			System.out.println("Remove Essentials: ");
			essentialBits.removeAllEssential(tmp);
			
			for(int i=0; i<64; i++)
				System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(0), i)+" ");
			System.out.println();
			
			longData.printLongPattern(tmp);	
			
			
			
			
			for(int i=0; i<64; i++)
				System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(0), i)+" ");
			System.out.println();
			
			
			System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+validRowAllFalse());
			
			
		}
		public static int numberOfFalseinSolution(){
			/** Gibt die Anzahl der false in solution zurueck
			@author Jan Dennis Reimer		
			@version1.0
			@return													Anzahl der D-Bits
			*/
			int counter=0;
			for(int j=0;j<solution.size();j++){
				if(solution.get(j)==false)
					counter++;
			}
			return counter;
		}
		public static boolean validRowAllFalse(){
			/**  ueberpruefung ob in saveRow nur true drin steht. Wenn ja, gebe true zurueck
			@author Jan Dennis Reimer		
			@version1.0
			@return									alles true gebe true zurueck, else false				-
			*/
			int counter=0;
			for (int i=0; i<longData.validRow.size();i++){
				if(!longData.validRow.get(i))
					counter++;
			}
			if(longData.validRow.size()==counter)
				return true;
			else 
				return false;
				
		}
		//Programmablauf 
		/*
		public static ArrayList<ArrayList<Boolean>> essentialdominating(ArrayList<ArrayList<Boolean>> tmp) throws IOException{
			int counter=0;
			int counter1=2;
			int a=0;
			while(!saveRowAllTrue()){
				
				while(counter!=counter1){
					counter=0;
					System.out.println("Schritt: "+a);
					for(int x=0; x<saveRow.size();x++){
						if(saveRow.get(x))
							counter++;
					}
					counter1=0;
					System.out.println("Equal? = "+ counter + " " + counter1);
					
					System.out.println("Remove all False Columns: ");
					tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
					//print.arrayList.print2DTEST(tmp);

					System.out.println("Remove False Rows: ");
					tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
					//print.arrayList.print2DTEST(tmp);
					
					System.out.println("Remove all EssentialBits: ");
					tmp=essentialBits.removeAllEssential(tmp);
					//print.arrayList.print2DTEST(tmp);
					
					System.out.println("Remove all False Columns: ");
					tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
					//print.arrayList.print2DTEST(tmp);

					System.out.println("Remove False Rows: ");
					tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
					//print.arrayList.print2DTEST(tmp);
					
					System.out.println("Remove NOT dominating and Equal Rows: ");
			 		tmp=domRows.removeNotDominatingRowsAndEqualRows(tmp);
			 		//print.arrayList.print2DTEST(tmp);
			 		
					System.out.println("Remove all NOT dominating Columns and Eqaual Columns: ");
					tmp=domColumn.removeNotDominatingColumns(tmp);
					//print.arrayList.print2DTEST(tmp);
					
					for(int k=0; k<saveRow.size();k++){
						if(saveRow.get(k))
							counter1++;
					}
					tmp = heuristic.removeBitWithMostTrues(tmp);
					a++;
				}
				
			}
			return tmp;
		}
		*/
		public static void removeOneRowTrueColumns(ArrayList<ArrayList<Long>> tmp, int Row){
			/** Die uebergebene Reihe(Row) soll zur Loesung hinzugefuegt werden. Loeschen von einer Reihe sowie jeweils die dazugehoerigen Spalten.
			@author Jan Dennis Reimer		
			@version1.0
			@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
			@param int Row											Die Reihe, die zur Loesung hinzugefuegt werden soll. Und ausserdem geloescht werden soll
			@return													3D-ArrayList ohne diese Reihe und Spalten
			*/
			int c=63;
			for(int i= tmp.get(Row).size()-1; i>=0;){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(Row).get(i), c)==1){
					longData.validColumn.set(i,stuff.DirtyLittleHelpers.setBitAtPosition(longData.validColumn.get(i), c, false));
				}
				c--;
				if (c==0){
					c=63;
					i--;
				}
			}
			longData.validRow.set(Row, false);
			solution.set(Row, true);
		}
		
		
		public static void removeColumn(ArrayList<ArrayList<Long>> tmp, int column){
			/** Spalte die geloescht werden soll
			@author Jan Dennis Reimer		
			@version1.0
			@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
			@param int Column										Spalte column wird geloescht.
			@return													3D-ArrayList ohne diese Spalte
			*/
			int c=0;
			int d=0;
			for (int i=0; i<column; i++){
				c++;
				if(c==63){
					c=0;
					d++;
				}
			}
			longData.validColumn.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(longData.validColumn.get(d), c, false));
		}
		public static void removeColumn(ArrayList<ArrayList<Long>> tmp, int d, int c){
			/** Spalte die geloescht werden soll
			@author Jan Dennis Reimer		
			@version1.0
			@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
			@param int Column										Spalte column wird geloescht.
			@return													3D-ArrayList ohne diese Spalte
			*/
			longData.validColumn.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(longData.validColumn.get(d), c, false));
		}
		public static void removeRow (ArrayList<ArrayList<Long>> tmp, int row, boolean solutionBit){
			/** Reihe die geloescht werden soll
			@author Jan Dennis Reimer		
			@version1.0
			@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
			@param int row											Reihe row wird geloescht.
			@param boolean solutionBit								Gibt an, ob es sich um ein Bit handelt, dass auch zur Loesung hinzugefuegt werden muss.
			@return													3D-ArrayList ohne diese Spalte
			*/
			
			int save=0;
			for(int j=0; j<longData.validRow.size() && j<=row+save;j++){			//Um die richtigen Zeilen zu speichern
				if(!longData.validRow.get(j))										//Berechnung von save, bzw Berechnung wie viele trues schon in saveRow gespeichert sind
					save++;
			}
			longData.validRow.set(row+save, false);
			if (solutionBit)
				solution.set(row+save, true);
			save=0;
		}
	}
