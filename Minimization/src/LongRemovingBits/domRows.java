package LongRemovingBits;
import java.util.ArrayList;

import readdata.longData;
public class domRows{
	/**  Finde alle Reihen, die von int row dominiert werden .
	@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList uebergeben	(ueberdeckungstabelle)
	@param int row											Es wird jeweils ueberprueft, ob diese Reihe irgendeine andere Reihe dominiert
	@return													Es wird eine ArrayList zurueckgegeben, wo die erste Spalte jeweils die dominierende Reihe ist.
															Die 2.Spalte ist dann die nicht dominierende Reihe
	*/
public static void dominatingRows(ArrayList<ArrayList<Long>> tmp){
	int counttrueA=0;
	int counttrueB=0;
	int c=0;
	boolean dominationcounterA=false;
	boolean dominationcounterB=false;
	boolean isdominated= true;
	if(!removingBits.validRowAllFalse()){
		for (int row=tmp.size()-1; row>=0;row--){
			if(longData.validRow.get(row)){
				System.out.println("Reihe "+row+" wird ueberprueft");
			for (int k = 0; k < tmp.size(); k++) {
				if (readdata.longData.validRow.get(k)) {
					for (int d = 0; d < tmp.get(0).size() && isdominated;) {
						if (stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c) == 1) {
							// Entscheidendes Kriterium!! Ueberpruefe ob Reihe A die Reihe B dominiert.
							if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c) == 0
									&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 1)
									&& k != row
									&& readdata.make1DatafileLong.numberOfTruesInRow
											.get(row) >= readdata.make1DatafileLong.numberOfTruesInRow.get(k)
									&& !dominationcounterA) {
								
								if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c) == 1) {
									counttrueA++;
									if (counttrueA == readdata.make1DatafileLong.numberOfTruesInRow.get(k)) {
										isdominated = true;
									}
								}
							} else {
								dominationcounterA = true;
							}
							// Entscheidendes Kriterium!! Ueberpruefe ob Reihe B die Reihe A dominiert.
							if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c) == 1
									&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 0)
									&& k != row
									&& readdata.make1DatafileLong.numberOfTruesInRow.get(k) 
									>= readdata.make1DatafileLong.numberOfTruesInRow.get(row)
									&& !dominationcounterB) {
								if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 1) {
									counttrueB++;
									if (counttrueB == readdata.make1DatafileLong.numberOfTruesInRow.get(row)) {
										isdominated = true;
									}
								}
							} else {
								dominationcounterB = true;
							}
							if (dominationcounterA && dominationcounterB) {
								isdominated = false;
							}
						}
						c++;
						if (c == 64) {
							d++;
							c = 0;
						}
					}
					if (counttrueA >= 1 && !dominationcounterA && isdominated) {
						System.out.println(" Reihe " + row + " ist dominiernd auf " + k);
						removingBits.removeRow(tmp, k, false);
						dominationcounterB=true;							//Damit bei gleichen Zeilen nicht beide geloescht werden
					}
					if (counttrueB >= 1 && !dominationcounterB && isdominated) {
						System.out.println(" Reihe " + k + " ist dominiernd auf " + row);
						removingBits.removeRow(tmp, row, false);
					}
					isdominated = true;
					dominationcounterA = false;
					dominationcounterB = false;
					counttrueA = 0;
					counttrueB = 0;
					c = 0;
				}
			}
			}
		}
		}
	}
/** Loeschen von gleichen Reihen aus der Ueberdeckungstabelle
@param ArrayList<ArrayList<ArrayList<Long>>> tmp		Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
@return													2D-ArrayList ohne gleiche Reihen
*/
	public static void removeEqualRows(ArrayList<ArrayList<Long>> tmp) {
		// Diese Funktion ist nicht mehr brauchbar! Denn sie wird dominiert du
		// die oberen Funktion
		// Start Initialisieren
		int counter = 0;
		int c = 0;
		// Ende Initialisieren
		if (!removingBits.validRowAllFalse()) {
			for (int j = 0; j < tmp.size(); j++) {
				if (longData.validRow.get(j)) {
					for (int k = 0; k < tmp.size(); k++) {
						if (longData.validRow.get(k) && j != k) {
							for (int d = 0; d < tmp.get(0).size();) {
								if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d),
										c) == 1) {
									// Wenn beide Zeilen gleich sind, dann
									// zaehle
									// counter hoch
									if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(j).get(d),
											c) == stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)) {
										counter++;
									}
								}
									c++;
									if (c == 64) {
										d++;
										c = 0;
									}
								
							}
						}
						if (counter == removingBits.numberOfvalidColumns(tmp)) {
							removingBits.removeRow(tmp, k, false);
						}
						counter = 0;
					}
				}
			}
		}
	}
}