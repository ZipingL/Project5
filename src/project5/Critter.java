
/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

import java.util.*;
import java.lang.*;
import project4.Main;
/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	protected void look(int direction) {}
	protected void look2(int direction) {}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	protected final void walk(int direction) {

		subtractEnergy(Params.walk_energy_cost);
		
		switch(direction){
			
		case 0: x_coord += 1;
					break;
			case 1: x_coord -= 1;
					break;
			case 2: y_coord += 1;
					break;
			case 3: y_coord -= 1;
					break;
			case 4: x_coord += 1;
					y_coord += 1;
					break;
			case 5: x_coord -= 1;
					y_coord += 1;
					break;
			case 6: x_coord -= 1;
					y_coord += 1;
					break;
			case 7: x_coord -= 1;
					y_coord -= 1;
					break;				
		}
		
		x_coord = checkCoordValidity(x_coord, Params.world_width);
		y_coord = checkCoordValidity(y_coord, Params.world_height);
		
	}
	
	// Summary: Checks if coord is in a valid range [0,max)
	// and adjust coordinate if necessary to allow for world rap around
	protected static final int checkCoordValidity(int coordinate,int max)
	{
		if(coordinate >= max)
		{
			while(coordinate >= max)
			{
				coordinate = coordinate - max;
			}
		}
		
		else if(coordinate < 0)
		{
			while(coordinate < 0)
			{
				coordinate = max + coordinate;
			}
		}
		
		return coordinate;
	}
	
	protected final void run(int direction) {

		subtractEnergy(Params.run_energy_cost);
		int x = this.x_coord;
		int y = this.y_coord;
		
		switch(direction) {
		
			case 0: x_coord += 2;
					break;
			case 1: x_coord -= 2;
					break;
			case 2: y_coord += 2;
					break;
			case 3: y_coord -= 2;
					break;
			case 4: x_coord += 2;
					y_coord += 2;
					break;
			case 5: x_coord -= 2;
					y_coord += 2;
					break;
			case 6: x_coord -= 2;
					y_coord += 2;
					break;
			case 7: x_coord -= 2;
					y_coord -= 2;
					break;	
		}		
		x_coord = checkCoordValidity(x_coord, Params.world_width);
		y_coord = checkCoordValidity(y_coord, Params.world_height);

	}
	private final void subtractEnergy(int energy)
	{
		this.energy -= energy;
	}
	
	protected final void reproduce(Critter offspring, int direction) {

		CritterWorld.offspringList.add(offspring);

		// if parent has not enough energy to reproduce, then don't
		if(energy < Params.min_reproduce_energy) return;
		
		//give energy to offspring and update parent energy
		offspring.energy = energy / 2;
		energy -= offspring.energy;
		
		offspring.x_coord = x_coord;
		offspring.y_coord = y_coord;
		
		offspring.walk(direction);
		
		CritterWorld.offspringList.add(offspring);

	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	
	public static void worldTimeStep() {}
	
	public static void displayWorld() {}
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
			
		try {
			Class<?> generic = Class.forName("project4." + critter_class_name);
			Critter c;
			c = (Critter) generic.newInstance();
			
			c.x_coord = Critter.getRandomInt(Params.world_width);
			c.y_coord = Critter.getRandomInt(Params.world_height);
			c.energy = Params.start_energy;
			
			CritterWorld.add(c);

			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
			throw new InvalidCritterException(critter_class_name);
		}			
	
	}	
	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException 
	{
		List<Critter> result = new java.util.ArrayList<Critter>();
		Class<?> generic = null;
		
		
		try {
			generic = Class.forName("project4." + critter_class_name);
			Critter c;
			c = (Critter) generic.newInstance();
			
			for(Critter crit : CritterWorld.critterList)
			{
				if(generic.isInstance(crit))
				{
					result.add(crit);
				}
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ClassCastException e) {
			throw new InvalidCritterException(critter_class_name);
		}	
		
	
		
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure thath the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctup update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	
	public static void worldTimeStep() {
		// Call doTimeStep for each critter (moves or reproduces them)	
		if(CritterWorld.critterList == null)
			return;
		
		CritterWorld.offspringList = new ArrayList<Critter>();
		for(int i = 0; i < CritterWorld.critterList.size(); i++)
		 {
		 	CritterWorld.critterList.get(i).doTimeStep();
		 }
		
		

		
		// Add algae to the critter world
		for(int i = 0; i < Params.refresh_algae_count; i++)
		{
			try{ Critter.makeCritter("Algae"); }
			catch(InvalidCritterException e)
			{
				// This exception should NEVER happen, since "Algae" is a valid critter
				System.out.println(e);
			}
		}
		
		// Get a snapshot of the world
		char[][] array_slices = new char[Params.world_height + Params.world_border_width + 1][];
		for(int i = 0; i < array_slices.length; i++)
		{
			array_slices[i] = new char[Params.world_width + Params.world_border_width + 1];
		}
		Critter.setUpWorldBorder(array_slices);
		Critter.placeCritters(array_slices);
		CritterWorld.currentWorldSnapShot = array_slices;

		
		// Take care of encounters
		ArrayList<Integer> critterEncounters = findClashingNodes();
		workoutEncounters(critterEncounters);
		
		// remove dead critters	
		for(int i = 0; i < CritterWorld.critterList.size();  i++)
		{
			if(CritterWorld.critterList.get(i).energy <= 0)
			{
				CritterWorld.critterList.remove(i);
				i--;
			}
		}
		
		// add offsprings to CritterWorld
		if(CritterWorld.offspringList != null)
		for(Critter c : CritterWorld.offspringList) {
			CritterWorld.add(c);
		}
		
		CritterWorld.offspringList = null;
		
	}
	
	// Summary: solves the encounters by taking a list of critters that
	// are in the same coords and encounters them in an arbitrary sequence
	// Parameter: ArrayList<Integer> critterEncounters, significance explained in findClashingNodes()
	private static void workoutEncounters(ArrayList<Integer> critterEncounters)
	{
		boolean baseSetChange = false;
		int baseCritter = -1;
		for(int i = 0; i < critterEncounters.size(); i++)
		{
			if(critterEncounters.get(i).intValue() == -1)
			{
				i++;
				baseCritter = critterEncounters.get(i).intValue();
				baseSetChange = true;
			}
			
			if(baseSetChange == false)
			{
				if(baseCritter != -1)
				{
					encounterOutcome outcome = verseTwoCritters(baseCritter, critterEncounters.get(i).intValue());
					
					switch(outcome)
					{
					case A_WON:
						break;
					case B_WON:
						baseCritter = critterEncounters.get(i).intValue();
						break;
					case BOTH_FLED:
						baseCritter = -1;
						break;
					}
				}
				else
				{
					baseCritter = critterEncounters.get(i).intValue();
				}
					
			}
			else
			{
				baseSetChange = false;
			}
		}
	}
	
	// Summary: Solve the encounter of critter's baseCritter and otherCritter
	// Parameters: The parameters refer to the indices of the critters
	private static encounterOutcome verseTwoCritters(int baseCritter, int otherCritter)
	{
		Critter.encounterOutcome outcome = Critter.encounterOutcome.INVALID;
		int BaseX = CritterWorld.critterList.get(baseCritter).x_coord;
		int BaseY = CritterWorld.critterList.get(baseCritter).y_coord;
		int OtherX = CritterWorld.critterList.get(otherCritter).x_coord;
		int OtherY = CritterWorld.critterList.get(otherCritter).y_coord;
		int BaseEnergy = CritterWorld.critterList.get(baseCritter).getEnergy();
		int OtherEnergy = CritterWorld.critterList.get(otherCritter).getEnergy();
		
		
		boolean baseCritterFightOtherCritter = CritterWorld.critterList.get(baseCritter).fight(CritterWorld.critterList.get(otherCritter).toString());
		boolean otherCritterFightBaseCritter = CritterWorld.critterList.get(otherCritter).fight(CritterWorld.critterList.get(otherCritter).toString());
		
		int BaseEnergy2 = CritterWorld.critterList.get(baseCritter).getEnergy();
		int OtherEnergy2 = CritterWorld.critterList.get(otherCritter).getEnergy();
		
		// If the critters are trying to run,walk ( fight returns false ) check if they ran to a valid place, if not, place them back where they are
		if(baseCritterFightOtherCritter == false)
		{
			if(BaseEnergy2 == BaseEnergy)
			{
				CritterWorld.critterList.get(baseCritter).energy -= Params.run_energy_cost;
			}
			else if(CritterWorld.currentWorldSnapShot[CritterWorld.critterList.get(baseCritter).y_coord + Params.world_border_width][CritterWorld.critterList.get(baseCritter).x_coord + Params.world_border_width] >= 64)
			{
				CritterWorld.critterList.get(baseCritter).x_coord = BaseX;
				CritterWorld.critterList.get(baseCritter).y_coord = BaseY;
			}
		}
		
		if(otherCritterFightBaseCritter == false)
		{
			if(OtherEnergy2 == OtherEnergy)
			{
				CritterWorld.critterList.get(otherCritter).energy -= Params.run_energy_cost;
			}
			else if(CritterWorld.currentWorldSnapShot[CritterWorld.critterList.get(otherCritter).y_coord + Params.world_border_width][CritterWorld.critterList.get(otherCritter).x_coord + Params.world_border_width] >= 64)
			{
				CritterWorld.critterList.get(otherCritter).x_coord = OtherX;
				CritterWorld.critterList.get(otherCritter).y_coord = OtherY;
			}
		}
		
		if(crittersMatchCoordinates(CritterWorld.critterList.get(baseCritter), CritterWorld.critterList.get(otherCritter)))
		{
			int baseEnergy = 0; int otherEnergy = 0;
			CritterWorld.critterList.get(baseCritter).energy = CritterWorld.critterList.get(baseCritter).energy < 0 ? 0 : CritterWorld.critterList.get(baseCritter).energy;
			CritterWorld.critterList.get(otherCritter).energy = CritterWorld.critterList.get(otherCritter).energy < 0 ? 0 : CritterWorld.critterList.get(otherCritter).energy;
			
			if(baseCritterFightOtherCritter == true)
				baseEnergy = Critter.getRandomInt((CritterWorld.critterList.get(baseCritter).getEnergy() + 1));
			if(otherCritterFightBaseCritter == true)
				baseEnergy = Critter.getRandomInt(CritterWorld.critterList.get(baseCritter).getEnergy() + 1);
			
			
			// Determine who kills who based on energy levels
			if(baseEnergy > otherEnergy)
			{
				aKillB(baseCritter, otherCritter);
				outcome = encounterOutcome.A_WON;
			}
			
			else if(otherEnergy > baseEnergy)
			{
				aKillB(otherCritter, baseCritter);
				outcome = encounterOutcome.B_WON;
			}
			
			// If energy levels are the same, pick a random Critter to win
			else
			{
				int random = Critter.getRandomInt(2); // flip a coin
				{
					if(random == 0) //heads
					{
						aKillB(baseCritter, otherCritter);
						outcome = encounterOutcome.A_WON;
					}
					else // tails
					{
						aKillB(otherCritter, baseCritter);
						outcome = encounterOutcome.B_WON;
					}
				}
			}
			
			if(outcome == encounterOutcome.INVALID)
			{
				outcome = encounterOutcome.BOTH_FLED;
			}
		}
		
		return outcome;
	}
	
	private static void aKillB(int a, int b)
	{
		CritterWorld.critterList.get(a).energy += CritterWorld.critterList.get(b).energy / 2;
		CritterWorld.critterList.get(b).energy = 0;
	}
	
	//Summary: Finds critters that have the same coordinates
	
	//Returns: A list of integers that contains the indices of critters
	//that have the same coords. Each set of critters that have 
	//the same coord is seperated by a -1 value, chosen since -1 is not a valid index.
	//indices refers to the index of the critter in CritterWorld.critterList
	
	//e.g. [-1][21][23][2][-1][1][8]
	//This example list contains two sets of critters that have the same coord.
	//Every set is a different coord. So critters at indices 21, 23, 2 have the same coord say (a,b).
	//Critters at indices 1,8 have the same coord say (d,c).
	//However, these two sets do not have the same coord: (a,b) != (d,c).
	private static ArrayList<Integer> findClashingNodes()
	{
		ArrayList<Integer> encounterCritters = new ArrayList<Integer>();
		
		for(int i = 0; i < CritterWorld.critterList.size(); i++)
		{
			boolean critterMatchFound = false;
			if(critterAlreadyChecked(encounterCritters, i))
			{
				continue;
			}
			
			
			
			for(int j = 0; j < CritterWorld.critterList.size(); j++)
			{
				if(i != j && !critterAlreadyChecked(encounterCritters, j))
				{
					if(crittersMatchCoordinates(CritterWorld.critterList.get(i), CritterWorld.critterList.get(j)))
					{
						if(critterMatchFound == false)
						{
							critterMatchFound = true;
							encounterCritters.add(new Integer(-1));
						}
						encounterCritters.add(new Integer(j));
					}
				}
			}
			
			if(critterMatchFound)
			{
				encounterCritters.add(new Integer(i));
			}

		}
		s
		return encounterCritters;
	}
	
	private static boolean critterAlreadyChecked(ArrayList<Integer> encounterCritters, int critter)
	{
		for(Integer alreadyCheckedCritter : encounterCritters)
		{
			if(alreadyCheckedCritter.intValue() == critter)
				return true;
		}
		
		return false;
	}
	
	private static boolean crittersMatchCoordinates(Critter critter, Critter otherCritter)
	{
		if(critter.x_coord == otherCritter.x_coord)
		{
			if(critter.y_coord == otherCritter.y_coord)
			{
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean equals(Object that)
	{
		Critter thats = (Critter) that;
		if(this.x_coord == thats.x_coord)
		{
			if(this.y_coord == thats.y_coord)
			{
				return true;
			}
		}
		return false;
	}
	
	private static enum encounterOutcome 
	{
		A_WON, // Stage 1
		B_WON,
		BOTH_FLED,
		INVALID
	}
	
	//Summary: Prints out the current world of critters with a border around it
	public static void displayWorld() {
		

		// An array of horizontal slices of the world
		char[][] array_slices = new char[Params.world_height + Params.world_border_width + 1][];
		
		// Each slice is a horizontal slice of the world
		for(int i = 0; i < array_slices.length; i++)
		{
			array_slices[i] = new char[Params.world_width + Params.world_border_width + 1];
		}
		
		
		setUpWorldBorder(array_slices);
		placeCritters(array_slices);
		printSlices(array_slices);
		
	}
	
	private static void setUpWorldBorder(char[][] array_slices)
	{
		int max_height = Params.world_height + Params.world_border_width;
		int max_width = Params.world_width + Params.world_border_width;
		
		for(int row = 0; row < (Params.world_height + Params.world_border_width + 1); row++)
		{
			for(int col = 0; col < (Params.world_width + Params.world_border_width + 1); col++)
			{

				
				if(row == 0 || row == max_height)
				{
					if (col == 0 || col == max_width) {
						array_slices[row][col] = '+';
					} else {
						array_slices[row][col] = '-';
					}
				}
				else
				{
					if (col == 0 || col == max_width) {
						array_slices[row][col] = '|';
					}
				}		
			}
		}
		
	}
	
	private static void placeCritters(char[][] array_slices)
	{
		if(CritterWorld.critterList != null)
		{
			for (int i = 0; i < CritterWorld.critterList.size(); i++)
			{
				int col = CritterWorld.critterList.get(i).x_coord + Params.world_border_width;
				int row = CritterWorld.critterList.get(i).y_coord + Params.world_border_width;
				
				if (CritterWorld.critterList.get(i) instanceof Critter)
				{
					array_slices[row][col] = CritterWorld.critterList.get(i).toString().charAt(0);
				}
				
				else
				{
					array_slices[row][col] = '?';
				}
			}
		}
			
	}
	
	private static void printSlices(char[][] array_slices)
	{
		for(int i = array_slices.length - 1; i >= 0; i--)
		{
			for(int j = 0; j < Params.world_width + Params.world_border_width + 1; j ++)
			{
				if(Character.isDefined(array_slices[i][j]))
				{
					System.out.print(array_slices[i][j]);
				}
				else
				{
					System.out.print('!');
				}
			}
			System.out.println("");
		}
	}
	

}
