package LongRemovingBits;

import java.io.IOException;
import java.util.ArrayList;

import readdata.DBit;


/**
 * Diese Klasse loescht die eigentlichen Spalten sowie Reihen von der Ueberdeckungstabelle
 * @author Dennis
 *
 */
public class removeRowsColumns {
	/**
	 * Sortiert nach ValidColum die Ueberdeckungstabelle. In den ersten k Spalten befinden sich nur noch validColumns.
	 * @param tmp
	 * @throws IOException
	 */
	public static ArrayList<DBit> removeColumnsfromList(ArrayList<DBit> tmp) throws IOException {
		// Initialisierungen
		int c = 0;
		int e = 63;
		boolean finish = false;
		boolean breakcounter = false;
		int counter = 0;
		int removecounter = 0;
		int d = 0;
		int d_temp = 0;
		int y = 0;
		int y_temp = tmp.get(0).getList().size() - 1;
		ArrayList<Long> tmp1;
		DBit DB;
		System.out.println(tmp.get(0).getList().size() - 1);
		if (!removingBits.validRowAllFalse(tmp)) {
			if (removingBits.numberOfnotvalidColumns(tmp) > 64) {
				while (!finish) {
					for (d = d_temp; d < tmp.get(0).getList().size() && !breakcounter;) {
						if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 0) {
							breakcounter = true;
						}
						if (!breakcounter) {
							c++;
							if (c == 64) {
								d++;
								c = 0;
							}
						}
					}
					breakcounter = false;
					for (y = y_temp; y >= 0 && !breakcounter;) {
						if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(y), e) == 1) {
							breakcounter = true;
						}
						// System.out.println(breakcounter + " " );
						if (!breakcounter) {
							e--;
							if (e == -1) {
								y--;
								e = 63;
							}
						}
					}
					System.out.println(d + " " + c + " " + y + " " + e);
					if (y <= d && e <= c) {
						finish = true;
					}
					if (y < 0)
						finish = true;
					tmp1 = new ArrayList<Long>();
					if (!finish) {
						for (int row = 0; row < tmp.size(); row++) {
							if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(y), e) == 1) {
								tmp1 = tmp.get(row).getList();
								tmp1.set(d, stuff.DirtyLittleHelpers.setBitAtPosition(tmp.get(row).getList().get(d), c,
										true));
								DB = new DBit(tmp.get(row).getValue(), true, tmp1);
								tmp.set(row, DB);
							} else {
								tmp1 = tmp.get(row).getList();
								tmp1.set(d, stuff.DirtyLittleHelpers.setBitAtPosition(tmp.get(row).getList().get(d), c,
										false));
								DB = new DBit(tmp.get(row).getValue(), true, tmp1);
								tmp.set(row, DB);
							}
						}
						readdata.make1DatafileLong.numberOfTruesInColumn.get(d).set(c,
								readdata.make1DatafileLong.numberOfTruesInColumn.get(y).get(e));
						readdata.make1DatafileLong.numberOfTruesInColumn.get(y).set(e, -1);

						readdata.longData.validColumn.set(d, stuff.DirtyLittleHelpers
								.setBitAtPosition(readdata.longData.validColumn.get(d), c, true));
						readdata.longData.validColumn.set(y, stuff.DirtyLittleHelpers
								.setBitAtPosition(readdata.longData.validColumn.get(y), e, false));

						d_temp = d;
						y_temp = y;
						breakcounter = false;
						counter++;
					}
				}
//				removecounter = counter / 64;
//				System.out.println("Removecounter= " + removecounter);
				// while(removecounter>0){
				removecounter = 0;
				System.out.println(readdata.longData.validColumn.get(readdata.longData.validColumn.size() - 1)
						+ " #Index= " + (readdata.longData.validColumn.size() - 1));
				// for (int i=0; i<readdata.longData.validColumn.size();i++){
				// if(readdata.longData.validColumn.get(readdata.longData.validColumn.size()-1)==(0L)){
				// removecounter++;
				// }
				// }
				//
				while (readdata.longData.validColumn.get(readdata.longData.validColumn.size() - 1) == (0L)
						&& (readdata.longData.validColumn.size() - 1 > 0)) {
					readdata.make1DatafileLong.numberOfTruesInColumn
							.remove(readdata.make1DatafileLong.numberOfTruesInColumn.size() - 1);
					System.out.println("Letzten Long geloescht: " + (readdata.longData.validColumn.size() - 1));
					System.out.println("tmp.get(0).getList().size(): " + tmp.get(0).getList().size());
					for (int row = 0; row < tmp.size(); row++) {
						tmp1 = new ArrayList<Long>();
						tmp1 = tmp.get(row).getList();
						// System.out.println("Size tmp1: "+tmp1.size());
						tmp1.remove(tmp1.size() - 1);
						DB = new DBit(tmp.get(row).getValue(), true, tmp1);
						tmp.set(row, DB);
					}
					System.out.println("tmp.get(0).getList().size(): " + tmp.get(0).getList().size());
					tmp1 = new ArrayList<Long>();
					tmp1 = readdata.longData.validColumn;
					tmp1.remove(tmp1.size() - 1);
					readdata.longData.validColumn = tmp1;
					removecounter--;
				}
//			readdata.longData.validColumn.get(index)

			}
		}
		return tmp;
	}
	public static ArrayList<DBit> removeRowsfromList(ArrayList<DBit> tmp) throws IOException {
		for (int row=tmp.size()-1; row>=0; row-- ){
			if(!tmp.get(row).getValid()){
				tmp.remove(row);
				readdata.make1DatafileLong.numberOfTruesInRow.remove(row);
			}
		}
		return tmp;
	}
}
