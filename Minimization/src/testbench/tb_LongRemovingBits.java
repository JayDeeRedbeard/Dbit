package testbench;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import LongRemovingBits.removingBits;
import readdata.longData;
import readdata.make1DatafileLong;


public class tb_LongRemovingBits {

	public static void main(String[] args) throws IOException {
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
				
				tmp=make1DatafileLong.returnbigList();
				longData.printLongPatternwithoutEmptySpace(tmp);
				
				
				
				
				
				
				
				System.out.println();
				System.out.println("everyFailurecovered: "+pruefen.solution.everyFailurecovered(tmp));	
				System.out.println("Number of False in Solution: "+removingBits.numberOfFalseinSolution());
				System.out.println("Number of Trues in Solution: "+removingBits.numberOfTruesinSolution());
				System.out.println();
				System.out.println("validRowAllFalse: "+removingBits.validRowAllFalse());
				System.out.println("everyFailurecovered: "+pruefen.solution.datacorrect(tmp));
			
				
			}
		
	}
	
	
	
}
