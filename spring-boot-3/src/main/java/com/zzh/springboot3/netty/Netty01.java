package com.zzh.springboot3.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/6/14 11:27
 */
public class Netty01 {


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        Future<Void> future = socketChannel.connect(new InetSocketAddress("192.168.1.116", 7397));
        System.out.println("itstack-demo-netty client start done. {关注公众号：bugstack虫洞栈 | 欢迎关注&获取源码}");
        future.get();
//        socketChannel.read(ByteBuffer.allocate(1024), null, new AioClientHandler(socketChannel, Charset.forName("GBK")));
        Thread.sleep(100000);
    }



}
