package einlesenderdaten;
import java.util.*;
import java.io.*;
public class einlesen {
	//public static String testfile = "Peter";
	public static String testfile = "Testmuster";
		public static void main (String [] args) throws IOException
		{	/*
			System.out.println("Number of Outputs: "+numberOfOutputs()); 
			System.out.println("Nächstes Testmuster beginnt bei Zeile: "+nextPattern(0));
			System.out.println("Maximale Anzahl an Fehlern: "+ numberOfFailures());
			System.out.println("Wie viele Pattern gibt es in der Datei:(von 0 angefangen zu zählen) "+howmuchtestpattern());
			testpatternOneData();
			print3DTEST(testpatternOneData());
			
			
			//Testmuster für ein einzelnes Pattern
			boolean[][] pa= pattern(0);
			printpattern2D(pa);
			
			/*
			//Testmuster für dbitcoveragerow
			boolean[] x= dbitcoveragerow("{f3,f4,f8,f6,f16}|0",numberOfFailures());	
			for (int k = 0; k<x.length; k++)
				System.out.print(x[k]+" ");
			*/
			
			ArrayList<ArrayList<Boolean>> booleanList = new ArrayList<ArrayList<Boolean>>();
			
			
			
			
			
		}
		public static void print3DTEST(boolean[][][] p) throws IOException {
			//Testmuster für 3D-Array
			int howmuchpattern= howmuchtestpattern();
			int Outputs =numberOfOutputs();
			int Failures= numberOfFailures();
			int i=0;
			while(i<howmuchpattern){
				for(int y = 0; y<Outputs; y++){
					for(int z = 0; z<Failures; z++){
					System.out.print(p[i][y][z]+ "\t");
					}
					System.out.println();
				}
				System.out.println();
				System.out.println();
			i++;
			}
		}
		public static int numberOfFailures() throws IOException {
			//Gibt die Anzahl der Fehler zurück.
			return (nextPattern(0)-6);
		}
		public static int nextPattern(int row) throws IOException{
			/* Gibt jeweils die Zeilennummer aus nach der das nächste Testmuster losgeht
			int row -Welche Reihe befinden wir uns gerade.
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
		public static boolean[] dbitcoveragerow(String dbit, int max){
			/*gets an D-Bit in the String dbit with {f1,f2,f4,f3}|1
			returns an Boolean array with [1 1 1 1 0 0 0]
			String dbit...{f1 f2,f3....}
			int max...Maximale Anzahl an Fehler
			*/
			boolean[] dbitin = new boolean[max+1];
			for(int i=0; i<= max; i++){
				String x= ""+ i;
				if (dbit.contains("f"+x+",")||dbit.contains("f"+x+"|"))
					dbitin[i]= true;
			}
			return dbitin;
		}
		
		public static void printpattern2D(boolean[][] tmp) throws IOException{
			//Gibt ein 2D-boolsches Array in der Datei und auf der Konsole aus.
			PrintStream ausgabe = new PrintStream(new File("dbit.txt"));
			
			for(int x=0; x<tmp.length; x++){
				for(int y=0; y<tmp[0].length; y++){
					System.out.print(tmp[x][y]+ "\t");
					ausgabe.print(tmp[x][y]+ " ");
				}
				System.out.println();
				ausgabe.println();
			}
			ausgabe.close();
		}
		public static int numberOfOutputs() throws IOException{
			// Gibt die Anzahl der Outputs für das erste Pattern zurück.
			int firstpattern= nextPattern(0);
			int secondpattern= nextPattern(firstpattern+1);
			int numberOfOutputs= secondpattern-firstpattern-1;
			return numberOfOutputs;
		}
		public static boolean[][] pattern(int whichpattern) throws IOException{
			//Gibt für ein Testmuster eine Boolean matrix zurück mit Ausgänge mal Fehler
			//int whichpattern : gibt an welches Pattern aus der Datei gewählt werden soll.
			boolean [][] pattern = new boolean[numberOfOutputs()][numberOfFailures()];
			boolean [] tmp1 = new boolean [numberOfFailures()];
			String b = "";
			Scanner s = new Scanner(new File(testfile+".txt"));
			int i= 1;
			int counter=0;
			int limit=nextPattern(whichpattern); //Sonst kommt es zur erheblichen Verschlechterungen der Laufzeit!
			int failures= numberOfFailures();
			int outputs= numberOfOutputs();
			while (s.hasNextLine()){
				Scanner tmp= new Scanner(s.nextLine());
				b=tmp.nextLine(); //Zwischenspeicherung der aktuellen Zeile
				//System.out.println(b.contains("{f")+" "+ b);
				if (i>limit && counter<=outputs){
					
					//System.out.print(" Counter= "+counter+" ");
					if(b.contains("{f")){		
						tmp1= dbitcoveragerow(b, failures-1);
						for(int j=0; j<tmp1.length;j++){
							pattern[counter][j]= tmp1[j];
						}
					}
					counter++;
				}
				i++;
				tmp.close();
			}
			s.close();
			return pattern;
		}
		public static int howmuchtestpattern()throws IOException{
			int a = 0;
			int b = 0;
			while (nextPattern(a)!= 42352){
				//System.out.println(a);
				a=nextPattern(a)+1;
				b++;
			}
			return b-1;	
		}
		public static boolean[][][] testpatternOneData()throws IOException{
			//Gebe ein Testmuster zurück in einer 3D-boolschen Array
			int howmuchpattern= howmuchtestpattern();
			int Outputs =numberOfOutputs();
			int Failures= numberOfFailures();
			boolean[][][] testpattern=new boolean[howmuchpattern][Outputs][Failures];
			int a=0;
			boolean[][] tmp= pattern(a);
			int i=0;
			while(i<howmuchpattern){
					for(int y = 0; y<Outputs; y++){
						for(int z = 0; z<Failures; z++){
							testpattern[i][y][z]= tmp[y][z];//Uebernehme Werte in dem 3D-Array
						}
					}
				//System.out.println(i);
				i++;
				if(a==0)		//IF Anweisung zur Optimierung der Laufzeit
					a=nextPattern(a)+1;
				else
					a=a+Outputs+1;
				tmp=pattern(a);
				//System.out.println(a);
			}
			return testpattern;
		}
}

