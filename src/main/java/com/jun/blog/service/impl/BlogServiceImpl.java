package com.jun.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.blog.domain.po.BlogPO;
import com.jun.blog.mapper.BlogMapper;
import com.jun.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@Slf4j
public class BlogServiceImpl extends ServiceImpl<BlogMapper, BlogPO> implements BlogService {

    @Override
    public Page<BlogPO> pageUserBlogs(Integer page, Integer size, Long userId) {
        // 1. 构建分页对象
        Page<BlogPO> pageParam = new Page<>(page, size);

        // 2. 构建查询条件
        QueryWrapper<BlogPO> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", userId)
                .orderByDesc("create_time");

        // 3. 执行分页查询
        Page<BlogPO> blogPage = this.page(pageParam, wrapper);

        // 4. 处理摘要：如果摘要为空，从内容中截取
        blogPage.getRecords().forEach(blog -> {
            if (blog.getSummary() == null || blog.getSummary().isEmpty()) {
                String content = blog.getContent();
                if (content != null && content.length() > 150) {
                    blog.setSummary(content.substring(0, 150) + "...");
                } else {
                    blog.setSummary(content);
                }
            }
        });

        return blogPage;
    }
}
