/*
 * Ziping Liu zl3858
 * Ankit Sharma as63437
 */
package project5;

import java.util.*;

public class CritterWorld {
	static ArrayList<Critter> critterList = new ArrayList<>();
	static String statOutput = null;
	static ArrayList<Critter> offspringList;
	static char[][] currentWorldSnapShot;
		
	static void add(Critter c){
		if(!critterList.contains(c)){
			critterList.add(c);
		}
	}
	
	
	
}