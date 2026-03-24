package com.jun.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jun.blog.common.result.Result;
import com.jun.blog.domain.po.UserPO;

import java.util.List;

public interface UserService extends IService<UserPO> {
    List<UserPO> findAllUsers(UserPO userPO) throws Exception;
}
