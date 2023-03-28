package utilities.date;

import utilities.file.TestDataManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatHelper {
    private static final TestDataManager DATA = new TestDataManager();
    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATA.getTestData("/dateFormatPattern"));
        return dateFormat.format(new Date());
    }
}
