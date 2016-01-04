package readdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class longData {
	public static ArrayList<Long> validColumn = new ArrayList<Long>();
	public static ArrayList<Boolean> validRow = new ArrayList<Boolean>();
	public static ArrayList<Boolean> validRowZwischenspeicher = new ArrayList<Boolean>();
	
	public static String testpfad = "C:/Users/Dennis/git/Minimization/src/TestDaten";
	//public static String testpfad = "C:/Users/Dennis/git/Minimization/src/b14_1";
	//public static String testpfad = "/home/dj0804/Downloads/minimization/src/src/b14_1";
	public static void main (String [] args) throws IOException{
		
		//ArrayList<ArrayList<Long>> a= pattern(readingdata.testfile+".txt");		
		//printLongPattern(a);
		
		//for(int i=0; i<64; i++)
		//	System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(validColumn.get(0), i)+" ");
		//
	}
	public static void printLongPatternwithoutEmptySpace(ArrayList<ArrayList<Long>> a)throws IOException{
		/**			 k=0  k=1  k=2  k=3	 k=4  k=5
		 * i=0		[...][...][...][...][...][...]   
		 * i=1		[...][...][...][...][...][...]
		 * i=2		[...][...][...][...][...][...]
		 * i=...	[...][...][...][...][...][...]
		 * [...] j=0...63
		 */
		
		for (int i=0; i<a.size(); i++){
			if(validRow.get(i)){
			for(int k=0; k<a.get(i).size() ; k++){
				for(int j=0; j<64  ; j++){
					if (stuff.DirtyLittleHelpers.getBitAtPosition(validColumn.get(k), j)== 1)
						System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(a.get(i).get(k), j)+" ");
				}
			}
			System.out.println();
			}
			
		}
		System.out.println();
	}
	public static void printLongPattern(ArrayList<ArrayList<Long>> a)throws IOException{
		/**			 k=0  k=1  k=2  k=3	 k=4  k=5
		 * i=0		[...][...][...][...][...][...]   
		 * i=1		[...][...][...][...][...][...]
		 * i=2		[...][...][...][...][...][...]
		 * i=...	[...][...][...][...][...][...]
		 * [...] j=0...63
		 */
		
		for (int i=0; i<a.size(); i++){
			if(validRow.get(i)){
			for(int k=0; k<a.get(i).size() ; k++){
				for(int j=0; j<64  ; j++){
					if (stuff.DirtyLittleHelpers.getBitAtPosition(validColumn.get(k), j)== 1)
						System.out.print(stuff.DirtyLittleHelpers.getBitAtPosition(a.get(i).get(k), j)+" ");
					else 
						System.out.print("  ");
				}
			}
			}
			System.out.println();
			
		}
		System.out.println();
	}
	public static ArrayList<Long> dbitcoveragerow(String dbit,int max)throws IOException{
		/** gets an D-Bit in the String dbit with {f1,f2,f4,f3}|1
		returns an Boolean ArrayList with [1 1 1 1 0 0 0]	
		@version1.0
		@param String dbit			Hat die Form : {f1,f2,f4,f3}|1	
		@return ArrayList 			Welches Bit abgedeckt ist
		*/
		//int max...Maximale Anzahl an Fehler
		ArrayList<Long> LongList = new ArrayList<Long>();
		float a= max/64;
		int b= (int) a +1;
		int c=0;
		int d=0;
		//System.out.println(a + " " + b+ " "+max);
		for(int j=0;j<b; j++){
			LongList.add(0L);
		}
		for(int i=0; i<= max; i++){
			String x= ""+ i;
			if (dbit.contains("f"+x+",")||dbit.contains("f"+x+"}")) //Es gibt nur diese 2 Moeglichkeiten
				LongList.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(LongList.get(d), c, true));
			else
				LongList.set(d,stuff.DirtyLittleHelpers.setBitAtPosition(LongList.get(d), c, false));
			c++;
			if(c==64){
				c=0;
				d++;
			}
		}
		return LongList;
	}
	public static ArrayList<ArrayList<Long>> pattern (String testfile) throws IOException{
		/**Gibt immer ein Testmuster zurueck in einer ArrayList	
		@version1.1
		@param int whichpattern 	in welchen Testmuster in der Datei befinden wir uns?	//Wird nun immer auf 0 gesetzt um immer ein 2D-ArrayList zu bekommen.
		@return						Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
		*/
		ArrayList<ArrayList<Long>> pattern= new ArrayList<ArrayList<Long>>();
		ArrayList<Long> tmp1= new ArrayList<Long>();
		int max=readingdata.numberOfFailures(testfile);
		int c=0;
		int k=0;
		//Initialisierung von validColumn
		float a= max/64;
		
		int idx= (int) a +1;
		System.out.println(a+"\t"+ idx);
		for(int j=0;j<idx; j++){
			validColumn.add(0L);
		}
		for(int i=0; i<=max; i++){
			validColumn.set(k, stuff.DirtyLittleHelpers.setBitAtPosition(validColumn.get(k), c, true));
			c++;
			if(c==64 ){
				c=0;
				k++;
			}
		}//Ende Initialisierung von validColumn
		
		String b = "";
		Scanner s = new Scanner(new File(longData.testpfad+"/"+testfile));				
		while (s.hasNextLine()){									
			Scanner tmp= new Scanner(s.nextLine());
			b=tmp.nextLine(); 															//Zwischenspeicherung der aktuellen Zeile
			//System.out.println(b.contains("{f")+" "+ b);
			
			if(b.contains("{f")){	
				tmp1= dbitcoveragerow(b,max);
				if(!pattern.contains(tmp1)){
					pattern.add(tmp1);
					validRow.add(true);
					validRowZwischenspeicher.add(true);
				} else {
					validRowZwischenspeicher.add(false);
				}
				LongRemovingBits.removingBits.solution.add(false);
			}	
			tmp.close();
		}
		s.close();
		
		return pattern;
	}
}
