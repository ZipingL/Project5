/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */

package project5;

import project5.Critter.TestCritter;

public class Algae extends TestCritter {

	public String toString() { return "@"; }

	private int getFightEnergy()
	{
		return 0;
	}
	
	public boolean fight(String opponent) {
		if (toString().equals(opponent)) { // same species as me!
			/* try to move away */
			walk(Critter.getRandomInt(8));
		}
		return false; 
	}
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	public CritterShape viewShape() { return CritterShape.CIRCLE; }
	public javafx.scene.paint.Color viewColor() { return javafx.scene.paint.Color.GREEN; }
}
