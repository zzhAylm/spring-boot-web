package com.zzh.pattern.template;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 18:03
 */
public abstract class TemplateAbstract implements Template {

    public void template() {
        process1();
        process2();
    }

    abstract void process1();

    abstract void process2();

}
