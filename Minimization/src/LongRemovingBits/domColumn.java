package LongRemovingBits;
import java.util.ArrayList;

import readdata.DBit;

public class domColumn {
	/**  Finde alle Spalten, die dominiert werden von column.
	@param ArrayList<DBit> tmp					Bekommt die 2D-ArrayList(Long) uebergeben	(Ueberdeckungstabelle)
	@param int column										Es wird jeweils ueberprueft, ob diese Spalte irgendeine andere Spalte dominiert
	@return													Es wird eine ArrayList zurueckgegeben, wo die 0. Spalte jeweils die dominierende Spalte ist.
															Die 1.Spalte ist dann die nicht dominierende Spalte
	*/
	public static void dominatingColumns(ArrayList<DBit> tmp) {
		// Initialisierungen
		int counttrueA = 0;
		int counttrueB = 0;
		int c = 0;
		int e = 0;
		boolean dominationcounterA = false;
		boolean dominationcounterB = false;
		boolean isdominated = true;
		if (!removingBits.validRowAllFalse(tmp)) {
			for (int d = 0; d < tmp.get(0).getList().size();) {
				if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1) {
					System.out.println("Ueberpruefe Spalte");
					System.out.println("d= " + d + " c= " + c);
					for (int y = 0; y < tmp.get(0).getList().size();) {
						if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(y), e) == 1) {
							for (int k = 0; k < tmp.size() && isdominated; k++) {
							 	if (tmp.get(k).getValid() && !(d == y && c == e) ) {
									// Entscheidendes Kriterium!! Ueberpruefe ob Spalte A die Spalte B dominiert.
									if (!dominationcounterA) {
										if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 1
												&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(y),
														e) == 0)) {
											if (readdata.make1DatafileLong.numberOfTruesInColumn.get(d)
													.get(c) <= readdata.make1DatafileLong.numberOfTruesInColumn.get(y)
															.get(e)) {
												if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d),
														c) == 1) {
													counttrueA++;
													if (counttrueA == readdata.make1DatafileLong.numberOfTruesInColumn
															.get(d).get(c)) {
														isdominated = true;
													}
												}
											} else {
												dominationcounterA = true;
											}
										} else {
											dominationcounterA = true;
										}
									}
									// Entscheidendes Kriterium!! Ueberpruefe ob Spalte B die Spalte A dominiert.
									if (!dominationcounterB) {
										if (!(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d), c) == 0
												&& stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(y),
														e) == 1)) {
											if (readdata.make1DatafileLong.numberOfTruesInColumn.get(y)
													.get(e) <= readdata.make1DatafileLong.numberOfTruesInColumn.get(d)
															.get(c)) {
												if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).getList().get(d),
														c) == 1) {
													counttrueB++;
													if (counttrueB == readdata.make1DatafileLong.numberOfTruesInColumn
															.get(y).get(e)) {
														isdominated = true;
													}
												}
											} else {
												dominationcounterA = true;
											}
										} else {
											dominationcounterB = true;
										}
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
	public static void removeEqualColumns(ArrayList<DBit> tmp) {
		// Diese Funktion ist nicht mehr brauchbar! Denn sie wird dominiert du
		// die oberen Funktion
		// Start Initialisieren
		int counttrueA=0;
		boolean isdominated=false;
		//Zum fruehzeitigen Abbruch der Schleife 
		boolean notequalbreak=false;
		int c = 0;
		int e = 0;
		// Ende Initialisieren
		for (int d = 0; d < tmp.get(0).getList().size();) {
			if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c) == 1) {
				for (int y = 0; y < tmp.get(0).getList().size();) {
//					System.out.println("  ,");
					if (stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(y), e) == 1
							&& !(d == y && c == e)) {
						if (readdata.make1DatafileLong.numberOfTruesInColumn.get(d)
								.get(c) == readdata.make1DatafileLong.numberOfTruesInColumn.get(y).get(e)) {
							for (int row = 0; row < tmp.size() && !isdominated && !notequalbreak; row++) {
								if (tmp.get(row).getValid()) {
									if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d),
													c) == stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(y), e)) {
										if (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).getList().get(d), c) == 1) {
											counttrueA++;
											if (counttrueA == readdata.make1DatafileLong.numberOfTruesInColumn.get(d)
													.get(c)) {
												isdominated = true;
											}
										}
									} else {
										notequalbreak=true;
									}
								}
							}
							if (isdominated) {
								System.out.println("Loesche Spalte: y" + y + " e" + e);
								removingBits.removeColumn(tmp, y, e);
							}
						} else{
							notequalbreak=true;
						}
					}
					counttrueA = 0;
					isdominated = false;
					notequalbreak=false;
					e++;
					if (e == 64) {
						y++;
						e = 0;
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
