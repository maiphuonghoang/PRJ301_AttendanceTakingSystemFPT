package util;

/**
 *
 * @author ADMIN
 */
import java.sql.Date;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ChatGPT {

    public static List<Date> getDaysInSameWeekOfMonth(Date date) {
        LocalDate localDate = date.toLocalDate();
        DayOfWeek dow = localDate.getDayOfWeek();
        LocalDate startOfWeek = localDate.minusDays(dow.getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);
        List<Date> daysInSameWeek = new ArrayList<>();
        for (LocalDate d = startOfWeek; !d.isAfter(endOfWeek); d = d.plusDays(1)) {
            daysInSameWeek.add(Date.valueOf(d));
        }
        return daysInSameWeek;
    }
    public static String getToday(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedToday = today.format(formatter);
        return formattedToday;
    }
    public static Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime = formatter.format(calendar.getTime());
        java.util.Date utilDate = null;
        try {
            utilDate = formatter.parse(formattedDateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long time = utilDate.getTime();
        return new java.sql.Date(time);
    }

    public static void main(String[] args) {
        java.sql.Date currentDateTime = getCurrentDateTime();
        System.out.println(currentDateTime.toString());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDateTime = formatter.format(currentDateTime);
        System.out.println(formattedDateTime);
        Date date = Date.valueOf("2023-02-20");
        List<Date> sameWeekDays = ChatGPT.getDaysInSameWeekOfMonth(date);
        System.out.println("Days in the same week as " + date.toLocalDate().getDayOfMonth() + " " + date.toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ": " + sameWeekDays);

    }
}
