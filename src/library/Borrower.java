package library;

@SuppressWarnings("squid:S106")
public class Borrower {

    private static final int MAX_NO_BOOKS_BORROW = 10;

    private static int idCounter = 1;
    private final String borrowerName;
    private final int borrowerID;
    private int borrowedBooksCounter;
    private final Book[] borrowedBooks;

    public Borrower(String borrowerName) {
        this.borrowerID = idCounter++;
        this.borrowerName = borrowerName;
        this.borrowedBooks = new Book[MAX_NO_BOOKS_BORROW];
        this.borrowedBooksCounter = 0;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getBorrowedBooksCounter() {
        return borrowedBooksCounter;
    }

    public Book[] getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean borrowBook(Book book, Transaction[] transactions, int transactionCount) {
        if (borrowedBooksCounter >= MAX_NO_BOOKS_BORROW) {
            System.out.println("Borrow limit reached. Cannot borrow more than " + MAX_NO_BOOKS_BORROW + " books.");
            return false;
        }

        if (book.isBorrowed()) {
            System.out.println("Book is already borrowed.");
            return false;
        }

        borrowedBooks[borrowedBooksCounter++] = book;
        book.setBorrowed(true);

        transactions[transactionCount] = new Transaction(this.getBorrowerID(), book.getBookID(), book.getTitle(), "Borrow");
        System.out.println("Book borrowed successfully.");

        return true;
    }

    public boolean returnBook(int bookId, Transaction[] transactions, int transactionCount) {
        for (int i = 0; i < borrowedBooksCounter; i++) {
            Book currentBook = borrowedBooks[i];
            if (currentBook != null && currentBook.getBookID() == bookId) {
                currentBook.setBorrowed(false);

                // Shift borrowedBooks left to fill the gap
                for (int j = i; j < borrowedBooksCounter - 1; j++) {
                    borrowedBooks[j] = borrowedBooks[j + 1];
                }
                borrowedBooks[--borrowedBooksCounter] = null;

                transactions[transactionCount] = new Transaction(this.getBorrowerID(), bookId, currentBook.getTitle(), "Return");

                System.out.println("Book returned successfully.");
                return true;
            }
        }
        System.out.println("Book not found in borrowed list.");
        return false;
    }

    public void viewBorrowedBooks() {
        if (borrowedBooksCounter == 0) {
            System.out.println(borrowerName + " has not borrowed any books.");
            return;
        }

        System.out.printf("%nBorrowed Books for %s:%n", borrowerName);
        System.out.printf("%-10s %-50s %-25s %-25s %-5s%n",
            "Book ID", "Book Title", "Author's First Name", "Author's Last Name", "Year");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < borrowedBooksCounter; i++) {
            if (borrowedBooks[i] != null) {
                borrowedBooks[i].display();
            }
        }
    }

    public void displayBasicInfo() {
        System.out.printf("%-10d %-25s%n", getBorrowerID(), getBorrowerName());
    }

    public void clearBorrowedBooks(Transaction[] transactions, int[] transactionCountHolder) {
        for (int i = 0; i < borrowedBooksCounter; i++) {
            if (borrowedBooks[i] != null) {
                borrowedBooks[i].setBorrowed(false);
            }
        }

        // Clear all borrowed books and record returns

        while (borrowedBooksCounter > 0) {
            assert borrowedBooks[0] != null : "borrowedBooks[0] should never be null here";
            boolean returned = returnBook(borrowedBooks[0].getBookID(), transactions, transactionCountHolder[0]);
            if (returned) {
                transactionCountHolder[0]++;
            }
        }

    }
}
