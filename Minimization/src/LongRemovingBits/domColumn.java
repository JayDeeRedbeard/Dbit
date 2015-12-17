package LongRemovingBits;
import java.io.IOException;
import java.util.ArrayList;

import readdata.longData;

public class domColumn {
	public static void removeNotDominatingColumns(ArrayList<ArrayList<Long>> tmp) throws IOException{
		/**  Remove all NOT dominating Columns
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList uebergeben	(ueberdeckungstabelle)
		@return													2D-ArrayList ohne die Zeilen, die dominiert wurden, also (hier) nur 2.Spalte loeschen.
		*/
		ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();
		if(!removingBits.validRowAllFalse()){
		for (int x=0; x<tmp.get(0).size()*64;x++){
			System.out.println("Ueberpruefe Spalte"+ x );
			tmp1=dominatingColumns(tmp,x);									//Die uebergebene ArrayList hat 2 Spalten (beide nicht sortiert, 
																		//sowie mit moeglich doppelten Eintraegen)
			//Removing the Columns
			for(int y=tmp1.get(1).size()-1;y>=0;y--){
				removingBits.removeColumn(tmp, tmp1.get(1).get(y) ,tmp1.get(2).get(y));
			}
		}
		}
	}
	public static ArrayList<ArrayList<Integer>> dominatingColumns(ArrayList<ArrayList<Long>> tmp, int column){
		/**  Finde alle Reihen die dominiert werden von int row.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList uebergeben	(ueberdeckungstabelle)
		@param int row											Es wird jeweils ueberprueft, ob diese Reihe irgendeine andere Reihe dominiert
		@return													Es wird eine ArrayList zurueckgegeben, wo die erste Spalte jeweils die dominierende Reihe ist.
																Die 2.Spalte ist dann die nicht dominierende Spalte
		*/
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();						//tmp2 und 3 zum initailisieren von tmp1.
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();		
		ArrayList<Integer> tmp4 = new ArrayList<Integer>();	
		tmp1.add(tmp2);tmp1.add(tmp3);	tmp1.add(tmp4);
		int counttrue=0;
		boolean isdominated= true;
		int d=0;
		int c=0;
		int e=0;
		for(int z=0; z<column ; z++){
			c++;
			if (c>63){
				c=0;
				d++;
			}
		}
		//System.out.println("d= "+d + " c= "+c);
		for(int y=0; y<tmp.get(0).size();){
			for (int k=0; k<tmp.size() && isdominated && stuff.DirtyLittleHelpers.getBitAtPosition(readdata.longData.validColumn.get(d), c)==1; k++){	
				if(longData.validRow.get(k)){
				if( !(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1 && stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(y), e)==0) ){	//Entscheidendes Kriterium!!
					if(!(d==y  && c==e)){
					//counttrue muss groesser gleich 1 sein,
					//da es sonst keine dominierens Zeile sein kann
					if(stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1)
						counttrue++;
					//System.out.println("d= "+d + " c= "+c+ " k= "+k+ " y= " + y+" e= " + e+ " counttrue= "+ counttrue);
					}
				}
				else{
					//System.out.println("dc= "+stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)+ " "+"ye= "+ +stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(y), e) );
					//System.out.println(" Column "+ column + " ist nicht dominiernd auf y= "+y+ " e= "+e);
					isdominated=false;
					}
				}
				//System.out.println("counttrue= " + counttrue );	
			}
			if(counttrue>=1 && isdominated)	{
				//System.out.println(" Column "+ column + " ist dominiernd auf y= "+y+ " e= "+e);
				tmp1.get(0).add(column);
				tmp1.get(1).add(y);
				tmp1.get(2).add(e);
			}
			isdominated=true;
			counttrue=0;
			e++;
			if(e==64){
				y++;
				e=0;
			}
		}
		return tmp1;
	}
}
