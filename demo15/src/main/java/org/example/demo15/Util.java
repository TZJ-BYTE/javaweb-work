package org.example.demo15;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(new Date());
    }
}
