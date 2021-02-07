package com.wntime.service.controller;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.FileUtil;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.service.entity.InfoBatteryTypeEntity;
import com.wntime.util.IDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.wntime.service.entity.InfoBusTypeEntity;
import com.wntime.service.service.InfoBusTypeService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mark
 * @desc 公交车型
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 14:28:17
 */
@Api(value = "公交车型管理", tags = {"公交车型管理"})
@RestController
@RequestMapping("service/infobustype")
public class InfoBusTypeController extends AbstractController {
    @Autowired
    private InfoBusTypeService infoBusTypeService;

    @Value("${wntime.service.upload.filePath}")
    private String imageDir;

    @Value("${server.webUrl}")
    private String webUrl;

    @Value("${wntime.common.dataPath}")
    private String commonDataPath;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation(value = "@desc 公交车型分页列表", httpMethod = "GET", notes = "@desc 公交车型管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("@desc 公交车型分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoBusTypeService.queryPage(params);
        List<InfoBusTypeEntity> list = page.getList();
        for (InfoBusTypeEntity item : list) {
            item.setBusTypeImage(webUrl + "/" + item.getBusTypeImage());
        }
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取单条@desc 公交车型", httpMethod = "GET", notes = "获取单条@desc 公交车型")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条@desc 公交车型,参数ID:" + id);
        InfoBusTypeEntity infoBusTypeEntity;
        try {
            infoBusTypeEntity = infoBusTypeService.getDetailById(id);
            if(infoBusTypeEntity.getBusTypeImage() != null){
                infoBusTypeEntity.setBusTypeImageUrl(webUrl + "/" +infoBusTypeEntity.getBusTypeImage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条@desc 公交车型失败");
        }
        return R.ok().put("data", infoBusTypeEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存@desc 公交车型")
    @ApiOperation(value = "添加@desc 公交车型", httpMethod = "POST", notes = "@desc 公交车型管理页面中添加@desc 公交车型")
    public R save(InfoBusTypeEntity infoBusTypeEntity) {

        logger.info("保存@desc 公交车型,参数:" + infoBusTypeEntity.toString());
        ValidatorUtils.validateEntity(infoBusTypeEntity);

        QueryWrapper<InfoBusTypeEntity> QueryWrapper = new QueryWrapper<InfoBusTypeEntity>()
                .eq("is_deleted", 0)
                .eq("bus_type_code",infoBusTypeEntity.getBusTypeCode());

        InfoBusTypeEntity get = infoBusTypeService.getOne(QueryWrapper);
        if(get != null){
            return R.error("公交车型[车型编码:"+infoBusTypeEntity.getBusTypeCode()+"]数据已存在，保存失败。");
        }

        MultipartFile file = infoBusTypeEntity.getBusTypeImageFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getName());
            }
            infoBusTypeEntity.setBusTypeImage("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);
        }

        try {
            infoBusTypeService.save(infoBusTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存@desc 公交车型失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改@desc 公交车型")
    @ApiOperation(value = "修改@desc 公交车型", httpMethod = "POST", notes = "@desc 公交车型管理页面中修改@desc 公交车型")
    public R update(InfoBusTypeEntity infoBusTypeEntity) {
        logger.info("修改@desc 公交车型,参数:" + infoBusTypeEntity.toString());
        ValidatorUtils.validateEntity(infoBusTypeEntity);

        QueryWrapper<InfoBusTypeEntity> QueryWrapper = new QueryWrapper<InfoBusTypeEntity>()
                .eq("is_deleted", 0)
                .ne("bus_type_id",infoBusTypeEntity.getBusTypeId())
                .eq("bus_type_code",infoBusTypeEntity.getBusTypeCode());

        InfoBusTypeEntity get = infoBusTypeService.getOne(QueryWrapper);
        if(get != null){
            return R.error("公交车型[车型编码:"+infoBusTypeEntity.getBusTypeCode()+"]数据已存在，保存失败。");
        }

        MultipartFile file = infoBusTypeEntity.getBusTypeImageFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getOriginalFilename());
            }
            infoBusTypeEntity.setBusTypeImage("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);

            InfoBusTypeEntity old = infoBusTypeService.getById(infoBusTypeEntity.getBusTypeId());
            if (!StringUtils.isEmpty(old.getBusTypeImage()) && old.getBusTypeImage().length() > 5) {
                String oldPath = commonDataPath + old.getBusTypeImage().substring(5);
                logger.info("删除文件:" + oldPath);
                cn.hutool.core.io.FileUtil.del(oldPath);
            }
        }

        try {
            infoBusTypeService.updateById(infoBusTypeEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除@desc 公交车型", httpMethod = "POST", notes = "@desc 公交车型管理页面中删除@desc 公交车型")
    @SysLog("单条删除@desc 公交车型")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除@desc 公交车型,id:" + id);
        try {
            infoBusTypeService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除@desc 公交车型", httpMethod = "POST", notes = "批量删除@desc 公交车型")
    @SysLog("批量删除@desc 公交车型")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除@desc 公交车型,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusTypeService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(e.getMessage());
        }

        return R.ok("批量删除成功!");
    }

    /**
     * 查询车型列表数据
     */
    @ApiOperation(value = "查询车型列表数据", httpMethod = "GET", notes = "查询车型列表数据")
    @RequestMapping("/allList")
//    @RequiresPermissions("service:infobustype:list")
    public R getAllList() {
        List<InfoBusTypeEntity> list = infoBusTypeService.getAllList();
        return R.ok().put("list", list);
    }
}
