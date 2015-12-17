package readdata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class make1DatafileOLD {
	public static ArrayList<ArrayList<Boolean>> returnbigList() throws IOException{
		ArrayList<ArrayList<Boolean>> a = new ArrayList<ArrayList<Boolean>>();
		ArrayList<ArrayList<Boolean>> b = new ArrayList<ArrayList<Boolean>>();
		File folder = new File("C:/Users/Dennis/git/Minimization/src/b14_1");
		//File folder = new File("/home/dj0804/Downloads/minimization/src/src/b14_1");
		for( File file : folder.listFiles() ){
			System.out.println( file.getName() );
		   	a=pattern(0,file.getName());
		   	System.out.println(a.size());
		   	b.addAll(a);
		}
		return b;
	}
	public static ArrayList<ArrayList<Boolean>> pattern (int whichpattern, String testfile) throws IOException{
		/**Gibt immer ein Testmuster zurueck in einer ArrayList	
		@version1.1
		@param int whichpattern 	in welchen Testmuster in der Datei befinden wir uns?	//Wird nun immer auf 0 gesetzt um immer ein 2D-ArrayList zu bekommen.
		@return						Gibt eine 2D-ArrayList zurueck die man dann spaeter verarbeiten kann.
		*/
		ArrayList<ArrayList<Boolean>> pattern= new ArrayList<ArrayList<Boolean>>();
		ArrayList<Boolean> tmp1= new ArrayList<Boolean>();
		String b = "";
		//Scanner s = new Scanner(new File("/home/dj0804/Downloads/minimization/src/src/b14_1/"+testfile));
		Scanner s = new Scanner(new File("C:/Users/Dennis/git/Minimization/src/b14_1/"+testfile));
		int i= 1;
		//int counter=0;					
		while (s.hasNextLine()){									
			Scanner tmp= new Scanner(s.nextLine());
			b=tmp.nextLine(); 															//Zwischenspeicherung der aktuellen Zeile
			//System.out.println(b.contains("{f")+" "+ b);
			//System.out.print(" Counter= "+counter+" ");
			if(b.contains("{f")){	
				tmp1= readingdata.dbitcoveragerow(b);
				
				
				if(!pattern.contains(tmp1)){
					pattern.add(tmp1);
					removingBits.removingBits.saveRow.add(false);
					//System.out.println(b);
				} else {
					removingBits.removingBits.saveRow.add(true);
				}
				removingBits.removingBits.solution.add(false);
			}	
			i++;
			tmp.close();
		}
		s.close();
		return pattern;
	}
}
