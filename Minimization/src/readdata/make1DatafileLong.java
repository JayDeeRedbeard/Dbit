package readdata;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import LongRemovingBits.removingBits;
public class make1DatafileLong {
		public static ArrayList<Integer> numberOfTruesInRow = new ArrayList<Integer>();
		public static ArrayList<ArrayList<Integer>> numberOfTruesInColumn = new ArrayList<ArrayList<Integer>>();
		//Speicher fuer numberoftrues:
		public static ArrayList<ArrayList<Integer>> failureMem = new ArrayList<ArrayList<Integer>>();
		
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
			int idx=0;;
			File folder = new File(longData.testpfad);
			for( File file : folder.listFiles() ){
				if(t){
					max=readingdata.numberOfFailures(file.getName());
					
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
					t=false;
				}
				System.out.println( file.getName() );
				a=pattern(file.getName(), idx);
				System.out.println("#D-Bits hinzugefuegt: "+a.size());
				b.addAll(a);
//				System.out.println("ValidColumn: " + readdata.longData.validColumn.size() + " #Columns: "+ a.get(0).getList().size() );
				
			}
			System.out.println("D-Bits: "+b.size());
			return b;
		}
		/**Gibt immer ein Testmuster zurueck in einer ArrayList	
		@return		Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
		*/
		public static ArrayList<DBit> pattern (String testfile, int max) throws IOException{
			ArrayList<DBit> pattern= new ArrayList<DBit>();
			ArrayList<Long> tmp1= new ArrayList<Long>();
			DBit DB ;
			int counter=0;
			//int counter=0;
			String b = "";
			Scanner s = new Scanner(new File(longData.testpfad +"/"+testfile));				
			while (s.hasNextLine()){									
				Scanner tmp= new Scanner(s.nextLine());
				b=tmp.nextLine(); 															//Zwischenspeicherung der aktuellen Zeile
				//System.out.println(b.contains("{f")+" "+ b);
//				if(b.charAt(0)=='{'){  //Dies bringt keine Verbesserung
				if(b.contains("{f")){	
					tmp1= readdata.longData.dbitcoveragerow(b,max);
					//System.out.println(b);
					DB = new DBit(counter, true,tmp1);
					if (isdominatedRow(pattern, DB)) {
						// Zwischenspeicher ist da, um keinen
						// Informationsverlust zu haben, da dominierte Zeilen
						// geloescht werden
						longData.validRowZwischenspeicher.add(false);
					} else {
						pattern.add(DB);
//						longData.validRow.add(true);
						longData.validRowZwischenspeicher.add(true);
						numberOfTruesInRow.add(longData.truecounter);
						for (int index = 0; index < failureMem.get(0).size(); index++) {
							numberOfTruesInColumn.get(failureMem.get(0).get(index)).set(failureMem.get(1).get(index),
									numberOfTruesInColumn.get(failureMem.get(0).get(index))
											.get(failureMem.get(1).get(index)) + 1);
						}
					}
					counter++;
					longData.truecounter=0;
					LongRemovingBits.removingBits.solution.add(false);
				}
				tmp.close();
			}
			s.close();
			return pattern;
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
		int c = 0;
		int counttrueA=0;
		int counttrueB=0;
		boolean isdominated = true;
		boolean dominationcounterA=false;
		boolean dominationcounterB=false;
		for (int k = 0; k < tmp.size(); k++) {
			if (tmp.get(k).getValid()) {
				for (int d = 0; d < tmp.get(0).getList().size() && isdominated;) {
					if (!dominationcounterA) {
						if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1
								&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d), c) == 0) && 
								readdata.make1DatafileLong.numberOfTruesInRow.get(k) >= 
								longData.truecounter) {
							if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d),
									c) == 1) {
								counttrueA++;
								if (counttrueA == readdata.make1DatafileLong.numberOfTruesInRow
										.get(k) ) {
									isdominated = true;
								}
							}	
						} else {
							dominationcounterA = true;
						}
					}
					if (!dominationcounterB) {
						if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 0
								&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d), c) == 1) 
								&& readdata.make1DatafileLong.numberOfTruesInRow.get(k) 
								<= longData.truecounter) {
							if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.getList().get(d),c) == 1) {
								counttrueB++;
								if (counttrueB == longData.truecounter) {
									isdominated = true;
								}
							}
						} else {
							dominationcounterB = true;
						}
					}
					if (dominationcounterA && dominationcounterB) {
						isdominated = false;
					}
					
					c++;
					if (c == 64) {
						d++;
						c = 0;
					}
				}
				if (counttrueA >= 1 && !dominationcounterA && isdominated) {
					//System.out.println(" Reihe " + k + " ist dominiernd auf " +"neu" );
					return true;
				}
				if (counttrueB >= 1 && !dominationcounterB && isdominated) {
					//System.out.println(" Reihe " +" neu" + " ist dominiernd auf " +k );
					removingBits.removeRow(tmp, k, false);
					return false;
				}
				isdominated = true;
				dominationcounterA = false;
				dominationcounterB = false;
				counttrueA = 0;
				counttrueB = 0;
			}
		}
		return false;
	}
}