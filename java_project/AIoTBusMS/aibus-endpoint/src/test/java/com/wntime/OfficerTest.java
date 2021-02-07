package com.wntime;

import com.wntime.modules.officer.entity.EducationBureauEntity;
import com.wntime.modules.officer.service.EducationBureauService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class OfficerTest {

    @Autowired
    private EducationBureauService educationBureauService;


    @Test
    public void  educationbureau(){
        EducationBureauEntity entity = new EducationBureauEntity();
        entity.setId(1350358682130448386L);
        entity.setOrgName("西安教育局");
        entity.setAddress("西安兵马集团");
        entity.setWebsite("http://www.xian.cn");
        entity.setCreateDt(new Date());
        entity.setCreateUserId("1");
        entity.setModifyDt(new Date());
        entity.setModifyUserId("1");

        boolean save = educationBureauService.saveOrUpdate(entity);
        System.out.println(entity);

    }


}
