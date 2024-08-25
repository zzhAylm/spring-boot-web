package com.zzh.springboot3.io;

import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/8/6 21:05
 */
@Slf4j
public class BufferTest {


    @Test
    public void test() throws IOException {
        ByteBuffer byteBuf = ByteBuffer.allocate(1024);
        IntBuffer intBuf = IntBuffer.allocate(1024);
        LongBuffer longBuf = LongBuffer.allocate(1024);

        byteBuf.put("zzh".getBytes(StandardCharsets.UTF_8));

        System.out.println(new String(byteBuf.array()).trim());

        FileChannel fileChannel=FileChannel.open(Path.of("/Users/zzh/Company/projects/spring-boot-web/logs/channel/test.txt"), StandardOpenOption.APPEND);
       fileChannel.write(byteBuf);


    }

//    public static ByteBuffer wrap(byte[] array) {
//
//    }
}
