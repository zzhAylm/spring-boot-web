package com.zzh.springboot3.io;

import cn.hutool.core.io.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/1 14:24
 */
@Slf4j
public class TopKServiceTest {


    private static final String PATH = "";


    private Map<String, Integer> countMap = new HashMap<>();

    @Test
    public void readFile() throws IOException {
//        BufferedReader utf8Reader = ResourceUtil.getUtf8Reader("static/topk.txt");

        InputStream fileStream = ResourceUtil.getStream("static/topk.txt");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            log.info("line:{}", line);
            countMap.compute(line, (key, value) -> {
                if (StringUtils.isEmpty(key) || Objects.isNull(value)) {
                    return 1;
                }
                return value + 1;
            });
        }

        countMap.forEach((key,value)->{
            log.info("key is :{},value is :{}",key,value);
        });

        // 小顶堆
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            if (minHeap.size() < 3) {
                minHeap.offer(entry);
            } else if (entry.getValue() > minHeap.peek().getValue()) {
                minHeap.poll();
                minHeap.offer(entry);
            }
        }

        while (!minHeap.isEmpty()){
            log.info("minHeap pool is :{}",minHeap.poll());
        }
    }


}
