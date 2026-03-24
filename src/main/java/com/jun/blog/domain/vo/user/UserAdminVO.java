package com.jun.blog.domain.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel(description = "管理员用户VO")
public class UserAdminVO extends UserVO{

    @ApiModelProperty(value = "账号创建时间")
    private LocalDateTime createdAt;
    @ApiModelProperty(value = "账号更新时间")
    private LocalDateTime updatedAt;
    @ApiModelProperty(value = "登录状态", example = "0", notes = "0-在线 1-离线")
    private Integer loginStatus;
    @ApiModelProperty(value = "上次登录时间")
    private LocalDateTime lastLoginTime;
}
