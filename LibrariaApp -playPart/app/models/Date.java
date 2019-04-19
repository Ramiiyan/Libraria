aaaaadpackage models;

public class Date {
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;

    public Date() {
    }

    public Date(int year, int month, int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        } else {
            System.err.print("Invalid Borrowed Date!");
        }
        if (month >= 1 && month <= 12) {
            this.month = month;
        } else {
            System.err.print("Invalid Borrowed month!");
        }
        if (year >= 1940 && year <= 2018) {
            this.year = year;
        } else {
            System.err.print("Invalid Borrowed year!");
        }
    }

    public Date(String jsonDate) {
        String year = jsonDate.substring(0, 4);
        String month = jsonDate.substring(5, 7);
        String day = jsonDate.substring(8);
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.day = Integer.parseInt(day);
    }

    public Date(int day, int month, int year, int hour, int minute) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        } else {
            System.err.print("Invalid Borrowed Date!");
        }
        if (month >= 1 && month <= 12) {
            this.month = month;
        } else {
            System.err.print("Invalid Borrowed month!");
        }
        if (year >= 1940 && year <= 2018) {
            this.year = year;
        } else {
            System.err.print("Invalid Borrowed year!");
        }
        if (hour >= 0 && hour <= 24) {
            this.hour = hour;
        } else {
            System.err.print("Invalid Borrowed Time!");
        }
        if (minute >= 0 && minute <= 59) {
            this.minute = minute;
        } else {
            System.err.print("Invalid Borrowed Time!");
        }
    }

    public String getDate() {
        String print;
        if ((this.getDay() >= 1) && (this.getDay() <= 9)) {
            print = this.getYear() + "-" + this.getMonth() + "-" + "0" + this.getDay();
            if ((this.getMonth() >= 1) && (this.getMonth() <= 9))
                print = this.getYear() + "-0" + this.getMonth() + "-" + "0" + this.getDay();
        } else if ((this.getMonth() >= 1) && (this.getMonth() <= 9)) {
            print = this.getYear() + "-0" + this.getMonth() + "-" + this.getDay();
        } else {
            print = this.getYear() + "-" + this.getMonth() + "-" + this.getDay();
        }
        return print;
    }

    public String dateFormat() {
        String print;

        print = this.getYear() + "-" + this.getMonth() + "-" + this.getDay();

        return print;
    }

    public String currentTime() {
        long timeInMillis = System.currentTimeMillis();
        long second = (timeInMillis /1000) % 60;
        long minute = (timeInMillis / (1000 * 60)) % 60;
        long hour = (timeInMillis / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute,second);
    }


    public String getTime() {
        return getHour() + ":" + getMinute();
    }

    @Override
    public String toString() {
        return getDate();
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}