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
		//Initialisierung von solution und saveRow; patternEmpty
		initsolution(tmp);
		initsaveRow();
		initpatternEmpty();
		numberOfDBits();
		print.arrayList.print1DBooleanArrayList(solution);
		//Ausgabe der ungefilterterten Daten
		//print.arrayList.print2DTEST(tmp);
		System.out.println("Remove all False Columns: ");
		tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
		//print.arrayList.print2DTEST(tmp);
		System.out.println("Remove all EssentialBits: ");
		tmp=essentialBits.removeAllEssential(tmp);
		print.arrayList.print2DTEST(tmp);
		
		System.out.println("Remove all Equal Columns: ");
		tmp=removeEqualColumns(tmp);											
		print.arrayList.print2DTEST(tmp);
		System.out.println("Remove False Rows: ");
		tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
		print.arrayList.print2DTEST(tmp);
		
		

		//System.out.println(tmp.get(0).get(1).get(1).equals(tmp.get(0).get(1).get(1)) );
		
	}
	public static int numberOfDBits(){
		int counter=0;
		for(int j=0;j<solution.size();j++)
			if(solution.get(j)==false)
				counter++;
		System.out.println("D-Bits: " + counter);
		return counter;
	}
	
	public static void initpatternEmpty(){
		for (int i=0 ; i<solution.size(); i++)
			patternEmpty.add(true);
	}
	
	public static void initsaveRow(){
		//Es sind immer nur die übrigen D-Bits aktiv(true)
		saveRow= solution;
		/*for(int row =0;row<saveRow.size(); row++){
			if (saveRow.get(row))
				saveRow.set(row, false);
			else
				saveRow.set(row, true);
		}*/
	}
	public static void initsolution(ArrayList<ArrayList<Boolean>> tmp){
		solution=essentialBits.essential1D(tmp);
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
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();						//Brauche 2 verschiedene tmp ArrayList um zu überprüfen, 
		tmp1.add(tmp2);tmp1.add(tmp3);											//ob sich im Lösungsarray tmp1 schon die gesuchte Spalte befindet
		int counter=0;															// k und z sind die Laufindizes ,um jede Spalte durchzugehen						
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
			for(int x=tmp1.get(0).size()-1; x>=0;x--){
				//System.out.println("Remove: "+tmp1.get(0).get(x)+" "+ tmp1.get(1).get(x));//Zum Testen
				removeColumn(tmp,tmp1.get(1).get(x));							//
		}
		return tmp;
	}
	
	public static ArrayList<ArrayList<Boolean>> removeEqualRows(ArrayList<ArrayList<Boolean>> tmp){
		//Diese For-Schleife ist zum überprüfen ob es 2 gleiche Zeilen gibt.////&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		
			for(int j =0;j<tmp.size(); j++){
				for(int k =0;k<tmp.size(); k++){
					if (tmp.get(j).equals(tmp.get(k)) && tmp.get(j).contains(true)){
						tmp=removeRow(tmp,j);
						//Aufgepasst es muss nicht unbedint zur Lösung hinzugefügt werden
						//solution.get(i).set(k, true);				//in Solution erweitern
						//Aufpassen bei mehreren gleichen Reihen	
						System.out.println(j+" "+ k);
					}
				}
			}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeOneRowTrueColumns(ArrayList<ArrayList<Boolean>> tmp, int Row){
		for(int i= tmp.get(Row).size()-1; i>=0; i--){
			
			if(tmp.get(Row).get(i)==true){
				tmp=removeColumn(tmp,i);
			}
		}
		tmp=removeRow(tmp, Row);
		return tmp;
	}
	
	
	public static ArrayList<ArrayList<Boolean>> removeColumn(ArrayList<ArrayList<Boolean>> tmp, int column){
		for(int k = 0; k<tmp.size(); k++){
			tmp.get(k).remove(column);
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeRow (ArrayList<ArrayList<Boolean>> tmp, int row){
		tmp.remove(row);
		return tmp;
	}
	
}
