package com.zzh.pattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 15:21
 */
@Slf4j
public class DecoratorImplA extends Decorator {


    public DecoratorImplA(Component component) {
        super(component);
    }

    @Override
    public void cost() {
        log.info("DecoratorImplA before");
        super.cost();
        log.info("DecoratorImplA after");
    }
}
