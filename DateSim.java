/*Karena Qian
 * 7.25.2021
 * CS 211 Section C
 * Instructor: Neelakantan Kartha
 * Stable Marriage Problem (aka Dating Simulator) */
//This main class takes in a file listing men, women, and their list of partner preferences, 
//pairs them using the Gale–Shapley algorithm (I found that out while doing some research),
//and finds their overall partner choice value, using the MarriageStatus class

import java.io.*;	
import java.util.*;	

public class DateSim {
	
	public static void main(String[] args) throws FileNotFoundException{
		//get file and set scanner (shown path is to "short.dat" on my computer)
		File data = new File("C:\\Users\\karen\\Downloads\\stable.dat");	 
		Scanner s = new Scanner(data);	
		//lists for names and MarriageStatus objects
		ArrayList<String> menName = new ArrayList<>();	
		ArrayList<String> womenName = new ArrayList<>();	
		ArrayList<MarriageStatus> men = new ArrayList<>();	
		ArrayList<MarriageStatus> women = new ArrayList<>();	
		
		setList(men, menName, s);	
		setList(women, womenName, s);	
		
		setEngage(men, women, menName, womenName);	
	}
	
	//creates lists of String names and MarriageStatus from the input file
	public static void setList(ArrayList<MarriageStatus> candidates, 
								ArrayList<String> names, Scanner s) {
		while(s.hasNextLine()) {
			String save = "";	 //for String token (aka person name)
			String newLine = s.nextLine();	 //sets line of input
			if(!newLine.equals("END")) {
				ArrayList<Integer> spouses = new ArrayList<>();	 //for preferences list
				@SuppressWarnings("resource") //due to unused Scanner
				Scanner line = new Scanner(newLine);	 //reading line of input
				while(line.hasNext()) {
					if(!line.hasNextInt()) {
						save += line.next();	
					}
					else {
						spouses.add(line.nextInt());	
					}
				}
				names.add(save);	 //String names list
				MarriageStatus m = new MarriageStatus(spouses);	
				candidates.add(m);	 //MarriageStatus objects list
			}
			else {
				break;	 //breaks out of loop if Scanner reaches "END"
			}
		}
	}
	
	//pairs men and women using the Gale–Shapley algorithm
	//and finds their overall partner choice value
	public static void setEngage(ArrayList<MarriageStatus> m, ArrayList<MarriageStatus> w, 
									ArrayList<String> men, ArrayList<String> women) {
		//sets up target person
		int currentMan = 0;	 //person being assigned
		boolean nextManFound;	 //is person free and has non-empty preference list
		do { 
			nextManFound = false;	
			int i = currentMan;	
			do {
				// find the next man who's free and have non-empty preference list
				if (!m.get(i).isEngaged() && m.get(i).getPreferences().size() != 0) {
					nextManFound = true;	
					currentMan = i;	
				}
				else {
					i++;	
					if (i >= m.size()) {
						 i = 0;	
					}
					//both conditions need to be checked in the loop
					if (i == currentMan) {
						// loop around already and if no man found, break out the loop
						break;	
					}
				}
				
			} while (!nextManFound);	
			
			//main operation
			if (nextManFound) {
				// get the 1st woman on current man's preference list
				int firstWoman = m.get(currentMan).getPreferences().get(0);	
				MarriageStatus woman = w.get(firstWoman);	
				// if the 1st woman is engaged, set her free
				if(woman.isEngaged()) {
					int j = woman.getPartner();	
					m.get(j).changeStatus(false, -1);	
					woman.changeStatus(false, -1);	
				}
				// mark current man engaged with his 1st woman choice
				m.get(currentMan).changeStatus(true, firstWoman);	
				woman.changeStatus(true, currentMan);	
				// delete all connections between selected woman and men 
				//who are after current man on woman's preference list
				ArrayList<Integer> womanList = woman.getPreferences();	
				int k = womanList.size() - 1;	
				int manLocation = womanList.indexOf(currentMan);	
				for (int l = k; l > manLocation; l--) {
					ArrayList<Integer> r = m.get(womanList.get(l)).getPreferences();	
					womanList.remove(l);	
					r.remove(r.indexOf(firstWoman));	
				}
			} 
		} while (nextManFound);	
		//variables for calculating overall choice value
		double sum = 0;	
		int divisor = 0;	
		//prints out list of people, their partners, and original partner choice value
		//(skips over unpaired people)
		for(int i = 0; i < m.size(); i++) {
			if(m.get(i).isEngaged()) {
				int partner = m.get(i).getPartner();	
				System.out.println(men.get(i) + " is engaged to " + women.get(partner)
				+ ", who was originally at choice value " + m.get(i).originalIndex() + ".");	
				System.out.println();	
				sum += m.get(i).originalIndex();	
				divisor++;	
			}
		}
		//prints out overall choice value
		System.out.println("The overall choice value is: " + (sum / divisor));	
	}
	
}
