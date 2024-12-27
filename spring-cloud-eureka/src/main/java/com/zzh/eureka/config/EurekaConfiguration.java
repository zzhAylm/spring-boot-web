package com.zzh.eureka.config;

import feign.Contract;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/27 11:02
 */
@Configuration
public class EurekaConfiguration {

    @Resource
    private FeignClientProperties feignClientProperties;

    @Autowired(required = false)
    private List<AnnotatedParameterProcessor> parameterProcessors = new ArrayList<>();

    @Resource
    private ConversionService feignConversionService;

    @Bean
    @ConditionalOnMissingBean
    public Contract feignContract() {
        boolean decodeSlash = feignClientProperties == null || feignClientProperties.isDecodeSlash();
        return new CustomSpringMvcContract(parameterProcessors, feignConversionService, decodeSlash);
    }
}
