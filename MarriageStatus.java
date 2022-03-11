/*Karena Qian
 * 7.25.2021
 * CS 211 Section C
 * Instructor: Neelakantan Kartha
 * Stable Marriage Problem (aka Dating Simulator) */
//This class creates objects keeping track of a person's engagement status, 
//list of preferences, and assigned partner

import java.util.*;	

public class MarriageStatus{
	private boolean isEngaged;	
	private ArrayList<Integer> preferences;	
	private int partner;	
	private int[] original;	 //stores person's original preference list 
							//(before it is changed by the DateSim methods)
	
	public MarriageStatus(ArrayList<Integer> p) {
		isEngaged = false;	
		preferences = p;	
		partner = -1;	
		original = new int[p.size()];	
		for(int i = p.size(); i > 0; i--) {
			original[i - 1] = p.get(i - 1);	
		}
	}
	
	//returns engagement status
	public boolean isEngaged() {
		return isEngaged;	
	}
	
	//returns preference list (changeable)
	public ArrayList<Integer> getPreferences() {
		return preferences;	
	}
	
	//returns assigned partner
	public int getPartner() {
		return partner;	
	}
	
	//changes engagement status and assigned partner
	public void changeStatus(boolean b, int person) {
		isEngaged = b;	
		if(!b) {
			partner = -1;	
		}
		else {
			partner = person;	
		}
	}
	
	//returns index of assigned partner in original preference list
	public int originalIndex() {
		int index = 0;	
		for(int i = 0; i < original.length; i++) {
			if (original[i] == partner) {
				index = i;	
			}
		}
		return index;	
	}
	
	//returns original list as unclosed string list (for testing)
	public String getOriginal() {
		String s = "";	
		for(int i = 0; i < original.length; i++) {
			s += original[i] + ", ";	
		}
		return s;	
	}
	
	//MarriageStatus' toString method (for testing)
	public String toString() {
		return "Engaged Status: " + isEngaged + "\n" + preferences + "\n";	
	}
}
