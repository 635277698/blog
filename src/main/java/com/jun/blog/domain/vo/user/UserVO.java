package com.jun.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "普通用户VO")
public class UserVO extends UserBaseVO{
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "发布的博客数量")
    private Integer blogCount;
    @ApiModelProperty(value = "角色", example = "0", notes = "0-普通用户 1-管理员")
    private Integer role;
    @ApiModelProperty(value = "账户状态", example = "0", notes = "0-正常 1-禁用")
    private Integer accountStatus;
}
