/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

/*
 * Ankit Critter Properities:
 * This critter will only fight 1/2 the time
 * Ankit can move in all 8 directions but will only run or reproduce during timestep
 * 
 */
public class Ankit extends Critter {
	
	public String toString() { return "A"; }
	
	private Main.lastMove lastMoveTracker;


	@Override
	public void doTimeStep() {

		if (getEnergy() > Params.min_reproduce_energy) {
			lastMoveTracker = Main.lastMove.REPRODUCE;
			Ankit child = new Ankit();
			reproduce(child, Critter.getRandomInt(8));
			
		} else {
			
			lastMoveTracker = Main.lastMove.RUN;
			run(Critter.getRandomInt(8));
		}
		
	}

	@Override
	public boolean fight(String opponent) {
		
		int tryFight = Critter.getRandomInt(8);
		
		if(tryFight >= 4) 
			return true;
		
		switch(lastMoveTracker)
		{
		// If critter moved previously, then he cannot move again, thus he cannot run from the fight
		case WALK:
		case RUN:
			break;
		
		// If critter did not move previously, then he can run
		case REPRODUCE:
		default:
			// Don't fight if otherwise. Critter will Try to run instead
			this.run(Critter.getRandomInt(8));
		}
		return false;
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}



}
