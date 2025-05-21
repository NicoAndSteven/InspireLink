package com.coco.inspirelink.service;

import com.coco.inspirelink.dto.UserDTO;
import com.coco.inspirelink.entity.User;
import org.springframework.http.ResponseEntity;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/16 下午3:47
 * @Version: 1.0
 */
public interface UserLogin {
    public String login(UserDTO userDTO);
    public String register(User user);
}
