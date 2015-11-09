/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;
/*Liu Critter Properties
 * Liu only fights Ziping, or else it will attempt to run away from the encounter
 * Liu can only move in even numbered directions and will only walk or reproduce during timestep
 */
public class Liu extends Critter {
	private Controller.lastMove lastMoveTracker;
	@Override
	public void doTimeStep() {
		
		if(this.getEnergy() < Params.min_reproduce_energy)
		{
			lastMoveTracker = Controller.lastMove.RUN;
			this.run(Critter.getRandomInt(4) * 2);
		}
		else
		{
			lastMoveTracker = Controller.lastMove.REPRODUCE;
			
			Ziping baby = new Ziping();
			reproduce(baby, Critter.getRandomInt(4)*2);
		}

	}

	@Override
	public boolean fight(String oponent) {
		// Fight if energy levels are at minimum required
		if(oponent.equals("Z"))
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
			this.run(Critter.getRandomInt(4)*2);
		}
		return false;
	}
	
	public String toString()
	{
		return "L";
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}


}
