
/**
 * Song.java
 * @author Derrick Nguyen
 */
public class Song implements Comparable<Song> {

	private String name;
	private String artist;
	private int year;
	private String lyrics;
	private String[] lyricsArray;

	/* Constructors */

	public Song() {
		this("unknown name", "artist unknown", 0, " ");
	}

	public Song(String name, String artist, int year, String lyrics) {
		this.name = name;
		this.artist = artist;
		this.year = year;
		this.lyrics = lyrics;
	}

	public Song(String name, String artist) {
		this.name = name;
		this.artist = artist;
		this.year = 0;
		this.lyrics = " ";
	}

	public Song(Song s) {
		if (s != null) {
			this.name = s.name;
			this.year = s.year;
			this.artist = s.artist;
			this.lyrics = s.lyrics;
			this.lyricsArray = s.lyricsArray;
		}
	}

	/* Accessors */

	public String getName() {
		return name;
	}

	public int getYear() {
		return year;
	}

	public String getArtist() {
		return artist;
	}

	public String getLyrics() {
		return lyrics;
	}

	public String[] getLyricsArray() {
		return lyricsArray;
	}

	/* Mutators */
	public void setName(String name) {
		this.name = name;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	public void setLyricsArray(String[] lyricsArray) {
		this.lyricsArray = lyricsArray;
	}

	/* Methods */

	@Override
	public String toString() {
		return ("\nSong Name: " + name + " \nArtist: " + artist + "\nYear: " + year + "\nLyrics:\n" + lyrics);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof Song)) {
			return false;
		} else {
			Song s = (Song) o;
			return this.name.equals(s.name) && this.artist.equals(s.artist) && this.year == s.year
					&& this.lyrics.equals(s.lyrics);
		}
	}

	@Override
	public int compareTo(Song s) {
		if (this.equals(s)) {
			return 0;

		} else if (!this.name.equals(s.name)) {
			return this.name.compareTo(s.name);

		} else if (!this.artist.equals(s.artist)) {
			return this.artist.compareTo(s.artist);

		} else if (!this.lyrics.equals(s.lyrics)) {
			return this.lyrics.compareTo(s.lyrics);

		} else {
			return Integer.compare(this.year, s.year);
		}
	}

	@Override
	public int hashCode() {
		String n = removePunctuations(name.toLowerCase());

		String key = n + artist.toLowerCase();

		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

	private String removePunctuations(String source) {
		return source.replaceAll("\\p{Punct}", "");
	}

}