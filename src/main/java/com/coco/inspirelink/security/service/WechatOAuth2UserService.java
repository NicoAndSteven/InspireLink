package com.coco.inspirelink.security.service;

import com.coco.inspirelink.entity.User;
import com.coco.inspirelink.mapper.UserMapper;
import com.coco.inspirelink.security.config.CustomUserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/20 下午4:11
 * @Version: 1.0
 */

@Service
public class WechatOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Resource
    private UserMapper userMapper;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        // 获取微信用户信息
        OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(request);
        Map<String, Object> attributes = oauth2User.getAttributes();

        // 检查本地用户是否存在（openid作为唯一标识）
        String openid = (String) attributes.get("openid");
        User user = userMapper.findByWechatOpenid(openid);
        if (user == null){
            // 自动注册微信用户
            User newUser = new User();
            newUser.setWechatOpenid(openid);
            userMapper.insert(newUser);
            user = userMapper.findByWechatOpenid(openid);
        }



        // 转换为Spring Security识别的UserDetails
        return new CustomUserDetails(user,attributes);
    }
}
