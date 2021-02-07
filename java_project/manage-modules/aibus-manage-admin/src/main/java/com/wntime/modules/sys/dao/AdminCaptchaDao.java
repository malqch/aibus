package com.wntime.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wntime.modules.sys.entity.AdminCaptcha;
import org.apache.ibatis.annotations.Mapper;

/**
 * 验证码

 */
@Mapper
public interface AdminCaptchaDao extends BaseMapper<AdminCaptcha> {

    void deleteExpire();
}
