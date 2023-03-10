package util;

/**
 *
 * @author ADMIN
 */
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static void main(String[] args) {
        Date date = Date.valueOf("2023-02-20");
        List<Date> sameWeekDays = ChatGPT.getDaysInSameWeekOfMonth(date);
        System.out.println("Days in the same week as " + date.toLocalDate().getDayOfMonth() + " " + date.toLocalDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + ": " + sameWeekDays);
    }
}
