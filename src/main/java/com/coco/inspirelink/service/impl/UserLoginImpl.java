package com.coco.inspirelink.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coco.inspirelink.common.right.Role;
import com.coco.inspirelink.dto.UserDTO;
import com.coco.inspirelink.entity.User;
import com.coco.inspirelink.mapper.UserMapper;
import com.coco.inspirelink.service.UserLogin;
import com.coco.inspirelink.utils.jwt.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/16 下午3:47
 * @Version: 1.0
 */
@Service
public class UserLoginImpl implements UserLogin {
    @Resource
    private UserMapper  userMapper;

    @Resource
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String login(UserDTO userparam) throws RuntimeException{
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",userparam.getUsername()).eq("password",userparam.getPassword());
        if(userMapper.selectOne(wrapper)==null){
            throw new RuntimeException("用户名或密码错误");
        }
        return jwtTokenProvider.generateToken(userparam.getUsername(), Role.ADMIN.getLevel());

    }

    @Override
    public String register(User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",user.getUsername());
        if(userMapper.selectOne(wrapper)!=null){
            return "用户名已存在";
        }
        wrapper.eq("email",user.getEmail());
        if(userMapper.selectOne(wrapper)!=null){
            return "邮箱已存在";
        }
        wrapper.eq("phoneNumber",user.getPhoneNumber());
        if(userMapper.selectOne(wrapper)!=null){
            return "手机号已存在";
        }
        int insert = userMapper.insert(user);
        if(insert==1){
            return "注册成功";
        }
        return "注册失败";
    }
}
