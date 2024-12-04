package com.zzh.springboot3.cache;

import com.zzh.springboot3.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 20:51
 */
@Slf4j
@Service
public class DoubleCacheService {

    @DoubleCache(key = "#id")
    public ResponseDto<String> doubleCache(String id) {
        log.info("id is :{},cache get", id);
        return ResponseDto.success("zzh");
    }

    @DoubleCacheEvict(key = "#id")
    public void doubleCacheEvict(String id) {
        log.info("id is :{},cache evict", id);
    }

    @DoubleCachePut(key = "#p0")
    public ResponseDto<String> doubleCachePut(String id) {
        log.info("id is :{},cache put", id);
        return ResponseDto.success("zzh");
    }



}
