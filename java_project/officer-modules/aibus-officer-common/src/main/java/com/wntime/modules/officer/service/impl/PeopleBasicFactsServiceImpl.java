package com.wntime.modules.officer.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wntime.common.utils.FileUtil;
import com.wntime.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.Query;

import com.wntime.modules.officer.dao.PeopleBasicFactsDao;
import com.wntime.modules.officer.entity.PeopleBasicFactsEntity;
import com.wntime.modules.officer.service.PeopleBasicFactsService;
import org.springframework.util.StringUtils;


@Service("peopleBasicFactsService")
public class PeopleBasicFactsServiceImpl extends ServiceImpl<PeopleBasicFactsDao, PeopleBasicFactsEntity> implements PeopleBasicFactsService {

    @Value("${wntime.common.dataPath}")
    private String dataPath;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PeopleBasicFactsEntity> page = this.page(
                new Query<PeopleBasicFactsEntity>().getPage(params),
                new QueryWrapper<PeopleBasicFactsEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PeopleBasicFactsEntity saveOrUpdateForIM(PeopleBasicFactsEntity entity) {
        String idNo = entity.getIdNo();
        QueryWrapper<PeopleBasicFactsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id_no",entity.getIdNo());
        PeopleBasicFactsEntity factsEntity = this.baseMapper.selectOne(wrapper);
        if (null != factsEntity) {
            entity.setModifyUserId(ShiroUtils.getUserId());
            entity.setModifyDt(new Date());
            UpdateWrapper<PeopleBasicFactsEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",factsEntity.getId());
            this.baseMapper.update(entity, updateWrapper);
        }else {
            entity.setCreateUserId(ShiroUtils.getUserId());
            entity.setCreateDt(new Date());
            this.baseMapper.insert(entity);
        }
        return this.baseMapper.selectOne(wrapper);
    }

    @Override
    public PeopleBasicFactsEntity findByIdAndName(String idNo, String name) {
        QueryWrapper<PeopleBasicFactsEntity> wrapper = new QueryWrapper<PeopleBasicFactsEntity>();
        wrapper.eq("full_name", name);
        List<PeopleBasicFactsEntity> factsEntities = this.baseMapper.selectList(wrapper);
        for (PeopleBasicFactsEntity entity : factsEntities) {
            String no = entity.getIdNo();
            int length = no.length();
            if (no.substring(length - 6, length).equals(idNo)) {
                return entity;
            }
        }
        return null;
    }

    @Override
    public PeopleBasicFactsEntity getOneInfoByIdNo(String idNo) {
        QueryWrapper<PeopleBasicFactsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id_no", idNo);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * @Author Buxl
     * @Description 查询所有人员列表
     * @Date 11:43 2021/1/25
     * @Param [date]
     * @return java.util.List<com.wntime.modules.officer.entity.PeopleBasicFactsEntity>
     **/
    @Override
    public List<PeopleBasicFactsEntity> getAllPersonnelByTimestamp(long busId,Date date) {
        List<PeopleBasicFactsEntity> basicFactsList = getBaseMapper().getAllPersonnelByTimestamp(busId,date);
        List<PeopleBasicFactsEntity> basicFactsList1 = new ArrayList<>();
        for(PeopleBasicFactsEntity basicFacts : basicFactsList){
            if(!StringUtils.isEmpty(basicFacts.getImg())){
                String imgBase64 = FileUtil.getImgStr(dataPath + File.separator + basicFacts.getImg());
                basicFacts.setImg(imgBase64);
                basicFactsList1.add(basicFacts);
            }
        }
//        basicFactsList.forEach(basicFacts ->{
//            try {
//                String imgBase64 = FileUtil.getImgStr(dataPath + File.separator + basicFacts.getImg());
////                String imgBase64 = FileUtil.getImgStr("C:\\Users\\Buxl\\Downloads\\02.png");
//                basicFacts.setImg(imgBase64);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        });
        return basicFactsList1;
    }

}