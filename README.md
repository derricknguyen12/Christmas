# Christmas Song Manager

A Java-based application for managing a collection of Christmas songs, including features such as uploading songs, removing songs, searching for songs, and generating statistics. The application employs different data structures such as Binary Search Trees (BST), Hash Tables, Linked Lists, and more to organize and manage the songs efficiently.

## Project Overview

The Christmas Song Manager application provides an interactive interface for users to manage a collection of Christmas songs. The system allows users to upload, remove, modify, and search songs. Additionally, the application calculates statistics related to the song collection such as word count and song frequency. This project demonstrates the use of fundamental data structures like BST, Hash Tables, Linked Lists, and Stacks.

## Features

* **Upload Songs**: Load songs from a formatted text file and store them in different data structures.
* **Remove Songs**: Remove songs based on the song name and artist.
* **Search Songs**: Search for songs using keywords or exact song names.
* **Modify Songs**: Edit song details such as name, artist, and year.
* **Statistics**: Generate statistics like word count or average release year.
* **Export to File**: Export the current song collection to a text file.
* **Efficient Searching**: Utilizes BST for sorting and hashing for fast lookup.

## Components

### BST (Binary Search Tree)

The BST (Binary Search Tree) is used to store songs sorted by song names for efficient searching. It provides logarithmic time complexity for insertion, deletion, and lookup operations in average cases.

#### Key Features:

* Each node contains a song and its associated information.
* Each song's name is used as the key to maintain order within the tree.
* The tree allows for fast searching, insertion, and deletion operations with a time complexity of O(log n) in average cases.

Code Location: `BST.java`

### Hash Table

The Hash Table is used to store songs using their unique song names as keys, allowing for fast retrieval by leveraging hashing techniques. This structure is ideal for applications requiring quick lookups of songs.

#### Key Features:

* **Hashing**: A hash function is applied to the song name to determine the index in the hash table.
* **Collision Resolution**: Utilizes chaining with a linked list to handle collisions when two songs hash to the same index.
* **Efficient Search**: Provides constant time complexity O(1) for search, insert, and delete operations on average.

Code Location: `HashTable.java`

### Linked List

The Linked List is used for managing songs in certain operations such as chaining in the hash table and for iterating over collections when necessary.

#### Key Features:

* A singly linked list where each node stores a song and a reference to the next node.
* The linked list is used to store multiple songs at the same hash index in the hash table (when a hash collision occurs).

Code Location: `LinkedList.java`

### Search Engine

The Search Engine is the core of the search functionality in the application. It performs searches using different data structures based on the criteria provided (song name or keywords).

#### Key Features:

* **Binary Search Tree (BST)**: Searches for songs based on song names using the BST structure.
* **Hash Table**: Allows quick lookups of songs using a hash table.
* **Full-Text Search**: Performs keyword searches in song lyrics using a custom search engine method.

Code Location: `SearchEngine.java`

### Song Class

The Song class represents individual Christmas songs. It holds the details of the song, including the name, artist, year of release, and lyrics. This class is used as the basic unit of storage across the different data structures.

#### Key Features: 

* Stores song name, artist, year of release, and lyrics.
* Includes methods for comparing songs, especially for sorting and searching within the BST.

Code Location: `Song.java`

### User Controls

The UserControls class is responsible for handling user input and controlling the overall flow of the application. It serves as the interface for users to interact with the application, allowing them to upload songs, search, remove, and modify song details.

#### Key Features:

* **Interactive Menu**: Provides an interactive menu to the user.
* **Error Handling**: Validates user input and handles exceptions gracefully.
* **Delegation**: Delegates tasks to other components such as the BST, hash table, and linked list.

Code Location: `UserControls.java`

### WordID Class

The WordID class represents a word and its associated ID. It is used in cases where song lyrics are stored and indexed for searching. The class implements the Comparable interface, allowing it to be sorted based on the word's value.

#### Key Features:

* Stores a word and its associated ID.
* Overrides equals(), hashCode(), and compareTo() to ensure proper behavior when stored in hash tables and sorted collections.

Code Location: `WordID.java`

### Driver Class

The Driver class is the entry point for running the application. It contains the `main()` method that initializes the UserControls and launches the program. The driver sets up the necessary environment and interacts with the user to manage songs.

Code Location: `Driver.java`

## Usage

### Uploading Songs
* Songs are uploaded from a formatted text file using the uploadSong() method in UserControls. Each song is expected to have the format: songName, artist, lyrics.

### Searching for Songs
* You can search for songs using the Binary Search Tree or Hash Table. The search results are displayed based on song names or lyrics (full-text search).

### Modifying Songs
* Modifications such as changing the song name, artist, or year can be made using the UserControls interface.

### Removing Songs
* Songs can be removed from the collection based on their name and artist.

### Generating Statistics
* Basic statistics such as the total number of songs, average song length, and total word count are computedsing methods from SearchEngine and displayed to the user.

### Exporting to File
* The writeToFile() method in UserControls allows the user to export the song collection into a text file.

## Ending

This Christmas Song Manager project showcases my proficiency with fundamental data structures and algorithms, including Binary Search Trees (BST), Hash Tables, Linked Lists, and Stacks. Through this application, I have demonstrated my ability to design and implement efficient search operations, file handling, and data management techniques in Java.

Key technical skills highlighted in this project include:

* **Efficient Search Algorithms**: Implementing search functionality using both a Binary Search Tree (BST) and Hash Table to quickly retrieve and manipulate song data.
* **Data Structure Implementation**: Designing and utilizing Linked Lists, Stacks, and custom classes such as WordID to manage song data and metadata.
* **File I/O**: Reading and writing song data from and to files, ensuring robust error handling for real-world usage scenarios.
* **Object-Oriented Programming (OOP)**: Applying OOP principles such as encapsulation, inheritance, and polymorphism to model the various components of the application.


This project demonstrates my ability to build scalable and maintainable Java applications, and is a testament to my problem-solving and coding capabilities.
