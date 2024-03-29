/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */

package project5;
import java.util.*;

import com.oracle.jrockit.jfr.InvalidValueException;

//import com.sun.java.util.jar.pack.Package.Class.Method;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Controller {

	// old main
	public static void hang() {
		
		Scanner myScanner = new Scanner(System.in);
		boolean quitStatus = false;
		CritterWorld.critterList = new ArrayList<Critter>();
		
		while(quitStatus == false)
		{
			System.out.print("critters> ");
			// TODO: Add cursor help
			String lineInput = myScanner.nextLine();
			ArrayList<String> parsedLine = parseLineInput(lineInput);
			commandType commandType = parseCommand(parsedLine);
			//quitStatus = runCommand(lineInput, parsedLine, commandType);
		}
		myScanner.close();
		System.exit(0);
	}
	
	// Takes in the line input from user and puts each argument into an arraylist for convenience sake
	public static ArrayList<String> parseLineInput(String input_line) {
		Scanner sc = new Scanner(input_line);
		ArrayList<String> input = new ArrayList<>();
		while(sc.hasNext("[^ ]+"))
		{
			input.add(sc.next("[^ ]+"));
		}
		sc.close();
		return input;	
	}
	
	// Takes in a parsedLinedInput and determines and returns the command type
	private static commandType parseCommand(ArrayList<String> userInput)
	{
		commandType returnCommandType = commandType.DEFAULT;
		if(userInput.size() > 0 && userInput.size() < 4)
		{
			String command = new String(userInput.get(0).toLowerCase());
			// Check commands that take no other arguments
			if(userInput.size() == 1)
			{
				if(command.equals("quit"))
				{
					returnCommandType = commandType.QUIT;
				}
				else if(command.equals("show"))
				{
					returnCommandType = commandType.SHOW;
				}
	
	
				if(command.equals("make"))
				{
					returnCommandType = commandType.MAKE;
				}
				if(command.equals("step"))
				{
					returnCommandType = commandType.STEP;
				}
			}
			// Check commands that multiple arguments
			else
			{
				if(command.equals("seed"))
				{
					// Seed must have 2 arguments total
					if(userInput.size() == 2)
					{
						returnCommandType = commandType.SEED;
					}
					else
					{
						returnCommandType = commandType.DEFAULT;
					}

				}
				// note: make and step can have more arguments but it's not required
				if(command.equals("make"))
				{
					returnCommandType = commandType.MAKE;
				}
				else if(command.equals("step"))
				{
					returnCommandType = commandType.STEP;
				}
				
				else if(command.equals("stats"))
				{
					if(userInput.size() == 2)
						returnCommandType = commandType.STATS;
					else
						returnCommandType = commandType.DEFAULT;
				}	
			}

		}
		
		// Check other arguments if there are more
		if(userInput.size() > 1)
		{
			boolean multipleArgumentValidity = checkMultipleArguments(userInput, returnCommandType);
			if(multipleArgumentValidity == false)
			{
				returnCommandType = commandType.PROCESS_ERROR;
			}
		}
		
		return returnCommandType;
	}
	
	// Checks if other arguments are valid 
	public static boolean checkMultipleArguments(ArrayList<String> userInput, commandType returnCommandType)
	{
		boolean validity = false;
		int ifStatement = 0;
		try{
			if(returnCommandType == commandType.STEP || // should have integer as the second argument, optionally
			   returnCommandType == commandType.SEED || // should have integer as the second argument
			   returnCommandType == commandType.MAKE ||// should have integer as the third argument, optionally
			   returnCommandType == commandType.STATS) //Should have string as second argument
			{
				// Check for valid integers				
				if(userInput.size() == 2 && returnCommandType == commandType.STEP || returnCommandType == commandType.SEED)
				{
					ifStatement = 1;
					Integer test = Integer.parseInt(userInput.get(1));
					validity = true;
					
				}
				
				if(userInput.size() == 3 && returnCommandType == commandType.MAKE)
				{
					ifStatement = 2;
					Integer test = Integer.parseInt(userInput.get(2));
					validity = true;
					
				}			
				// Check for valid strings, if it can be parsed into an integer, it's not a proper string name for a critter
				if(userInput.size() >= 2 && returnCommandType == commandType.STATS || returnCommandType == commandType.MAKE)
				{
					ifStatement = 3;
					Integer test = Integer.parseInt(userInput.get(1));	
					validity = false;
					
				}
			
			}
		}
		
		catch(NumberFormatException e) 
		{
			switch(ifStatement)
			{
			case 1:
				validity = false;
				break;
			case 2:
				validity = false;
				break;
			case 3:
				validity = true;
				break;
			default:
				break;
			}
		}
		return validity;
	}
	
	public static enum commandType 
	{
		QUIT, // Stage 1
		SHOW,
		STEP,
		SEED, // Stage 2
		MAKE, // Stage 3
		STATS,
		PROCESS_ERROR,
		DEFAULT
	}
	
	public static enum lastMove
	{
		WALK,
		RUN,
		REPRODUCE,
		DEFAULT
	}
	
	// runs the command, based on the given commandType 
	public static boolean runCommand(String critter_name, long value, commandType command)
	{
		boolean quitStatus = false;
		switch(command)
		{
			case QUIT:
			{
				quitStatus = true;
				break;
			}
			
			case SHOW:
			{
				Critter.displayWorld2();
				
				break;
			}
			
			case STEP:
			{

				// Check if user inputed "step #", with # being the
				// desired amount of steps to do
				int count = (int) value;
				for(int i = 0; i < count; i++)
				{
					Critter.worldTimeStep();
				}
				break;
			}
			
			case SEED:
			{
				Critter.setSeed(value);
				break;
			}
			
			case MAKE:
			{		
				int count = (int) value; // number of desired critter to make into the world
					
				try{
					if(count < 1)
						throw new InvalidNumberException(count);
					for(int i = 0; i < count; i ++)
						Critter.makeCritter(critter_name);	
				} 
				catch(InvalidCritterException | InvalidNumberException e)
				{
					System.out.println("error processing: ");
				}
				break;
			}
			
			case STATS:
			{
				
				if(critter_name != null && !critter_name.equals("All"))
				{
					Class<?> generic;
					try{
					generic = Class.forName("project5." + critter_name);
					Critter c = (Critter) generic.newInstance();
					
					if(generic.isInstance(new Craig()))
					{
						System.out.println("CRAIGGG");
					}
	
					Class[] cArg = new Class[1];
					cArg[0] = List.class;
					ArrayList<Object> args = new ArrayList<>();
					args.add(List.class.getClasses());
					
					Method runStats = c.getClass().getMethod("runStats",cArg);
					runStats.invoke(c, Critter.getInstances(critter_name));
					} catch(ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | IllegalArgumentException | InvocationTargetException | InvalidCritterException e)
					{
						System.out.println("error processing: ");
					}
				}
				
				else
				{
					Critter.runStats(CritterWorld.critterList);
				}
				
				break;
			}
			
			
			case PROCESS_ERROR:
			{
				System.out.println("error processing: ");
				break;
			}
			
			case DEFAULT:
			default:
			{
				System.out.println("invalid command: ");
				break;
			} 
		}
		return quitStatus;
	}
	
	private class GenericClass<T> {

	     private final Class<T> type;

	     public GenericClass(Class<T> type) {
	          this.type = type;
	     }

	     public Class<T> getMyType() {
	         return this.type;
	     }
	}
}


