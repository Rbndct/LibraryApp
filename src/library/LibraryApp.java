package library;

import java.util.Scanner;

@SuppressWarnings("squid:S106")
public class LibraryApp {
    private static final int MAX_NO_BOOKS = 100;
    private static  final int MAX_NO_BORROWERS = 50;

    private static final Book[] books = new Book[MAX_NO_BOOKS];
    private static int bookCount = 0;

    private static final Borrower[] borrowers = new Borrower[MAX_NO_BORROWERS];
    private static int borrowerCount =0;

    private static final int MAX_TRANSACTIONS = 200;
    private static final Transaction[] transactions = new Transaction[MAX_TRANSACTIONS];
    private static int transactionCount = 0;

    private static final Scanner scanner = new Scanner(System.in);

    private static final String NO_BORROWERS_MSG = "No borrowers registered yet!";
    private static final String MENU_ITEM_FORMAT = "%-5s%-25s%n";
    private static final String INVALID_NUMBER_PROMPT = "Invalid input. Please enter a number: ";
    private static final String PROMPT_CHOICE = "Enter your choice: ";
    private static final String INVALID_CHOICE_MESSAGE = "Invalid choice. Try again.";
    private static final String BACK_TO_MAIN_MENU = "Back to Main Menu";
    private static final String RETURNING_TO_MAIN_MENU_MSG = "Returning to main menu...";




    public static void main (String[] args) {
       loadTestData();

        int mainMenuChoice;
        do {
            System.out.println("\nLibrary Main Menu");
            printMenuItem("1.", "Book Menu");
            printMenuItem("2.", "Borrower Menu");
            printMenuItem("3.", "Transaction Menu");
            printMenuItem("4.", "Exit");
            System.out.print(PROMPT_CHOICE);


            while (!scanner.hasNextInt()) {
                System.out.print(INVALID_NUMBER_PROMPT);
                scanner.next();
            }

            mainMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline

            switch (mainMenuChoice) {
                case 1:
                    bookMenu();
                    break;
                case 2:
                    borrowerMenu();
                    break;
                case 3:
                    transactionMenu();
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (mainMenuChoice != 4);
        scanner.close();
    }


    private static void printMenuItem(String number, String description) {
        System.out.printf(MENU_ITEM_FORMAT, number, description);
    }

    public static void bookMenu() {
        int choice;
        do {
            System.out.println("\nBook Menu");
            printMenuItem("1.", "Add New Book");
            printMenuItem("2.", "View Books");
            printMenuItem("3.", "Search Books");
            printMenuItem("4.", BACK_TO_MAIN_MENU);

            System.out.print(PROMPT_CHOICE);

            while (!scanner.hasNextInt()) {
                System.out.print(INVALID_NUMBER_PROMPT);
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    searchBooks();
                    break;
                case 4:
                    System.out.println(RETURNING_TO_MAIN_MENU_MSG);
                    break;
                default:
                    System.out.println(INVALID_CHOICE_MESSAGE);
            }
        } while (choice != 4);
    }


    public static void borrowerMenu() {
        int choice;
        do {
            System.out.println("\nBorrower Menu");
            printMenuItem("1.", "Borrow Book");
            printMenuItem("2.", "Return Book");
            printMenuItem("3.", "View Borrowed Books");
            printMenuItem("4.", "View all Borrowers");
            printMenuItem("5.", "Add New Borrower");
            printMenuItem("6.", "Delete a Borrower");
            printMenuItem("7.", BACK_TO_MAIN_MENU);

            System.out.print(PROMPT_CHOICE);

            while (!scanner.hasNextInt()) {
                System.out.print(INVALID_NUMBER_PROMPT);
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    borrowBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    viewBorrowedBooks();
                    break;
                case 4:
                    viewBorrowers();
                    break;
                case 5:
                    registerBorrower();
                    break;
                case 6:
                    deleteBorrower();
                    break;
                case 7:
                    System.out.println(RETURNING_TO_MAIN_MENU_MSG);
                    break;
                default:
                    System.out.println(INVALID_CHOICE_MESSAGE);
            }
        } while (choice != 7);
    }


    private static void addBook() {
        if (bookCount >= MAX_NO_BOOKS) {
            System.out.println("Book list is full!");
            return;
        }

        System.out.print("\nEnter book title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter author's first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter author's last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter publication year: ");
        int year;
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid year. Please enter a number: ");
            scanner.next();
        }
        year = scanner.nextInt();
        scanner.nextLine();

        books[bookCount++] = new Book(title, firstName, lastName, year);
        System.out.println("Book added successfully!");
    }

    private static void printBookTableHeader() {
        System.out.printf("%n%-10s %-50s %-25s %-25s %-10s %-10s%n",
            "Book ID", "Book Title", "Author's First Name", "Author's Last Name", "Year", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void viewBooks()
    {
        if (bookCount == 0) {
            System.out.println("No books logged yet!");
            return;
        }

        printBookTableHeader();

        for (int i = 0; i < bookCount; i++) {
            books[i].display();
        }
    }

    private static void searchBooks() {
        if (bookCount == 0) {
            System.out.println("No books logged yet!");
            return;
        }

        System.out.print("Enter search word: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        boolean found = false;

        for (int i = 0; i < bookCount; i++) {
            Book b = books[i];
            if (b.getTitle().toLowerCase().contains(keyword)
                || b.getAuthorFirstName().toLowerCase().contains(keyword)
                || b.getAuthorLastName().toLowerCase().contains(keyword)
                || String.valueOf(b.getPublicationYear()).contains(keyword)) {

                if (!found) {  // Print header only once, before the first match
                    System.out.println("Match found displaying results:");
                    printBookTableHeader();
                }

                b.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching books found.");
        }
    }

    private static boolean noBorrowers() {
        if (borrowerCount == 0) {
            System.out.println(NO_BORROWERS_MSG);
            return true;
        }
        return false;
    }

    private static Borrower selectBorrowerByID() {
        if (noBorrowers()) return null;

        System.out.println("\nBorrowers List:");
        for (int i = 0; i < borrowerCount; i++) {
            System.out.printf("ID: %d - %s%n", borrowers[i].getBorrowerID(), borrowers[i].getBorrowerName());
        }

        System.out.print("Enter borrower ID: ");
        int borrowerID = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < borrowerCount; i++) {
            if (borrowers[i].getBorrowerID() == borrowerID) {
                return borrowers[i];
            }
        }

        System.out.println("Invalid borrower ID.");
        return null;
    }

    private  static void registerBorrower() {
        if (borrowerCount >= MAX_NO_BORROWERS) {
            System.out.println("Borrower list is full!");
            return;
        }

        System.out.print("\nEnter borrower's name: ");
        String name = scanner.nextLine().trim();

        borrowers[borrowerCount++] = new Borrower(name);
        System.out.println("Borrower registered successfully!");
    }

    private static void viewBorrowers() {
        if (noBorrowers()) return;

        System.out.printf("%n%-10s %-25s%n", "Borrower ID", "Borrower Name");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < borrowerCount; i++) {
            borrowers[i].displayBasicInfo();
        }
    }

    private static void borrowBook() {
        if (noBorrowers()) return;

        if (bookCount == 0) {
            System.out.println("No books available for borrowing!");
            return;
        }

        System.out.println("Select a borrower:");
        for (int i = 0; i < borrowerCount; i++) {
            System.out.printf("%d. %s%n", i + 1, borrowers[i].getBorrowerName());
        }

        System.out.print("Enter the ID of the borrower: ");
        int userIDBorrower = scanner.nextInt() - 1;
        scanner.nextLine();

        if (userIDBorrower < 0 || userIDBorrower >= borrowerCount) {
            System.out.println("Invalid borrower ID.");
            return;
        }

        Borrower borrower = borrowers[userIDBorrower];

        System.out.println("Select a book to borrow:");
        viewBooks();

        System.out.print("Enter the ID of the book to borrow: ");
        while (!scanner.hasNextInt()) {
            System.out.print(INVALID_NUMBER_PROMPT);
            scanner.next();
        }

        int bookID = scanner.nextInt();
        scanner.nextLine();

        Book bookToBorrow = null;
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookID() == bookID) {
                bookToBorrow = books[i];
                break;
            }
        }

        if (bookToBorrow == null) {
            System.out.println("Book not found.");
            return;
        }

        boolean success = borrower.borrowBook(bookToBorrow, transactions, transactionCount);

        if (success) {
            transactionCount++;
        }
    }

    private static void viewBorrowedBooks() {
        Borrower borrower = selectBorrowerByID();
        if (borrower != null) {
            borrower.viewBorrowedBooks();
        }
    }

    private static void deleteBorrower() {
        Borrower borrower = selectBorrowerByID();
        if (borrower == null) return;

        if (borrower.getBorrowedBooksCounter() > 0) {
            int[] transactionCounterHolder = { transactionCount };
            borrower.clearBorrowedBooks(transactions, transactionCounterHolder);
            transactionCount = transactionCounterHolder[0]; // update global counter
        }

        for (int i = 0; i < borrowerCount; i++) {
            if (borrowers[i].getBorrowerID() == borrower.getBorrowerID()) {
                for (int j = i; j < borrowerCount - 1; j++) {
                    borrowers[j] = borrowers[j + 1];
                }
                borrowers[--borrowerCount] = null;
                System.out.println("Borrower deleted successfully.");
                return;
            }
        }

        System.out.println("Borrower not found.");
    }


    private static void returnBook() {
        Borrower borrower = selectBorrowerByID();
        if (borrower == null) return;

        if (borrower.getBorrowedBooksCounter() == 0) {
            System.out.println(borrower.getBorrowerName() + " has not borrowed any books.");
            return;
        }

        System.out.println("\nBorrowed Books:");
        System.out.printf("%-10s %-50s%n", "Book ID", "Book Title");
        for (int i = 0; i < borrower.getBorrowedBooksCounter(); i++) {
            Book b = borrower.getBorrowedBooks()[i];
            if (b != null) {
                System.out.printf("%-10d %-50s%n", b.getBookID(), b.getTitle());
            }
        }

        System.out.print("Enter the Book ID to return: ");
        while (!scanner.hasNextInt()) {
            System.out.print(INVALID_NUMBER_PROMPT);
            scanner.next();
        }
        int bookIdToReturn = scanner.nextInt();
        scanner.nextLine();

        boolean returned = borrower.returnBook(bookIdToReturn, transactions, transactionCount);

        if (returned) {
            transactionCount++;
        } else {
            System.out.println("Return operation failed. Book not found in borrower's list.");
        }
    }


    public static void transactionMenu() {
        int choice;
        do {
            System.out.println("\nTransaction Menu");
            printMenuItem("1.", "View All Transactions");
            printMenuItem("2.", "View Transactions by Borrower");
            printMenuItem("3.", BACK_TO_MAIN_MENU);

            System.out.println(PROMPT_CHOICE);

            while (!scanner.hasNextInt()) {
                System.out.print(INVALID_NUMBER_PROMPT);
                scanner.next();
            }

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllTransactions();
                    break;
                case 2:
                    viewTransactionsByBorrower();
                    break;
                case 3:
                    System.out.println(RETURNING_TO_MAIN_MENU_MSG);
                    break;
                default:
                    System.out.println(INVALID_CHOICE_MESSAGE);
            }
        } while (choice != 3);
    }

    static void printTransactionTableHeader() {
        System.out.printf("%-5s %-10s %-50s %-10s %-15s %-15s %-15s%n",
            "ID", "Book ID", "Book Name", "Borrower", "Date", "Time", "Type");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
    }


    private static void viewAllTransactions() {
        if (transactionCount == 0) {
            System.out.println("No transactions recorded yet!");
            return;
        }

        printTransactionTableHeader();
        for (int i = 0; i < transactionCount; i++) {
            transactions[i].displayTransaction();
        }
    }



    private static void viewTransactionsByBorrower() {
        if (noBorrowers()) return;

        Borrower borrower = selectBorrowerByID();
        if (borrower == null) return;

        boolean found = false;
        printTransactionTableHeader();

        for (int i = 0; i < transactionCount; i++) {
            if (transactions[i].getBorrowerId() == borrower.getBorrowerID()) {
                transactions[i].displayTransaction();
                found = true;
            }
        }

        if (!found) {
            System.out.println(borrower.getBorrowerName() + " has no transactions yet.");
        }
    }

    private static void loadTestData() {
        // Add books
        books[bookCount++] = new Book("Harry Potter and the Philosopher’s Stone", "Joanne", "Rowling", 1997);
        books[bookCount++] = new Book("Harry Potter and the Chamber of Secrets", "Joanne", "Rowling", 1998);
        books[bookCount++] = new Book("Harry Potter and the Prisoner of Azkaban", "Joanne", "Rowling", 1999);
        books[bookCount++] = new Book("Harry Potter and the Goblet of Fire", "Joanne", "Rowling", 2000);
        books[bookCount++] = new Book("Harry Potter and the Order of the Phoenix", "Joanne", "Rowling", 2003);
        books[bookCount++] = new Book("Harry Potter and the Half-Blood Prince", "Joanne", "Rowling", 2005);
        books[bookCount++] = new Book("Harry Potter and the Deathly Hallows", "Joanne", "Rowling", 2007);

        // Add borrowers
        borrowers[borrowerCount++] = new Borrower("Harry Potter");
        borrowers[borrowerCount++] = new Borrower("Hermione Granger");
        borrowers[borrowerCount++] = new Borrower("Ron Weasley");
        borrowers[borrowerCount++] = new Borrower("Draco Malfoy");

        Borrower harry = borrowers[0]; // Harry Potter at index 0

        // Harry borrows all books
        for (int i = 0; i < bookCount; i++) {
            boolean borrowed = harry.borrowBook(books[i], transactions, transactionCount);
            if (borrowed) {
                transactionCount++;
            }
        }

        // Harry returns all books one by one
        int[] transactionCountHolder = new int[]{transactionCount};
        harry.clearBorrowedBooks(transactions, transactionCountHolder);
        transactionCount = transactionCountHolder[0];

        System.out.println("Test data loaded and Harry Potter borrowed and returned all books.");
    }
}