package readdata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;

import LongRemovingBits.removingBits;
public class make1DatafileLong {
		public static ArrayList<Integer> numberOfTruesInRow = new ArrayList<Integer>();
		public static ArrayList<ArrayList<Integer>> numberOfTruesInColumn = new ArrayList<ArrayList<Integer>>();
		//Speicher fuer numberoftrues:
		public static ArrayList<ArrayList<Integer>> failureMem = new ArrayList<ArrayList<Integer>>();
		public static int counter =0;
		
		/**
		 * @return Gibt eine Ueberdeckungstabelle zurueck indem jede Datei untereinander geschrieben wird.
		 * @throws IOException
		 */
		public static ArrayList<DBit> returnbigList() throws IOException{
			ArrayList<DBit> a = new ArrayList<DBit>();
			ArrayList<DBit> b = new ArrayList<DBit>();
			int max=0;
			boolean t = true; //Um es nur einmal zu berechnen
			int c=0;
			int k=0;
			float abc;
			int idx=0;
			File folder = new File(longData.testpfad);
			for( File file : folder.listFiles() ){
				if(t && file.getName().contains(("behavior"))){
					max=readingdata.numberOfFailures(file.getName());
					
					t=false;
					//Initialisierung von validColumn
					abc= max/64;
					idx= (int) abc +1;
					//System.out.println(a+"\t"+ idx);
					for(int j=0;j<idx; j++){
						readdata.longData.validColumn.add(0L);
						numberOfTruesInColumn.add(new ArrayList<Integer>());
						for (int column=0; column<64; column++){
							numberOfTruesInColumn.get(j).add(0);
						}
					}
					for(int i=0; i<=max; i++){
						readdata.longData.validColumn.set(k, stuff.DirtyLittleHelpers.setBitAtPosition(readdata.longData.validColumn.get(k), c, true));
						c++;
						if(c==64 ){
							c=0;
							k++;
						}
					}//Ende Initialisierung von validColumn
					System.out.println("#Fehler: "+ max );
					System.out.println("#d: "+ idx );
				}
				if (file.getName().contains(("behavior"))) {
					System.out.println(file.getName());
					a = pattern(file.getName(), idx);
					System.out.println("#D-Bits hinzugefuegt: " + a.size());
					b.addAll(a);
					// System.out.println("ValidColumn: " +
					// readdata.longData.validColumn.size() + " #Columns: "+
					// a.get(0).getList().size() );
			}
				
			}
			return b;
		}
		
		/**Gibt immer ein Testmuster zurueck in einer ArrayList	
		@return		Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
		*/
		public static ArrayList<DBit> pattern (String testfile, int max) throws IOException{
			ArrayList<DBit> pattern= new ArrayList<DBit>();
			ArrayList<Long> tmp1= new ArrayList<Long>();
			DBit DB ;
			String b = "";
//			System.out.println(longData.testpfad +"/"+testfile);
			GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(longData.testpfad +"/"+testfile));
			BufferedReader s = new BufferedReader(new InputStreamReader(gzip));
			
			while ((b = s.readLine()) != null){					//Zwischenspeicherung der aktuellen Zeile
				if(b.contains("{f")){	
					tmp1= readdata.longData.dbitcoveragerow(b,max);
					//System.out.println(b);
					DB = new DBit(counter, true,tmp1);
//					if (isdominatedRow(pattern, DB)) {
//						
//					} else {
						pattern.add(DB);
						numberOfTruesInRow.add(longData.truecounter);
						for (int index = 0; index < failureMem.get(0).size(); index++) {
							numberOfTruesInColumn.get(failureMem.get(0).get(index)).set(failureMem.get(1).get(index),
									numberOfTruesInColumn.get(failureMem.get(0).get(index))
											.get(failureMem.get(1).get(index)) + 1);
						}
//					}
					counter++;
					longData.truecounter=0;
					LongRemovingBits.removingBits.solution.add(false);
				}
			}
			s.close();
			gzip.close();
			return pattern;
		}
		/**Gibt immer ein Testmuster zurueck in einer ArrayList	
		@return		Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
		*/
		public static ArrayList<DBit> patternwithdomination (long startTime) throws IOException{
			ArrayList<DBit> b = new ArrayList<DBit>();
			int max=0;
			boolean t = true; //Um es nur einmal zu berechnen
			int c=0;
			int k=0;
			float abc;
			int idx=0;
			long endTime ;float duration;
			boolean stopdomination=true;
			String testfile;
			File folder = new File(longData.testpfad);
			for( File file : folder.listFiles() ){
				if(file.getName().contains(("behavior"))){
					if(t){
						max=readingdata.numberOfFailures(file.getName());
						
						t=false;
						//Initialisierung von validColumn
						abc= max/64;
						idx= (int) abc +1;
						//System.out.println(a+"\t"+ idx);
						for(int j=0;j<idx; j++){
							readdata.longData.validColumn.add(0L);
							numberOfTruesInColumn.add(new ArrayList<Integer>());
							for (int column=0; column<64; column++){
								numberOfTruesInColumn.get(j).add(0);
							}
						}
						for(int i=0; i<=max; i++){
							readdata.longData.validColumn.set(k, stuff.DirtyLittleHelpers.setBitAtPosition(readdata.longData.validColumn.get(k), c, true));
							c++;
							if(c==64 ){
								c=0;
								k++;
							}
						}//Ende Initialisierung von validColumn
						System.out.println("#Fehler: "+ max );
						System.out.println("#d: "+ idx );
					}
				System.out.println(file.getName());
				testfile = file.getName();
				max = idx;
				ArrayList<Long> tmp1 = new ArrayList<Long>();
				DBit DB;
				String bc = "";
				GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(longData.testpfad + "/" + testfile));
				BufferedReader s = new BufferedReader(new InputStreamReader(gzip));
				while ((bc = s.readLine()) != null) { // Zwischenspeicherung der
														// aktuellen Zeile
					if (bc.contains("{f")) {
						tmp1 = readdata.longData.dbitcoveragerow(bc, max);
//						System.out.println(bc + " " + max);
						DB = new DBit(counter, true, tmp1);
						if(stopdomination){
							if ( isdominatedRow(b, DB)) {
								
							} else {
								b.add(DB);
								numberOfTruesInRow.add(longData.truecounter);
								for (int index = 0; index < failureMem.get(0).size(); index++) {
									numberOfTruesInColumn.get(failureMem.get(0).get(index)).set(
										failureMem.get(1).get(index),
										numberOfTruesInColumn.get(failureMem.get(0).get(index))
												.get(failureMem.get(1).get(index)) + 1);
								}
							}
						} else{
							b.add(DB);
							numberOfTruesInRow.add(longData.truecounter);
							for (int index = 0; index < failureMem.get(0).size(); index++) {
								numberOfTruesInColumn.get(failureMem.get(0).get(index)).set(
									failureMem.get(1).get(index),
									numberOfTruesInColumn.get(failureMem.get(0).get(index))
											.get(failureMem.get(1).get(index)) + 1);
							}
						}
						counter++;
						longData.truecounter = 0;
						LongRemovingBits.removingBits.solution.add(false);
						endTime = System.nanoTime();
						duration = (float) (endTime - startTime)/(1000000000)/60;
						if (duration> 12*60){
							stopdomination=false;
						}
					}
				}
				s.close();
				gzip.close();

				System.out.println("#D-Bits hinzugefuegt: " + b.size() +" von insgesamt"+ counter);

			}
		}
		return b;
	}
	public static boolean isequalRow(ArrayList<DBit> tmp, DBit tmp1) throws IOException {
		int counter=0;
		for (int row=0; row<tmp.size(); row++){
//			System.out.println(" HALLO");
			if (tmp.get(row).getValid()) {
				if (readdata.make1DatafileLong.numberOfTruesInRow
						.get(row) == longData.truecounter){
					for (int d = 0; d < tmp.get(row).getList().size(); d++) {
						if (tmp.get(row).getList().get(d)== tmp1.getList().get(d)) {
							counter++;
						}
					}
					if ((tmp.get(row).getList().size())==counter){
	//					System.out.println("Reihe "+ row + " is equal with new");
						return true;
					}
				}
			}
			counter=0;
		}
		return false;
	}
	/**
	 * Kontrolliert beim Einlesen, ob es die aktuelle Spalte eine dominierende
	 * ist.
	 * @param tmp	2D ArrayList(Ueberdeckungstabelle)(noch nicht fertige)
	 * @param tmp1 	potentieller Kandidat zur Aufnahme in der Ueberdeckungstabelle           
	 * @return 		Gibt zuruck, ob die Spalte dominiert wird.
	 * @throws IOException 
	 */
	public static boolean isdominatedRow(ArrayList<DBit> tmp, DBit tmp1) throws IOException {
		int counttrueA = 0;
		int counttrueB = 0;
		Long var;
		boolean dominationcounterA = false;
		boolean dominationA = true;
		boolean dominationcounterB = false;
		boolean dominationB = true;
		
		boolean isdominated = true;
//		if (!removingBits.validRowAllFalse(tmp)) {
			for (int row = tmp.size() - 1; row >= 0; row--) {
				if (tmp.get(row).getValid()) {
						for (int d = 0; d < tmp.get(0).getList().size(); d++) {
							var = (tmp1.getList().get(d) & tmp.get(row).getList().get(d) & longData.validColumn.get(d));
								if (readdata.make1DatafileLong.numberOfTruesInRow
										.get(row) >= longData.truecounter && dominationA) {
									if (var == (tmp1.getList().get(d) & longData.validColumn.get(d)) ) { //  row// dominierend																	
										counttrueA++;
//										if (var!=0){
//											dominationcounterA=true;
//										}
									}
								} else {
									dominationA=false;
								}
								if (readdata.make1DatafileLong.numberOfTruesInRow
										.get(row) <= longData.truecounter && dominationB) {
									if (var == (tmp.get(row).getList().get(d) & longData.validColumn.get(d))) { //  neu	// dominierend								
										counttrueB++;
//										if (var!=0){
//											dominationcounterA=true;
//										}
									}
								} else{
									dominationB=false;
								}
								if(!dominationA && !dominationB){
									isdominated=false;
									break;
								}
							}
							if (counttrueB == tmp.get(0).getList().size() && isdominated /*&& !dominationcounterB*/ && dominationB) {
//								System.out.println(" Reihe " + k + " ist dominiernd auf " + row);
//								LongRemovingBits.removingBits.counterremoveRows++;
								removingBits.removeRow(tmp, row, false);
								// Damit bei gleichen Zeilen nicht beide geloescht werden
								isdominated = false;	
							}
							if (counttrueA == tmp.get(0).getList().size() && isdominated /*&& !dominationcounterA*/ && dominationA) {
//								System.out.println(" Reihe " + row + " ist dominiernd auf " + neu);
//								LongRemovingBits.removingBits.counterremoveRows++;
								return true;
							}
						}
				isdominated=true;
				counttrueA = 0;
				counttrueB = 0;
				dominationcounterA = false;
				dominationA = true;
				dominationcounterB = false;
				dominationB = true;
			}
//		}
		return false;
	}
		
		
		
		
//		
//		int c = 0;
//		int counttrueA = 0;
//		int counttrueB = 0;
//		boolean isdominated = false;
//		boolean dominationcounterA = false;
//		boolean dominationcounterB = false;
//		if (!removingBits.validRowAllFalse(tmp)) {
//			for (int k = 0; k < tmp.size(); k++) {
//				if (tmp.get(k).getValid()) {
//					if ((readdata.make1DatafileLong.numberOfTruesInRow.get(k) <= longData.truecounter) ) {//|| !(tmp.get(k).getList().get(0)<=tmp1.getList().get(0))
//						dominationcounterB = true;
////						System.out.println(" tmp.get(k).getList().get(0)<=tmp1.getList().get(0): "+ (tmp.get(k).getList().get(0)<=tmp1.getList().get(0)));
//					}
//					if ((readdata.make1DatafileLong.numberOfTruesInRow.get(k) >= longData.truecounter)) { // || !(tmp.get(k).getList().get(0)>=tmp1.getList().get(0))
//						dominationcounterA = true;
//					}
//					for (int d = 0; d < tmp.get(0).getList().size() && !isdominated;) {
//						if (stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c) == 1) {
//							if (!dominationcounterA) {
//								if ((!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1
//										&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d), c) == 0))) {
////									System.out.println("numbver of trues: "
////											+ readdata.make1DatafileLong.numberOfTruesInRow.get(k));
//									if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d), c) == 1) {
//										counttrueA++;
//										if (counttrueA == longData.truecounter && !dominationcounterB) {
//											isdominated = true;
//											break;
//										}
//									}
//								} else {
//									dominationcounterA = true;
//								}
//							}
//							if (!dominationcounterB) {
//								if ((!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 0
//										&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d), c) == 1))) {
//									if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d),
//											c) == 1) {
//										counttrueB++;
//										if (counttrueB == readdata.make1DatafileLong.numberOfTruesInRow.get(k)
//												&& !dominationcounterA) {
//											isdominated = true;
//											break;
//										}
//									}
//								} else {
//									dominationcounterB = true;
//								}
//							}
//							if (dominationcounterA && dominationcounterB) {
//								isdominated = false;
//							}
//						}
//						c++;
//						if (c == 64) {
//							d++;
//							c = 0;
//						}
//					}
//					if (counttrueB >= 1 && !dominationcounterB && isdominated) {
//						System.out.println(" Reihe " + k + " ist dominiernd auf " + "neu");
//						return true;
//					}
//					if (counttrueA >= 1 && !dominationcounterA && isdominated) {
//						System.out.println(" Reihe " + " neu" + " ist dominiernd auf " + k);
//						removingBits.removeRow(tmp, k, false);
//					}
//					isdominated = true;
//					dominationcounterA = false;
//					dominationcounterB = false;
//					counttrueA = 0;
//					counttrueB = 0;
//					c = 0;
//				}
//			}
//		}
//		return false;
//	}
}