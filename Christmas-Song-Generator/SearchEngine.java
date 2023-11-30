
/**
 * SearchEngine.java
 * @author Derrick Nguyen
 */

import java.util.ArrayList;

public class SearchEngine {
	private HashTable<Song> primaryKeys;
	private ArrayList<BST<Song>> wordList;
	private HashTable<WordID> keywords;
	private final int SIZE = 7777;
	private static int wordAsIndex = 0;

	private String[] commonWords = { "the", "and", "a", "an", "that", "i", "it", "not", "he", "as", "you", "this",
			"but", "his", "they", "her", "she", "me", "or", "an", "will", "my", "one", "all", "would", "there", "their",
			"to", "of", "in", "for", "on", "with", "at", "by", "from", "up", "about", "into", "over", "after", "be",
			"have", "do", "say", "get", "make", "go", "know", "take", "see", "come", "think", "look", "want", "give",
			"use", "find", "tell", "ask", "work", "seem", "feel", "try", "leave", "call", "lets", "ill", "dont", "if",
			"now", "go", "im", "well", "cause", "wholl", "please" };

	public SearchEngine() {
		wordList = new ArrayList<BST<Song>>();
		keywords = new HashTable<WordID>(SIZE);
		primaryKeys = new HashTable<Song>(SIZE);
	}

	/**
	 * Insert the song with unique keywords into the ArrayList<BST<Song>> wordList,
	 * and HashTable<Song> primaryKeys
	 * 
	 * @param song
	 */
	public void insertSong(Song song) {
		String lyrics = song.getLyrics();
		// lyrics will be lower case, remove \n, punctuation in it.
		lyrics = lyrics.toLowerCase();
		lyrics = lyrics.replace("\n", " ");
		lyrics = removePunctuations(lyrics);

		// split the string lyrics into an array with each word as an element
		String[] songArray = lyrics.split(" ");
		song.setLyricsArray(songArray);

		for (int i = 0; i < songArray.length; i++) {
			// create an instance of WordID object using constructor which only takes in
			// name
			WordID currWord = new WordID(songArray[i]);

			// check if it is a common word
			if (!isCommonWord(currWord)) {
				// check if word exists inside hash table
				if (!dupExist(currWord)) {
					// assign the currWord an ID and insert into hash table
					currWord.setId(wordAsIndex);
					insertWord(currWord);
					wordAsIndex++;
//					indexWordArray.add(currWord.getName());
				}
				// initialize a
				if (wordList.size() < wordAsIndex) {
					wordList.add(new BST<Song>());
				}
				// check if the value at currWordID is null, if it is create new BST, otherwise
				// insert to existing BST
				// our BST disallows duplicates so the same song will not be inserted multiple
				// times
				int indexOfExistingWord = keywords.getValue(currWord).getId();
				wordList.get(indexOfExistingWord).insert(song);
			}
		}
		// Thang's part: insertSong for primaryKeys
		primaryKeys.add(song);
	}

	// ************** ACCESOR METHODS ****************

	/**
	 * Search for the song(s) with the keyword in the song's lyrics
	 * 
	 * @param keyword
	 * @return founded list of song(s)
	 */
	public ArrayList<Song> searchKeyword(String keyword) {
		WordID instance = new WordID(keyword);
		if (keywords.contains(instance)) {
			int index = keywords.getValue(instance).getId();
			return wordList.get(index).inOrderObject();
		}
		return null;
	}

	/**
	 * Search for the song with the name and artist of that song
	 * 
	 * @param name   of the song
	 * @param artist of the song
	 * @return founded song
	 */
	// I had to divide this searchPrimaryKey Method into 2 methods to combat
	// collision problems if they occur - Thang
	// real method
	public Song searchPrimaryKey(String name, String artist) {
		String n = removePunctuations(name);
		Song temp = new Song(n, artist);
		int store = temp.hashCode();

		int hash = Math.abs(store) % SIZE;

		LinkedList<Song> l = primaryKeys.getList(hash);

		Song find = searchSong(l, n, artist);
		return find;
	}

	/**
	 * Helper method to Search for the song in one of the List in HashTable<Song>
	 * primaryKeys
	 * 
	 * @param l      the list contains the song
	 * @param name   of the song need to find
	 * @param artist of the song need to find
	 * @return
	 */
	private Song searchSong(LinkedList<Song> l, String name, String artist) {
		Song temp = new Song(name, artist);

		l.positionIterator();

		for (int i = 0; i < l.getLength(); i++) {
			if (removePunctuations(l.getIterator().getName()).equalsIgnoreCase(temp.getName())
					&& removePunctuations(l.getIterator().getArtist()).equalsIgnoreCase(temp.getArtist())) {
				return l.getIterator();

			} else {
				l.advanceIterator();
			}
		}
		return null;
	}

	public int getNum() {
		return primaryKeys.getNumElements();
	}

	public String display() {
		return primaryKeys.toString();
	}

	// ************** MUTATOR METHODS ****************

	/**
	 * Modify the song's name in the ArrayList<BST<Song>> wordList, and
	 * HashTable<Song> primaryKeys
	 * 
	 * @param song the song that will change the name
	 * @param name the new name
	 */
	public void modifySongName(Song song, String name) {
		Song revisedSong = new Song(song);
		revisedSong.setName(name);

		if (!song.getName().equals(name)) {
			// change song inside primary keys as well
			if (primaryKeys.delete(song)) {
				primaryKeys.add(revisedSong);
			}
			// rehash primarykeys with new songname + old artist name
			for (int i = 0; i < wordList.size(); i++) {
				if (wordList.get(i).search(song)) {
					wordList.get(i).remove(song);
					wordList.get(i).insert(revisedSong);
				}
			}

		}
	}

	/**
	 * Modify the song's artist in the ArrayList<BST<Song>> wordList, and
	 * HashTable<Song> primaryKeys
	 * 
	 * @param song   the song that will change the artist
	 * @param artist the new artist
	 */
	public void modifySongArtist(Song song, String artist) {
		Song revisedSong = new Song(song);
		revisedSong.setArtist(artist);

		if (!song.getArtist().equals(artist)) {
			if (primaryKeys.delete(song)) {
				primaryKeys.add(revisedSong);
			}

			for (int i = 0; i < wordList.size(); i++) {
				if (wordList.get(i).search(song)) {
					wordList.get(i).get(song).setArtist(artist);
				}
			}
		}
	}

	/**
	 * Modify the song's year in the ArrayList<BST<Song>> wordList, and
	 * HashTable<Song> primaryKeys
	 * 
	 * @param song the song that will change the year
	 * @param year the new year
	 */
	public void modifySongYear(Song song, int year) {
		if (song.getYear() != year) {
			if (primaryKeys.getValue(song) != null) {
				primaryKeys.getValue(song).setYear(year);
			}

			for (int i = 0; i < wordList.size(); i++) {
				if (wordList.get(i).search(song)) {
					wordList.get(i).get(song).setYear(year);
				}
			}
		}
	}

	/**
	 * Remove song in the ArrayList<BST<Song>> wordList, and HashTable<Song>
	 * primaryKeys
	 * 
	 * @param song the song that will remove
	 */
	public void removeSong(Song song) {
		for (int i = 0; i < wordList.size(); i++) {
			if (wordList.get(i).search(song)) {
				wordList.get(i).remove(song);
			}
		}
		primaryKeys.delete(song);
	}

	// ************** HELPER METHODS ****************

	/**
	 * Check for common words in lyrics
	 * 
	 * @param word to check with the list of common words
	 * @return true or false if there's a common word in the lyrics
	 */
	private boolean isCommonWord(WordID word) {
		for (int i = 0; i < commonWords.length; i++) {
			if (commonWords[i].equals(word.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check for duplicate word that already exist in the HashTable<WordID> keywords
	 * 
	 * @param word to check duplicating
	 * @return true or false if the word is duplicated
	 */
	private boolean dupExist(WordID word) {
		return keywords.contains(word);
	}

	/**
	 * Insert the word into HashTable<WordID> keywords
	 * 
	 * @param newWord
	 */
	private void insertWord(WordID newWord) {
		keywords.add(newWord);
	}

	/**
	 * Remove punctuation in the lyrics
	 * 
	 * @param source the lyrics
	 * @return new String of lyrics without punctuation
	 */
	private String removePunctuations(String source) {
		return source.replaceAll("\\p{Punct}", "");
	}

	/**
	 * Returns an arrayList with all the Song values from the Song HashTable used as
	 * a getter method for the calculateStats methods in UserControls.java
	 * 
	 * @return an arrayList of the Song objects
	 */
	public ArrayList<Song> getArray() {
		return primaryKeys.getArray();
	}
}