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
public static void removeEqualRows(ArrayList<ArrayList<Long>> tmp){
	// Diese Funktion ist nicht mehr brauchbar! Denn sie wird dominiert du die oberen Funktion
	//Start Initialisieren
	ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
	tmp1.add(new ArrayList<Integer>());
	tmp1.add(new ArrayList<Integer>());	
	int counter= 0;
	int c=0;
	//Ende Initialisieren
	for(int j =0;j<tmp.size(); j++){
		for (int k=0; k<tmp.size(); k++){							
			for(int d=0; d<tmp.get(0).size();){
				//System.out.println("j= "+j + " z= "+z+ "k= "+k);
				//System.out.println(tmp.get(j).get(z)+ "\t"+ tmp.get(k).get(z));
				
				// Wenn beide Zeilen gleich sind, dann zaehle counter hoch
				if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(j).get(d), c)==stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) && j!=k){
					counter++;
				}
				c++;
				if(c==64){
					d++;
					c=0;
				}
			}
			//System.out.println("Zaehler "+counter + " =? "+ tmp.get(0).size());	
			if (counter==tmp.get(0).size()*64){
				tmp1.get(0).add(k);
				tmp1.get(1).add(j);
			}
			counter=0;
		}
	}
	for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){					//Folgender Block loescht jeweils die zu viel hinzugefuegten Zwischenergebnisse
		for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hiernach sind keine gespiegelten Zwischenergebnisse mehr vorhanden.
			if(tmp1.get(0).get(idx).equals(tmp1.get(1).get(idy)) && tmp1.get(1).get(idx).equals(tmp1.get(0).get(idy))&& idx!=idy ){
				tmp1.get(0).remove(idy);
				tmp1.get(1).remove(idy);
				idx--;
				
			}
		}
	}
	for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){
		for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hier werden nun die Zwischenergebnisse aussortiert sodass in '1' jeweils die zu loeschenden Spalten sich befinden
			if(tmp1.get(1).get(idx).equals(tmp1.get(1).get(idy))&& idx!=idy ){
				tmp1.get(0).remove(idy);
				tmp1.get(1).remove(idy);
				idx--;
				
			}
		}
	}
	for(int x=tmp1.get(1).size()-1; x>=0;x--){
		//System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));//Zum Testen
		removingBits.removeRow(tmp,tmp1.get(1).get(x),false);	
	}
}
}