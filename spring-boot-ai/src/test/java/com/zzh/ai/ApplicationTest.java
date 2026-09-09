package com.zzh.ai;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/28 15:39
 */
public class ApplicationTest {

    @Test
    public void test() {
        assertNotNull("zzh");
        assertEquals(1, 2);
    }


    @Test
    public void date() {
        String date = "2024/06/03 09:27:00";
        String date2 = "2024/06/03 09:27:00";
//        LocalDateTime time1 = LocalDateTime.of(2025, 6, 4, 10, 0);
//        LocalDateTime time2 = LocalDateTime.of(2025, 6, 4, 10, 1);

        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
        LocalDateTime localDateTime2 = LocalDateTime.parse(date2, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

//        boolean isBefore = time1.isBefore(time2); // true
//        boolean isAfter = time1.isAfter(time2);   // false
//        boolean isEqual = time1.isEqual(time2);   // false

        System.out.println(localDateTime.compareTo(localDateTime2));
    }
}
