package LongRemovingBits;
import java.util.ArrayList;

import readdata.longData;
public class domRows{
	/**  Remove all NOT dominating Rows.
	@author Jan Dennis Reimer		
	@version1.0
	@param ArrayList<ArrayList<Long>> tmp					Bekommt die 2D-ArrayList uebergeben	(Ueberdeckungstabelle)
	@return													2D-ArrayList ohne die Zeilen, die dominiert wurden, also (hier) nur 1.Spalte (von Spalte 0 und 1) loeschen.
	 */
	public static void removeNotDominatingRowsAndEqualRows(ArrayList<ArrayList<Long>> tmp){
		ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();
		for (int x=tmp.size()-1; x>=0;x--){
			if(longData.validRow.get(x)){
				System.out.println("Reihe "+x+" wird ueberprueft");
				tmp1=dominatingRows(tmp,x);	
			}
			//Removing the not dominating  Rows (nur Spalte 1 loeschen )
			if(!tmp1.isEmpty()){
				for(int y=tmp1.get(0).size()-1;y>=0;y--){
					removingBits.removeRow(tmp,tmp1.get(1).get(y),false);
				}
			}
		}
	}
	/**  Finde alle Reihen, die von int row dominiert werden .
	@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList uebergeben	(ueberdeckungstabelle)
	@param int row											Es wird jeweils ueberprueft, ob diese Reihe irgendeine andere Reihe dominiert
	@return													Es wird eine ArrayList zurueckgegeben, wo die erste Spalte jeweils die dominierende Reihe ist.
															Die 2.Spalte ist dann die nicht dominierende Reihe
	*/
public static ArrayList<ArrayList<Integer>> dominatingRows(ArrayList<ArrayList<Long>> tmp, int row){
	/* Zum Testen in Main einfuegen.
	ArrayList<ArrayList<Integer>> tmp1= domRows.dominatingRows(tmp,5);
	for(int j=0;j<tmp1.get(0).size();j++)
		System.out.println(tmp1.get(0).get(j)+ " "+ tmp1.get(1).get(j));
	 * */
	ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> tmp2 = new ArrayList<Integer>();						//tmp2 und 3 zum initailisieren von tmp1.
	ArrayList<Integer> tmp3 = new ArrayList<Integer>();	
	tmp1.add(tmp2);tmp1.add(tmp3); 
	int counttrue=0;
	int c=0;
	boolean isdominated= true;
	if(!removingBits.validRowAllFalse()){
	for(int k=0; k<tmp.size(); k++){
		if(readdata.longData.validRow.get(k)){
			for(int d=0; d<tmp.get(0).size() && isdominated ;){
				if(stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c)==1){
					//System.out.println("row= "+row + " k= "+k + " d "+d +" c  "+ c);
					//System.out.println(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)+ "\t"+ 
					//	stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c) + "\t");
					if( !(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==0 && 
							stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1)  && k!=row){	//Entscheidendes Kriterium!!
						
						if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(row).get(d), c)==1){	//counttrue muss groesser als 1 sein,
							counttrue++;										//da es sonst keine dominierens Zeile sein kann
						}
					}
					else{
						//System.out.println(" Reihe "+ row + " ist nicht dominiernd auf "+k);
						isdominated=false;
					}
					//System.out.println("counttrue= " + counttrue + "\t"+stuff.DirtyLittleHelpers.getBitAtPosition(longData.validColumn.get(d), c));	
				}
				c++;
				if(c==64){
					d++;
					c=0;
				}
			}
			if(counttrue>1 && isdominated)	{
				System.out.println(" Reihe "+ row + " ist dominiernd auf "+k);
				tmp1.get(0).add(row);
				tmp1.get(1).add(k);
			}
			isdominated=true;
			counttrue=0;
			c=0;
		}
	}
	}
	return tmp1;
}
/** Loeschen von gleichen Reihen aus der Ueberdeckungstabelle
@author Jan Dennis Reimer		
@version1.0
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