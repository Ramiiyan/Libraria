package dataTable;

import io.ebean.Model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Reader")
public class ModelReader extends Model {

    @Id
    @Column(name = "readerId")
    private int readerId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobileNo")
    private String mobileNo;


    @Column(name="email",unique = true)
    private String email;

    @Column(name = "readerStatus")
    private String readerStatus;   //Good  or Bad or  HaveItem   Default = Good

    public ModelReader(){}

    public ModelReader(int readerId ,String name ,String email ,String mobileNo,String readerStatus ){
        this.readerId =readerId;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.readerStatus = readerStatus;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReaderStatus() {
        return readerStatus;
    }

    public void setReaderStatus(String readerStatus) {
        this.readerStatus = readerStatus;
    }
}