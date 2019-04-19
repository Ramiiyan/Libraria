package controllers;

import com.fasterxml.jackson.databind.JsonNode;


import dataTable.*;
import io.ebean.DataIntegrityException;
import io.ebean.Ebean;
import models.LibraryManager;
import models.WestministerLibraryManager;
import play.libs.Json;
import play.mvc.*;

import java.sql.Date;
import java.util.Iterator;


public class HomeController extends Controller {
    private String title, publisher, sector, author, date, producer, actor, isbn, isFound;
    private int pages, bookCount, dvdCount, borrowerId;
    private Date publishDate;
    private Iterator<JsonNode> languages, subtitles, alternateAuthor;
    private static final int MAX_BOOK = 100;
    private static final int MAX_DVD = 50;

    LibraryManager libraryManager = new WestministerLibraryManager();

    public Result index() throws ClassNotFoundException {
        return ok("success");
    }

    public Result addNewBooks() {
        bookCount = Ebean.find(ModelBook.class).findCount();
        System.out.println("no of books before add :" + bookCount);

        if (bookCount > MAX_BOOK) {
            System.out.println("Slots are Filled. No more Space to add Books.");
            return ok("Slots are filled ! \nNo more space to add books.");
        } else {
            JsonNode addBook = request().body().asJson();
            System.out.println(addBook);
            isbn = addBook.findPath("isbn").textValue();
            System.out.println(isbn);
            title = addBook.findPath("title").textValue();
            publisher = addBook.findPath("publisher").textValue();
            date = addBook.findPath("publishDate").textValue();
            models.Date mydate = new models.Date(date);
            publishDate = Date.valueOf(mydate.toString());
            pages = addBook.findPath("pages").asInt();
            sector = addBook.findPath("sector").textValue();
            author = addBook.findPath("author").textValue();
            alternateAuthor = addBook.findValues("alternateAuthors").iterator();
            boolean haveOtherAuthors = addBook.findValue("alternateAuthors").iterator().hasNext();
            try {
                if (haveOtherAuthors) {
                    String otherAuthors = alternateAuthor.next().toString();
                    System.out.println("Other Authors : " + otherAuthors);
                    System.out.println("Title : " + title + "\nISBN : " + isbn + "\nAuthor : " + author + " " +
                            otherAuthors + "\nSector : " + sector + "\nPublisher : " + publisher +
                            "\nPublish Date : " + publishDate + "\nPages : " + pages);
                    libraryManager.addBookAlternate(title, isbn, author, otherAuthors, sector, publisher, publishDate, pages);
                } else {
                    alternateAuthor = null;
                    System.out.println("Other Authors  NULL");
                    System.out.println("Title : " + title + "\nISBN : " + isbn + "\nAuthor : " + author +
                            "\nSector : " + sector + "\nPublisher : " + publisher + "\nPublish Date : " +
                            publishDate + "\nPages : " + pages);
                    libraryManager.addBooks(title, isbn, author, sector, publisher, publishDate, pages);
                }
            } catch (DataIntegrityException e) {
                System.out.println("ISBN is already Taken.\n" + e);
            }
            if (Ebean.find(ModelBook.class).findCount() == MAX_BOOK) {
                System.out.println("This is the Last Slot ! \nNo more Space for books.");
            }
            int totalBooks = Ebean.find(ModelBook.class).findCount();
            return ok("New Book SuccessFully Added.\n Total Books in library : " +
                    totalBooks + "\nRemaining Slots : " + (MAX_BOOK - totalBooks));
        }

    }

    public Result addNewDvds() {

        dvdCount = Ebean.find(ModelDvd.class).findCount();
        System.out.println("no of dvds before add :" + dvdCount);

        if (dvdCount > MAX_DVD) {
            System.out.println("Slots are Filled. No more Space to add Dvds.");
            return ok("Slots are filled ! \nNo more space to add Dvds.");

        } else {
            JsonNode newDvd = request().body().asJson();
            System.out.println(newDvd);
            isbn = newDvd.findPath("isbn").textValue();
            title = newDvd.findPath("title").textValue();
            producer = newDvd.findPath("producer").textValue();
            date = newDvd.findPath("publishDate").textValue();
            models.Date mydate = new models.Date(date);
            publishDate = Date.valueOf(mydate.toString());
            pages = newDvd.findPath("pages").asInt();
            sector = newDvd.findPath("sector").textValue();
            actor = newDvd.findPath("actors").textValue();

            languages = newDvd.findValues("selectedlanguage").iterator();
            subtitles = newDvd.findValues("selectedSubtitle").iterator();
            String lang = languages.next().toString();
            String sub = subtitles.next().toString();

            System.out.println("Title : " + title + "\nISBN : " + isbn + "\nProducer : " + producer + "\nActor : " +
                    actor + "\nSector : " + sector + "\nPublish Date: " + publishDate + "\nSelected Languages : " +
                    lang + "\nSelected Subtitles : " + sub);
            try {
                libraryManager.addDvds(title, isbn, producer, actor, sector, publishDate, lang, sub);
                System.out.println("New Dvd is Added Successfully !");
            } catch (DataIntegrityException e) {
                System.out.println("ISBN is already Taken.\n" + e);
            }
            if (Ebean.find(ModelDvd.class).findCount() == MAX_DVD) {
                System.out.println("This is the Last Slot ! \nNo more Space for Dvds.");
            }
            int totalDvds = Ebean.find(ModelDvd.class).findCount();
            return ok("New Book SuccessFully Added.\n Total Books in library : " +
                    totalDvds + "\nRemaining Slots : " + (MAX_DVD - totalDvds));
        }
    }

    public Result deleteItems(String isbn) {

        if (Ebean.find(ModelBook.class).where().eq("isbn", isbn).findCount() > 0) {
            libraryManager.deleteItems(isbn);
            return ok(Json.toJson(" Book successfully Destroyed"));
        } else if (Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findCount() > 0) {
            libraryManager.deleteItems(isbn);
            return ok(Json.toJson(" Dvd successfully Destroyed"));
        } else {
            return ok(Json.toJson(" cannot find library Item."));
        }

    }

    public Result borrowItem(String itemIsbn, int currentReaderId) {

        if ((Ebean.find(ModelBook.class).where().eq("isbn", itemIsbn).findCount() <= 0) &&
                (Ebean.find(ModelDvd.class).where().eq("isbn", itemIsbn).findCount() <= 0)) {

            isFound = "NoItem";
            System.out.println("There is no Item in database.");
            return ok(Json.toJson(isFound));

        } else if (Ebean.find(ModelBook.class).where().eq("isbn", itemIsbn).and()
                .eq("available", "Available").findCount() > 0) {

            isFound = "Available";
            libraryManager.borrowBook(itemIsbn, currentReaderId);
            return ok(Json.toJson(isFound));

        } else if (Ebean.find(ModelDvd.class).where().eq("isbn", itemIsbn).and()
                .eq("Borrowed", "Available").findCount() > 0) {

            isFound = "Available";
            libraryManager.borrowDvd(itemIsbn, currentReaderId);
            return ok(Json.toJson(isFound));

        } else if (Ebean.find(ModelBook.class).where().eq("isbn", itemIsbn).and()
                .eq("available", "Borrowed").findCount() > 0) {

            isFound = "NotAvailable";
            System.out.println("Book is already borrowed.");
            return ok(Json.toJson(isFound));

        } else if (Ebean.find(ModelDvd.class).where().eq("isbn", itemIsbn).and()
                .eq("Borrowed", "Borrowed").findCount() > 0) {

            isFound = "NotAvailable";
            System.out.println("Dvd is already borrowed.");
            return ok(Json.toJson(isFound));

        } else {
            isFound = "Reserved";
            System.out.println("Item is already Reserved.");
            return ok(Json.toJson(isFound));
        }
    }

    public Result reserveItem(String isbn, int readerId) {
        if (Ebean.find(ModelBook.class).where().eq("isbn", isbn).and()
                .eq("reservation", 0).findCount() > 0) {

            ModelBook reserveBook = Ebean.find(ModelBook.class).where().eq("isbn", isbn).findOne();
            reserveBook.setReserve(readerId);  //who is currently Reserved this book.
            System.out.println("this book reserved by " + readerId);
            Ebean.update(reserveBook);
            return ok(Json.toJson("You Reserve this Book : " + isbn));

        } else if (Ebean.find(ModelDvd.class).where().eq("isbn", isbn).and()
                .eq("reservation", 0).findCount() > 0) {

            ModelDvd reserveDvd = Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findOne();
            reserveDvd.setReserve(readerId);  //who is currently Reserved this Dvd.
            System.out.println("this Dvd reserved by " + readerId);
            Ebean.update(reserveDvd);
            return ok(Json.toJson("You Reserve this Dvd : " + isbn));

        } else {
            return ok(Json.toJson("Sorry, This Item already Reserved by OtherUser."));
        }
    }

    public Result returnItem(String isbn, int readerId) {
        String validate = check(isbn, readerId);
        if (validate.equals("ValidEntry")) {
            String feeDetail = libraryManager.returnItems(isbn, readerId);
            return ok(Json.toJson(feeDetail));
        } else {
            return ok(Json.toJson(validate));
        }
    }

    public String check(String isbn, int readerId) {
        if (Ebean.find(ModelBook.class).where().eq("isbn", isbn).findCount() <= 0) {
            if (Ebean.find(ModelDvd.class).where().eq("isbn", isbn).findCount() <= 0) {
                return "Invalid ISBN";
            } else {
                return "ValidEntry";
            }
        }
        if (Ebean.find(ModelReader.class).where().eq("readerId", readerId).findCount() <= 0) {
            return "Invalid ReaderId";
        }

        return "ValidEntry";
    }

    public Result about(String book) {

        return ok("About " + book);
    }

    public Result bookCount() {
        int bookcount = Ebean.find(ModelBook.class).findCount();
        return ok(Json.toJson(bookcount));
    }

    public Result dvdCount() {
        int dvdcount = Ebean.find(ModelDvd.class).findCount();
        return ok(Json.toJson(dvdcount));
    }

    public Result validateReader(int readerId) {
        String readerStatus;
        if (Ebean.find(ModelReader.class).where().eq("readerId", readerId).findCount() <= 0) {
            readerStatus = "NOTREADER";  //not a reader. suggest to create new Reader.
            return ok(Json.toJson(readerStatus));
        } else {
            readerStatus = Ebean.find(ModelReader.class, readerId).getReaderStatus(); //  Good || Bad || HaveItem
            System.out.println("Reader Status : " + readerStatus);
            return ok(Json.toJson(readerStatus));

        }
    }

    boolean getReaderIdAgain = true;

    public Result createNewReader(String name, String email, String mobile) {
        int readerNewId = 0;
        while (getReaderIdAgain) {
            readerNewId = (int) (Math.random() * 9999 + 1);  // to generate readerId
            if (Ebean.find(ModelReader.class).where().eq("readerId", readerNewId).findCount() <= 0) {
                libraryManager.createReader(readerNewId, name, email, mobile, "Good"); //Born child always Good.
                getReaderIdAgain = false;
            } else {
                System.out.println("getting ID Again");
                getReaderIdAgain = true;
            }
        }
        System.out.println("Name : " + name + "\nEmail : " + email + "\nMobile : " + mobile + "\nReaderId : " + readerNewId);
        return ok(Json.toJson(readerNewId));
    }


    public Result displayAllBook() {

        return ok(Json.toJson(showAllBook()));
    }

    public Result displayAllDvd() {
        return ok(Json.toJson(showAllDvd()));
    }

    public Result displayBorrow() {
        return ok(Json.toJson(showBorrow()));
    }

    public ModelBook[] showAllBook() {
        int totalBooks = Ebean.find(ModelBook.class).findCount();
        ModelBook[] books = new ModelBook[totalBooks];
        return Ebean.find(ModelBook.class).findList().toArray(books);
    }

    public ModelDvd[] showAllDvd() {
        int totalDvds = Ebean.find(ModelDvd.class).findCount();
        ModelDvd[] dvds = new ModelDvd[totalDvds];

        return Ebean.find(ModelDvd.class).findList().toArray(dvds);
    }

    public BorrowItem[] showBorrow() {
        int totalborrow = Ebean.find(BorrowItem.class).findCount();
        BorrowItem[] borrow = new BorrowItem[totalborrow];
        return Ebean.find(BorrowItem.class).findList().toArray(borrow);
    }


}
