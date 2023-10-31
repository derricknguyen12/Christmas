
/**
 * Driver.java
 * @author Derrick Nguyen
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		ArrayList<String> songs = new ArrayList<String>();
		songs.add("believe.txt");
		songs.add("lastChristmas.txt");
		songs.add("allIWantForChristmasIsYou.txt");
		songs.add("deckTheHalls.txt");
		songs.add("itsTheMostWonderfulTimeOfTheYear.txt");
		songs.add("jingleBells.txt");
		songs.add("joyToTheWorld.txt");
		songs.add("letItSnowLetItSnowLetItSnow.txt");
		songs.add("santaClausIsComingToTown.txt");
		songs.add("santaTellMe.txt");
		songs.add("sleighRide.txt");
		songs.add("winterThings.txt");
		songs.add("winterWonderland.txt");
		songs.add("underneathTheTree.txt");
		songs.add("snowMan.txt");
		
		UserControls u = new UserControls();
		for (int i = 0; i < songs.size(); i++) {
			u.readFile(songs.get(i));
		}
		
		Scanner userInput = new Scanner(System.in);
		String choice = "";
		Boolean end = false;
		u.printWelcome();
		while (!end) {
			u.printMenu();
			System.out.print("\nMake a selection by typing in the number besides the option you would like to make: ");
			choice = userInput.nextLine();
			if (choice.equals("1")) {
				u.uploadSong();
			} else if (choice.equals("2")) {
				u.removeSong();
			} else if (choice.equals("3")) {
				u.searchSong();
			} else if (choice.equals("4")) {
				u.modifySong();
			} else if (choice.equals("5")) {
				u.calculateStats();
			} else if (choice.equals("6")) {
				u.writeToFile();
				userInput.close();
				end = true;
				System.out.println("Looks like christmas is here! See ya next year!");
			} else {
				System.out.println(
						"\nOopsie! Looks like you may have put the wrong present in the wrong stocking! \nPlease enter one of the following options...\n");
			}

		}
	}
}