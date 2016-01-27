package LongRemovingBits;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import outputData.printData;
import readdata.*;
import readdata.make1DatafileLong;
import java.io.PrintWriter;
public class removingBits {
		public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
		public static String circuits= "C:/Users/Dennis/git/Minimization/";
//		public static String circuits="/home/dj0804/workspace/Minimization/";
		
		public static void main (String [] args) throws IOException{
			//Programmablauf
			
			long startTime = System.nanoTime();
			long endTime ;long duration;
			ArrayList<DBit> tmp; 
			File f = new File(circuits+"Schaltungen/");	
			//File f = new File(circuits+"TEST/");
			for(File files : f.listFiles()){
				startTime = System.nanoTime();
				tmp= new ArrayList<DBit>();
				System.out.println(files.getName());
				//longData.testpfad= circuits+"TEST/"+files.getName();
				longData.testpfad= circuits+"Schaltungen/"+files.getName();
				longData.protokoll= circuits + "logs/"+files.getName();
				longData.results= circuits + "results/"+files.getName();
				longData.validColumn=new ArrayList<Long>();
				longData.validRowZwischenspeicher=new ArrayList<Boolean>();
				make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
				
				tmp=make1DatafileLong.returnbigList();
				PrintWriter writer = new PrintWriter(longData.protokoll+"/Zusammenfassend.txt");
				writer.append("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp)+ "\n");
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
				
				
				essentialdominating(tmp);
				
				printData.ausgabeindatei();
				
				writer.append("everyFailurecovered: "+pruefen.solution.everyFailurecoveredHypergraph(tmp)+ "\n");
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecoveredHypergraph(tmp));	
		        writer.append("Start "+files.getName()+ "\n");
				writer.append("Number of False in Solution: "+numberOfFalseinSolution()+ "\n");
				System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
		        writer.append("Number of Trues in Solution: "+numberOfTruesinSolution()+ "\n");
				System.out.println("Number of Trues in Solution: "+numberOfTruesinSolution());
		        writer.append("\n");
				System.out.println();
				writer.append("validRowAllFalse: "+validRowAllFalse(tmp)+ "\n");
				System.out.println("validRowAllFalse: "+validRowAllFalse(tmp));
				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: "+duration+ "\n");
				writer.close();
			}
		}
		/** 
		* @return	Gibt die Anzahl der Trues in Solution zurueck	
		*/
		public static int numberOfTruesinSolution(){
			
			int counter=0;
			for(int j=0;j<solution.size();j++){
				if(solution.get(j))
					counter++;
			}
			return counter;
		}
		/** 
		@return		Gibt die Anzahl der False in solution zurueck													
		*/
		public static int numberOfFalseinSolution(){
			int counter=0;
			for(int j=0;j<solution.size();j++){
				if(solution.get(j)==false)
					counter++;
			}
			return counter;
		}
		/**  
		@return		Gibt die Anzahl von ValidRows zurueck 				-
		*/
		public static int numberOfvalidRows(ArrayList<DBit> tmp){
			int counter=0;
			for (int i=0; i<tmp.size();i++){
				if(tmp.get(i).getValid())
					counter++;
			}
			return counter;
		}
		/**  
		@return		Gibt die Anzahl von ValidColumns zurueck 				-
		*/
		public static int numberOfvalidColumns(ArrayList<DBit> tmp){
			int counter=0;
			int c=0;
			for (int d=0; d<tmp.get(0).getList().size();){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1)
					counter++;
				c++;
				if(c==64){
					d++;
					c=0;
				}
			}
			return counter;
		}
		/**  
		@return		Gibt die Anzahl von NOTValidColumns zurueck 				-
		*/
		public static int numberOfnotvalidColumns(ArrayList<DBit> tmp){
			int counter=0;
			int c=0;
			for (int d=0; d<tmp.get(0).getList().size();){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 0)
					counter++;
				c++;
				if(c==64){
					d++;
					c=0;
				}
			}
			return counter;
		}
		
		/**  
		@return	Wenn eine Reihe nur aus "false" besteht wird "True zurueckgegeben.				-
		*/
		public static boolean validRowAllFalse(ArrayList<DBit> tmp){
			int counter=0;
			for (int i=0; i<tmp.size();i++){
				if(!tmp.get(i).getValid())
					counter++;
			}
			if(tmp.size()==counter)
				return true;
			else 
				return false;
				
		} 
		/**
		 * Ruft selbststaendig alle Funktionen auf, um nach den Quine McCluskey Algorithmus eine minimale(nicht optimale) Loesung zu finden
		 * @param tmp	Bekommt eine 2D-ArrayList uebergeben  		
		 * @throws IOException
		 */
		public static void essentialdominating(ArrayList<DBit> tmp) throws IOException{
		// Fuer das Protokoll: START

		PrintWriter writer = new PrintWriter(longData.protokoll + "/protokoll.txt");

		writer.append("Start");
		long startTime = System.nanoTime();
		long endTime;
		long duration;
		// ENDE

		int counter = 0;
		int counter1 = 2;
		int counter1EqualRows = 2;
		int counter1FalseRows = 2;
		int counterColumns=0;
		int counter1Columns=2;
		int a = 0;
		while (!validRowAllFalse(tmp)) {
			counterColumns = 0;
			counter1Columns = 2;
			while (counterColumns != counter1Columns) {
				// Remove all NOT dominating Columns and Eqaual Columns
				counter = 0;
				counter1 = 2;
				while (counter != counter1) {
					// Remove NOT dominating and Equal Rows
					counterColumns = 0;
					counter1Columns = 2;
					while (counterColumns != counter1Columns) {
						// Remove Equal Columns
						counter = 0;
						counter1EqualRows = 2;
						while (counter != counter1EqualRows) {
							// Remove Equal Rows
							counter = 0;
							counter1FalseRows = 2;
							while (counter != counter1FalseRows) {
								// Remove FalseRows
								counter = 0;
								counter1 = 2;
								while (counterColumns != counter1Columns || counter != counter1) {
									// Remove all False Columns
									writer.append("\n" + "Equal? = " + counter + " " + counter1 + "\n");
									System.out.println("Equal? = " + counter + " " + counter1);
									
									writer.append("Schritt: " + a + "\n");
									System.out.println("Schritt: " + a);
									
									if(a>0)	{
										tmp = removeRowsColumns.removeColumnsfromList(tmp);
//										tmp = removeRowsColumns.removeRowsfromList(tmp);
									}
									System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse(tmp));
									
									counterColumns = removingBits.numberOfvalidColumns(tmp);
									counter= removingBits.numberOfvalidRows(tmp);
									
									if(a==0){
										writer.append("Remove all False Columns: " + "\n");
										System.out.println("Remove all False Columns: ");
										falseRowsAndColumns.RemoveFalseColumn(tmp);
									}
									endTime = System.nanoTime();
									duration = (endTime - startTime);
									writer.append("time: " + duration + "\n");

									writer.append("Remove all EssentialBits: " + "\n");
									System.out.println("Remove all EssentialBits: ");
									essentialBits.removeAllEssential(tmp);
									// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

									counter1 = 0;
									endTime = System.nanoTime();
									duration = (endTime - startTime);
									writer.append("time: " + duration + "\n");

									writer.append("Remove all False Columns: " + "\n");
									System.out.println("Remove all False Columns: ");
									falseRowsAndColumns.RemoveFalseColumn(tmp);
									// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
									counter1Columns = removingBits.numberOfvalidColumns(tmp);
									counter1 = removingBits.numberOfvalidRows(tmp);
									a++;
								}
								// ENDE Remove all False Columns
								endTime = System.nanoTime();
								duration = (endTime - startTime);
								writer.append("time: " + duration + "\n");

								writer.append("Remove False Rows: " + "\n");
								System.out.println("Remove False Rows: ");
								falseRowsAndColumns.RemoveFalseRows(tmp);
								// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

								counter1FalseRows = removingBits.numberOfvalidRows(tmp);
							}
							// ENDE Remove False Rows

							endTime = System.nanoTime();
							duration = (endTime - startTime);
							writer.append("time: " + duration + "\n");

							writer.append("RemoveEqual Rows: " + "\n");
							System.out.println("RemoveEqual Rows: ");
							domRows.removeEqualRows(tmp);

							counter1EqualRows = removingBits.numberOfvalidRows(tmp);
						}
						writer.append("RemoveEqual Columns: " + "\n");
						System.out.println("RemoveEqual Columns: ");
						domColumn.removeEqualColumns(tmp);
						counter1Columns = removingBits.numberOfvalidColumns(tmp);
					}
					// ENDE Remove Equal Columns

					writer.append("Remove NOT dominating and Equal Rows: " + "\n");
					System.out.println("Remove NOT dominating and Equal Rows: ");
					domRows.dominatingRows(tmp);
					// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: " + duration + "\n");
					counter1 = removingBits.numberOfvalidRows(tmp);
				}
				// ENDE Remove NOT dominating and Equal Rows

				writer.append("Remove all NOT dominating Columns and Eqaual Columns: " + "\n");
				System.out.println("Remove all NOT dominating Columns and Eqaual Columns: ");
				domColumn.dominatingColumns(tmp);
				// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
				counter1Columns = removingBits.numberOfvalidColumns(tmp);

				counter1 = removingBits.numberOfvalidRows(tmp);

				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: " + duration + "\n");

				writer.append("numberOfvalidRows: " + numberOfvalidRows(tmp) + "\n");
				writer.append("longData.validRow.size(): " + longData.validRow.size() + "\n");
				System.out.println("numberOfvalidRows: " + numberOfvalidRows(tmp));
				System.out.println("longData.validRow.size(): " + longData.validRow.size());
			}
			// ENDE Remove all NOT dominating Columns and Eqaual Columns

			endTime = System.nanoTime();
			duration = (endTime - startTime);
			writer.append("time: " + duration + "\n");

			writer.append("removeBitWithMostTrues: " + "\n");
			System.out.println("removeBitWithMostTrues: ");
			heuristic.removeBitWithMostTrues(tmp);
			// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
			counter = 0;
			counter1 = 2;
			writer.append("numberOfvalidRows: " + numberOfvalidRows(tmp) + "\n");
			System.out.println("numberOfvalidRows: " + numberOfvalidRows(tmp));

			endTime = System.nanoTime();
			duration = (endTime - startTime);
			writer.append("time: " + duration + "\n");
		}
		writer.close();
	}
		/** Die uebergebene Reihe(Row) soll zur Loesung hinzugefuegt werden. Loeschen von einer Reihe sowie jeweils die dazugehoerigen Spalten.
		@param tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
		@param row											Die Reihe, die zur Loesung hinzugefuegt werden soll. Und ausserdem geloescht werden soll.
		@return													2D-ArrayList ohne diese Reihe und Spalten (wird durch ValidRow/ValidColumn ausgeblendet).
		*/
		public static void removeOneRowTrueColumns(ArrayList<DBit> tmp, int row){
			int c=63;
			for(int i= tmp.get(row).getList().size()-1; i>=0;){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(i), c)==1){
					removeColumn(tmp,i,c);
				}
				c--;
				if (c==-1){
					c=63;
					i--;
				}
			}
			removeRow(tmp,row,true);
		}
		/** Spalte die geloescht werden soll
		@param tmp		Bekommt die 2D-ArrayList uebergeben	(ueberdeckungstabelle)
		@param Column	Spalte column wird geloescht.
		@return			2D-ArrayList ohne diese Spalte
		*/
		public static void removeColumn(ArrayList<DBit> tmp, int column){
			/** Spalte die geloescht werden soll
			@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList uebergeben	(ueberdeckungstabelle)
			@param int Column										Spalte column wird geloescht.
			@return													3D-ArrayList ohne diese Spalte
			*/
			int c=0;
			int d=0;
			for (int i=0; i<column; i++){
				c++;
				if(c==64){
					c=0;
					d++;
				}
			}
			// Anpassung von numberOfTruesRow/Column
			int counter = 0;
			for (int row = 0; row < tmp.size() && counter <= readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c); row++) {
				if (tmp.get(row).getValid()) {
					if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c) == 1) {
						counter++;
						readdata.make1DatafileLong.numberOfTruesInRow.set(row,
								(readdata.make1DatafileLong.numberOfTruesInRow.get(row) - 1));
					}
				}
			}
			readdata.make1DatafileLong.numberOfTruesInColumn.get(d).set(c, -1);
			longData.validColumn.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(longData.validColumn.get(d), c, false));
		}

		/** Spalte die geloescht werden soll
		@param Column	Spalte column wird geloescht.
		*/
		public static void removeColumn(ArrayList<DBit> tmp, int d, int c){
		// Anpassung von numberOfTruesRow/Column
		int counter = 0;
		int temp=0;
		for (int row = 0; row < tmp.size() && counter <= readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c); row++) {
			if (tmp.get(row).getValid()) {	
				if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c) == 1) {
					counter++;
					temp= readdata.make1DatafileLong.numberOfTruesInRow.get(row);
					readdata.make1DatafileLong.numberOfTruesInRow.set(row,( temp- 1));
				}
			}
		}
		readdata.make1DatafileLong.numberOfTruesInColumn.get(d).set(c, -1);
		longData.validColumn.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(longData.validColumn.get(d), c, false));
		}
		/** Reihe die geloescht werden soll.
		@param row			Reihe row wird geloescht.
		@param solutionBit	Gibt an, ob es sich um ein D-Bit handelt, dass auch zur Loesung hinzugefuegt werden muss.
		*/
		public static void removeRow (ArrayList<DBit> tmp, int row, boolean solutionBit){
			// Anpassung von numberOfTruesRow/Column
			int c=0;
			int counter=0;
//			int offset=0;
//			int offset1=0;
//			for (int i=0; i<=row;i++){
//				if(!readdata.longData.validRow.get(i))
//					offset1++;
//			}
//			for (int i=0; i<=row+offset1;i++){
//				if(!readdata.longData.validRow_tmp.get(i))
//					offset++;
//			}
			for(int d =0; d<tmp.get(row).getList().size() && counter<= readdata.make1DatafileLong.numberOfTruesInRow.get(row);){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c)==1){
					if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c)==1){
						counter++;
						readdata.make1DatafileLong.numberOfTruesInColumn.get(d).set(c, (readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)-1));
					}
				}
				c++;
				if(c==64){
					c=0;
					d++;
				}
			}
			readdata.make1DatafileLong.numberOfTruesInRow.set(row, -1);
			if (solutionBit)
				solution.set(tmp.get(row).getValue(), true);
			tmp.remove(row);
			readdata.make1DatafileLong.numberOfTruesInRow.remove(row);
		}
	}
