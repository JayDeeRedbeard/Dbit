package VerarbeitungderDaten;
import java.io.IOException;
import einlesenderdaten.*;

public class Verarbeitenderdaten {
	public static void main (String [] args) throws IOException{
		boolean [][][] tmp=einlesenderdaten.einlesen.testpatternOneData();
		//boolean[][] pa= einlesenderdaten.einlesen.pattern(0);
		//einlesenderdaten.einlesen.printpattern2D(pa);
		printeinpattern(tmp);
		
		
		//Testmuster für arraywithessentiellenOutputs()
		boolean[]test=arraywithessentiellenOutputs(tmp,0);
		int j=0;
		for(int i= 0; i<test.length;i++){
			//Testmuster für reihefalse
			
			//Für Kurze :
			//System.out.println(test[i]);
			//Für lange Test:
			
			if(test[i]==true){
				System.out.println(i);
				j++;
				tmp=reihefalse(tmp,0,i);
				printeinpattern(tmp);
			}
		}
		printeinpattern(tmp);
		//einlesenderdaten.einlesen.print3DTEST(tmp);
		
		
	}
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
		//Gibt ein Vektor zurück welche Ausgänge essentielle Bits haben
		boolean[] essentiell = new boolean[ary[0].length];
		for (int i=0; i<ary[0].length; i++){
			if (rowEssentielleBits(pattern,i, ary)<98979){
				System.out.println("Output= "+rowEssentielleBits(pattern,i, ary)+" i= "+i);
				
				essentiell[rowEssentielleBits(pattern,i, ary)]=true;
			}
		}
		return essentiell;
	}
	public static int rowEssentielleBits(int pattern ,int spalte, boolean[][][] ary)throws IOException{
		/*
		 * //Testmuster für rowEssentielleBits in main kopieren
		System.out.println(rowEssentielleBits(0,9,tmp));
		 * Gibt die Reihe Zurück von einem essentiellen Ausgang
		 */
		int i=0;
		int j=0;
		int row=0;
		while(i<ary[0].length){
			if(ary[pattern][i][spalte]== true){
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
	
	
}
