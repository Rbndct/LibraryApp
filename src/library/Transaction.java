package library;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("squid:S106")
public class Transaction {
    private static int idCounter = 1;
    private final int transactionId;
    private final int bookId;
    private final String bookName;
    private final int borrowerId;
    private final LocalDate date;
    private final LocalTime time;
    private final String transactionType;

    public Transaction(int borrowerId, int bookId, String bookName, String transactionType) {
        this.transactionId = idCounter++;
        this.borrowerId = borrowerId;
        this.bookId = bookId;
        this.bookName = bookName;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
        this.transactionType = transactionType;
    }

    public int getTransactionId() { return transactionId; }
    public int getBookId() { return bookId; }
    public String getBookName() { return bookName; }
    public int getBorrowerId() { return borrowerId; }
    public LocalDate getDate() { return date; }

    public String getTimeFormatted() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public String getTransactionType() { return transactionType; }

    public void displayTransaction() {
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        // Added borrowerId column to output
        System.out.printf("%-5d %-10d %-50s %-10d %-15s %-15s %-15s%n",
            transactionId, bookId, bookName, borrowerId, date, formattedTime, transactionType);
    }
}
