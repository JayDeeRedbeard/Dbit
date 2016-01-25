package LongRemovingBits;
import java.util.ArrayList;

import readdata.longData;

public class domColumn {
	/**  Finde alle Spalten, die dominiert werden von column.
	@param ArrayList<ArrayList<Long>> tmp					Bekommt die 2D-ArrayList(Long) uebergeben	(Ueberdeckungstabelle)
	@param int column										Es wird jeweils ueberprueft, ob diese Spalte irgendeine andere Spalte dominiert
	@return													Es wird eine ArrayList zurueckgegeben, wo die 0. Spalte jeweils die dominierende Spalte ist.
															Die 1.Spalte ist dann die nicht dominierende Spalte
	*/
	public static void dominatingColumns(ArrayList<ArrayList<Long>> tmp) {
		// Initialisierungen
		int counttrueA = 0;
		int counttrueB = 0;
		int c = 0;
		int e = 0;
		boolean dominationcounterA = false;
		boolean dominationcounterB = false;
		boolean isdominated = true;
		if (!removingBits.validRowAllFalse()) {
			for (int d = 0; d < tmp.get(0).size();) {
				if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1) {
					System.out.println("Ueberpruefe Spalte");
					System.out.println("d= " + d + " c= " + c);
					for (int y = 0; y < tmp.get(0).size();) {
						if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(y), e) == 1) {
							for (int k = 0; k < tmp.size() && isdominated; k++) {
								if (longData.validRow.get(k)) {
									// Entscheidendes Kriterium!! Ueberpruefe ob Spalte A die Spalte B dominiert.
									if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 1
											&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(y), e) == 0)) {
																	
										if (!(d == y && c == e) &&
												readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c) <= 
												readdata.make1DatafileLong.numberOfTruesInColumn.get(y).get(e)
												&& !dominationcounterA) {
											if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 1){
												counttrueA++;
												if(counttrueA==readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c) ){
													isdominated = true;
												}
											}
										}
									} else {
										dominationcounterA = true;
									}
									// Entscheidendes Kriterium!! Ueberpruefe ob Spalte B die Spalte A dominiert.
									if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 0
											&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(y), e) == 1)) { 
										if (!(d == y && c == e) &&
												readdata.make1DatafileLong.numberOfTruesInColumn.get(y)
												.get(e) <= readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)
												&& !dominationcounterB ) {
											if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) == 1){
												counttrueB++;
												if( counttrueB==readdata.make1DatafileLong.numberOfTruesInColumn.get(y).get(e) ){
													isdominated = true;
												}
											}
										}
									} else {
										dominationcounterB = true;
									}
									if (dominationcounterA && dominationcounterB) {
										isdominated = false;
									}

								}
							}
							if (counttrueA >= 1 && isdominated && !dominationcounterA) {
								System.out.println(" Column  d= " + d + " c= " + c + " ist dominiernd auf y= " + y + " e= " + e);
								removingBits.removeColumn(tmp, y, e);
								dominationcounterB = true;
							}
							if (counttrueB >= 1 && isdominated && !dominationcounterB) {
								System.out.println(" Column y= " + y + " e= " + e + " ist dominiernd auf c= " + d + " c= " + c);
								removingBits.removeColumn(tmp, d, c);
							}
							isdominated = true;
							dominationcounterA = false;
							dominationcounterB = false;
							counttrueA = 0;
							counttrueB = 0;
						}
						e++;
						if (e == 64) {
							y++;
							e = 0;
						}
					}
				}
				c++;
				if (c == 64) {
					d++;
					c = 0;
				}
			}
		}
	}
/**
 * Loescht alle gleichen Spalten
 * @param tmp
 */
	public static void removeEqualColumns(ArrayList<ArrayList<Long>> tmp) {
		// Diese Funktion ist nicht mehr brauchbar! Denn sie wird dominiert du
		// die oberen Funktion
		// Start Initialisieren
		int counttrueA=0;
		boolean isdominated=false;
		int c = 0;
		int e = 0;
		// Ende Initialisieren
		for (int d = 0; d < tmp.get(0).size();) {
			if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1) {
				for (int y = 0; y < tmp.get(0).size();) {
					if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(y), e) == 1
							&& !(d == y && c == e)) {
						if (readdata.make1DatafileLong.numberOfTruesInColumn.get(d)
								.get(c) == readdata.make1DatafileLong.numberOfTruesInColumn.get(y).get(e)) {
							for (int row = 0; row < tmp.size() && !isdominated; row++) {
								if (longData.validRow.get(row)) {
									if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d),
											c) == 1 && stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d),
													c) == stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(y), e)) {
										counttrueA++;
										if (counttrueA == readdata.make1DatafileLong.numberOfTruesInColumn.get(d).get(c)) {
											isdominated = true;
										}
									}
								}
							}
							if (isdominated) {
								System.out.println("Loesche Spalte: y" + y + " e" + e);
								removingBits.removeColumn(tmp, y, e);
							}
							counttrueA = 0;
							isdominated = false;
							e++;
							if (e == 64) {
								y++;
								e = 0;
							}
						}
					}
				}
				e=0;
			}
			c++;
			if (c == 64) {
				d++;
				c = 0;
			}
		}
	}
}
