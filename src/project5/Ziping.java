/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

/*
 * Ziping Critter Properities:
 * This critter will only fight Liu critters
 * If it encounters any other critter, it will try to run away
 * Ziping can move in all 8 directions but will only run or reproduce during timestep
 * 
 */
public class Ziping extends Critter {
	
	private Main.lastMove lastMoveTracker;
	@Override
	public void doTimeStep() {
		if(this.getEnergy() < Params.min_reproduce_energy)
		{
			lastMoveTracker = Main.lastMove.WALK;
			this.walk(Critter.getRandomInt(8));
		}
		else
		{
			lastMoveTracker = Main.lastMove.REPRODUCE;
			
			Ziping baby = new Ziping();
			reproduce(baby, Critter.getRandomInt(8));
		}

	}

	@Override
	public boolean fight(String oponent) {
		
				if(oponent.equals("L"))
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
	public String toString()
	{
		return "Z";
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}


}
