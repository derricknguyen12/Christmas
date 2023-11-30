
/**
 * UserControls.java
 * @author Derrick Nguyen
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class UserControls {

	public Scanner userInput = new Scanner(System.in);
	public SearchEngine s = new SearchEngine();

	/**
	 * Prints a simple welcome message to the user
	 */
	public void printWelcome() {
		System.out.println("Welcome to Christmas Search! \nThe place where you can search for your "
				+ "favorite Christmas songs. \nHave a holly jolly time!\n");
	}

	/**
	 * Prints a list of options the user can choose from to interact with search
	 * engine
	 */
	public void printMenu() {
		System.out.println("Here's a list of options of what you can do while we wait for Santa to arrive ..."
				+ "\n1. Upload a new christmas song." + "\n2. Remove a song off of your \"nice\" list."
				+ "\n3. Search for a christmas song." + "\n4. Modify and update a song." + "\n5. Show stats."
				+ "\n6. Quit and export songs to a file.");
	}

	/**
	 * Allows the user to input the name of the song file and calls readfile() to
	 * read file with song
	 * 
	 */
	public void uploadSong() {
		String songFileName;
		System.out.println("\nLets upload a new song!");
		System.out.print("Please enter the text file name containing the song name, artist, year, and lyrics: ");
		songFileName = userInput.nextLine();
		readFile(songFileName);
	}

	/**
	 * Gets rid of a song in the list of songs
	 * 
	 */
	public void removeSong() {
		String songName;
		String songArtist;
		Song song;
		System.out.println("\nLets get rid of a song.");
		System.out.print("Type in the name of the song: ");
		songName = userInput.nextLine();
		System.out.print("Type in the artist of the song: ");
		songArtist = userInput.nextLine();
		song = s.searchPrimaryKey(songName, songArtist);

		if (song != null) {
			s.removeSong(song);
			System.out.println("\nSuccessfully removed the song \"" + song.getName() + "\"\n");
		} else {
			System.out.println("\nLooks like \"" + songName + "\" isn't in your list of songs.\n");
		}
	}

	/**
	 * Lets the user search for a song either based on the primary key or keywords
	 * of the song and displays it
	 * 
	 */
	public void searchSong() {
		String choice;
		System.out.println("\nHere are your jolly options to search for your song: " + "\na. Using the primary key" // the
																													// name
																													// of
																													// the
																													// song
																													// im
																													// assuming
				+ "\nb. Using keywords in the song\n"); // searching the song through words
		System.out.print("Make your selection by typing \"a\" or \"b\": ");

		choice = userInput.nextLine();

		if (choice.equals("a")) {

			System.out.print("\nPlease enter the name of the song: ");
			String name = userInput.nextLine().toLowerCase();
			System.out.print("Please enter the artist of the song: ");
			String artist = userInput.nextLine().toLowerCase();

			Song result = s.searchPrimaryKey(name, artist);

			if (result == null) {
				System.out.println("\nSorry, that song isn't in our database. Please try again!");
				System.out.println();
			} else {
				System.out.println("\nHO HO HO! We found the song that you're searching for!");
				System.out.println(result.toString());
			}

		} else if (choice.equals("b")) {
			System.out.print("\nPlease enter a word in the song: ");
			choice = userInput.nextLine().toLowerCase();

			if (!(s.searchKeyword(choice) == null)) {
				System.out.println("The following songs contain the word " + choice + ": \n");

				ArrayList<Song> results = s.searchKeyword(choice);

				for (int i = 0; i < results.size(); i++) {
					System.out.println(i + 1 + ": " + results.get(i).getName());
				}
				System.out.println();
				boolean validInput = false;
				while (!validInput) {
					try {
						System.out.print(
								"To view more information about any of these songs, enter the number next to the song title: ");
						int index = Integer.parseInt(userInput.nextLine());
						index--;
						if (index < 0 || index >= results.size()) {
							System.out.println("\nThe index is out of bounds. Please try again \n");
						} else {
							System.out.println(results.get(index).toString());
						}
						validInput = true;

					} catch (Exception e) {
						System.out.println("\nThe year is invalid. Please enter again.");
					}
				}
			} else {
				System.out.println("\nLooks like that word doesnt show up on any of your songs!\n");
			}

		} else {
			System.out.println(
					"\nIt seems like an elf may have bumped into you and made you type in the wrong option... \nPlease try again.\n");
		}
	}

	/**
	 * Allows the user to change or update a song
	 * 
	 */
	public void modifySong() {
		String choice;
		String name;
		String artist;
		int year;
		Song song;
		System.out.print("Please enter the name of the song you would like to modify/update: ");
		name = userInput.nextLine();
		System.out.print("Please enter the artist of the song you would like to modify/update: ");
		artist = userInput.nextLine();

		song = s.searchPrimaryKey(name, artist);

		if (!(song == null)) {
			System.out.println("\nWhat would you like to change?" + "\n1. Update song name" + "\n2. Update artist"
					+ "\n3. Update song year\n");

			System.out.print("Type in the number besides the option of what would you like to modify: ");
			choice = userInput.nextLine();

			if (choice.equals("1")) {
				System.out.print("Please enter the new name of the song you would like to modify/update: ");
				name = userInput.nextLine();
				s.modifySongName(song, name);
				System.out.println("\nWe will make that update faster than Santa can eat his cookies!\n");
			} else if (choice.equals("2")) {
				System.out.print("Please enter the new artist of the song you would like to modify/update: ");
				artist = userInput.nextLine();
				s.modifySongArtist(song, artist);
				System.out.println("\nWe will make that update faster than Santa can eat his cookies!\n");
			} else if (choice.equals("3")) {
				boolean validInput = false;
				while (!validInput) {
					try {
						System.out.print("Please enter the new year of the song you would like to modify/update: ");
						year = Integer.parseInt(userInput.nextLine());
						s.modifySongYear(song, year);
						validInput = true;
						System.out.println("\nWe will make that update faster than Santa can eat his cookies!\n");
					} catch (Exception e) {
						System.out.println("\nThe year is invalid. Please enter again.");
					}
				}
			} else {
				System.out.println(
						"\nIt seems like an elf may have bumped into you and made you type in the wrong option.. \nPlease try again.\n");
			}

		} else {
			System.out.println("\nIt seems like that song isn't in our data base...\n");
		}

	}

	/**
	 * Gives the user options to learn more about a particular statistic based on
	 * the songs uploaded
	 * 
	 */
	public void calculateStats() {
		String choice;
		String name;
		String artist;
		Song song;
		String word;
		System.out.println("\nWhat would you like to know about these songs?"
				+ "\n1. How many times a specific word pops up in a particular song"
				+ "\n2. The average year these songs were released" + "\n3. How many words a song contains\n");

		System.out.print("Type in the number besides the option you would like to know more about: ");
		choice = userInput.nextLine();

		if (choice.equals("1")) {

			System.out.print("\nEnter the name of the song: ");
			name = userInput.nextLine();
			System.out.print("Enter the song's artist: ");
			artist = userInput.nextLine();
			song = s.searchPrimaryKey(name, artist);
			if (song != null) {
				System.out.println();
				System.out.print("Type in the word you would like to search: ");
				word = userInput.nextLine();
				String wordSearch = word.toLowerCase();
				int counter = 0;

				for (int i = 0; i < song.getLyricsArray().length; i++) {
					if (wordSearch.equals(song.getLyricsArray()[i])) {
						counter++;
					}
				}
				System.out.println("\nThe word " + word + " appears " + counter + " times in the song \""
						+ song.getName() + "\"\n");

			} else {
				System.out.println("\nSorry, that song isn't in our database. Please try again!\n");
			}

		} else if (choice.equals("2")) {
			int years = 0;
			ArrayList<Song> SongArray = s.getArray();
			for (int i = 0; i < SongArray.size(); i++) {
				years = years + SongArray.get(i).getYear();
			}
			System.out.println("\nThe average year the songs in our data base were released is "
					+ (years / SongArray.size()) + ".\n");

		} else if (choice.equals("3")) {
			System.out.print("\nEnter the name of the song: ");
			name = userInput.nextLine();
			System.out.print("Enter the song's artist: ");
			artist = userInput.nextLine();
			song = s.searchPrimaryKey(name, artist);
			if (song != null) {
				int words = countWordsInSong(song.getLyrics());
				System.out.println("\nThere are " + words + " words in the song: " + song.getName() + ".\n");
			} else {
				System.out.println("\nSorry, that song isn't in our database. Please try again!\n");
			}

		} else {
			System.out.println(
					"\nIt seems like an elf may have bumped into you and made you type in the wrong option.. \nPlease try again.\n");
		}
	}

	/**
	 * Helper methods to count the number of words
	 * 
	 * @param input the lyrics
	 * @return number of words in song's lyrics
	 */
	private int countWordsInSong(String input) {
		if (input == null || input.isEmpty()) {
			return 0;
		}
		String[] words = input.split("\\s+");
		return words.length;
	}

	/**
	 * Makes sure that the filename has ".txt" at the end, and calls readFileHelper
	 * 
	 * @param filename the name of the file
	 */
	public void readFile(String fileName) {
		if (fileName.length() <= 4 || !fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) {
			System.out.println("\nYour song file must end in \".txt\" please try again\n");
		} else {
			File file = new File(fileName);
			try {
				Scanner fileInput = new Scanner(file);
				readFileHelper(fileInput);
			} catch (IOException e) {
				System.out.println("Cannot open file " + fileName + "\nPlease try a different file.\n");
			} catch (NumberFormatException e) {
				System.out.println("\nThe year is invalid number! Please update your file and try again!\n");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	/**
	 * Extracts the name, artist, year, and lyrics of the song file in that order
	 * Creates a new song object and adds it to the list of songs in main
	 * 
	 * @param fileInput file to read
	 */
	private void readFileHelper(Scanner fileInput) throws NumberFormatException, Exception {
		// make sure the file actually has the 4 elements
		if (fileInput.hasNextLine()) {
			String name = fileInput.nextLine();
			String artist = fileInput.nextLine();
			int year = Integer.parseInt(fileInput.nextLine());

			String lyrics = fileInput.nextLine() + "\n";
			while (fileInput.hasNextLine()) {
				lyrics += fileInput.nextLine() + "\n";
			}
			Song sn = new Song(name, artist, year, lyrics);
			s.insertSong(sn);
			System.out.println("\n\"" + name + "\" has been uploaded sucessfully!\n");
		} else {
			throw new Exception(
					"\nIt looks like this file is empty! Please make sure your file has the song name, artist, year, and lyrics in seperate lines."
							+ "\nPlease try changing your file contents!\n");
		}

	}

	/**
	 * Makes sure the filename ends with ".txt" and calls helper method
	 * 
	 * @param filename name of the file to write to
	 * @throws IOException
	 */
	public void writeToFile() {
		String fileName;
		Scanner userInput = new Scanner(System.in);
		System.out.print("Type in the name of the file you would like to export your christmas songs to: ");
		fileName = userInput.nextLine();
		if (fileName.length() <= 4 || !fileName.substring(fileName.length() - 4, fileName.length()).equals(".txt")) {
			fileName = fileName + ".txt";
		}
		userInput.close();
		try {
			writeToFileHelper(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Creates a file with the name the user gave and writes into that text file
	 * 
	 * @param file name of file to write to
	 * @throws IOException
	 */
	private void writeToFileHelper(String file) throws IOException {
		File e = new File(file);
		PrintWriter out = new PrintWriter(e);

		out.println(s.display());
		System.out
				.println("\nYour songs have now been written to the file \"" + file + "\"\nHave fun singing along!\n");
		out.close();
	}
}