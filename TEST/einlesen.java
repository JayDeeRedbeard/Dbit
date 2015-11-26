import java.util.*;
import java.io.*;
public class einlesen {
	public static String testfile = "Peter";
		public static void main (String [] args) throws IOException
		{
			System.out.println("Number of Outputs: "+numberOfOutputs()); 
			//Testmuster nextPattern
			System.out.println("Nächstes Testmuster beginnt bei Zeile: "+nextPattern(0));
			System.out.println("Maximale Anzahl an Fehlern: "+ numberOfFailures());
			boolean[][] p= pattern(0);
			printpattern(p);
			
			/*
			//Testmuster für dbitcoveragerow
			boolean[] x= dbitcoveragerow("{f3,f4,f8,f6,f16}|0",numberOfFailures());	
			for (int k = 0; k<x.length; k++)
				System.out.print(x[k]+" ");
			*/
		}
		public static int numberOfFailures() throws IOException {
			//Gibt die Anzahl der Fehler zurück.
			return (nextPattern(0)-6);
		}
		public static int nextPattern(int row) throws IOException{
			/* Gibt jeweils die Zeilennummer aus nach der das nächste Testmuster losgeht
			int row -Welche Reihe befinden wir uns gerade.
			*/
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
		
		public static void printpattern(boolean[][] tmp) throws IOException{
			PrintStream ausgabe = new PrintStream(new File("dbit.txt"));
			
			for(int x=0; x<tmp.length; x++){
				for(int y=0; y<tmp[0].length; y++){
					System.out.print(tmp[x][y]+ " ");
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
			
			while (s.hasNextLine()){
				Scanner tmp= new Scanner(s.nextLine());
				b=tmp.nextLine(); //Zwischenspeicherung der aktuellen Zeile
				//System.out.println(b.contains("{f")+" "+ b);
				if (i>nextPattern(whichpattern)&&counter<=numberOfFailures()){
					if(b.contains("{f")){		
						tmp1= dbitcoveragerow(b, numberOfFailures()-1);
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
}

