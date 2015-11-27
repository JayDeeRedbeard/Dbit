package readdata;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class readingdata {
	/** Dies ist ein Program zum Einlesen der Testdaten vom Fachgebiet DATE um die Minimierung der D-Bits vorzunehmen
	@author Jan Dennis Reimer		
	@version1.0
	@param gesucht 	
	@param string	
	@return		
	*/
	//public static String testfile = "Peter";
	public static String testfile = "Testmuster";
	//public static String testfile = "Testmusterppt";
			
	public static void main (String [] args) throws IOException{
		
		
		ArrayList<ArrayList<Boolean>> booleanList = pattern(0);
		
		//printpattern2D(booleanList);System.out.println();
		print2DTEST(testpatternOneData());
		//ArrayList<ArrayList<ArrayList<Boolean>>> booleanList1 = testpatternOneData();
		//System.out.println(booleanList1.get(3).size());
	}
	public static void print2DTEST(ArrayList<ArrayList<Boolean>> p) throws IOException {
		/** Gebe auf der Konsole ein KLEINES Beispiel aus einer Überdeckungstabelle
		@author Jan Dennis Reimer		
		@version1.0
		@param p : in p wird die 2D-ArrayList übergeben 		
		*/
		System.out.println("Pattern: "+ 0);
		for(int y = 0; y<p.size(); y++){
			for(int z = 0; z<p.get(y).size(); z++){
				System.out.print(p.get(y).get(z)+ "\t");
			}
			System.out.println();			
		}
		System.out.println();
		System.out.println();
	}
	public static int numberOfFailures() throws IOException {
		/** Gibt die Anzahl der Fehler zurück
		@author Jan Dennis Reimer		
		@version1.0
		@param  		
		*/
		return (nextPattern(0)-6);
	}
	public static int numberOfOutputs() throws IOException{
		/** Gibt die Anzahl der Outputs für das erste Pattern zurück.
		@author Jan Dennis Reimer		
		@version1.0
		@param  		
		*/
		// Es reicht aus nur ein Pattern zu betrachten
		int firstpattern= nextPattern(0);
		int secondpattern= nextPattern(firstpattern+1);
		int numberOfOutputs= secondpattern-firstpattern-1;
		return numberOfOutputs;
	}
	public static int howmuchtestpattern()throws IOException{
		/** Gibt die Anzahl der Testmuster für die Datei zurück.
		@author Jan Dennis Reimer		
		@version1.0
		@param  		
		*/
		int a = 0;
		int b = 0;
		while (nextPattern(a)!= 42352){
			//System.out.println(a);   // zum Testen der Funktion
			a=nextPattern(a)+1;
			b++;
		}
		return b-1;	
	}
	public static int nextPattern(int row) throws IOException{
		/** Gibt jeweils die Zeilennummer aus nach der das nächste Testmuster losgeht	
		@version1.0
		@param row 	Welche Reihe befinden wir uns gerade.	
		@return		die Zeilennummer aus dem nächsten Testmuster
		*/
		try{
			Scanner s = new Scanner(new File(testfile+".txt"));
			int i = 1;
			boolean temp=true;
			while (s.hasNextLine()) {
				Scanner a = new Scanner(s.nextLine());
				if(s.next().equals("Pattern")&& row<= i && temp==true){
					//System.out.println(i);
					temp=false;
					a.close();
					return i+1;
				}
				i++;
				a.close();
			} 
			s.close();
		}
			catch (NoSuchElementException e){	
				return 42352; //Wichtig für die Funktion howmuchtestpattern()
			}
		return 0;
		}
	public static ArrayList<Boolean> dbitcoveragerow(String dbit)throws IOException{
		/** gets an D-Bit in the String dbit with {f1,f2,f4,f3}|1
		returns an Boolean ArrayList with [1 1 1 1 0 0 0]	
		@version1.0
		@param String dbit			Hat die Form : {f1,f2,f4,f3}|1	
		@return ArrayList 			Welches Bit abgedeckt ist
		*/
		//int max...Maximale Anzahl an Fehler
		int max=numberOfFailures();
		ArrayList<Boolean> booleanList = new ArrayList<Boolean>();
		for(int i=0; i<= max; i++){
			String x= ""+ i;
			if (dbit.contains("f"+x+",")||dbit.contains("f"+x+"}")) //Es gibt nur diese 2 Möglichkeiten
				booleanList.add(true);
			else
				booleanList.add(false);
		}
		return booleanList;
	}
	public static void printpattern2D(ArrayList<ArrayList<Boolean>> tmp) throws IOException{
		/** Gibt ein 2D-boolsches Array in der Datei und auf der Konsole aus.
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp 	Ist eine 2D-ArrayList die übergeben wird
		@return -
		*/
		//
		PrintStream ausgabe = new PrintStream(new File("dbit.txt"));
		//System.out.println(tmp.size());
		for(int x=0; x<tmp.size(); x++){
			for(int y=0; y<tmp.get(0).size(); y++){
				System.out.print(tmp.get(x).get(y)+ "\t");
				ausgabe.print(tmp.get(x).get(y)+ " ");
			}
			System.out.println();
			ausgabe.println();
		}
		ausgabe.close();
	}
	public static ArrayList<ArrayList<Boolean>> pattern (int whichpattern) throws IOException{
		/**Gibt immer ein Testmuster zurück in einer ArrayList	
		@version1.1
		@param int whichpattern 	in welchen Testmuster in der Datei befinden wir uns?	//Wird nun immer auf 0 gesetzt um immer ein 2D-ArrayList zu bekommen.
		@return						Gibt eine 2D-ArrayList zurück die man dann später verarbeiten kann.
		*/
		ArrayList<ArrayList<Boolean>> pattern= new ArrayList<ArrayList<Boolean>>();
		ArrayList<Boolean> tmp1= new ArrayList<Boolean>();
		String b = "";
		Scanner s = new Scanner(new File(testfile+".txt"));
		int i= 1;						
		int limit=nextPattern(whichpattern); 														//Sonst kommt es zur erheblichen Verschlechterungen der Laufzeit!
		while (s.hasNextLine()){									
			Scanner tmp= new Scanner(s.nextLine());
			b=tmp.nextLine(); 																		//Zwischenspeicherung der aktuellen Zeile
			//System.out.println(b.contains("{f")+" "+ b);
			if (i>limit){
				//System.out.print(" Counter= "+counter+" ");
				if(b.contains("{f")){		
					tmp1= dbitcoveragerow(b);
					pattern.add(tmp1);
				}																		
			}
			i++;
			tmp.close();
		}
		s.close();
		return pattern;
	}
	public static  ArrayList<ArrayList<Boolean>> testpatternOneData()throws IOException{
		/** Gebe ein Testmuster zurück in einer 2D-boolschen ArrayList	
		@version1.0
		@version1.1
		//Es wird nun immer nur noch ein pattern geben. die 3. Dimension wird weg gelassen.
		@param -
		@return			Um alle Testmuster abzudecken, wird für jedes Testmuster eine 2D-ArrayList erstellt 
						
		*/
		return pattern(0);
	}
	
	
}
