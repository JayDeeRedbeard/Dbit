package testbench;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import LongRemovingBits.removingBits;
import outputData.printData;
import readdata.longData;
import readdata.make1DatafileLong;


public class tb_LongRemovingBits {

	public static void main(String[] args) throws IOException {
			removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
			ArrayList<ArrayList<Long>> tmp; 
			File f = new File(removingBits.circuits+"TEST/Circuits/");
			for(File files : f.listFiles()){
				tmp= new ArrayList<ArrayList<Long>>();
				System.out.println(files.getName());
				longData.testpfad= removingBits.circuits+"TEST/Circuits/"+files.getName();
				longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
				longData.results= removingBits.circuits + "TEST/results/"+files.getName();
				longData.validRow=new ArrayList<Boolean>();
				longData.validColumn=new ArrayList<Long>();
				longData.validRowZwischenspeicher=new ArrayList<Boolean>();
				make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
				make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
				
				tmp=make1DatafileLong.returnbigList();
				longData.printLongPatternwithoutEmptySpace(tmp);
				
//				domRows.removeEqualRows(tmp);
//				longData.printLongPatternwithoutEmptySpace(tmp);
//				System.out.println("Columns");
//				domColumn.removeEqualColumns(tmp);
//				longData.printLongPatternwithoutEmptySpace(tmp);
				
				LongRemovingBits.removingBits.essentialdominating(tmp);
				printData.ausgabeindatei();
	
				//LongRemovingBits.removingBits.removeColumn(tmp, 0, 2);
				//longData.printLongPatternwithoutEmptySpace(tmp);
				printnumberOfTrues();
				
				System.out.println();
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
				System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
				System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
				System.out.println();
				System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse());
				System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
			
				
			}
		
	}
	public static void printnumberOfTrues(){
		int c=0;
		for(int d =0; d<make1DatafileLong.numberOfTruesInColumn.size();){
			if(stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c)==1){
				System.out.print(make1DatafileLong.numberOfTruesInColumn.get(d).get(c) + " ");
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
		}
		System.out.println();
		for(int i =0; i<make1DatafileLong.numberOfTruesInRow.size(); i++){
			if(readdata.longData.validRow.get(i)){
				System.out.println(make1DatafileLong.numberOfTruesInRow.get(i));
			}
		}
	}
	
	
	
}
