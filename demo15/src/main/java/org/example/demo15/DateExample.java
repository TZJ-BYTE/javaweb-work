package org.example.demo15;

import java.util.Date;
import java.util.Calendar;

public class DateExample {
    public static void main(String[] args) {
        // 获取当前时间的Date实例
        Date date = new Date();

        // 将Date转换为Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 获取年、月、日、小时和分钟
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH 从0开始，所以要加1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24小时制
        int minutes = calendar.get(Calendar.MINUTE);

        // 输出获取的时间信息
        System.out.println("Date 时间信息：");
        System.out.println("年: " + year);
        System.out.println("月: " + month);
        System.out.println("日: " + day);
        System.out.println("小时: " + hour);
        System.out.println("分钟: " + minutes);
    }
}
