
package com.zzh.springboot3.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class ConvertUtil {

    public static <T> T sourceToTarget(Object source, Class<T> target) {
        if (source == null) {
            return null;
        }
        T targetObject = null;
        try {
            targetObject = target.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, targetObject);
        } catch (Exception e) {
            log.error("convert error ", e);
        }
        return targetObject;
    }


    public static <T> List<T> sourceToTarget(Collection<?> sources, Class<T> target) {
        if (sources == null) {
            return null;
        }
        List<T> targets = new ArrayList<>(sources.size());
        try {
            for (Object source : sources) {
                T obj = target.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, obj);
                targets.add(obj);
            }
        } catch (Exception e) {
            log.error("convert error ", e);
        }

        return targets;
    }
}
