package testbench;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import LongRemovingBits.domRows;
import LongRemovingBits.falseRowsAndColumns;
import LongRemovingBits.removeRowsColumns;
import LongRemovingBits.removingBits;
import readdata.DBit;
import readdata.longData;
import readdata.make1DatafileLong;

public class tb_printData {
	public static void main(String[] args) throws IOException {
		removingBits.circuits = "C:/Users/Dennis/git/Minimization/";
		ArrayList<DBit> tmp;
		File f = new File(removingBits.circuits + "TEST/Circuits/");
		for (File files : f.listFiles()) {
			tmp = new ArrayList<DBit>();
			System.out.println(files.getName());
			longData.testpfad = removingBits.circuits + "TEST/Circuits/" + files.getName();
			longData.protokoll = removingBits.circuits + "TEST/logs/" + files.getName();
			longData.results = removingBits.circuits + "TEST/results/" + files.getName();
			longData.validColumn = new ArrayList<Long>();
//			longData.validRowZwischenspeicher = new ArrayList<Boolean>();
			make1DatafileLong.numberOfTruesInColumn = new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();

			tmp = make1DatafileLong.returnbigList();
			
			longData.printLongPatternwithoutEmptySpace(tmp);
			
			tmp = new ArrayList<DBit>();
			tmp = make1DatafileLong.returnbigListwithdomination();
			
			longData.printLongPatternwithoutEmptySpace(tmp);
		}

	}
	
}
