package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.entity.StudentLineSeatEntity;

import java.util.Map;

/**
 * @desc 学生线路与坐位
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-20 20:24:11
 */
public interface StudentLineSeatService extends IService<StudentLineSeatEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void copyLineSeatID();
}

