package com.zzh.mvc.service;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzh.springboot3.common.utils.ConvertUtil;
import com.zzh.springboot3.common.utils.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/12/20 15:51
 */
@Slf4j
@Service
public class JsonServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void json() throws JsonProcessingException {
        String json = "{\"user_name\":\"zzh\",\"user_id\":\"1\",\"dept_id\":\"1\"}";
        User user = objectMapper.readValue(json, User.class);
        log.info("user is :{}", JSONUtil.toJsonStr(user));

        String newJson = objectMapper.writeValueAsString(user);
        log.info("new json user is :{}", newJson);
    }


    @Test
    public void jsonUtilTest() {
        String json = "{\"user_name\":\"zzh\",\"user_id\":\"1\",\"dept_id\":\"1\"}";
        User user = JsonUtil.toBean(json, User.class);
        log.info("json util user is :{}", JsonUtil.toJson(user));

        List<User> users = JsonUtil.toList("[{\"user_name\":\"zzh\",\"user_id\":\"1\",\"dept_id\":\"1\"},{\"user_name\":\"zzh\",\"user_id\":\"1\",\"dept_id\":\"1\"}]");

        log.info("json util users is :{}", JsonUtil.toJson(users));


        Map<String, Object> objectMap = JsonUtil.toMap("{\"user_name\":\"zzh\",\"user_id\":\"1\",\"dept_id\":\"1\"}");

        log.info("json util user map is :{}", JsonUtil.toJson(objectMap));

    }

    @Test
    public void convertUtilTest() {
        UserDto userDto = new UserDto();
        userDto.setUserName("zzh");
        userDto.setUserId("1");
        userDto.setDeptId("1");
        User user = ConvertUtil.sourceToTarget(userDto, User.class);
        log.info("convert user is :{}", JsonUtil.toJson(user));
    }

    @Data
    public static class UserDto {
        private String userName;
        private String userId;
        private String deptId;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    // json中如果没有与之对应的的字段，就会忽律;; 不加，如果发现json中有字段无法和类中的字段对应，就会报错
    public static class User implements Serializable {
        @JsonProperty("user_name")
        private String userName;
        @JsonProperty("user_id")
        private String userId;

        private String deptId;
    }

}
