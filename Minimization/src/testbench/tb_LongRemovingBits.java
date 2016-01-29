package testbench;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import LongRemovingBits.removingBits;
import outputData.printData;
import readdata.DBit;
import readdata.longData;
import readdata.make1DatafileLong;


public class tb_LongRemovingBits {

	public static void main(String[] args) throws IOException {
		
		tb_normal();
//		tb_removeColumns();
	}
	
	public static void tb_removeColumns() throws IOException{
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
		ArrayList<DBit> tmp; 
		File f = new File(removingBits.circuits+"TEST/Circuits/");
		for(File files : f.listFiles()){
			tmp= new ArrayList<DBit>();
			System.out.println(files.getName());
			longData.testpfad= removingBits.circuits+"TEST/Circuits/"+files.getName();
			longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
			longData.results= removingBits.circuits + "TEST/results/"+files.getName();
			longData.validColumn=new ArrayList<Long>();
			longData.validRowZwischenspeicher=new ArrayList<Boolean>();
			make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
			
			tmp=make1DatafileLong.returnbigList();
			longData.printLongPattern(tmp);
			longData.printvalidColumn(tmp);
			LongRemovingBits.falseRowsAndColumns.RemoveFalseColumn(tmp);
			longData.printLongPattern(tmp);
			longData.printvalidColumn(tmp);
			LongRemovingBits.removeRowsColumns.removeColumnsfromList(tmp);
			longData.printLongPattern(tmp);
			longData.printvalidColumn(tmp);
			longData.printnumberofTrues(tmp);
		}
	}

	public static void tb_normal() throws IOException{
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
		ArrayList<DBit> tmp; 
		File f = new File(removingBits.circuits+"TEST/Circuits/");
		for(File files : f.listFiles()){
				tmp= new ArrayList<DBit>();
				System.out.println(files.getName());
				longData.testpfad= removingBits.circuits+"TEST/Circuits/"+files.getName();
				longData.protokoll= removingBits.circuits + "TEST/logs/"+files.getName();
				longData.results= removingBits.circuits + "TEST/results/"+files.getName();
				longData.validColumn=new ArrayList<Long>();
				longData.validRowZwischenspeicher=new ArrayList<Boolean>();
				make1DatafileLong.numberOfTruesInColumn= new ArrayList<ArrayList<Integer>>();
				make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
				
				tmp=make1DatafileLong.returnbigList();
				longData.printLongPatternwithoutEmptySpace(tmp);
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
				
				
				LongRemovingBits.removingBits.essentialdominating(tmp);
				printData.ausgabeindatei();

				printnumberOfTrues(tmp);
				
				System.out.println();
				
				System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
				System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
				System.out.println();
				System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse(tmp));
//				System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
			}
				
		}
	public static void printnumberOfTrues(ArrayList<DBit> tmp){
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
			if(tmp.get(i).getValid()){
				System.out.println(make1DatafileLong.numberOfTruesInRow.get(i));
			}
		}
	}
	
	
	
}
