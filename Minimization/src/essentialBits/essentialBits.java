package essentialBits;
import java.io.IOException;
import java.util.Arrays;
/*ArrayList<ArrayList<Boolean>> booleanList = einlesenderdaten.Einlesen_new.pattern(0);

ArrayList<ArrayList<ArrayList<Boolean>>> tmp=einlesenderdaten.Einlesen_new.testpatternOneData();
einlesenderdaten.Einlesen_new.print3DTEST(tmp);

ArrayList<Boolean> ary= arraywithessentiellenDBits(tmp,1);
for(int i=0; i<ary.size(); i++){
	System.out.println(ary.get(i));
}
*/
//import java.util.List;
import java.util.ArrayList;
import readdata.*;
/*
initsaveRow();
for(int k=0;k<saveRow.size();k++){
	for(int i=0; i<saveRow.get(k).size();i++){
		System.out.print(saveRow.get(k).get(i)+ " \t");
	}
	System.out.println();
}
*/
public class essentialBits {
	
	public static ArrayList<Boolean> solution = new ArrayList<Boolean>();
	public static ArrayList<Boolean> saveRow = new ArrayList<Boolean>(); //Zur sp�teren Bestimmung bei den Dominanzen
	public static ArrayList<Boolean> patternEmpty = new ArrayList<Boolean>();
	
	public static void main (String [] args) throws IOException{
		ArrayList<ArrayList<Boolean>> tmp=readdata.readingdata.testpatternOneData();
		//Initialisierung von solution und saveRow; patternEmpty
		initsolution(tmp);
		initsaveRow();
		initpatternEmpty();
		numberOfDBits();
		print1DBooleanArrayList(solution);
		//Ausgabe der ungefilterterten Daten
		//readdata.readingdata.print2DTEST(tmp);
		System.out.println("Remove all False Columns: ");
		tmp=RemoveFalseColumn(tmp);
		//readdata.readingdata.print2DTEST(tmp);
		System.out.println("Remove all EssentialBits: ");
		tmp=removeAllEssential(tmp);
		readdata.readingdata.print2DTEST(tmp);
		
		System.out.println("Remove one of the EqualColumns: ");
		tmp=removeEqualColumns(tmp);											
		readdata.readingdata.print2DTEST(tmp);
		
		
		
		//System.out.println("Remove all EqualRows: ");
		//tmp=removeEqualRows(tmp);
		//einlesenderdaten.Einlesen_new.print3DTEST(tmp);
		

				
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
		//Es sind immer nur die �brigen D-Bits aktiv(true)
		saveRow= solution;
		for(int row =0;row<saveRow.size(); row++){
			if (saveRow.get(row))
				saveRow.set(row, false);
			else
				saveRow.set(row, true);
		}
	}
	public static void initsolution(ArrayList<ArrayList<Boolean>> tmp){
		solution=essential1D(tmp);
	}
	public static ArrayList<ArrayList<Boolean>> removeEqualColumns(ArrayList<ArrayList<Boolean>> tmp){
		/** L�schen von gleichen Spalten aus der �berdeckungstabelle
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne gleiche Spalten
		*/
		ArrayList<ArrayList<Integer>> tmp1= new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tmp2 = new ArrayList<Integer>();
		ArrayList<Integer> tmp3 = new ArrayList<Integer>();						//Brauche 2 verschiedene tmp ArrayList um zu �berpr�fen, 
		tmp1.add(tmp2);tmp1.add(tmp3);											//ob sich im L�sungsarray tmp1 schon die gesuchte Spalte befindet
		int counter=0;															// k und z sind die Laufindizes ,um jede Spalte durchzugehen						
			for (int k=0; k<tmp.get(0).size(); k++){							//j - Reihe
				for(int z=0; z<tmp.get(0).size(); z++){				
					for(int j =0;j<tmp.size(); j++){						
						//System.out.println("Spalte k ="+k+" j= "+j+" z="+z );											//Zum Testen
						//System.out.println("jk "+(tmp.get(j).get(k))+ " \t jz "+ tmp.get(j).get(z));					//Zum Testen
					
						if(tmp.get(j).get(k).equals(tmp.get(j).get(z)) && z!=k){
							counter++;
						}
						//System.out.println("Z�hler "+counter);														//Zum Testen
					}
					//System.out.println("Z�hler "+counter + " =? "+ tmp.size());										//Zum Testen
					if(counter==tmp.size()){									//Es m�ssen alle Zeilen in einer Spalten �bereinstimmen
							tmp1.get(0).add(z);
							tmp1.get(1).add(k);
					}
					counter=0;	
				}
		}
			for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){					//Folgender Block l�scht jeweils die zu viel hinzugef�gten Zwischenergebnisse
				for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hiernach sind keine gespiegelten Zwischenergebnisse mehr vorhanden.
					if(tmp1.get(0).get(idx).equals(tmp1.get(1).get(idy)) && tmp1.get(1).get(idx).equals(tmp1.get(0).get(idy))&& idx!=idy ){
						tmp1.get(0).remove(idy);
						tmp1.get(1).remove(idy);
						idx--;
						
					}
				}
			}
			for(int idx=tmp1.get(0).size()-1; idx>=0;idx--){
				for(int idy=tmp1.get(1).size()-1; idy>=0;idy--){				//Hier werden nun die Zwischenergebnisse aussortiert sodass in '1' jeweils die zu l�schenden Spalten sich befinden
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
			
		/*for (int i=tmp.size()-1; i>=0; i--){//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
			if(tmp.get(0).isEmpty() || tmp.get(i).isEmpty()){
				tmp.remove(i);
				//patternEmpty.set(i, true);
			}
		}*/
		return tmp;
	}
	
	public static ArrayList<ArrayList<Boolean>> removeEqualRows(ArrayList<ArrayList<Boolean>> tmp){
		//Diese For-Schleife ist zum �berpr�fen ob es 2 gleiche Zeilen gibt.////&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
		
			for(int j =0;j<tmp.size(); j++){
				for(int k =0;k<tmp.size(); k++){
					if (tmp.get(j).equals(tmp.get(k)) && tmp.get(j).contains(true)){
						tmp=removeRow(tmp,j);
						//Aufgepasst es muss nicht unbedint zur L�sung hinzugef�gt werden
						//solution.get(i).set(k, true);				//in Solution erweitern
						//Aufpassen bei mehreren gleichen Reihen	
						System.out.println(j+" "+ k);
					}
				}
			}
		//�berpr�fen, denn beim Riesen Testmuster kommt es zu einem Fehler
		/*for (int i=tmp.size()-1; i>=0; i--){
			if(tmp.get(i).get(0).isEmpty()|| tmp.get(i).isEmpty()){
				tmp.remove(i);
				patternEmpty.set(i, true);
			}
		}*/
		
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
	
	public static ArrayList<ArrayList<Boolean>> removeAllEssential(ArrayList<ArrayList<Boolean>> tmp)throws IOException{
		ArrayList<Boolean> essent1D= essential1D(tmp);
		
		
			for(int row =tmp.size()-1;row>=0; row--){
				//System.out.println("Row: "+row);
				if(essent1D.get(row)){
					//System.out.println("Row: "+row);
					tmp= removeOneRowTrueColumns(tmp, row);
					 //Test
						/*for(int y = 0; y<tmp.size(); y++){
							for(int z = 0; z<tmp.get(y).size(); z++){
								System.out.print(tmp.get(y).get(z)+ "\t");
							}
							System.out.println();	
							
						}
						System.out.println();	
					*/
					
				}
			}
		
		//�berpr�fen, denn beim Riesen Testmuster kommt es zu einem Fehler
		//Falls ein Pattern schon die richtige L�sung hat, muss das jeweilige Pattern gel�scht werden
			/*if(tmp.get(0).isEmpty()|| tmp.isEmpty()){
				if(!tmp.isEmpty()){
				tmp.remove(i);
				patternEmpty.set(i, true);
				}
			}*/
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
	public static void print1DBooleanArrayList(ArrayList<Boolean> tmp){
		for(int i = 0; i<tmp.size(); i++){
			System.out.println(tmp.get(i)+ "\t");
		}
		System.out.println();
	}
	public static ArrayList<Boolean> BooleanAry1DToArrayList(boolean[] tmp){
		ArrayList<Boolean> k = new ArrayList<Boolean>();
		for(int i = 0; i<tmp.length; i++){
			k.add(tmp[i]);
		}
		return k;
	}
	public static ArrayList<Boolean> essential1D(ArrayList<ArrayList<Boolean>> tmp) {
		boolean[] essentialAry = new boolean[tmp.size()];
		int tmp1=0;
		int counter=0;
		for (int column = 0; column<tmp.get(0).size(); column++){
			for (int row=0; row<tmp.size(); row++){
				if(tmp.get(row).get(column)){
					counter++;
					tmp1= row;
				}
				//System.out.println(row + " " + counter + " " );
			}
			if(counter==1){
				essentialAry[tmp1]= true;
				
				//System.out.println("Reihe "+tmp1 + " true");
				counter=0;
			} else{
				counter=0;
			}
		}
		return BooleanAry1DToArrayList(essentialAry);
	}
	public static ArrayList<ArrayList<Boolean>> RemoveFalseRows(ArrayList<ArrayList<Boolean>> tmp){
		/**
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													2D-ArrayList ohne Reihen die aus False bestehen
		*/
		int j =0;
			while(j<tmp.size()){					
				//System.out.println(columnAllFalse(tmp,i,j) + " "+i +" " +j);
				if (rowAllFalse(tmp,j)){
					//System.out.println( "Pattern: " + i + "     j"+  j);
					for(int k = 0; k<tmp.size(); k++){
						tmp.get(j).remove(k);
					}
					if(j<tmp.size())						//Nach jeden Schritt wo eine Spalte entfernt wurde muss geguckt 
						j--;									//werden ob die nachger�ckte Spalte auch komplett False ist 
				}
			j++;
			}
		return tmp;
	}
	public static boolean rowAllFalse(ArrayList<ArrayList<Boolean>> tmp, int row){
		/** Gibt zur�ck, ob eine Reihe komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<Boolean>> tmp				Bekommt die 2D-ArrayList �bergeben	
		@param int row											Welche Reihe soll getestet werden	
		@return													Boolean, row False oder True
		*/
		boolean counter=false;									
		for(int i = 0; i<tmp.size(); i++){
			if(tmp.get(row).get(i)==true){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zur�chgegeben
				counter= true;
			}
		}
		if(counter==true){
			return false;
		}
		else{
			return true;
		}
	}
	public static ArrayList<ArrayList<Boolean>> RemoveFalseColumn(ArrayList<ArrayList<Boolean>> tmp){
		/** Berechnung einer ArrayList in der keine Spalte aus False besteht.
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	(�berdeckungstabelle)
		@return													3D-ArrayList ohne Spalte die aus False bestehen
		*/
		/*
		 *  Testmuster um diese Methode zu testen
		  	readdata.readingdata.print2DTEST(tmp);
			tmp=RemoveFalseColumn(tmp);
			readdata.readingdata.print2DTEST(tmp);
		 * */
		int j =0;
			while(j<tmp.get(0).size()){					//AUFGEPASST HIER get(0) nicht get(j), weil j abh�ngig sonst von j
				//System.out.println(columnAllFalse(tmp,i,j) + " "+i +" " +j);
				if (columnAllFalse(tmp,j)){
					//System.out.println( "Pattern: " + i + "     j"+  j);
					for(int k = 0; k<tmp.size(); k++){
						tmp.get(k).remove(j);
					}
					if(j<tmp.get(0).size())				//Nach jeden Schritt wo eine Spalte entfernt wurde muss geguckt 
						j--;									//werden ob die nachger�ckte Spalte auch komplett False ist 
				}
			j++;
			}
		return tmp;
	}
	public static boolean columnAllFalse(ArrayList<ArrayList<Boolean>> tmp, int column){
		/** Gibt zur�ck, ob eine Spalte komplett FALSE ist
		@author Jan Dennis Reimer		
		@version1.0
		@param ArrayList<ArrayList<ArrayList<Boolean>>> tmp		Bekommt die 3D-ArrayList �bergeben	
		@param int pattern										In welches Testmuster soll getestet werden	
		@param int column										Welche Spalte soll getestet werden	
		@return													Boolean, Spalte False oder True
		*/
		boolean counter=false;									
		for(int i = 0; i<tmp.size(); i++){
			//System.out.println("Ausgabe= "+tmp.get(pattern).get(i).get(column));
			if(tmp.get(i).get(column)==true){		//Sobald eine Zeile in der gegeben Spalte true ist, wird False zur�chgegeben
				counter= true;
			}
		}
		if(counter==true){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	
	/*
	public static int rowEssentielleBits(int pattern ,int spalte, ArrayList<ArrayList<ArrayList<Boolean>>> ary)throws IOException{
		/*
		 * //Testmuster f�r rowEssentielleBits in main kopieren
		System.out.println(rowEssentielleBits(0,9,tmp));
		 * Gibt die Reihe Zur�ck von einem essentiellen D-BIT
		 *
		int i=0;
		int j=0;
		int row=0;
		while(i<ary.get(0).get(0).size()){
			if(ary.get(pattern).get(i).get(spalte)== true){
				j++;
				row = i;
			}
		i++;	
		} 
		if (j==1){
			return row;
		}
		else{
			return 98979;
		}
	}
	
	public static ArrayList<Boolean> arraywithessentiellenDBits(ArrayList<ArrayList<ArrayList<Boolean>>> ary, int pattern) throws IOException {
		//Gibt ein Vektor zur�ck welche Ausg�nge essentielle Bits haben
		ArrayList<Boolean> essentiell = new ArrayList<Boolean>();
		
		for (int i=0; i<ary.get(0).get(0).size(); i++){
			if (rowEssentielleBits(pattern,i, ary)<98979){
				System.out.println("Output= "+rowEssentielleBits(pattern,i, ary)+" i= "+i);
				essentiell.add(true);
			}else{
				essentiell.add(false);
			}
		}
		return essentiell;
	}
	
	/*
	public static void printeinpattern(boolean[][][] p){
		int howmuchpattern= p.length;
		int Outputs =p[0].length;
		int Failures= p[0][0].length;
		int i=0;
			for(int y = 0; y<Outputs; y++){
				for(int z = 0; z<Failures; z++){
				System.out.print(p[i][y][z]+ "\t");
				}
				System.out.println();
			}
			System.out.println();
			System.out.println();
	}
	public static boolean[] arraywithessentiellenOutputs(boolean[][][] ary, int pattern) throws IOException {
		//Gibt ein Vektor zur�ck welche Ausg�nge essentielle Bits haben
		boolean[] essentiell = new boolean[ary[0].length];
		for (int i=0; i<ary[0].length; i++){
			if (rowEssentielleBits(pattern,i, ary)<98979){
				System.out.println("Output= "+rowEssentielleBits(pattern,i, ary)+" i= "+i);
				
				essentiell[rowEssentielleBits(pattern,i, ary)]=true;
			}
		}
		return essentiell;
	}
	
	public static boolean[][][] reihefalse(boolean[][][] ary, int pattern, int essentialrow){
		for(int k=0; k<ary[0][0].length; k++){
			//System.out.println("k= "+k);
			if(ary[pattern][essentialrow][k]==true){
				//System.out.println("essentielle Reihe: "+essentialrow);
				
				//ary[pattern][essentialrow][k]=false;
				
				for (int l= 0; l<ary[0].length; l++){
					ary[pattern][l][k]=false;
					//System.out.println("dominanzen ary: "+l + " " + k);
				}
			}
			//printeinpattern(ary);
		}
		return ary;
	}
	public static void dominanzen(boolean[][][] ary){
		
	}
	*/
	
}
