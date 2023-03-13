/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author ADMIN
 */
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {
        Time currentTime = getCurrentTime();
        System.out.println(currentTime);
        System.out.println( new Time(System.currentTimeMillis()));
        String formattedTime = formatTime(currentTime);
        System.out.println(formattedTime);
    }

    public static Time getCurrentTime() {
        return new Time(System.currentTimeMillis());
    }

    public static String formatTime(Time time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormat.format(time);
    }
}




