package com.jun.blog.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jun.blog.domain.po.UserPO;
import com.jun.blog.enums.UserEnum;
import com.jun.blog.mapper.UserMapper;
import com.jun.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService {
    @Override
    public List<UserPO> findAllUsers(UserPO userPO) throws Exception {
        if (userPO.getId() == null && userPO.getUsername() == null){
            log.error("参数不正确: 需要用户id或者用户名");
            throw new Exception("需要用户id或者用户名参数");

        }
        LambdaQueryWrapper<UserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(userPO.getId() != null, UserPO::getId, userPO.getId())
                .eq(userPO.getUsername() != null, UserPO::getUsername, userPO.getUsername());
        UserPO user = getOne(wrapper);
        if (user == null){
            log.error("满足条件的用户不存在");
            throw new Exception("满足条件的用户不存在");
        }
        if (!isAdminUser(user)) {
            log.error("需要管理员权限");
            throw new Exception("需要管理员权限");
        }
        return list();
    }


    private boolean isAdminUser(UserPO userPO) {
        return userPO.getRole() == UserEnum.Role.ADMIN;
    }
}
