package com.coco.inspirelink.common;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/21 上午9:22
 * @Version: 1.0
 */
public enum Role {
    ADMIN("管理员", 1),
    VISITOR("浏览者", 2),
    SUPERMAN("DongMo", 3);

    private String description;
    private int level;

    // 构造函数
    private Role(String description, int level) {
        this.description = description;
        this.level = level;
    }

    // Getter 方法
    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
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