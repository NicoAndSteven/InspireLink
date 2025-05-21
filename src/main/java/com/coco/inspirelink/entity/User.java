package com.coco.inspirelink.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/14 上午11:16
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
public class User {
    private String userId;
    private String username;
    private String password;
    private String uuid;
    private String email;
    private String phoneNumber;
    private String status;
    private String createAt;
    private String updateAt;
    private String wechatOpenid;
    private int Roles;
}
