public class Book {
    private String title;
    private String ISBN;
    private int yearOfPublish;
    /* Can be multiple authors */
    private Author[] authors;
    private String isEbook;
    private int authorCount;

    // parameter constructors 
    public Book(String pTitle, String pISBN, int pYearOfPublish, Author[] pAuthors) {
        title = pTitle;
        ISBN = pISBN;
        yearOfPublish = pYearOfPublish;
        authors = new Author[3];
        authorCount = 0;
        isEbook = "no"; // Default to non-eBook
    }
    

    /* Getters */
    public String getTitle() { return title; }
    public String getISBN() { return ISBN; }
    public int getYearOfPublish() { return yearOfPublish; }
    public int getAuthorCount() { return authorCount; }
    public Author[] getAllAuthors() { return authors; }
    public String getEBook() { return isEbook; }

    public Author getSpecificAuthor(String authorName) {
        for (int i = 0; i < authorCount; i++) {
            if (authors[i].getName().equalsIgnoreCase(authorName)) {
                return authors[i];
            }
        }
        return null;
    }

    public void addAuthor(Author author) {
        if (authorCount < 3) {
            authors[authorCount] = author;
            authorCount++;
        } else {
            throw new IllegalStateException("A book can have no more than 3 authors.");
        }
    }

    /* Setters */
    public void setTitle(String pTitle) { title = pTitle; }
    public void setISBN(String pISBN) { ISBN = pISBN; }
    public void setYearOfPublish(int pYearOfPublish) { yearOfPublish = pYearOfPublish; }
    public void setEBook(String pIsEbook) { isEbook = pIsEbook; }


    /* Equals to */
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book other = (Book) obj;
        if (!title.equals(other.getTitle()) || !ISBN.equals(other.getISBN()) || 
            yearOfPublish != other.getYearOfPublish() || !isEbook.equals(other.getEBook()) || 
            authorCount != other.getAuthorCount())
            return false;
        for (int i = 0; i < authorCount; i++) {
            if (!authors[i].equals(other.getAllAuthors()[i]))
                return false;
        }
        return true;
    }

    /* To String */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title)
          .append(", ISBN: ").append(ISBN)
          .append(", Year: ").append(yearOfPublish)
          .append(", eBook: ").append(isEbook)
          .append("\nAuthors: ");
        for (int i = 0; i < authorCount; i++) {
            if (i > 0) sb.append("; ");
            sb.append(authors[i].toString());
        }
        return sb.toString();
    }
}