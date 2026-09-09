package com.zzh.springboot3;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/6/5 10:44
 */
public class DateApplicationTest {

    @Test
    public void testDateConvert() throws ParseException {
        String dateStr = "2024/6/3  09:27:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d  HH:mm:ss");
        Date date = sdf.parse(dateStr);
        System.out.println("转换后的Date对象：" + date);

        // 如果需要格式化输出
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = outputFormat.format(date);
        System.out.println("格式化后的日期字符串：" + formattedDate);


        // 获取当前时间
        Date now = new Date();
        System.out.println("当前时间：" + outputFormat.format(now));

        // 比较日期先后
        System.out.println("date 是否在当前时间之前: " + date.before(now));
        System.out.println("date 是否在当前时间之后: " + date.after(now));

        // 比较时间戳
        long timestamp1 = date.getTime();
        long timestamp2 = now.getTime();
        System.out.println("两个日期相差的毫秒数：" + Math.abs(timestamp2 - timestamp1));

        // 计算相差的天数
        long daysDiff = Math.abs(timestamp2 - timestamp1) / (1000 * 60 * 60 * 24);
        System.out.println("两个日期相差的天数：" + daysDiff);

        // 克隆日期对象
        Date clonedDate = (Date) date.clone();
        System.out.println("克隆的日期对象：" + outputFormat.format(clonedDate));

        // 判断日期是否相等
        System.out.println("原始日期和克隆日期是否相等：" + date.equals(clonedDate));


    }
}
