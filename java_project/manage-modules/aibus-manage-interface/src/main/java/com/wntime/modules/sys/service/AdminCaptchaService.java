

package com.wntime.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.modules.sys.entity.AdminCaptcha;

import java.awt.image.BufferedImage;

/**
 * 验证码

 */
public interface AdminCaptchaService extends IService<AdminCaptcha> {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
