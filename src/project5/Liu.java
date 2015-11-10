/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;
/*Liu Critter Properties
 * Liu only fights Ziping and will only fight if there are other Ziping's around ziping, or else it will attempt to run away from the encounter
 * while attempting to run away from encounters, if all spots around it are filled, it will fight instead
 * Liu only walks or reproduces during timestep
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
		if(oponent.equals("@"))
			return true;
		// Fight if energy levels are at minimum required
		if(oponent.equals("Z"))
		{
			for(int i = 0; i < 8; i++)
			{
				if(look2(i).equals("Z"))
					return true;
			}
	
		}
		switch(lastMoveTracker)
		{
		// If critter moved previously, then he cannot move again, thus he cannot run from the fight
		case WALK:
		case RUN:
			return false; //break
		
		// If critter did not move previously, then he can run
		case REPRODUCE:
		default:
			// Don't fight if otherwise. Critter will Try to run instead
			int direction = Critter.getRandomInt(4)*2;
			for(int i = 0; i < 8; i++)
			{
				if(look2(i) == null)
				{
					direction = i;
					this.run(direction);
					return false;
				}
			}
			
		}
		return true;
	}
	
	public String toString()
	{
		return "L";
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return Critter.CritterShape.SQUARE;
	}

	@Override
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.RED; 
	}
	

}
