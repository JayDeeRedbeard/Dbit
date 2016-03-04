package pruefen;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import LongRemovingBits.removingBits;
import readdata.DBit;
import readdata.longData;
import readdata.make1DatafileLong;
import readdata.readingdata;

public class solution {
	public static void main(String[] args) throws IOException {
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
//		removingBits.circuits = "/home/dj0804/workspace/Minimization/";
		ArrayList<DBit> tmp; 
//		File f = new File(removingBits.circuits+"TEST/results/");
//		for(File files : f.listFiles()){
//			tmp= new ArrayList<DBit>();
//			System.out.println(files.getName());
////			longData.testpfad= removingBits.circuits+"results/"+files.getName();
//			longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
//			longData.testpfad= removingBits.circuits + "TEST/results/"+files.getName();
		File f = new File(removingBits.circuits+"results/");
		for(File files : f.listFiles()){
			tmp= new ArrayList<DBit>();
			System.out.println(files.getName());
//			longData.testpfad= removingBits.circuits+"results/"+files.getName();
			longData.protokoll= removingBits.circuits + "logs/"+files.getName();
			longData.testpfad= removingBits.circuits + "results/"+files.getName();
			longData.validColumn=new ArrayList<Long>();
			make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
			
			tmp=make1DatafileLong.returnbigList();
			System.out.println();
			System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
			System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
			System.out.println();
			System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse(tmp));
			System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
		}
		
	}

	/**
	 * Gibt die Anzahl der Fehler zurueck die ueberdeckt sind durch die
	 * Ueberdeckungstabelle
	 * 
	 * @param tmp Bekommt eine 2D-ArrayList uebergeben
	 * @return Anzahl der ueberdeckten Fehler
	 * @throws IOException
	 */
	public static int everyFailurecoveredHypergraph(ArrayList<DBit> tmp)
			throws IOException {
			boolean covered = false;
			int counter = 0;
			int c = 0;
			for (int d = 0; d < tmp.get(0).getList().size();) {
					for (int k = 0; k < removingBits.solution.size() && !covered; k++) {
						if (removingBits.solution.get(k)) {
							for (int b=0; b<tmp.size(); b++){
								if (tmp.get(b).getValue()==k){
									if ((stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(b).getList().get(d), c) == 1)) {
										covered = true;
										counter++;
									}
								}
							}
						}
					}
					c++;
					if (c == 64) {
						d++;
						c = 0;
					}
					covered = false;
				
			}
			return counter;
		}
	/**
	 * Gibt die Anzahl der Fehler zurueck die ueberdeckt sind durch die
	 * Ueberdeckungstabelle
	 * 
	 * @param tmp Bekommt eine 2D-ArrayList uebergeben
	 * @return Anzahl der ueberdeckten Fehler
	 * @throws IOException
	 */
	public static int everyFailurecovered(ArrayList<DBit> tmp) throws IOException {
		boolean covered = false;
		int counter = 0;
		int c = 0;
		for (int d = 0; d < tmp.get(0).getList().size();) {
			if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1) {
				for (int k = 0; k < tmp.size() && !covered; k++) {
					if (tmp.get(k).getValid()) {
						if ((stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1)) {
							covered = true;
							counter++;
						}
					}
				}
			}
			c++;
			if (c == 64) {
				d++;
				c = 0;
			}
			covered = false;
		}
		return counter;
	}
	/** Ueberprueft am Ende der Minimierung ob alle Fehler ueberdeckt wurden.
	 * 
	 * @param tmp 	Bekommt eine 2D-ArrayList uebergeben
	 * @return		Anzahl der ueberdeckten Fehler
	 * @throws IOException
	 */
	public static int datacorrect(ArrayList<DBit> tmp) throws IOException{
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
		for(int d=0; d<tmp.get(0).getList().size() ;){
			for (int k=0; k<tmp.size() && !covered; k++){
					if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c)==1)  ){
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
	public static void numberoftruescorrect(ArrayList<DBit> tmp) throws IOException{
		int c=0;
		int counter=0;
		for (int row =0 ; row< tmp.size() ; row++){
			c=0;
			if (tmp.get(row).getValid()){
				for(int d=0; d<tmp.get(0).getList().size() ;){
					if(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
						if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c)==1)  ){
							counter++;
						}
					}
					c++;
					if(c==64){
						d++;
						c=0;
					}
					
//					System.out.println(c);
				}
				
			
			
				if(counter==0 && readdata.make1DatafileLong.numberOfTruesInRow.get(row)<0){
				}else{
					if(!readdata.make1DatafileLong.numberOfTruesInRow.get(row).equals(counter)){
						if(readdata.make1DatafileLong.numberOfTruesInRow.get(row)!=-1){
							System.out.println("FEHLER in Number Of Trues row:"+ row);
							System.out.println(counter +" =!" + readdata.make1DatafileLong.numberOfTruesInRow.get(row)+ "   " +readdata.make1DatafileLong.numberOfTruesInRow.get(row).equals(counter));
							readdata.make1DatafileLong.numberOfTruesInRow.set(row,counter);
						}
					}
				}
				
			}	
			counter=0;
		}
	}
	public static void numberoftruescorrectColumn(ArrayList<DBit> tmp) throws IOException{
		int c=0;
		int counter=0;
		for(int d=0; d<tmp.get(0).getList().size() ;){
			if(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
				for (int row =0 ; row< tmp.size() ; row++){
					if (tmp.get(row).getValid()){
						if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c)==1)  ){
							counter++;
						}
					}
				}
				if(counter==0 && readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)==-1){
					
				}else{
					if(readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)<=	-1){
						if(counter!=readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)){
							System.err.println("FEHLER in Number Of Trues Column");
							System.out.println(counter +" =!" + readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c) + " "+d + " "+ c);
							readdata.make1DatafileLong.numberOfTruesInColumn.get(d).set(c,counter);
						}
					}
				}	
			}
			
//			System.out.println(c);
			c++;
			if(c==64){
				d++;
				c=0;
			}
			counter=0;
		}
	}
}
