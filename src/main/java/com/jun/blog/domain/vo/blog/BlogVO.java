package com.jun.blog.domain.vo.blog;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "博客VO")
public class BlogVO {
    @ApiModelProperty(value = "博客id")
    private Long id;
    @ApiModelProperty(value = "博客title")
    private String title;
    @ApiModelProperty(value = "博客摘要")
    private String summary;
    @ApiModelProperty(value = "博客内容")
    private String content;
    @ApiModelProperty(value = "博客的作者id")
    private Long authorId;
    @ApiModelProperty(value = "博客的作者名字")
    private String authorName;
    @ApiModelProperty(value = "博客创建日期")
    private LocalDateTime createTime;
}
