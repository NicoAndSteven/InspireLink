package com.coco.inspirelink.controller;

import com.coco.inspirelink.dto.UserDTO;
import com.coco.inspirelink.service.SearchforUser;
import com.coco.inspirelink.service.UserLogin;
import com.coco.inspirelink.service.impl.SearchforUserImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/14 下午1:54
 * @Version: 1.0
 */
@RestController
public class UserVerifyContriller {
    @Resource
    private UserLogin userLogin;

    @PostMapping("/login")
    public String solveLogin(@RequestBody UserDTO userDTO){
        return userLogin.login(userDTO);
    }
}
