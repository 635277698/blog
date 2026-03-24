package com.jun.blog.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public class UserEnum {

    /**
     * 用户角色枚举
     */
    @Getter
    public enum Role{
        USER(0, "普通用户"),
        ADMIN(1, "管理员");

        @EnumValue  // MyBatis-Plus: 告诉mybatisPlus，下面这个字段和数据库的int类型映射
        @JsonValue  // Jackson: 序列化到前端的值
        private final Integer value;

        private final String description;

        Role(Integer value, String description) {
            this.value = value;
            this.description = description;
        }
    }


}
