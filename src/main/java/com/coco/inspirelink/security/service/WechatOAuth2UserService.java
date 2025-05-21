package com.coco.inspirelink.security.service;

import com.coco.inspirelink.common.right.Role;
import com.coco.inspirelink.entity.User;
import com.coco.inspirelink.mapper.UserMapper;
import com.coco.inspirelink.security.config.CustomUserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: MOHE
 * @Description: 微信OAuth2用户服务
 * @Date: 2025/5/20 下午4:11
 * @Version: 1.0
 */
@Service
public class WechatOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Resource
    private UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        try {
            // 1. 获取微信用户信息
            OAuth2User oauth2User = new DefaultOAuth2UserService().loadUser(request);
            Map<String, Object> attributes = oauth2User.getAttributes();

            // 2. 验证必要的微信用户信息
            String openid = validateAndGetOpenid(attributes);
            String nickname = (String) attributes.get("nickname");
            String headimgurl = (String) attributes.get("headimgurl");

            // 3. 检查本地用户是否存在
            User user = userMapper.findByWechatOpenid(openid);
            if (user == null) {
                // 4. 自动注册微信用户
                user = createWechatUser(openid, nickname, headimgurl);
            } else {
                // 5. 更新用户信息
                updateUserInfo(user, nickname, headimgurl);
            }

            // 6. 设置用户权限
            Set<SimpleGrantedAuthority> authorities = determineUserAuthorities(user);

            // 7. 转换为Spring Security识别的UserDetails
            return new CustomUserDetails(user, attributes, authorities);
        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Failed to load user from WeChat");
        }
    }

    /**
     * 验证并获取微信openid
     */
    private String validateAndGetOpenid(Map<String, Object> attributes) {
        String openid = (String) attributes.get("openid");
        if (!StringUtils.hasText(openid)) {
            throw new OAuth2AuthenticationException("OpenID not found in WeChat response");
        }
        return openid;
    }

    /**
     * 创建微信用户
     */
    private User createWechatUser(String openid, String nickname, String headimgurl) {
        User newUser = new User();
        newUser.setWechatOpenid(openid);
        newUser.setUsername(nickname != null ? nickname : "微信用户_" + openid.substring(0, 8));
        newUser.setStatus("active");
        newUser.setCreateAt(new Date().toString());
        newUser.setUpdateAt(new Date().toString());
        // 设置默认角色为VISITOR
        newUser.setRoles(Role.VISITOR.getLevel());
        
        userMapper.insert(newUser);
        return userMapper.findByWechatOpenid(openid);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(User user, String nickname, String headimgurl) {
        boolean needUpdate = false;
        
        if (StringUtils.hasText(nickname) && !nickname.equals(user.getUsername())) {
            user.setUsername(nickname);
            needUpdate = true;
        }
        
        if (needUpdate) {
            user.setUpdateAt(new Date().toString());
            userMapper.updateById(user);
        }
    }

    /**
     * 确定用户权限
     */
    private Set<SimpleGrantedAuthority> determineUserAuthorities(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        
        // 添加基本角色
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        
        // 根据用户角色添加额外权限
        if (user.getRoles() >0) {
            switch (user.getRoles()) {
                case 1: // ADMIN
                    authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getDescription()));
                    break;
                case 2: // VISITOR
                    authorities.add(new SimpleGrantedAuthority(Role.VISITOR.getDescription()));
                    break;
                case 3: // SUPERMAN
                    authorities.add(new SimpleGrantedAuthority(Role.SUPERMAN.getDescription()));
                    break;
            }
        }
        
        return authorities;
    }
}
