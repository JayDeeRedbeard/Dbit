package readdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class longData {
	
	public static void main (String [] args) throws IOException{
		
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
		for(int j=0;j<=b; j++){
			LongList.add(new Long(0));
		}
		for(int i=0; i<= max; i++){
			String x= ""+ i;
			if (dbit.contains("f"+x+",")||dbit.contains("f"+x+"}")) //Es gibt nur diese 2 Moeglichkeiten
				stuff.DirtyLittleHelpers.setBitAtPosition(LongList.get(d), i, true);
			else
				stuff.DirtyLittleHelpers.setBitAtPosition(LongList.get(d), i, false);
			if(c==63){
				i=0;
				d++;
			}
			c++;
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
		int max=readingdata.numberOfFailures();
		String b = "";
		//Scanner s = new Scanner(new File("/home/dj0804/Downloads/minimization/src/src/b14_1/"+testfile));
		Scanner s = new Scanner(new File("C:/Users/Dennis/git/Minimization/src/b14_1/"+testfile));				
		while (s.hasNextLine()){									
			Scanner tmp= new Scanner(s.nextLine());
			b=tmp.nextLine(); 															//Zwischenspeicherung der aktuellen Zeile
			//System.out.println(b.contains("{f")+" "+ b);
			//System.out.print(" Counter= "+counter+" ");
			if(b.contains("{f")){	
				tmp1= dbitcoveragerow(b,max);
				if(!pattern.contains(tmp1)){
					pattern.add(tmp1);
					removingBits.removingBits.saveRow.add(false);
					//System.out.println(b);
				} else {
					removingBits.removingBits.saveRow.add(true);
				}
				removingBits.removingBits.solution.add(false);
			}	
			tmp.close();
		}
		s.close();
		return pattern;
	}
}
