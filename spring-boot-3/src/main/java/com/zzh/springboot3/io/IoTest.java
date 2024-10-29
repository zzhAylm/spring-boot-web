package com.zzh.springboot3.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/16 15:42
 */
@Slf4j
public class IoTest {


    @Test
    public void fileIoTest() throws IOException {
        BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(""));
        byte[] bytes = bufferedInputStream.readAllBytes();
    }




}
