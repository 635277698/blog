package com.jun.blog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jun.blog.enums.UserEnum;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("users")
public class UserPO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
//    @TableField("username")
    private String username;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String nickname;
    private Integer blogCount;
    private UserEnum.Role role;            // 0-普通用户，1-管理员
    private Integer accountStatus;   // 0-正常，1-禁用
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer loginStatus;     // 0-在线，1-离线
    private LocalDateTime lastLoginTime;
}
