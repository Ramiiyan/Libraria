package models;

import java.sql.Date;

import io.ebean.*;
import dataTable.*;

public interface LibraryManager {

    void addBooks(String title, String isbn, String author, String sector, String publisher, Date publisherDate, int pages);

    void addBookAlternate(String title, String isbn, String author, String alternateAuthor, String sector,
                          String publisher, Date publisherDate, int pages);

    void addDvds(String title, String isbn, String producer, String actor, String sector, Date published_date,
                 String languages, String subtitles);

    void deleteItems(String isbn);

    void borrowBook(String isbn, int readerId);

    void borrowDvd(String isbn, int readerId);

    String returnItems(String isbn, int readerId);

    void createReader(int readerId, String name, String email, String mobile, String readerStatus);

}
