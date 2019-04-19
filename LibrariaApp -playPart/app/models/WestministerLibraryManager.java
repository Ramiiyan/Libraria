package models;

import dataTable.BorrowItem;
import dataTable.ModelReader;
import io.ebean.*;
import dataTable.ModelBook;
import dataTable.ModelDvd;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WestministerLibraryManager implements LibraryManager {
    private Timestamp borrowTime,returnTime,getBorrowDate;
    @Override
    public void addBooks(String title, String isbn, String author, String sector, String publisher,
                         Date publisherDate, int pages) {
        ModelBook book = new ModelBook(isbn, title, sector, publisher, publisherDate,
                pages, author, "Available"); //default set to Available
        Ebean.save(book);
    }

    @Override
    public void addBookAlternate(String title, String isbn, String author, String alternateAuthor, String sector,
                                 String publisher, Date publisherDate, int pages) {
        String authors = author + "," + refact(alternateAuthor);
        ModelBook book = new ModelBook(isbn, title, sector, publisher,
                publisherDate, pages, authors, "Available"); //default set to Available
        Ebean.save(book);
    }

    @Override
    public void addDvds(String title, String isbn, String producer, String actor, String sector,
                        Date publishDate, String languages, String subtitles) {

        ModelDvd dvd = new ModelDvd(isbn, title, sector, producer, actor,
                publishDate, languages, subtitles, "Available"); //default set to Available
        Ebean.save(dvd);
    }

    @Override
    public void deleteItems(String isbn) {

        if (Ebean.find(ModelBook.class).where().eq("isbn", isbn) != null) {
            Ebean.find(ModelBook.class).where().eq("isbn", isbn).delete();
        }
        Ebean.find(ModelDvd.class).where().eq("isbn", isbn).delete();
        System.out.println("Item is Deleted Successfully !");

    }

    @Override
    public void borrowBook(String isbn, int readerId) {

        Date currentDate = new Date(System.currentTimeMillis());
        models.Date mydate = new models.Date();
        String currentDateTime = currentDate + " " + mydate.currentTime();
        // FORMAT 2018-12-01 06:05:38
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            java.util.Date date = sdf1.parse(currentDateTime);
             borrowTime = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ModelBook book = Ebean.find(ModelBook.class).where().eq("isbn", isbn).findOne();
        book.setAvailable("Borrowed");
        Ebean.update(book);
        System.out.println("borrowBook updated in Book database!");

        ModelReader reader = Ebean.find(ModelReader.class).where().eq("readerId",readerId).findOne();
        reader.setReaderStatus("HaveItem");
        Ebean.update(reader);
        System.out.println("reader Updated in Reader Database!");

        System.out.println("Settle updated in borrowItem database!");
        BorrowItem borrowItem = new BorrowItem(readerId, isbn, borrowTime,"notSettled");
        Ebean.save(borrowItem);

    }

    @Override
    public void borrowDvd(String isbn, int readerId) {

        Date currentDate = new Date(System.currentTimeMillis());
        models.Date mydate = new models.Date();
        String currentDateTime = currentDate + " " + mydate.currentTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            java.util.Date date = sdf1.parse(currentDateTime);
            borrowTime = new Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        ModelDvd dvd = Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findOne();
        dvd.setBorrowed("Borrowed");
        Ebean.update(dvd);
        System.out.println("borrowDvd Updated in Dvd Database!");

        ModelReader reader = Ebean.find(ModelReader.class).where().eq("readerId",readerId).findOne();
        reader.setReaderStatus("HaveItem");
        Ebean.update(reader);
        System.out.println("reader Updated in Reader Database!");

        BorrowItem borrowItem = new BorrowItem(readerId, isbn, borrowTime,"notSettled");
        Ebean.save(borrowItem);

    }
    @Override
    public String returnItems(String isbn, int readerId) {
        try {
             getBorrowDate = Ebean.find(BorrowItem.class).where().eq("isbn", isbn).and()
                    .eq("Reader_Id", readerId).and()
                    .eq("settle", "notSettled").findOne().getBorrowedDate();
            System.out.println("Reader's BorrowDate " + getBorrowDate);
        }catch (NullPointerException e){
            e.printStackTrace();
            System.out.println("There is an Null value in database.");
        }


        Date currentDate = new Date(System.currentTimeMillis());
        models.Date mydate = new models.Date();
        String currentDateTime = currentDate + " " + mydate.currentTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            java.util.Date date = sdf1.parse(currentDateTime);
            returnTime  = new Timestamp(date.getTime());
            System.out.println("Return Time : "+returnTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        BorrowItem returnUpdate = Ebean.find(BorrowItem.class).where().eq("isbn", isbn).and()
                .eq("Reader_Id", readerId).and()
                .eq("settle", "notSettled").findOne();
        returnUpdate.setReturnDate(returnTime);
        returnUpdate.setSettle("settled");
        Ebean.update(returnUpdate);

        if (Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findCount() > 0) {
            ModelDvd dvd = Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findOne();
            dvd.setBorrowed("Available");
            Ebean.update(dvd);
            System.out.println("Returned Updated in Dvd Database!");
        } else {
            ModelBook book = Ebean.find(ModelBook.class).where().eq("isbn", isbn).findOne();
            book.setAvailable("Available");
            System.out.println("Returned Updated in Book Database!");
            Ebean.update(book);
        }

        ModelReader reader = Ebean.find(ModelReader.class).where().eq("readerId", readerId).findOne();
        reader.setReaderStatus("Good"); //when reader return book he always good
        Ebean.update(reader);

        long difDays = daysBetween(returnTime, getBorrowDate);
        long difHours = difDays * 24;
        System.out.println("days difference :" + difDays);
        System.out.println("time difference :" + difHours);
        int allocateHours = 168;  // 7 days =>7X24 =168
        BorrowItem overDue = Ebean.find(BorrowItem.class).where().eq("isbn",isbn).and()
                .eq("Reader_Id",readerId).and().eq("settle","settled").findOne();
        if (difHours <= allocateHours) {
            overDue.setOverDue("0");
            Ebean.update(overDue);
            return "NOFEE";

        } else if (difHours <= 240) {
            double dueFeeHours = difHours - allocateHours;
            double rentFee = dueFeeHours * 0.2;
            overDue.setOverDue(""+rentFee+"");
            Ebean.update(overDue);
            return "Allocate days : 7 \nReader's Extra Hours : " + dueFeeHours + "\nReader have to pay : " + rentFee;
        } else {
            double dueFeeHours = difHours - allocateHours;
            double rentFee = dueFeeHours * 0.5;
            overDue.setOverDue(""+rentFee+"");
            Ebean.update(overDue);
            return "Allocate days : 7 \nReader's Extra Hours : " + dueFeeHours + "\nReader have to pay : " + rentFee;

        }
    }

    @Override
    public void createReader(int readerId, String name, String email, String mobile, String readerStatus) {
        ModelReader reader = new ModelReader(readerId, name, email, mobile, readerStatus);
        Ebean.save(reader);
    }

    public String refact(String attributes) {
        attributes = attributes.substring(1, attributes.length() - 1).replace('"', ' ');
        return attributes;
    }

    public long daysBetween(Timestamp one, Timestamp two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }
}
