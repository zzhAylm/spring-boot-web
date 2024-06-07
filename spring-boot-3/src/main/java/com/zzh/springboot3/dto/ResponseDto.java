package com.zzh.springboot3.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2023/9/27 14:51
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<T> implements Serializable {

    private Integer code;

    private String message;

    @Builder.Default
    private String timestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(LocalDateTime.now());

    private T data;

    public static <T> ResponseDto<T> build(int code, String message, T data) {
        return ResponseDto.<T>builder().code(code).message(message).data(data).build();
    }

    public static ResponseDto<?> success(int code, String message) {
        return ResponseDto.builder().code(code).message(message).build();
    }

    public static <T> ResponseDto<T> success(T data) {
        return build(200, "成功", data);
    }

    public static ResponseDto<?> success(int code) {
        return success(code, "成功");
    }

    public static ResponseDto<?> success() {
        return success(200);
    }


    public static ResponseDto<?> failOf(String message) {
        return ResponseDto.builder().code(500).message(message).build();
    }

    public static ResponseDto<?> fail(int code, String message) {
        return ResponseDto.builder().code(code).message(message).build();
    }

    public static <T> ResponseDto<T> fail(T data) {
        return build(500, "失败", data);
    }


    public static ResponseDto<?> fail(int code) {
        return fail(code, "失败");
    }

    public static ResponseDto<?> fail() {
        return fail(500);
    }

    public boolean isSuccess() {
        return this.code == 200;
    }
}
