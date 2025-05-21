package com.coco.inspirelink.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coco.inspirelink.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: MOHE
 * @Description: TODO
 * @Date: 2025/5/14 下午2:34
 * @Version: 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    User findByWechatOpenid(String openid);
}
