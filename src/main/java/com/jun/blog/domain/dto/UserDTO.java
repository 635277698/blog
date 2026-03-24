package com.jun.blog.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("请求参数")
public class UserDTO {
    @ApiModelProperty(value = "用户id", example = "2")
    private Long id;
    @ApiModelProperty(value = "用户名", example = "test1")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "邮件")
    private String email;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "昵称")
    private String nickname;

}
