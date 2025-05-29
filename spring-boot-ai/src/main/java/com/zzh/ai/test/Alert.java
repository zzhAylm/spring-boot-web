package com.zzh.ai.test;

import java.util.ServiceLoader;

/**
 * @Description:
 * @Author: zzh
 * @Create 2025/5/26 09:56
 */
public class Alert {

    public static void main(String[] args) {
        System.out.println("zzh");

        ServiceLoader<MyService> load = ServiceLoader.load(MyService.class);

        for (MyService myService : load) {
            myService.say();
        }


//        Class<T> clazz = (Class<T>) obj.getClass();
//        RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
//        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
//        try {
//            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
//        } finally {
//            buffer.clear();
//        }
//        ProtostuffIOUtil.toByteArray("")


    }


}
