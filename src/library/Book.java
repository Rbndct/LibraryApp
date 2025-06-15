package library;

@SuppressWarnings("squid:S106")
public class Book {

    private final String title;
    private final String authorFirstName;
    private final String authorLastName;
    private final int publicationYear;
    private final int bookID;
    private boolean isBorrowed;

    private static int idCounter = 1;

    public Book(String title, String authorFirstName, String authorLastName, int publicationYear) {
        this.bookID = idCounter++;
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.publicationYear = publicationYear;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public int getBookID() {
        return bookID;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.isBorrowed = borrowed;
    }

    public void display() {
        String status = isBorrowed ? "Borrowed" : "Available";
        System.out.printf("%-10d %-50s %-25s %-25s %-10s %-10s%n",
            bookID, title, authorFirstName, authorLastName, publicationYear, status);
    }
}