package LongRemovingBits;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import outputData.printData;
import readdata.longData;
import readdata.make1DatafileLong;
import java.io.PrintWriter;
public class removingBits {
		public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
		public static String circuits= "C:/Users/Dennis/git/Minimization/";
		//public static String circuits="/home/dj0804/Minimization/";
		
		public static void main (String [] args) throws IOException{
			long startTime = System.nanoTime();
			long endTime ;long duration;
			ArrayList<ArrayList<Long>> tmp; 
			//File f = new File(circuits+"Schaltungen/");	
			File f = new File(circuits+"TEST/");
			for(File files : f.listFiles()){
				tmp= new ArrayList<ArrayList<Long>>();
				System.out.println(files.getName());
				longData.testpfad= circuits+"TEST/"+files.getName();
				//longData.testpfad= circuits+"Schaltungen/"+files.getName();
				longData.protokoll= circuits + "logs/"+files.getName();
				longData.results= circuits + "results/"+files.getName();
				
				tmp=make1DatafileLong.returnbigList();
				
				essentialdominating(tmp);
				
				printData.ausgabeindatei();
				
				PrintWriter writer = new PrintWriter(longData.protokoll+"/Zusammenfassend.txt");
		        writer.append("Start "+files.getName()+ "\n");
		        writer.append("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp)+ "\n");
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
				writer.append("Number of False in Solution: "+numberOfFalseinSolution()+ "\n");
				System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
		        writer.append("Number of Trues in Solution: "+numberOfTruesinSolution()+ "\n");
				System.out.println("Number of Trues in Solution: "+numberOfTruesinSolution());
		        writer.append("\n");
				System.out.println();
				writer.append("validRowAllFalse: "+validRowAllFalse()+ "\n");
				System.out.println("validRowAllFalse: "+validRowAllFalse());
				writer.append("everyFailurecovered: "+pruefen.solution.datacorrect(tmp)+ "\n");
				System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: "+duration+ "\n");
				writer.close();
			}
			/*
			
			//System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
			
			//longData.printLongPattern(tmp);	
			essentialdominating(tmp);
			printData.ausgabeindatei();
			System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));		
			System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
			System.out.println("Number of Trues in Solution: "+numberOfTruesinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+validRowAllFalse());*/
			
			
			
		}
		public static int numberOfTruesinSolution(){
			/** Gibt die Anzahl der Trues in Solution zurueck
			@author Jan Dennis Reimer		
			@version1.0
			@return													Anzahl der D-Bits
			*/
			int counter=0;
			for(int j=0;j<solution.size();j++){
				if(solution.get(j))
					counter++;
			}
			return counter;
		}
		public static int numberOfFalseinSolution(){
			/** Gibt die Anzahl der False in solution zurueck
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
		public static int numberOfvalidRows(){
			/**  ueberpruefung ob in saveRow nur true drin steht. Wenn ja, gebe true zurueck
			@author Jan Dennis Reimer		
			@version1.0
			@return									alles true gebe true zurueck, else false				-
			*/
			int counter=0;
			for (int i=0; i<longData.validRow.size();i++){
				if(longData.validRow.get(i))
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
		
		public static void essentialdominating(ArrayList<ArrayList<Long>> tmp) throws IOException{
			// Fuer das Protokoll: START
			
			PrintWriter writer = new PrintWriter( longData.protokoll +"/protokoll.txt");
			
            writer.append("Start");
            long startTime = System.nanoTime();
			long endTime ;long duration;
			// ENDE
            
			int counter=0;
			int counter1=2;
			int a=0;
			while(!validRowAllFalse()){
				while(counter!=counter1){
					writer.append("\n"+"Equal? = "+ counter + " " + counter1+"\n");
					System.out.println("Equal? = "+ counter + " " + counter1);
					counter=0;
					writer.append("Schritt: "+a+"\n");
					System.out.println("Schritt: "+a);
					for(int x=0; x<longData.validRow.size();x++){
						if(longData.validRow.get(x))
							counter++;
					}
					counter1=0;
					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");
					
					writer.append("Remove all False Columns: " + "\n");
					System.out.println("Remove all False Columns: ");
					falseRowsAndColumns.RemoveFalseColumn(tmp);
					//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
					
					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");	
					
					writer.append("Remove False Rows: "+ "\n");
					System.out.println("Remove False Rows: ");
					falseRowsAndColumns.RemoveFalseRows(tmp);											
					//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
					
					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");
					
					writer.append("Remove all EssentialBits: "+ "\n");
					System.out.println("Remove all EssentialBits: ");
					essentialBits.removeAllEssential(tmp);
					//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
					
					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");

					writer.append("Remove NOT dominating and Equal Rows: "+ "\n");
					System.out.println("Remove NOT dominating and Equal Rows: ");
			 		domRows.removeNotDominatingRowsAndEqualRows(tmp);
			 		//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
			 		
			 		endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");

					writer.append("Remove all NOT dominating Columns and Eqaual Columns: "+ "\n");
					System.out.println("Remove all NOT dominating Columns and Eqaual Columns: ");
					domColumn.removeNotDominatingColumns(tmp);
					//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
					
					for(int k=0; k<longData.validRow.size();k++){
						if(longData.validRow.get(k))
							counter1++;
					}	
					
					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: "+duration+ "\n");
					
					writer.append("numberOfvalidRows: "+	numberOfvalidRows()+ "\n");
					writer.append("longData.validRow.size(): "+	longData.validRow.size()+ "\n");
					System.out.println("numberOfvalidRows: "+	numberOfvalidRows());
					System.out.println("longData.validRow.size(): "+	longData.validRow.size() );
					a++;
				}
				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: "+duration+ "\n");
				
				writer.append("removeBitWithMostTrues: "+ "\n");
				System.out.println("removeBitWithMostTrues: ");
				heuristic.removeBitWithMostTrues(tmp);
				//writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
				counter=0;
				counter1=2;
				writer.append("numberOfvalidRows: "+	numberOfvalidRows()+ "\n");
				System.out.println("numberOfvalidRows: "+	numberOfvalidRows());
				
				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: "+duration+ "\n");
			}
			writer.close();
		}
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
			
			longData.validRow.set(row, false);
			if (solutionBit)
				solution.set(row, true);
		}
	}
