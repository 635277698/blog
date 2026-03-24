package com.jun.blog.domain.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("blog")
public class BlogPO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private String summary;

    @TableField("author_id")
    private Long authorId;

    @TableField("author_name")
    private String authorName;

    @TableField(value = "create_time")
    private LocalDateTime createTime;
}
