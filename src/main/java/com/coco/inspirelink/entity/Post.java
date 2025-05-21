package com.coco.inspirelink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/19 上午11:26
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private String postId;
    private String userId;
    private String title;
    private String content;
    private String commentCount;
    private String likeCount;
    private String comment;
}
