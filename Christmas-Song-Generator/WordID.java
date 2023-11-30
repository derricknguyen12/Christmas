
/**
 * WordID.java
 * @author Derrick Nguyen
 */
public class WordID implements Comparable<WordID> {
	private int id;
	private String name;

	public WordID(String name) {
		this.name = name;
		this.id = -1;
	}

	public WordID(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() { // the hashCod determine on the name
		String key = name; // this will store the "key" for this class - edited
		int sum = 0;
		for (int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof WordID)) {
			return false;
		} else {
			WordID s = (WordID) o;
			return s.name.equalsIgnoreCase(this.name);
		}
	}

	@Override
	public int compareTo(WordID s) {
		if (this.equals(s)) {
			return 0;
		} else if (!this.name.equals(s.name)) {
			return this.name.compareTo(s.name);
		} else {
			return Integer.compare(this.id, s.id);
		}
	}

	@Override
	public String toString() {
		return name + ": " + id;
	}
}