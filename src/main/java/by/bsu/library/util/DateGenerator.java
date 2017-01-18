package by.bsu.library.util;

import java.util.Date;
import java.util.Calendar;

public class DateGenerator {

    public static java.sql.Date dateOfReturn () {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date date = cal.getTime();
        return new java.sql.Date(date.getTime());
    }

}
