package com.atao.task.quartz.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 通用API封装
 * @author atao
 * @date 2020/12/1
 */
@Data
public class ApiResponse {

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public ApiResponse() {
    }

    private ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @param data    返回数据
     * @return ApiResponse
     */
    public static ApiResponse of(String message, Object data) {
        return new ApiResponse(message, data);
    }

    /**
     * 通用成功封装获取ApiResponse对象
     *
     * @param data 返回数据
     * @return ApiResponse
     */
    public static ApiResponse ok(Object data) {
        return new ApiResponse(HttpStatus.OK.getReasonPhrase(), data);
    }

    /**
     * 通用封装获取ApiResponse对象
     *
     * @param message 返回信息
     * @return ApiResponse
     */
    public static ApiResponse msg(String message) {
        return of(message, null);
    }

}
