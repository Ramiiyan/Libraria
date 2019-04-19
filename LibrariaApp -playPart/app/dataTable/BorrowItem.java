package dataTable;
import io.ebean.Model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "BorrowedDetail")
public class BorrowItem extends Model {

    @Id
    @Column(name = "borrowId")
    private int borrowId;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "borrowedDate")
    private Timestamp borrowedDate;

    @Column(name ="returnDate")
    private  Timestamp returnDate;

    @Column(name = "Reader_Id" )
    private int  readerId;

    @Column(name = "settle")       // settled || notSettled
    private String settle;

    @Column(name ="overDueFee")
    private String overDue;

    public String getOverDue() {
        return overDue;
    }

    public void setOverDue(String overDue) {
        this.overDue = overDue;
    }

    public BorrowItem(){

    }
    public BorrowItem(int readerId,String isbn,Timestamp borrowedDate,String settle ){
        this.readerId =readerId;
        this.isbn = isbn;
        this.borrowedDate =borrowedDate;
        this.settle = settle;
    }

    public int getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }

    public Timestamp getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Timestamp borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSettle() {
        return settle;
    }

    public void setSettle(String settle) {
        this.settle = settle;
    }
}