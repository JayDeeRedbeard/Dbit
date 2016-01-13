package pruefen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import readdata.longData;

public class solution {
	/**	Gibt die Anzahl der Fehler zurueck die ueberdeckt sind durch die Ueberdeckungstabelle
	 * @param tmp	Bekommt eine 2D-ArrayList uebergeben
	 * @return		Anzahl der ueberdeckten Fehler
	 * @throws IOException
	 */
	public static int everyFailurecovered(ArrayList<ArrayList<Long>> tmp) throws IOException{
		boolean covered=false;
		int counter=0;
		int c=0;
		for(int d=0; d<tmp.get(0).size() ;){
			for (int k=0; k<tmp.size() && !covered; k++){
				if(LongRemovingBits.removingBits.solution.get(k)){
					if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1) ){
						covered=true;
						counter++;
					}
				}
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
			covered=false;
		}
		return counter;
	}
	/** Ueberprueft am Ende der Minimierung ob alle Fehler ueberdeckt wurden.
	 * 
	 * @param tmp 	Bekommt eine 2D-ArrayList uebergeben
	 * @return		Anzahl der ueberdeckten Fehler
	 * @throws IOException
	 */
	public static int datacorrect(ArrayList<ArrayList<Long>> tmp) throws IOException{
		File ausgabeDatei = new File(longData.protokoll+"/coveragefailure.txt");
		PrintStream writer = null;
		if ( !ausgabeDatei.exists() )
			writer = new PrintStream(ausgabeDatei);
		else {
			System.out.println("Datei existiert schon.");
			writer = new PrintStream(ausgabeDatei);
		}
        writer.append("Start"+ "\n");
        
		boolean covered=false;
		int counter=0;
		int c=0;
		for(int d=0; d<tmp.get(0).size() ;){
			for (int k=0; k<tmp.size() && !covered; k++){
					if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1)  ){
						covered=true;
						counter++;
					}
			}
			if (!covered && stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
				System.out.println("Fehler:"+ (d*64+c) +" ist nicht abgedeckt");
				writer.append("Fehler:"+ (d*64+c) +" ist nicht abgedeckt"+ "\n");
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
			covered=false;
		}
		writer.close();
		return counter;
	}
	
}
