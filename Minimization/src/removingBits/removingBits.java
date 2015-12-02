package removingBits;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import readdata.*;

public class removingBits {
	
	public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
	public static ArrayList<Boolean> saveRow = new ArrayList<Boolean>(); //Zur späteren Bestimmung bei den Dominanzen Speicher Reihe die weg ist aber nicht zur Lösung gehört(e.g. Allfalse Reihen
	public static ArrayList<Boolean> patternEmpty = new ArrayList<Boolean>();
	
	public static void main (String [] args) throws IOException{
		ArrayList<ArrayList<Boolean>> tmp=readdata.readingdata.testpatternOneData();
		//ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();		
		//Initialisierung von solution und saveRow; patternEmpty
		initsolution(tmp);
		initsaveRow();
		//initpatternEmpty();
		for (int index=0; index<9; index++){
			//Ausgabe der ungefilterterten Daten
			//print.arrayList.print2DTEST(tmp);
			if(!saveRowAllTrue()){
			System.out.println(index+" Remove all False Columns: ");
			tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+" Remove False Rows: ");
			tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+" Remove all EssentialBits: ");
			tmp=essentialBits.removeAllEssential(tmp);
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+" Remove all False Columns: ");
			tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+" Remove False Rows: ");
			tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+"Remove all Equal Columns: ");
			tmp=removeEqualColumns(tmp);											
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+"Remove Equal Rows: ");
			tmp= removeEqualRows(tmp);
			//print.arrayList.print2DTEST(tmp);
			}if(!saveRowAllTrue()){
			System.out.println(index+"Remove NOT dominating Rows: ");
			tmp=removeNotDominatingRows(tmp);
			//print.arrayList.print2DTEST(tmp);
			}
		}
		numberOfFalseinSolution();
		//Vorläufiges Ergebnis
		System.out.println("solution");
		int counter=0;
		for (int j=0; j<solution.size(); j++)
			if (!solution.get(j))
				counter++;
		print.arrayList.print1DBooleanArrayList(solution);
		System.out.println(counter + " "+ solution.size());
	
		
		
	}
	public static int numberOfFalseinSolution(){
		/** Gibt die Anzahl der false in solution zurück
		@author Jan Dennis Reimer		
		@version1.0
		@return													Anzahl der D-Bits
		*/
		int counter=0;
		for(int j=0;j<solution.size();j++)
			if(solution.get(j)==false)
				counter++;
		System.out.println("D-Bits: " + counter);
		return counter;
	}
	
	/*public static void initpatternEmpty(){ // wird zurzeit nicht gebraucht.
		/** initialisierung von patternEmpty
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		/
		for (int i=0 ; i<solution.size(); i++)
			patternEmpty.add(true);
	}*/
	
	public static void initsaveRow(){
		/** Initialisierung von saveRow. Hier werden alle Reihen gespeichert die gestrichen wurden, um einen Überblick zu behalten. 
		 *  Es soll jederzeit gewissheit herrschen, um welches DBit es sich handelt
		@author Jan Dennis Reimer		
		@version1.0
		@return						-
		*/
		//Es sind immer nur die übrigen D-Bits aktiv(true)
		for(int row =0;row<solution.size(); row++)
			saveRow.add(row, false);
	}
	public static void initsolution(ArrayList<ArrayList<Boolean>> tmp){
		/**  Initialisierung von solution: da zuerst die essentiellen D-Bits zur Lösung hinzugefügt werden, wird solution direkt hiermit initialisiert
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList übergeben	(Überdeckungstabelle)
		@return													-
		*/
		solution=essentialBits.essential1D(tmp);
	}
	public static boolean saveRowAllTrue(){
		/**  Überprüfung ob in saveRow nur true drin steht. Wenn ja, gebe true zurück
		@author Jan Dennis Reimer		
		@version1.0
		@return									alles true gebe true zurück, else false				-
		*/
		int counter=0;
		for (int i=0; i<saveRow.size();i++){
			counter++;
		}
		if(saveRow.size()==counter)
			return true;
		else 
			return false;
			
	}
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
				removeRow(tmp,tmp1.get(1).get(y),false);
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
			for(int z=0; z<tmp.get(0).size() && isdominated&& k!=row; z++){
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
				tmp1.get(0).add(row);
				tmp1.get(1).add(k);
			}
			isdominated=true;
			counttrue=0;
		}
		return tmp1;
	}
	public static ArrayList<ArrayList<Boolean>> removeEqualColumns(ArrayList<ArrayList<Boolean>> tmp){
		/** Löschen von gleichen Spalten aus der Überdeckungstabelle
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		*/
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();						//Brauche 2 verschiedene tmp ArrayList um zu initialisieren, 
		tmp1.add(tmp2);tmp1.add(tmp3);											//ob sich im Lösungsarray tmp1 schon die gesuchte Spalte befindet
		int counter=0;															//k und z sind die Laufindizes ,um jede Spalte durchzugehen						
			for (int k=0; k<tmp.get(0).size(); k++){							//j - Reihe
				for(int z=0; z<tmp.get(0).size(); z++){				
					for(int j =0;j<tmp.size(); j++){						
						//System.out.println("Spalte k ="+k+" j= "+j+" z="+z );											//Zum Testen
						//System.out.println("jk "+(tmp.get(j).get(k))+ " \t jz "+ tmp.get(j).get(z));					//Zum Testen
					
						if(tmp.get(j).get(k).equals(tmp.get(j).get(z)) && z!=k){
							counter++;
						}
						//System.out.println("Zähler "+counter);														//Zum Testen
					}
					//System.out.println("Zähler "+counter + " =? "+ tmp.size());										//Zum Testen
					if(counter==tmp.size()){									//Es müssen alle Zeilen in einer Spalten übereinstimmen
							tmp1.get(0).add(z);
							tmp1.get(1).add(k);
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
			for(int x=tmp1.get(0).size()-1; x>=0;x--){							//Löschen von Spalte 1 (tmp1)
				//System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));	//Zum Testen
				removeColumn(tmp,tmp1.get(1).get(x));
			}
		return tmp;
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
			removeRow(tmp,tmp1.get(1).get(x),false);	
			for(int j=0; j<removingBits.saveRow.size() && j<=tmp1.get(1).get(x);j++){			//Um die richtigen Zeilen zu speichern
				if(removingBits.saveRow.get(j))
					save++;
			}
			removingBits.saveRow.set(tmp1.get(1).get(x)+save, true);
			save=0;
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeOneRowTrueColumns(ArrayList<ArrayList<Boolean>> tmp, int Row){
		/** Die übergebene Reihe(Row) soll zur Lösung hinzugefügt werden. Löschen von einer Reihe sowie jeweils die dazugehörigen Spalten.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@param int Row											Die Reihe, die zur Lösung hinzugefügt werden soll. Und außerdem gelöscht werden soll
		@return													3D-ArrayList ohne diese Reihe und Spalten
		*/
		for(int i= tmp.get(Row).size()-1; i>=0; i--){
			
			if(tmp.get(Row).get(i)==true){
				tmp=removeColumn(tmp,i);
			}
		}
		tmp=removeRow(tmp, Row,true);
		return tmp;
	}
	
	
	public static ArrayList<ArrayList<Boolean>> removeColumn(ArrayList<ArrayList<Boolean>> tmp, int column){
		/** Spalte die gelöscht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@param int Column										Spalte column wird gelöscht.
		@return													3D-ArrayList ohne diese Spalte
		*/
		for(int k = 0; k<tmp.size(); k++){
			tmp.get(k).remove(column);
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeRow (ArrayList<ArrayList<Boolean>> tmp, int row, boolean solutionBit){
		/** Reihe die gelöscht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList übergeben	(Überdeckungstabelle)
		@param int row											Reihe row wird gelöscht.
		@param boolean solutionBit								Gibt an, ob es sich um ein Bit handelt, dass auch zur Lösung hinzugefügt werden muss.
		@return													3D-ArrayList ohne diese Spalte
		*/
		tmp.remove(row);
		
		int save=0;
		for(int j=0; j<removingBits.saveRow.size() && j<=row+save;j++){			//Um die richtigen Zeilen zu speichern
			if(removingBits.saveRow.get(j))										//Berechnung von save, bzw Berechnung wie viele trues schon in saveRow gespeichert sind
				save++;
		}
		removingBits.saveRow.set(row+save, true);
		if (solutionBit)
			removingBits.solution.set(row+save, true);
		save=0;
		return tmp;
	}
	
}
