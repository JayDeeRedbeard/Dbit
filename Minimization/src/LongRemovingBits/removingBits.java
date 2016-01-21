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
		//public static String circuits= "C:/Users/Dennis/git/Minimization/";
		public static String circuits="/home/dj0804/workspace/Minimization/";
		
		public static void main (String [] args) throws IOException{
			//Programmablauf
			
			long startTime = System.nanoTime();
			long endTime ;long duration;
			ArrayList<ArrayList<Long>> tmp; 
			File f = new File(circuits+"Schaltungen/");	
			//File f = new File(circuits+"TEST/");
			for(File files : f.listFiles()){
				startTime = System.nanoTime();
				tmp= new ArrayList<ArrayList<Long>>();
				System.out.println(files.getName());
				//longData.testpfad= circuits+"TEST/"+files.getName();
				longData.testpfad= circuits+"Schaltungen/"+files.getName();
				longData.protokoll= circuits + "logs/"+files.getName();
				longData.results= circuits + "results/"+files.getName();
				longData.validRow=new ArrayList<Boolean>();
				longData.validColumn=new ArrayList<Long>();
				longData.validRowZwischenspeicher=new ArrayList<Boolean>();
				make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
				make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
				
				
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
		public static int numberOfvalidRows(ArrayList<ArrayList<Long>> tmp){
			int counter=0;
			for (int i=0; i<tmp.size();i++){
				if(longData.validRow.get(i))
					counter++;
			}
			return counter;
		}
		/**  
		@return		Gibt die Anzahl von ValidColumns zurueck 				-
		*/
		public static int numberOfvalidColumns(ArrayList<ArrayList<Long>> tmp){
			int counter=0;
			int c=0;
			for (int d=0; d<tmp.get(0).size();){
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
		@return	Wenn eine Reihe nur aus "false" besteht wird "True zurueckgegeben.				-
		*/
		public static boolean validRowAllFalse(){
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
		/**
		 * Ruft selbststaendig alle Funktionen auf, um nach den Quine McCluskey Algorithmus eine minimale(nicht optimale) Loesung zu finden
		 * @param tmp	Bekommt eine 2D-ArrayList uebergeben  		
		 * @throws IOException
		 */
		public static void essentialdominating(ArrayList<ArrayList<Long>> tmp) throws IOException{
		// Fuer das Protokoll: START

		PrintWriter writer = new PrintWriter(longData.protokoll + "/protokoll.txt");

		writer.append("Start");
		long startTime = System.nanoTime();
		long endTime;
		long duration;
		// ENDE

		int counter = 0;
		int counter1 = 2;
		int a = 0;
		while (!validRowAllFalse()) {
			while (counter != counter1) {
				while (counter != counter1) {
					while (counter != counter1) {
						writer.append("\n" + "Equal? = " + counter + " " + counter1 + "\n");
						System.out.println("Equal? = " + counter + " " + counter1);
						counter = 0;
						writer.append("Schritt: " + a + "\n");
						
						if(a>0){
							writer.append("RemoveEqual Rows: " + "\n");
							System.out.println("RemoveEqual Rows: ");
							domRows.removeEqualRows(tmp);
							
							writer.append("RemoveEqual Columns: " + "\n");
							System.out.println("RemoveEqual Columns: ");
							domColumn.removeEqualColumns(tmp);							
						}
						System.out.println("Schritt: " + a);
						for (int x = 0; x < longData.validRow.size(); x++) {
							if (longData.validRow.get(x))
								counter++;
						}
						counter1 = 0;
						endTime = System.nanoTime();
						duration = (endTime - startTime);
						writer.append("time: " + duration + "\n");

						writer.append("Remove all False Columns: " + "\n");
						System.out.println("Remove all False Columns: ");
						falseRowsAndColumns.RemoveFalseColumn(tmp);
						// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

						endTime = System.nanoTime();
						duration = (endTime - startTime);
						writer.append("time: " + duration + "\n");

						writer.append("Remove False Rows: " + "\n");
						System.out.println("Remove False Rows: ");
						falseRowsAndColumns.RemoveFalseRows(tmp);
						// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

						endTime = System.nanoTime();
						duration = (endTime - startTime);
						writer.append("time: " + duration + "\n");

						writer.append("Remove all EssentialBits: " + "\n");
						System.out.println("Remove all EssentialBits: ");
						essentialBits.removeAllEssential(tmp);
						// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

						endTime = System.nanoTime();
						duration = (endTime - startTime);
						writer.append("time: " + duration + "\n");

						for (int k = 0; k < longData.validRow.size(); k++) {
							if (longData.validRow.get(k))
								counter1++;
						}
						a++;
					}

					writer.append("Remove NOT dominating and Equal Rows: " + "\n");
					System.out.println("Remove NOT dominating and Equal Rows: ");
					domRows.dominatingRows(tmp);
					// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);

					endTime = System.nanoTime();
					duration = (endTime - startTime);
					writer.append("time: " + duration + "\n");
					counter1=0;
					for (int k = 0; k < longData.validRow.size(); k++) {
						if (longData.validRow.get(k))
							counter1++;
					}
				}

				writer.append("Remove all NOT dominating Columns and Eqaual Columns: " + "\n");
				System.out.println("Remove all NOT dominating Columns and Eqaual Columns: ");
				domColumn.dominatingColumns(tmp);
				// writer=longData.printLongPatternwithoutEmptySpace(tmp,writer);
				
				counter1=0;	
				for (int k = 0; k < longData.validRow.size(); k++) {
					if (longData.validRow.get(k))
						counter1++;
				}

				endTime = System.nanoTime();
				duration = (endTime - startTime);
				writer.append("time: " + duration + "\n");

				writer.append("numberOfvalidRows: " + numberOfvalidRows(tmp) + "\n");
				writer.append("longData.validRow.size(): " + longData.validRow.size() + "\n");
				System.out.println("numberOfvalidRows: " + numberOfvalidRows(tmp));
				System.out.println("longData.validRow.size(): " + longData.validRow.size());
			}
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
		public static void removeOneRowTrueColumns(ArrayList<ArrayList<Long>> tmp, int row){
			int c=63;
			for(int i= tmp.get(row).size()-1; i>=0;){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(i), c)==1){
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
		public static void removeColumn(ArrayList<ArrayList<Long>> tmp, int column){
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
				if (readdata.longData.validRow.get(row)) {
					if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c) == 1) {
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
		public static void removeColumn(ArrayList<ArrayList<Long>> tmp, int d, int c){
		// Anpassung von numberOfTruesRow/Column
		int counter = 0;
		int temp;
		for (int row = 0; row < tmp.size() && counter <= readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c); row++) {
			if (readdata.longData.validRow.get(row)) {
				if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c) == 1) {
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
		public static void removeRow (ArrayList<ArrayList<Long>> tmp, int row, boolean solutionBit){
			// Anpassung von numberOfTruesRow/Column
			int c=0;
			int counter=0;
			for(int d =0; d<tmp.get(row).size() && counter<= readdata.make1DatafileLong.numberOfTruesInRow.get(row);){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c)==1){
					if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1){
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
			longData.validRow.set(row, false);
			if (solutionBit)
				solution.set(row, true);
		}
	}
