package com.zzh.ai;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/6/5 10:44
 */
public class DateApplicationTest {

    @Test
    public void testDateConvert() throws ParseException {
        String dateStr = "2024/6/3  10:00:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d  HH:mm:ss");
        Date beginDate = sdf.parse(dateStr);
        System.out.println("转换后的Date对象：" + beginDate);


        Calendar calendarBegin = Calendar.getInstance();
        calendarBegin.setTime(beginDate);

        Calendar limit = Calendar.getInstance();
        limit.setTime(beginDate); // 同一天
        limit.set(Calendar.HOUR_OF_DAY, 10);
        limit.set(Calendar.MINUTE, 1);
        limit.set(Calendar.SECOND, 0);
        limit.set(Calendar.MILLISECOND, 0);

        if (calendarBegin.getTime().compareTo(limit.getTime()) < 0) {
            System.out.println("加班");
        }else {
            System.out.println("不算加班");
        }



        // 如果需要格式化输出
//        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = outputFormat.format(date);
//        System.out.println("格式化后的日期字符串：" + formattedDate);
    }
}
