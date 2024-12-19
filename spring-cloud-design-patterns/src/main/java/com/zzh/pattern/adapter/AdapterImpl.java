
package com.zzh.pattern.adapter;

/**
 * @Description: 适配器模式
 * @Author: zzh
 * @Crete 2024/12/19 16:16
 */
public class AdapterImpl implements Adapter {

    private final Target target;

    public AdapterImpl(Target target) {
        this.target = target;
    }

    @Override
    public void adapter() {
        target.request();
    }
}
