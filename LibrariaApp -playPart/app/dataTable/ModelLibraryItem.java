package dataTable;

import io.ebean.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ModelLibraryItem {

    @Id
    @Column(name = "Item_id")
    private int itemId;

    @Column(name = "isbn",unique=true)
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "sector")
    private String sector;

    @Column(name ="reservation")
    private int reserve;            //who is currently reserve this item.

    public ModelLibraryItem(){}

    public ModelLibraryItem( String isbn, String title, String sector) {
        this.isbn = isbn;
        this.title = title;
        this.sector = sector;
    }

    public int getId() {
        return itemId;
    }

    public void setId(int itemId) {
        this.itemId = itemId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getReserve() {
        return reserve;
    }

    public void setReserve(int reserve) {
        this.reserve = reserve;
    }
}
