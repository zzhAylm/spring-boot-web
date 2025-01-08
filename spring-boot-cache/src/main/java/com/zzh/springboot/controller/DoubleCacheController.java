package com.zzh.springboot.controller;

import com.zzh.springboot.cache.DoubleCacheService;
import com.zzh.springboot3.common.dto.ResponseDto;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/11/5 21:03
 */
@Slf4j
@RestController
@RequestMapping("/double/cache")
public class DoubleCacheController {

    @Resource
    private DoubleCacheService doubleCacheService;


    @GetMapping("")
    public ResponseDto<String> doubleCacheGet(String id) {
        return doubleCacheService.doubleCache(id);
    }

    @PostMapping("")
    public ResponseDto<String> doubleCachePost(String id) {
        return doubleCacheService.doubleCachePut(id);
    }

    @DeleteMapping("")
    public void doubleCacheDelete(String id) {
        doubleCacheService.doubleCacheEvict(id);
    }

    @PutMapping("")
    public void doubleCachePut(String id) {
        doubleCacheService.doubleCacheEvict(id);
    }


}
