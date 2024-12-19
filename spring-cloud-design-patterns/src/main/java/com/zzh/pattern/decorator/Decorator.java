package com.zzh.pattern.decorator;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/19 15:21
 */
// 装饰者： 当被装饰者需要动态修改的时候，符合开闭原则 使用者和具体的组件节藕
@Slf4j
public abstract class Decorator implements Component {

    private final Component component;

    protected Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void cost() {
        log.info("Decorator before");
        component.cost();
        log.info("Decorator after");

    }
}
