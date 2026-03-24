package com.jun.blog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jun.blog.common.result.Result;
import com.jun.blog.domain.dto.UserDTO;
import com.jun.blog.domain.po.UserPO;
import com.jun.blog.domain.vo.user.UserAdminVO;
import com.jun.blog.domain.vo.user.UserVO;
import com.jun.blog.enums.UserEnum;
import com.jun.blog.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户管理")
@Slf4j
public class UserController {
    private final UserService userService;


    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询所有用户(管理员)")
    public Result<List<UserAdminVO>> selectAllUsers(UserDTO userDTO){
        try {
            List<UserPO> allUsers = userService.findAllUsers(BeanUtil.copyProperties(userDTO, UserPO.class));
            return Result.success(BeanUtil.copyToList(allUsers, UserAdminVO.class));
        } catch (Exception e) {
            log.error("查询所有用户异常了,{}", e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/{username}")
    @ResponseBody
    @ApiOperation(value = "查询用户")
    public Result<UserVO> selectUserByUserName(@ApiParam(value = "用户名",required=true) @PathVariable String username){
        if (StrUtil.isBlank(username)){
            log.error("参数username为空");
            return Result.fail("参数username为空");
        }

        try {
            QueryWrapper<UserPO> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(UserPO::getUsername, username);
            UserPO user = userService.getOne(wrapper);
            if(user == null){
                log.error("用户{}不存在", username);
                return Result.fail(StrUtil.format("用户{}不存在", username));
            }
            return Result.success(BeanUtil.copyProperties(user, UserVO.class));
        } catch (Exception e) {
            log.error("查询用户异常了,{}", e.getMessage());
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/doLogin")
    public String doLogin(String username, String password, RedirectAttributes redirectAttributes, HttpSession session) {
        System.out.println("登录页面。。。");
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserPO::getUsername, username)
                .eq(UserPO::getPassword, password);
        UserPO user = userService.getOne(wrapper);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/blog";
        }
        redirectAttributes.addFlashAttribute("error", "登录失败，请稍后重试");
        redirectAttributes.addFlashAttribute("userDTO", user);
        redirectAttributes.addAttribute("login", "true");
        return "redirect:/login";
    }

    @PostMapping("/register")
    @ApiOperation(value = "注册用户")
    public String registerUser(UserDTO userDTO, RedirectAttributes redirectAttributes,HttpSession session){
        try {
            // 检查用户名是否已存在
            UserPO existingUser = userService.getOne(
                    new LambdaQueryWrapper<UserPO>()
                            .eq(UserPO::getUsername, userDTO.getUsername()));
            if (existingUser != null) {
                redirectAttributes.addFlashAttribute("error", "用户名已存在");
                redirectAttributes.addFlashAttribute("userDTO", userDTO);
                redirectAttributes.addAttribute("register", "true");
                return "redirect:/login";
            }

            // 创建新用户
            UserPO user = new UserPO();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setRole(UserEnum.Role.USER);
            user.setEmail(userDTO.getEmail());
            user.setPhone(userDTO.getPhone());
            user.setAccountStatus(0);
            user.setLoginStatus(1);
            user.setBlogCount(0);
            boolean isCompleted = userService.save(user);
            if(! isCompleted){
                redirectAttributes.addFlashAttribute("error", "注册失败，请稍后重试");
                redirectAttributes.addFlashAttribute("userDTO", userDTO);
                redirectAttributes.addAttribute("register", "true");
                return "redirect:/login";
            }
            // 注册成功，自动登录
            session.setAttribute("user", user);
            log.info("用户{}注册成功", user.getUsername());

            // 使用 forward 转发到 /blog，让 /blog 的 Controller 处理博客列表获取
            return "forward:/blog";
        } catch (Exception e) {
            log.error("注册异常", e);
            redirectAttributes.addFlashAttribute("error", "系统异常：" + e.getMessage());
            redirectAttributes.addFlashAttribute("userDTO", userDTO);
            redirectAttributes.addAttribute("register", "true");
            return "redirect:/login";
        }
    }

    //    @GetMapping("/profile")
//    public String profile(Model model, HttpSession session) {
//        UserPO user = (UserPO) session.getAttribute("user");
//        if (user != null) {
//            UserStats stats = userService.getUserStats(user.getId());
//            model.addAttribute("userStats", stats);
//        }
//        return "profile";
//    }







}
