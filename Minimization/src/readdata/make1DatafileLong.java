package readdata;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import LongRemovingBits.removingBits;
public class make1DatafileLong {

		public static ArrayList<ArrayList<Long>> returnbigList() throws IOException{
			ArrayList<ArrayList<Long>> a = new ArrayList<ArrayList<Long>>();
			ArrayList<ArrayList<Long>> b = new ArrayList<ArrayList<Long>>();
			File folder = new File(longData.testpfad);
			for( File file : folder.listFiles() ){
					System.out.println( file.getName() );
					a=pattern(file.getName());
					System.out.println(a.size());
					b.addAll(a);
			}
			return b;
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
			//System.out.println(a+"\t"+ idx);
			for(int j=0;j<idx; j++){
				readdata.longData.validColumn.add(0L);
			}
			for(int i=0; i<=max; i++){
				readdata.longData.validColumn.set(k, stuff.DirtyLittleHelpers.setBitAtPosition(readdata.longData.validColumn.get(k), c, true));
				c++;
				if(c==64 ){
					c=0;
					k++;
				}
			}//Ende Initialisierung von validColumn
			int counter=0;
			String b = "";
			Scanner s = new Scanner(new File(longData.testpfad +"/"+testfile));				
			while (s.hasNextLine()){									
				Scanner tmp= new Scanner(s.nextLine());
				b=tmp.nextLine(); 															//Zwischenspeicherung der aktuellen Zeile
				//System.out.println(b.contains("{f")+" "+ b);
				
				if(b.contains("{f")){	
					tmp1= readdata.longData.dbitcoveragerow(b,max);
					//System.out.println(b);
					//System.out.println("isdominatedRow(pattern,tmp1): "+ isdominatedRow(pattern,tmp1)+ " pattern.contains(tmp1) "+pattern.contains(tmp1));
					//System.out.println((pattern.contains(tmp1)) || (isdominatedRow(pattern,tmp1)));
					
					if((pattern.contains(tmp1)) || (isdominatedRow(pattern,tmp1)) ){
						longData.validRowZwischenspeicher.add(false);
					} else {
						pattern.add(tmp1);
						longData.validRow.add(true);
						longData.validRowZwischenspeicher.add(true);
					}
					LongRemovingBits.removingBits.solution.add(false);
				}
				tmp.close();
			}
			s.close();
			
			return pattern;
		}
		public static boolean isdominatedRow(ArrayList<ArrayList<Long>> tmp, ArrayList<Long> tmp1) {
			int c=0;
			boolean isdominated= true;
			if(!removingBits.validRowAllFalse()){
				for(int k=0; k<tmp.size()  ; k++){
					for(int d=0; d<tmp.get(0).size() && isdominated;){
							if( !(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==0 && 
									stuff.DirtyLittleHelpers.getBitAtPosition(tmp1.get(d), c)==1) ){	
								
							}else{	
								isdominated=false;
							} 
						c++;
						if(c==64){
							d++;
							c=0;
						}
					}
					if (isdominated)
						return true;
					c=0;
					isdominated=true;
				}
				
			}
			return false;
		}
}