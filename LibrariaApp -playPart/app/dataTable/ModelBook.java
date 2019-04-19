package dataTable;


import io.ebean.Finder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import io.ebean.*;
@Entity
@Table(name = "LibrariaBook")
public class ModelBook extends ModelLibraryItem {

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "published_date")
    private Date publishDate;

    @Column(name = "pages")
    private int pages;

    @Column(name = "author")
    private String author;

    @Column(name ="available")
    private String available;  //borrowed or not      Available || Borrowed || Reserved by readerID



    public static Finder<String,ModelBook> findItem = new Finder<>(ModelBook.class);
    public ModelBook(){ }
    public ModelBook( String isbn, String title, String sector,String publisher,
                      Date publishDate ,int pages ,String author,String available) {
        super(isbn, title, sector);
        this.publisher =publisher;
        this.publishDate =publishDate;
        this.pages =pages;
        this.author =author;
        this.available =available;
    }


    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }


}
