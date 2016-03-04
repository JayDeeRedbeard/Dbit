package readdata;
import java.util.ArrayList;

public class DBit {
	int value; 
	boolean valid; 
	ArrayList<Long> List;
	public DBit(int value,boolean valid, ArrayList<Long> List){
		this.List=List;
		this.valid = valid;
		this.value = value;
	}
	public int getValue() {
		return this.value;
	}
	
//	public int getnumberOfTrues() {
//		return this.numberOfTrues;
//	}
//	public void setnumberOfTrues(int a) {
//		this.numberOfTrues = a;
//	}
	public void setValid(boolean a) {
		this.valid = a;
	}
	public boolean getValid() {
		return this.valid;
	}
	public ArrayList<Long> getList() {
		return this.List;
	}
	public void setList(ArrayList<Long> a) {
		this.List=a;
	}
	public boolean istGleich(DBit DB) {
		return this.List == DB.List;
	}
}
