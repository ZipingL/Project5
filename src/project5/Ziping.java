/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

/*
 * Ziping Critter Properities:
 * This critter will only fight Liu critters
 * If it encounters any other critter, it will try to run away
 * Ziping critter will try to run away to a spot with a Liu critter in it
 * Therefore, Ziping critter actively tries to find and kill Liu Critters
 * Ziping can move in all 8 directions but will only run or reproduce during timestep
 * 
 */
public class Ziping extends Critter {
	
	private Controller.lastMove lastMoveTracker;
	@Override
	public void doTimeStep() {
		if(this.getEnergy() < Params.min_reproduce_energy)
		{
			lastMoveTracker = Controller.lastMove.WALK;
			this.walk(Critter.getRandomInt(8));
		}
		else
		{
			lastMoveTracker = Controller.lastMove.REPRODUCE;
			
			Ziping baby = new Ziping();
			reproduce(baby, Critter.getRandomInt(8));
		}

	}

	@Override
	public boolean fight(String oponent) {
		
				if(oponent.equals("@"))
					return true;
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
					int direction = Critter.getRandomInt(8);
					for(int i = 0; i < 8; i++)
					{
						if(this.look2(i) == "L")
						direction = i;
					}
				
					this.run(direction);
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
		return Critter.CritterShape.SQUARE;
	}
	
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.RED; 
	}
	


}
