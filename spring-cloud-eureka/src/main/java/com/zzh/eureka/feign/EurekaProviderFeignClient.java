package com.zzh.eureka.feign;

import com.zzh.eureka.api.EurekaProviderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/26 20:42
 */
@FeignClient(name = "SPRING-CLOUD-EUREKA-PROVIDER-APPLICATION")
public interface EurekaProviderFeignClient extends EurekaProviderApi {

}
