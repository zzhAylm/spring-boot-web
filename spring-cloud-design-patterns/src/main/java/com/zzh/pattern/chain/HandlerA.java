package com.zzh.pattern.chain;

import com.zzh.springboot3.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/23 20:40
 */
@Slf4j
@Component
public class HandlerA implements AbstractHandler<RequestParam>{

    public void handler(RequestParam requestParam){
        log.info("HandlerA ,request param is :{}", JsonUtil.toJson(requestParam));
    }

}
