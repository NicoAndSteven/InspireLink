package com.coco.inspirelink.utils.tools;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/16 下午4:00
 * @Version: 1.0
 */

public class UidGenerator {
    public static String generateUid() {
        String uid = java.util.UUID.randomUUID().toString();
        return uid.replaceAll("-", "");
    }

}
