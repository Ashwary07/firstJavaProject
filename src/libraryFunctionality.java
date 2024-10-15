import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch; 


public class libraryFunctionality {
    private static final int MAX_BOOKS = 100;
    private static Book[] books = new Book[MAX_BOOKS];
    private static int bookCount = 0;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        // To make an asynchronous call: https://www.baeldung.com/java-asynchronous-programming
        new Thread(() -> {
            // First call
            try {
                Thread.sleep(2000); // Simulate long-running operation
                loadBooks();
                latch.countDown(); // Signal that first call is done
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        latch.await(); // Wait for first call to complete
        viewAllBooks();
        menu();
    }
    
    static void menu() {
        // the main menu of the program 
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean run = true;
        while (run) {
            System.out.println("\n  ******************************");
            System.out.println("      Welcome to the Library");
            System.out.println("  ******************************");
            System.out.println("    1 > View All Books");
            System.out.println("    2 > View All eBooks");
            System.out.println("    3 > View All non-eBooks");
            System.out.println("    4 > View an author's books");
            System.out.println("    5 > Add Book");
            System.out.println("    6 > Edit Book");
            System.out.println("    7 > Exit");
            System.out.println("  ******************************");

            try {
                System.out.print("  Enter your choice: ");
                String input = reader.readLine();
                int choice = Integer.parseInt(input);
                if (choice <= 0 || choice >= 8) {
                    System.out.println("  Not an option");
                    System.out.print("  Do you want to try again? (yes/no): ");
                    String retry = reader.readLine().toLowerCase();
                    if (!retry.equals("yes")) {
                        System.out.println("    See you later");
                        run = false;
                    }
                } else if (choice == 7) {
                    // Exits if choosen 7 
                    System.out.println("Thank you for using the system!");
                    run = false;
                }   
                 else {
                    // Else go to menu2 function
                    menu2(choice, reader);
                }
            } catch (Exception e) {
                System.out.println("Wrong input: " + e.getMessage());
            }
        }
    }
    
    static void menu2(int choice, BufferedReader reader) throws IOException {
        switch (choice) {
            // Shows all books
            case 1 -> viewAllBooks();

            // Shows all ebooks
            case 2 -> viewAlleBooks();

            // Shows all non-ebooks
            case 3 -> viewAllNonEBooks();

            // Searh by author
            case 4 -> {
                System.out.print("Enter the name of author: ");
                String authorsName = reader.readLine();
                searchByAuthor(authorsName);
            }

            // Adding a book
            case 5 -> addingBook(reader);

            // To edit a book 
            case 6 -> {
                System.out.print("Enter the name of the book you want to edit: ");
                String editBook = reader.readLine();
                editingBook(editBook, reader);
            }
        }
    }
    // Function to load al the books
    static void loadBooks() {
        String fileName = "./csv/csv.csv";
        // Initalising bufferreader to read the contents of file 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null && bookCount < MAX_BOOKS) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { 
                    String title = parts[0].trim();
                    String ISBN = parts[1].trim();
                    String isEbook = parts[2].trim();
                    int yearOfPublish = Integer.parseInt(parts[3].trim());

                    Book book = new Book(title, ISBN, yearOfPublish, new Author[3]);
                    book.setEBook(isEbook);

                    for (int i = 4; i < parts.length; i += 3) {
                        if (i + 2 < parts.length) {
                            String authorName = parts[i].trim();
                            String nationality = parts[i + 1].trim();
                            int yearOfBirth = Integer.parseInt(parts[i + 2].trim());
                            Author author = new Author(authorName, nationality, yearOfBirth);
                            book.addAuthor(author);
                        }
                    }

                    books[bookCount++] = book;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    // to view all the books
    static void viewAllBooks() {
        System.out.println("All books in the library:");
        // prints everysingle book
        for (int i = 0; i < bookCount; i++) {
            System.out.println(books[i].toString());
            System.out.println("--------------------");
        }
    }

    // to view all e-books
    static void viewAlleBooks() {
        System.out.println("All eBooks in the library:");
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getEBook().equalsIgnoreCase("yes")) {
                System.out.println(books[i].toString());
                System.out.println("--------------------");
            }
        }
    }

    // to view all non e-books
    static void viewAllNonEBooks() {
        System.out.println("All non-eBooks in the library:");
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getEBook().equalsIgnoreCase("no")) {
                System.out.println(books[i].toString());
                System.out.println("--------------------");
            }
        }
    }

    // to search by author 
    static void searchByAuthor(String authorsName) {
        boolean found = false;
        System.out.println("Books by author " + authorsName + ":");
        for (int i = 0; i < bookCount; i++) {
            Author[] authors = books[i].getAllAuthors();
            for (int j = 0; j < books[i].getAuthorCount(); j++) {
                if (authors[j].getName().equalsIgnoreCase(authorsName)) {
                    System.out.println(books[i].toString());
                    System.out.println("--------------------");
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("No books found for author: " + authorsName);
        }
    }

    // to add a book

    static void addingBook(BufferedReader reader) throws IOException {
        System.out.print("Enter the name of the book: ");
        String title = reader.readLine();
        System.out.print("Enter the ISBN of the book: ");
        String ISBN = reader.readLine();
        System.out.print("Is it an ebook (yes/no): ");
        String isEBook = reader.readLine().toLowerCase();
        System.out.print("Enter the year the book was published: ");
        int yearOfPublish = Integer.parseInt(reader.readLine());

        Book newBook = new Book(title, ISBN, yearOfPublish, new Author[3]);
        newBook.setEBook(isEBook);


        // to limit authors upto 3

        for (int i = 0; i < 3; i++) {
            System.out.print("Enter the name of the author (or press enter to finish): ");
            String authorName = reader.readLine();
            if (authorName.isEmpty()) break;

            System.out.print("Enter the nationality of the author: ");
            String nationality = reader.readLine();
            System.out.print("Enter the year of birth of the author: ");
            int yearOfBirth = Integer.parseInt(reader.readLine());

            Author author = new Author(authorName, nationality, yearOfBirth);
            newBook.addAuthor(author);

            if (i < 2) {
                System.out.print("Add another author? (yes/no): ");
                if (!reader.readLine().equalsIgnoreCase("yes")) break;
            }
        }

        if (bookCount < MAX_BOOKS) {
            books[bookCount++] = newBook;
            // writes everything into file
            writeBooksToFile();
            // notify the user
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Error: Maximum number of books reached.");
        }
    }


    // To edit a specific book 

    static void editingBook(String editBook, BufferedReader reader) throws IOException {
        // Check the author name passed by user with the database for the book realted to that author
        for (int i = 0; i < bookCount; i++) {
            // if there is get everything related to that book 
            if (books[i].getTitle().equalsIgnoreCase(editBook)) {
                // And now starts the editing part
                System.out.println("Editing book: " + books[i].getTitle());
                
                System.out.print("Enter new title (or press enter to keep current): ");
                String newTitle = reader.readLine();
                if (!newTitle.isEmpty()) books[i].setTitle(newTitle);

                System.out.print("Enter new ISBN (or press enter to keep current): ");
                String newISBN = reader.readLine();
                if (!newISBN.isEmpty()) books[i].setISBN(newISBN);

                System.out.print("Is it an ebook (yes/no, or press enter to keep current): ");
                String newEBook = reader.readLine().toLowerCase();
                if (!newEBook.isEmpty()) books[i].setEBook(newEBook);

                System.out.print("Enter new year of publish (or press enter to keep current): ");
                String newYear = reader.readLine();
                if (!newYear.isEmpty()) books[i].setYearOfPublish(Integer.parseInt(newYear));

                // Editing authors
                System.out.println("Current authors:");
                Author[] currentAuthors = books[i].getAllAuthors();
                for (int j = 0; j < books[i].getAuthorCount(); j++) {
                    System.out.println((j + 1) + ". " + currentAuthors[j].toString());
                }

                System.out.print("Do you want to edit authors? (yes/no): ");
                if (reader.readLine().equalsIgnoreCase("yes")) {
                    books[i] = new Book(books[i].getTitle(), books[i].getISBN(), books[i].getYearOfPublish(), new Author[3]);
                    books[i].setEBook(newEBook.isEmpty() ? books[i].getEBook() : newEBook);

                    // upto 3 authors 
                    for (int j = 0; j < 3; j++) {
                        System.out.print("Enter the name of the author (or press enter to finish): ");
                        String authorName = reader.readLine();
                        if (authorName.isEmpty()) break;

                        System.out.print("Enter the nationality of the author: ");
                        String nationality = reader.readLine();
                        System.out.print("Enter the year of birth of the author: ");
                        int yearOfBirth = Integer.parseInt(reader.readLine());

                        Author author = new Author(authorName, nationality, yearOfBirth);
                        books[i].addAuthor(author);

                        if (j < 2) {
                            System.out.print("Add another author? (yes/no): ");
                            if (!reader.readLine().equalsIgnoreCase("yes")) break;
                        }
                    }
                }
                
                // finally write into file 
                writeBooksToFile();
                System.out.println("Book edited successfully.");
                return;
            }
        }
        System.out.println("Book not found: " + editBook);
    }


    // to write books to file 
    static void writeBooksToFile() {
        String fileName = "./csv/csv.csv";
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (int i = 0; i < bookCount; i++) {
                Book book = books[i];
                Author[] authors = book.getAllAuthors();
                pw.print(book.getTitle() + "," + book.getISBN() + "," + 
                         book.getEBook() + "," + book.getYearOfPublish());
                for (int j = 0; j < book.getAuthorCount(); j++) {
                    pw.print("," + authors[j].getName() + "," + 
                             authors[j].getNationality() + "," + 
                             authors[j].getYearOfBirth());
                }
                pw.println();
            }
            System.out.println("Books successfully written to file.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
