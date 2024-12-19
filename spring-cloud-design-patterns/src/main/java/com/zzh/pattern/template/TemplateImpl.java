
package com.zzh.pattern.template;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 18:03
 */
@Slf4j
public class TemplateImpl extends TemplateAbstract {

    @Override
    void process1() {
        log.info("processor 1");
    }

    @Override
    void process2() {
        log.info("processor 2");
    }

    public static void main(String[] args) {
        TemplateImpl template = new TemplateImpl();

    }
}
