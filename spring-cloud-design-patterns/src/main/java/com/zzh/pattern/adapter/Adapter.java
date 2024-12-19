package com.zzh.pattern.adapter;

/**
 * @Description: 适配器模式, 会改变被代理类的接口，装饰者模式不会修改，会有共同的接口
 * <p>
 * Spring 中使用实例：
 * 1. HandlerAdapter, DispatcherServlet , HandlerMapping ,Handler
 * 2. WebMvcConfigurerAdapter
 * @Author: zzh
 * @Crete 2024/12/19 16:16
 */
public interface Adapter {

    void adapter();

}
