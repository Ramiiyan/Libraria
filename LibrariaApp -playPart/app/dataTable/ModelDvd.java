package dataTable;

import java.sql.Date;
import io.ebean.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LibrariaDvd")
public class ModelDvd extends ModelLibraryItem{

    @Column(name="producer")
    private String producer;

    @Column(name="actor")
    private String actor;

    @Column(name="publishDate")
    private Date publishDate;

    @Column(name="languages")
    private String languages;

    @Column(name="subtitles")
    private String subtitles;

    @Column(name ="Borrowed")
    private String borrowed;   //Borrowed or not  Borrowed || Available || Reserved by readerID


    public ModelDvd(){}
    public ModelDvd(String isbn, String title, String sector,String producer, String actor,
                    Date publishDate,String languages ,String subtitles ,String borrowed) {
        super(isbn, title, sector);
        this.producer =producer;
        this.actor =actor;
        this.publishDate =publishDate;
        this.languages = languages;
        this.subtitles =subtitles;
        this.borrowed = borrowed;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }

    public String getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(String borrowed) {
        this.borrowed = borrowed;
    }
}
