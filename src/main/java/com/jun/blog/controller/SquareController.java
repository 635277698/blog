package com.jun.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.blog.common.result.Result;
import com.jun.blog.domain.dto.UserDTO;
import com.jun.blog.domain.po.BlogPO;
import com.jun.blog.domain.po.UserPO;
import com.jun.blog.domain.vo.user.UserAdminVO;
import com.jun.blog.domain.vo.user.UserVO;
import com.jun.blog.enums.UserEnum;
import com.jun.blog.service.BlogService;
import com.jun.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/square")
@RequiredArgsConstructor
@Api(tags = "博客广场管理")
@Slf4j
public class SquareController {
    private final BlogService blogService;

    @GetMapping
    public String square(Model model) {
        System.out.println("博客广场。。。");
        // 获取所有博客 - 返回BlogPO列表
        List<BlogPO> squareBlogs = blogService.list();
        model.addAttribute("squareBlogs", squareBlogs);
        return "square";
    }






}
