package pruefen;

import java.io.IOException;
import java.util.ArrayList;

public class solution {
	public static int everyFailurecovered(ArrayList<ArrayList<Long>> tmp) throws IOException{
		boolean covered=false;
		int counter=0;
		int c=0;
		for(int d=0; d<tmp.get(0).size() ;){
			for (int k=0; k<tmp.size() && !covered; k++){
				if(LongRemovingBits.removingBits.solution.get(k)){
					if( (stuff.DirtyLittleHelpers.getBitAtPosition(tmp.get(k).get(d), c)==1) ){
						covered=true;
						counter++;
					}
				}
			}
			c++;
			if(c==64){
				d++;
				c=0;
			}
			covered=false;
		}
		return counter;
	}
}
