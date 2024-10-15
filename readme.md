#### ALL CODE RESIDES IN src FOLDER ####
## NOTE: The database file already have 60 books in it for you to run tests and play with it and have space for 40 more books, if you want to add more books change MAX_BOOKS (libraryFunctionality.java line 10) to *DESIRED NUMBER*  

# Library Management System

## Overview
This Library Management System is a Java-based application that allows users to manage a collection of books. It supports operations such as viewing books, adding new books, editing existing books, and searching for books by author.

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- A text editor or IDE (e.g., IntelliJ IDEA, Eclipse, VS Code, or WebStorm)

### Installation
1. Clone this repository or download the source code.
2. Ensure that the `csv` folder contains the `csv.csv` file with the initial book data.

### Compilation
Compile the Java files using the following commands: 

javac Author.java
javac Book.java
javac libraryFunctionality.java

### Running the Application
Run the application using the following command:

java libraryFunctionality

## Using the System

Upon running the application, you will be presented with a menu offering the following options:

1. View All Books
2. View All eBooks
3. View All non-eBooks
4. View an author's books
5. Add Book
6. Edit Book
7. Exit

### 1. View All Books
- Displays all books in the library, including their details and authors.

### 2. View All eBooks
- Shows only the books that are marked as eBooks.

### 3. View All non-eBooks
- Displays books that are not eBooks.

### 4. View an author's books
- Prompts you to enter an author's name.
- Shows all books written by that author.

### 5. Add Book
- Guides you through adding a new book to the library.
- You can add up to 3 authors for each book.
- The new book will be saved to the CSV file.

### 6. Edit Book
- Allows you to modify the details of an existing book.
- You can edit the title, ISBN, publication year, eBook status, and authors.

### 7. Exit
- Closes the application.

## Notes
- The system can manage up to 100 books.
- Books are automatically saved to the CSV file after adding or editing.
- The system is case-insensitive for author searches.

## Troubleshooting
- If you encounter any issues loading the CSV file, ensure it's located in the `./csv/` directory and named `csv.csv`.
- For any other issues, please check the console for error messages.

## Contributing
Feel free to fork this project and submit pull requests with any improvements or bug fixes.
