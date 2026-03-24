package com.jun.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jun.blog.common.untils.MarkdownUtils;
import com.jun.blog.domain.po.BlogPO;
import com.jun.blog.domain.po.UserPO;
import com.jun.blog.service.BlogService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
@Api(tags = "博客管理")
@Slf4j
public class BlogController {
    private final BlogService blogService;


    @GetMapping
    public String blog(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size,
                       Model model,
                       HttpSession session) {
        System.out.println("我的博客。。。");
        UserPO user = (UserPO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        // 调用 Service 层分页查询
        Page<BlogPO> blogPage = blogService.pageUserBlogs(page, size, user.getId());

        model.addAttribute("pageData", blogPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "blog";
    }

    @GetMapping("/detail/{id}")
    public String blogDetail(@PathVariable Long id, Model model, HttpSession session) {
        // 1. 根据 id 查询博客
        BlogPO blog = blogService.getById(id);
        if (blog != null) {
            // 服务端渲染 Markdown 为 HTML
            String htmlContent = MarkdownUtils.renderToHtml(blog.getContent());
            model.addAttribute("blogContent", htmlContent);
            model.addAttribute("blog", blog);
        }
        return "blog-detail";
    }

    @PostMapping("/create")
    public String createBlog(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String summary,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        System.out.println("创建博客。。。");
        UserPO user = (UserPO) session.getAttribute("user");
        if (user != null) {
            BlogPO blog = new BlogPO();
            blog.setTitle(title);
            blog.setContent(content);
            blog.setSummary(summary);
            blog.setAuthorId(user.getId());
            blog.setAuthorName(user.getUsername());

            boolean success = blogService.save(blog);
            if (success) {
                redirectAttributes.addFlashAttribute("success", "创建成功");
            } else {
                redirectAttributes.addFlashAttribute("error", "创建失败");
            }
        }
        return "redirect:/blog";
    }

    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable Long id,
                             HttpSession session,  // 注入 HttpSession
                             RedirectAttributes redirectAttributes) {
        System.out.println("删除博客。。。"+ id);
        // 从 Session 中获取用户信息
        UserPO user = (UserPO) session.getAttribute("user");

        // 判断用户是否存在
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "请先登录");
            return "redirect:/login";
        }

        // 后续业务逻辑...
        BlogPO blog = blogService.getById(id);
        if (blog == null) {
            redirectAttributes.addFlashAttribute("error", "博客不存在");
            return "redirect:/blog";
        }
        // 执行删除
        boolean success = blogService.removeById(id);
        System.out.println(success);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "删除成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "删除失败");
        }
        return "redirect:/blog";
    }






}
