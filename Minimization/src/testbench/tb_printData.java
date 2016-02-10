package testbench;

import java.io.File;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import LongRemovingBits.domRows;
import LongRemovingBits.falseRowsAndColumns;
import LongRemovingBits.removeRowsColumns;
import LongRemovingBits.removingBits;
import readdata.DBit;
import readdata.longData;
import readdata.make1DatafileLong;

public class tb_printData {
	public static void main(String[] args) throws IOException {
		removingBits.circuits = "D:/OneDrive/Documents/Studium/Bachlorarbeit/simulationsergebnisse/";
		ArrayList<DBit> tmp;
		File f = new File(removingBits.circuits );
		for (File files : f.listFiles()) {
			tmp = new ArrayList<DBit>();
			System.out.println(files.getName());
//			longData.testpfad = removingBits.circuits + "TEST/Circuits/" + files.getName();
//			longData.protokoll = removingBits.circuits + "TEST/logs/" + files.getName();
//			longData.results = removingBits.circuits + "TEST/results/" + files.getName();
			longData.validColumn = new ArrayList<Long>();
			make1DatafileLong.numberOfTruesInColumn = new ArrayList<ArrayList<Integer>>();
			make1DatafileLong.numberOfTruesInRow = new ArrayList<Integer>();
			
			File folder = new File(removingBits.circuits + files.getName());
			for (File file : folder.listFiles()) {
				System.out.println(file.getName());
				if (file.getName().contains(("behavior"))) {
					System.out.println("/"+files.getName()+"/"+file.getName());
					GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(removingBits.circuits+
							"/"+files.getName()+"/"+file.getName()));
					BufferedReader br = new BufferedReader(new InputStreamReader(gzip));
					String content;
					while ((content = br.readLine()) != null)
					   System.out.println(content);
				}
			}
			
			tmp = make1DatafileLong.returnbigList();
			
			longData.printLongPatternwithoutEmptySpace(tmp);
			
		}

	}
	
}
