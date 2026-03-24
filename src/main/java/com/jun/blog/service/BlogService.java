package com.jun.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.blog.domain.po.BlogPO;

import java.util.List;


public interface BlogService extends IService<BlogPO> {
    Page<BlogPO> pageUserBlogs(Integer page, Integer size, Long userId);

}
