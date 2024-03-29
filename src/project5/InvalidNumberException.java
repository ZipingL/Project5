/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

public class InvalidNumberException extends Exception {
	int offendingNumber;
	
	public InvalidNumberException(int value) {
		offendingNumber = value;
	}
	
	public String toString() {
		return "Invalid Number " + offendingNumber;
	}
}
