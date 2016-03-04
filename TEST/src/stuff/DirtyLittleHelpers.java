package stuff;

import java.io.File;

/** This class provides various functions that are helpful.
 * 
 * @author Matthias Kampmann (kampmams)
 */
public class DirtyLittleHelpers {
	//------------------------------------------------------------------------------------------------------------------
	// Public members.
	//------------------------------------------------------------------------------------------------------------------
	/** Constant for float comparison operations. */
	public static final float FLOAT_EPSILON = 0.000001F; // Work with fs resolution.
//	public static final float FLOAT_EPSILON = 0.001F; // Work with ps resolution.
	
	/** Get the bit of the given 64 bit word at the given position.
	 * 
	 * @param word The 64 bit word to analyze.
	 * @param position The position of the wanted bit.
	 * 
	 * @return The bit at the given position (0 or 1).
	 */
	public static short getBitAtPosition(long word, int position) {
		return (short)((word >> position) & 0x1);
	}
	
	/** Set the bit of the given word at the given position.
	 * 
	 * @param word The 64 bit word to manipulate.
	 * @param position The position at which the bit should be changed.
	 * @param value The new bit value.
	 * 
	 * @return The modified word.
	 */
	public static long setBitAtPosition(long word, int position, boolean value) {
		long result = 0L;
		
		if(value) {
			// Set the bit at the given position.
			// DEBUG:
			result = word | (1L << position);
		} else {
			// Unset the bit at the given position.
			result = word & ~(1L << position);
		}
		 
		return result;
	}
	
	/** Get the percentage of part in total as an integer (e.g. 70 corresponds to 70 percent).
	 * 
	 * @param part The part of total.
	 * @param total The total.
	 * 
	 * @return The percentage of part in total.
	 */
	public static int getPercentage(int part, int total) {
		return (int)(100.0F * ((float)part / (float)total));
	}
	
	/** Get the percentage of part in total as a float.
	 * 
	 * @param part The part of total.
	 * @param total The total.
	 * 
	 * @return The percentage of part in total.
	 */
	public static float getPercentageFloat(int part, int total) {
		return 100.0F * (float)part / (float)total;
	}
	
	/** Compare two values. Returns true if |a - b| < FLOAT_EPSILON. This is done to avoid rounding errors made by cpus
	 * using floating point variables.
	 * 
	 * @param a The fist value.
	 * @param b The second value.
	 * 
	 * @return True if |a - b| < FLOAT_EPSILON.
	 */
	public static boolean isEqual(float a, float b) {
		if(FLOAT_EPSILON > Math.abs(a - b)) {
			return true;
		}
		
		return false;
	}
	
	/** Compare two values. Returns true if |a - b| < epsilon. This is done to avoid rounding errors made by cpus
	 * using floating point variables.
	 * 
	 * @param a The fist value.
	 * @param b The second value.
	 * @param epsilon Accuracy factor.
	 * 
	 * @return True if |a - b| < FLOAT_EPSILON.
	 */
	public static boolean isEqual(float a, float b, float epsilon) {
		if(epsilon > Math.abs(a - b)) {
			return true;
		}
		
		return false;
	}
	
	/** Join the contents of the given array, separated by the given separator.
	 * <p>
	 * Example: <code>joinElements({"a", "b", "c"}, "+")</code> yields <code>"a+b+c"</code>
	 * 
	 * @param parts The elements to join.
	 * @param separator The separator between the elements.
	 * 
	 * @return A string containing the parts array with each element separated by the given separator.
	 */
	public static String joinElements(String[] parts, String separator) {
		String result = "";
		for(int idx = 0; idx < parts.length - 1; idx++) {
			result += parts[idx] + separator;
		}
		if(0 < parts.length) {
			result += parts[parts.length - 1];
		}
		
		return result;
	}
	
	/** Check whether a is greater than or equal to b in a float-safe fashion.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return True if a >= b.
	 */
	public static boolean greaterEqualThan(float a, float b) {
		if(a > b) {
			return true;
		}
		
		return isEqual(a, b);
	}
	
	/** Check whether a is greater than or equal to b in a float-safe fashion.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @param epsilon Accuracy factor.
	 * 
	 * @return True if a >= b.
	 */
	public static boolean greaterEqualThan(float a, float b, float epsilon) {
		if(a > b) {
			return true;
		}
		
		return isEqual(a, b, epsilon);
	}
	
	/** Check whether a is lesser than or equal to b in a float-safe fashion.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @return True if a <= b.
	 */
	public static boolean lesserEqualThan(float a, float b) {
		if(a < b) {
			return true;
		}
		
		return isEqual(a, b);
	}
	
	/** Check whether a is lesser than or equal to b in a float-safe fashion.
	 * 
	 * @param a The first value.
	 * @param b The second value.
	 * @param epsilon Accuracy factor.
	 * 
	 * @return True if a <= b.
	 */
	public static boolean lesserEqualThan(float a, float b, float epsilon) {
		if(a < b) {
			return true;
		}
		
		return isEqual(a, b, epsilon);
	}
	
	/**
	 * Check if the folder with given path name exists, or create otherwise. 
	 * @param folderName The folder.
	 */
	public static void checkOrCreateFolder(String folderName) {
	    File dir = new File(folderName);
	    if (! dir.exists()) {
	        dir.mkdir();
        }
	}
}
