package com.coco.inspirelink.common.response;

import lombok.Data;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/19 下午3:06
 * @Version: 1.0
 */
@Data
public class Result<T> {
    private int code;    // 状态码（200=成功，400=客户端错误，500=服务器错误）
    private String msg;  // 提示信息
    private T data;      // 返回的数据（泛型，可以是任意类型）

    // 构造方法（私有化，避免直接 new）
    private Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 快速生成成功响应（无数据）
    public static <T> Result<T> success(String msg) {
        return new Result<>(200, msg, null);
    }

    // 快速生成成功响应（带数据）
    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

//    public static <T> Result<T> success(T data) {return new Result<>(200,"",data);}

    // 快速生成错误响应
    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

}