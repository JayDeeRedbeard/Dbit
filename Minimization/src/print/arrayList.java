package print;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class arrayList {
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
	public static void print1DBooleanArrayList(ArrayList<Boolean> tmp){
		for(int i = 0; i<tmp.size(); i++){
			System.out.println(tmp.get(i)+ "\t");
		}
		System.out.println();
	}
}
