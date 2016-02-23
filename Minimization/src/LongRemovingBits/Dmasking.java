package LongRemovingBits;

import java.util.ArrayList;

import readdata.DBit;
import readdata.longData;

public class Dmasking {
	public static void dominatingRows(ArrayList<DBit> tmp, long startTime) {
		int counttrueA = 0;
		int counttrueB = 0;
		Long var;
//		int dominationcounterA = 0;
		boolean dominationA = true;
//		int dominationcounterB = 0;
		boolean dominationB = true;
		long endTime ;
		boolean isdominated = true;
		if (!removingBits.validFalse(tmp)) {
			for (int row = tmp.size() - 1; row >= 0 && removingBits.stopdomination; row--) {
				if (tmp.get(row).getValid()) {
					for (int k = 0; k < tmp.size() && removingBits.stopdomination; k++) {
						if (tmp.get(k).getValid() && row!=k) {
							for (int d = 0; d < tmp.get(0).getList().size(); d++) {
								var = (tmp.get(k).getList().get(d) & longData.validColumn.get(d) & tmp.get(row).getList().get(d));
								if (readdata.make1DatafileLong.numberOfTruesInRow
										.get(row) >= readdata.make1DatafileLong.numberOfTruesInRow.get(k) && dominationA) {
									if (var == (tmp.get(row).getList().get(d) & longData.validColumn.get(d)) ) { // Auf// row// dominierend																	
										counttrueA++;
										if (var==0){
//											dominationcounterA++;
										}
									}
								} else {
									dominationA=false;
								}
								if (readdata.make1DatafileLong.numberOfTruesInRow
										.get(row) <= readdata.make1DatafileLong.numberOfTruesInRow.get(k) && dominationB) {
									if (var == (tmp.get(k).getList().get(d) & longData.validColumn.get(d)) ) { // Auf// k	// dominierend								
										counttrueB++;
										if (var==0){
//											dominationcounterB++;
										}
									}
								} else{
									dominationB=false;
								}
								if(!dominationA && !dominationB){
									isdominated=false;
									break;
								}
							}
							if (counttrueA == tmp.get(0).getList().size() && isdominated && /*dominationcounterA!=tmp.get(0).getList().size() && */dominationA) {
								System.out.println(" Reihe " + k + " ist dominiernd auf " + row);
								LongRemovingBits.removingBits.counterremoveRows++;
								removingBits.removeRow(tmp, row, false);
								// Damit bei gleichen Zeilen nicht beide geloescht werden
								isdominated = false;	
							}
							if (counttrueB == tmp.get(0).getList().size() && isdominated && /*dominationcounterB!=tmp.get(0).getList().size() && */dominationB) {
								System.out.println(" Reihe " + row + " ist dominiernd auf " + k);
								removingBits.removeRow(tmp, k, false);
								LongRemovingBits.removingBits.counterremoveRows++;
							}
							isdominated=true;
							counttrueA = 0;
							counttrueB = 0;
//							dominationcounterA = 0;
							dominationA = true;
//							dominationcounterB = 0;
							dominationB = true;
						}
						endTime = System.nanoTime();
						removingBits.duration = (float) (endTime - startTime)/(1000000000)/60;
						if (removingBits.duration> 24*60){
							removingBits.stopdomination=false;
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
		public static void removeEqualRows(ArrayList<DBit> tmp, long startTime) {
			int counttrueA = 0;
			Long var;
			boolean dominationcounterA = true;
			boolean dominationA = true;
			long endTime ;float duration;
			
			if (!removingBits.validFalse(tmp)) {
				for (int row = tmp.size() - 1; row >= 0 && removingBits.stopdomination; row--) {
					if (tmp.get(row).getValid()) {
						System.out.println("Reihe " + row + " wird ueberprueft");
						for (int k = 0; k < tmp.size() && removingBits.stopdomination; k++) {
							if (tmp.get(k).getValid() && k!=row) {
								if (readdata.make1DatafileLong.numberOfTruesInRow
										.get(row) == readdata.make1DatafileLong.numberOfTruesInRow.get(k)) {
									for (int d = 0; d < tmp.get(0).getList().size() &&removingBits.stopdomination ; d++) {
										var = (tmp.get(k).getList().get(d) & longData.validColumn.get(d)
												& tmp.get(row).getList().get(d));
										if (var == (tmp.get(k).getList().get(d) & longData.validColumn.get(d)) && var == (tmp.get(row).getList().get(d) & longData.validColumn.get(d))){
											counttrueA++;
//											if(var==0){
//												dominationcounterA=false;
//											}
										} else{
											dominationA=false;
											break;
										}
									}
								if (counttrueA == tmp.get(0).getList().size() && dominationcounterA && dominationA) {
//									System.out.println(" Row " + k + " is equal " + row);
									LongRemovingBits.removingBits.counterremoveRows++;
									removingBits.removeRow(tmp, row, false);
								}
								
							}
						}
							dominationA=true;
							counttrueA=0;
							endTime = System.nanoTime();
							duration = (float) (endTime - startTime)/(1000000000)/60;
							if (duration> 24*60){
								removingBits.stopdomination=false;
							}
					}
				}
			}
		}
	}
}
