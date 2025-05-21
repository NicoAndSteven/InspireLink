package com.coco.inspirelink.controller;

import com.coco.inspirelink.common.response.Result;
import com.coco.inspirelink.dto.UserDTO;
import com.coco.inspirelink.service.UserLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/14 下午1:54
 * @Version: 1.0
 */
@RestController
public class UserVerifyController {
    @Resource
    private UserLogin userLogin;

    @PostMapping("/login")
    public ResponseEntity<?> solveLogin(@RequestBody UserDTO userDTO){
        String token = userLogin.login(userDTO);
        return ResponseEntity.ok().body(Result.success("登陆成功",token));
    }
}
