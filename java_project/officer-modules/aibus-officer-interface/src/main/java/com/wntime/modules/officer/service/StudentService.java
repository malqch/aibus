package com.wntime.modules.officer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wntime.common.utils.PageUtils;
import com.wntime.modules.officer.dto.StudentInfoDto;
import com.wntime.modules.officer.entity.StudentEntity;
import com.wntime.modules.officer.vo.LineStationVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @desc 学生信息表
 *
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-16 14:43:43
 */
public interface StudentService extends IService<StudentEntity> {

     Set<LineStationVO> getLineStation(Long companyLineId);

    boolean removeById(Long id);

    PageUtils queryPage(Map<String, Object> params);


    HashMap<String, Object> importInfo(MultipartFile attach, Long schoolId, Long classId) throws Exception;

    HashMap<String, Object> importCompress(MultipartFile attach, String type);

    StudentInfoDto getStudentInfoById(Long id);


    void updateStudentInfo(StudentInfoDto student);

//    Studen getStudentInfoById(Long id);
}

