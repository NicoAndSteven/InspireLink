package com.coco.inspirelink.common.right;

import lombok.Getter;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/21 上午9:22
 * @Version: 1.0
 */
@Getter
public enum Role {
    ADMIN("管理员", 1),
    VISITOR("普通用户", 2),
    SUPERMAN("DongMo", 3);

    // Getter 方法
    private String description;
    private int level;

    // 构造函数
    Role(String description, int level) {
        this.description = description;
        this.level = level;
    }

    // 可以根据需要添加其他方法，比如根据级别获取角色描述等
    public static String getDescriptionByLevel(int level) {
        for (Role role : Role.values()) {
            if (role.getLevel() == level) {
                return role.getDescription();
            }
        }
        return "未知角色";
    }
}