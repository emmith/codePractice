package time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class TimeTest {


    public static void main(String[] args) {
        System.out.println(LocalDate.now());
        System.out.println(LocalTime.now());
        System.out.println(LocalDateTime.now());

        String time1 = "2022-06-16T16:27:02.830";
        LocalDateTime ldt = LocalDateTime.parse(time1, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        System.out.println(time1);

        String time2 = "2022-06-16T16:27:02.830Z";
        ZonedDateTime zdt = ZonedDateTime.parse(time2);
        System.out.println(zdt);
    }
}
