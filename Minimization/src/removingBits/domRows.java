package removingBits;
import java.util.ArrayList;
public class domRows{
public static ArrayList<ArrayList<Boolean>> removeNotDominatingRows(ArrayList<ArrayList<Boolean>> tmp){
	/**  Remove all NOT dominating Rows.
	@author Jan Dennis Reimer		
	@version1.0
	@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList übergeben	(Überdeckungstabelle)
	@return													2D-ArrayList ohne die Zeilen, die dominiert wurden, also (hier) nur 2.Spalte löschen.
	*/
	ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();
	for (int x=tmp.size()-1; x>=0;x--){
		tmp1=dominatingRows(tmp,x);									//Die übergebene ArrayList hat 2 Spalten (beide nicht sortiert, 
																	//sowie mit möglich doppelten Einträgen)
		// Sortieralgorithmus Bubble Sort start:
		int temp1, temp2;
		for(int i=0; i<tmp1.get(0).size(); i++)
		{
		        for(int k= i+1; k <tmp1.get(0).size(); k++)
		        {
		                if((tmp1.get(1).get(k)).compareTo(tmp1.get(1).get(i)) < 0)
		                {
		                	 temp1 = tmp1.get(1).get(i);
		                	 temp2 = tmp1.get(0).get(i);
		                     tmp1.get(1).set(i, tmp1.get(1).get(k));
		                     tmp1.get(0).set(i, tmp1.get(0).get(k));
		                     tmp1.get(1).set(k,temp1);
		                     tmp1.get(0).set(k,temp2);	
		                }
		        }
		        System.out.println(tmp1.get(0).get(i)+ tmp1.get(1).get(i));   
		}///BubbleSort End
		
		//Removing the Rows
		for(int y=tmp1.get(0).size()-1;y>=0;y--){
			removingBits.removeRow(tmp,tmp1.get(1).get(y),false);
		}
		
	}
	return tmp;
}
public static ArrayList<ArrayList<Integer>> dominatingRows(ArrayList<ArrayList<Boolean>> tmp, int row){
	/**  Finde alle Reihen die dominiert werden von int row.
	@author Jan Dennis Reimer		
	@version1.0
	@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList übergeben	(Überdeckungstabelle)
	@param int row											Es wird jeweils überprüft, ob diese Reihe irgendeine andere Reihe dominiert
	@return													Es wird eine ArrayList zurückgegeben, wo die erste Spalte jeweils die dominierende Reihe ist.
															Die 2.Spalte ist dann die nicht dominierende Spalte
	*/
	/* Zum Testen in Main einfügen.
	for(int j=0;j<tmp1.get(0).size();j++)
	System.out.println(tmp1.get(0).get(j)+ " "+ tmp1.get(1).get(j));
	 * */
	ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> tmp2 = new ArrayList<Integer>();						//tmp2 und 3 zum initailisieren von tmp1.
	ArrayList<Integer> tmp3 = new ArrayList<Integer>();						 
	tmp1.add(tmp2);tmp1.add(tmp3);	
	int counttrue=0;
	boolean isdominated= true;
	for (int k=0; k<tmp.size(); k++){							
		for(int z=0; z<tmp.get(0).size() && isdominated&& k!=row ; z++){
			if( !(tmp.get(row).get(z).equals(false) && tmp.get(k).get(z).equals(true)) ){	//Entscheidendes Kriterium!!
				//System.out.println("row= "+row + " z= "+z+ "k= "+k);
				//System.out.println(tmp.get(row).get(z)+ "\t"+ tmp.get(k).get(z));
				if(tmp.get(row).get(z)){													//counttrue muss größer als 1 sein,
					counttrue++;															//da es sonst keine dominierens Zeile sein kann
				}
			}
			else{
					//System.out.println(" Reihe "+ row + " ist nicht dominiernd auf "+k);
					isdominated=false;
				}
			//System.out.println("counttrue= " + counttrue );	
		}
		if(counttrue>1 && isdominated)	{
			//System.out.println(" Reihe "+ row + " ist dominiernd auf "+k);
			tmp1.get(0).add(row);
			tmp1.get(1).add(k);
		}
		isdominated=true;
		counttrue=0;
	}
	return tmp1;
}
public static ArrayList<ArrayList<Boolean>> removeEqualRows(ArrayList<ArrayList<Boolean>> tmp){
	/** Löschen von gleichen Reihen aus der Überdeckungstabelle
	@author Jan Dennis Reimer		
	@version1.0
	@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
	@return													3D-ArrayList ohne gleiche Reihen
	*/
	ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> tmp2 = new ArrayList<Integer>();
	ArrayList<Integer> tmp3 = new ArrayList<Integer>();	
	tmp1.add(tmp2);tmp1.add(tmp3);	
	int counter= 0;
	int save=0;
	for(int j =0;j<tmp.size(); j++){
		for (int k=0; k<tmp.size(); k++){							
			for(int z=0; z<tmp.get(0).size(); z++){
				//System.out.println("j= "+j + " z= "+z+ "k= "+k);
				//System.out.println(tmp.get(j).get(z)+ "\t"+ tmp.get(k).get(z));
				if(tmp.get(j).get(z).equals(tmp.get(k).get(z)) && j!=k){
					counter++;
				}
			}
			//System.out.println("Zähler "+counter + " =? "+ tmp.get(0).size());	
			if (counter==tmp.get(0).size()){
				tmp1.get(0).add(k);
				tmp1.get(1).add(j);
			}
			counter=0;
		}
	}
	for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){					//Folgender Block löscht jeweils die zu viel hinzugefügten Zwischenergebnisse
		for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hiernach sind keine gespiegelten Zwischenergebnisse mehr vorhanden.
			if(tmp1.get(0).get(idx).equals(tmp1.get(1).get(idy)) && tmp1.get(1).get(idx).equals(tmp1.get(0).get(idy))&& idx!=idy ){
				tmp1.get(0).remove(idy);
				tmp1.get(1).remove(idy);
				idx--;
				
			}
		}
	}
	for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){
		for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hier werden nun die Zwischenergebnisse aussortiert sodass in '1' jeweils die zu löschenden Spalten sich befinden
			if(tmp1.get(1).get(idx).equals(tmp1.get(1).get(idy))&& idx!=idy ){
				tmp1.get(0).remove(idy);
				tmp1.get(1).remove(idy);
				idx--;
				
			}
		}
	}
	for(int x=tmp1.get(1).size()-1; x>=0;x--){
		System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));//Zum Testen
		removingBits.removeRow(tmp,tmp1.get(1).get(x),false);	
		for(int j=0; j<removingBits.saveRow.size() && j<=tmp1.get(1).get(x);j++){			//Um die richtigen Zeilen zu speichern
			if(removingBits.saveRow.get(j))
				save++;
		}
		removingBits.saveRow.set(tmp1.get(1).get(x)+save, true);
		save=0;
	}
	return tmp;
}
}