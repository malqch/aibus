package com.wntime.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.modules.sys.dao.AdminCaptchaDao;
import com.wntime.modules.sys.entity.AdminCaptcha;
import com.wntime.modules.sys.service.AdminCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 验证码

 */
@Service("adminCaptchaService")
public class AdminCaptchaServiceImpl extends ServiceImpl<AdminCaptchaDao, AdminCaptcha> implements AdminCaptchaService {
    @Autowired
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new RRException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        AdminCaptcha captcha = new AdminCaptcha();
        captcha.setUuid(uuid);
        captcha.setCode(code);
        //5分钟后过期
        captcha.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        this.save(captcha);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        AdminCaptcha captcha = this.getOne(new QueryWrapper<AdminCaptcha>().eq("uuid", uuid));
        if(captcha == null){
            return false;
        }

        //删除验证码
        this.removeById(uuid);
        this.baseMapper.deleteExpire();

        if(captcha.getCode().equalsIgnoreCase(code) && captcha.getExpireTime().getTime() >= System.currentTimeMillis()){
            return true;
        }

        return false;
    }
}
