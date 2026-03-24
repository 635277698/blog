package com.jun.blog.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "统一返回结果")
@Data
public class Result<T> {
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;

    @ApiModelProperty(value = "消息", example = "success")
    private String message;

    @ApiModelProperty("数据")
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String msg) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(msg);
        return result;
    }

}