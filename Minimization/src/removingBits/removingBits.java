package removingBits;
import java.io.IOException;
import java.util.ArrayList;

public class removingBits {
	
	public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
	public static ArrayList<Boolean> saveRow = new ArrayList<Boolean>(); //Zur sp�teren Bestimmung bei den Dominanzen Speicher Reihe die weg ist aber nicht zur L�sung geh�rt(e.g. Allfalse Reihen
	
	
	public static void main (String [] args) throws IOException{
		ArrayList<ArrayList<Boolean>> tmp=readdata.make1Datafile.returnbigList();
		//ArrayList<ArrayList<Integer>> tmp1 = new ArrayList<ArrayList<Integer>>();		
		//Initialisierung von solution und saveRow; patternEmpty
		initsolution(tmp);
		initsaveRow();
		//Ausgabe der ungefilterterten Daten
		//print.arrayList.print2DTEST(tmp);		

		System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
		
		tmp=essentialdominating(tmp);
		System.out.println();
		System.out.println("saveRowAllTrue: "+saveRowAllTrue());
		System.out.println("Number of False in Solution: "+numberOfFalseinSolution());
				
		//Vorl�ufiges Ergebnis
		//System.out.println("saveRowAllTrue: "+saveRowAllTrue());
		//System.out.println("numberOfFalseinSolution "+numberOfFalseinSolution()+" ALLE D-Bits: " + solution.size());
		
		//System.out.println();
		
	}
	public static int numberOfFalseinSolution(){
		/** Gibt die Anzahl der false in solution zur�ck
		@author Jan Dennis Reimer		
		@version1.0
		@return													Anzahl der D-Bits
		*/
		int counter=0;
		for(int j=0;j<solution.size();j++){
			if(solution.get(j)==false)
				counter++;
		}
		return counter;
	}
	
	/*public static void initpatternEmpty(){ // wird zurzeit nicht gebraucht.
		/** initialisierung von patternEmpty
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		/
		for (int i=0 ; i<solution.size(); i++)
			patternEmpty.add(true);
	}*/
	
	public static void initsaveRow(){
		/** Initialisierung von saveRow. Hier werden alle Reihen gespeichert die gestrichen wurden, um einen �berblick zu behalten. 
		 *  Es soll jederzeit gewissheit herrschen, um welches DBit es sich handelt
		@author Jan Dennis Reimer		
		@version1.0
		@return						-
		*/
		//Es sind immer nur die �brigen D-Bits aktiv(true)
		for(int row =0;row<solution.size(); row++)
			saveRow.add(row, false);
	}
	public static void initsolution(ArrayList<ArrayList<Boolean>> tmp){
		/**  Initialisierung von solution: da zuerst die essentiellen D-Bits zur L�sung hinzugef�gt werden, wird solution direkt hiermit initialisiert
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													-
		*/
		solution=essentialBits.essential1D(tmp);
	}
	public static boolean saveRowAllTrue(){
		/**  �berpr�fung ob in saveRow nur true drin steht. Wenn ja, gebe true zur�ck
		@author Jan Dennis Reimer		
		@version1.0
		@return									alles true gebe true zur�ck, else false				-
		*/
		int counter=0;
		for (int i=0; i<saveRow.size();i++){
			if(saveRow.get(i))
				counter++;
		}
		if(saveRow.size()==counter)
			return true;
		else 
			return false;
			
	}
	public static ArrayList<ArrayList<Boolean>> essentialdominating(ArrayList<ArrayList<Boolean>> tmp) throws IOException{
		int counter=0;
		int counter1=2;
		while(!saveRowAllTrue()){
			
			while(counter!=counter1){
				counter=0;
				for(int x=0; x<saveRow.size();x++){
					if(saveRow.get(x))
						counter++;
				}
				counter1=0;
				System.out.println("Equal? = "+ counter + " " + counter1);
				
				System.out.println("Remove all False Columns: ");
				tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
				//print.arrayList.print2DTEST(tmp);

				System.out.println("Remove False Rows: ");
				tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
				//print.arrayList.print2DTEST(tmp);
				
				System.out.println("Remove all EssentialBits: ");
				tmp=essentialBits.removeAllEssential(tmp);
				//print.arrayList.print2DTEST(tmp);
				
				System.out.println("Remove all False Columns: ");
				tmp=falseRowsAndColumns.RemoveFalseColumn(tmp);
				//print.arrayList.print2DTEST(tmp);

				System.out.println("Remove False Rows: ");
				tmp=falseRowsAndColumns.RemoveFalseRows(tmp);											
				//print.arrayList.print2DTEST(tmp);
				
				System.out.println("Remove NOT dominating and Equal Rows: ");
		 		tmp=domRows.removeNotDominatingRowsAndEqualRows(tmp);
		 		//print.arrayList.print2DTEST(tmp);
		 		
				System.out.println("Remove all NOT dominating Columns and Eqaual Columns: ");
				tmp=domColumn.removeNotDominatingColumns(tmp);
				print.arrayList.print2DTEST(tmp);
				
				for(int k=0; k<saveRow.size();k++){
					if(saveRow.get(k))
						counter1++;
				}
				tmp = heuristic.removeBitWithMostTrues(tmp);
			}
			
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeOneRowTrueColumns(ArrayList<ArrayList<Boolean>> tmp, int Row){
		/** Die �bergebene Reihe(Row) soll zur L�sung hinzugef�gt werden. L�schen von einer Reihe sowie jeweils die dazugeh�rigen Spalten.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int Row											Die Reihe, die zur L�sung hinzugef�gt werden soll. Und au�erdem gel�scht werden soll
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
		/** Spalte die gel�scht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int Column										Spalte column wird gel�scht.
		@return													3D-ArrayList ohne diese Spalte
		*/
		for(int k = 0; k<tmp.size(); k++){
			tmp.get(k).remove(column);
		}
		return tmp;
	}
	public static ArrayList<ArrayList<Boolean>> removeRow (ArrayList<ArrayList<Boolean>> tmp, int row, boolean solutionBit){
		/** Reihe die gel�scht werden soll
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@param int row											Reihe row wird gel�scht.
		@param boolean solutionBit								Gibt an, ob es sich um ein Bit handelt, dass auch zur L�sung hinzugef�gt werden muss.
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
