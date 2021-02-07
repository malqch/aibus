package com.wntime.service.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.FileUtil;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;

import com.wntime.controller.AbstractController;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wntime.service.entity.InfoBusFactoryEntity;
import com.wntime.service.service.InfoBusFactoryService;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author psp
 * @desc 公交车厂表
 * @date 2020-08-25 14:28:17
 */
@RestController
@RequestMapping("service/infoBusFactory")
public class InfoBusFactoryController extends AbstractController {
    @Autowired
    private InfoBusFactoryService infoBusFactoryService;

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
    @ApiOperation(value = "公交车厂分页列表", httpMethod = "GET", notes = "公交车厂管理页面中Grid获取数据接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "paramName", value = "公交车厂", required = false, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "params", value = "公交车厂", required = false, dataType = "String")
    })
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("公交车厂分页列表,参数:" + JSON.toJSONString(params));
        PageUtils page = infoBusFactoryService.queryPage(params);
        List<InfoBusFactoryEntity> list = page.getList();
        for (InfoBusFactoryEntity item : list) {
            item.setFactorySnapshot(webUrl + "/" + item.getFactorySnapshot());
        }
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "获取全部公交车厂", httpMethod = "GET", notes = "获取全部公交车厂")
    @GetMapping("/getComboBox")
    public R getComboBox() {
        logger.info("获取全部公交车厂");
        List<InfoBusFactoryEntity> result = infoBusFactoryService.getAll();
        return R.ok().put("selection", result);
    }

    @ApiOperation(value = "获取单条公交车厂", httpMethod = "GET", notes = "获取单条公交车厂")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") Long id) {
        logger.info("获取单条公交车厂,参数ID:" + id);
        InfoBusFactoryEntity infoBusFactoryEntity;
        try {
            infoBusFactoryEntity = infoBusFactoryService.getDetailById(id);
            if(infoBusFactoryEntity.getFactorySnapshot() != null){
                infoBusFactoryEntity.setFactorySnapshotUrl(webUrl + "/" +infoBusFactoryEntity.getFactorySnapshot());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("获取单条公交车厂失败");
        }
        return R.ok().put("data", infoBusFactoryEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @SysLog("保存公交车厂")
    @ApiOperation(value = "添加公交车厂", httpMethod = "POST", notes = "公交车厂管理页面中添加公交车厂")
    public R save(InfoBusFactoryEntity infoBusFactoryEntity) {
        logger.info("保存公交车厂,参数:" + infoBusFactoryEntity.toString());
        ValidatorUtils.validateEntity(infoBusFactoryEntity);
        QueryWrapper<InfoBusFactoryEntity> QueryWrapper = new QueryWrapper<InfoBusFactoryEntity>()
                .eq("is_deleted", 0)
                .eq("factory_code",infoBusFactoryEntity.getFactoryCode());

        InfoBusFactoryEntity get = infoBusFactoryService.getOne(QueryWrapper);
        if(get != null){
            return R.error("公交车厂中[统一信用代码:"+infoBusFactoryEntity.getFactoryCode()+"]数据已存在，保存失败。");
        }
        MultipartFile file = infoBusFactoryEntity.getFactorySnapshotFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getName());
            }
            infoBusFactoryEntity.setFactorySnapshot("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);
        }

        try {
            infoBusFactoryService.save(infoBusFactoryEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("保存公交车厂失败");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLog("修改公交车厂")
    @ApiOperation(value = "修改公交车厂", httpMethod = "POST", notes = "公交车厂管理页面中修改公交车厂")
    public R update(InfoBusFactoryEntity infoBusFactoryEntity) {
        logger.info("修改公交车厂,参数:" + infoBusFactoryEntity.toString());
        ValidatorUtils.validateEntity(infoBusFactoryEntity);
        QueryWrapper<InfoBusFactoryEntity> QueryWrapper = new QueryWrapper<InfoBusFactoryEntity>()
                .eq("is_deleted", 0)
                .ne("factory_id",infoBusFactoryEntity.getFactoryId())
                .eq("factory_code",infoBusFactoryEntity.getFactoryCode());

        InfoBusFactoryEntity get = infoBusFactoryService.getOne(QueryWrapper);
        if(get != null){
            return R.error("公交车厂中[统一信用代码:"+infoBusFactoryEntity.getFactoryCode()+"]数据已存在，保存失败。");
        }
        MultipartFile file = infoBusFactoryEntity.getFactorySnapshotFile();
        String fileName;
        if (file != null && !file.isEmpty()) {
            logger.info("保存文件:" + file.getOriginalFilename());
            fileName = FileUtil.saveFile(file, imageDir);
            if (fileName == null) {
                return R.error("保存文件失败:" + file.getOriginalFilename());
            }
            infoBusFactoryEntity.setFactorySnapshot("image" + imageDir.substring(commonDataPath.length()) + "/" + fileName);
            InfoBusFactoryEntity old = infoBusFactoryService.getById(infoBusFactoryEntity.getFactoryId());
            if (!StringUtils.isEmpty(old.getFactorySnapshot()) && old.getFactorySnapshot().length() > 5) {
                String oldPath = commonDataPath + old.getFactorySnapshot().substring(5);
                logger.info("删除文件:" + oldPath);
                cn.hutool.core.io.FileUtil.del(oldPath);
            }
        }

        try {
            infoBusFactoryService.updateById(infoBusFactoryEntity, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改公交车厂失败");
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "单条删除公交车厂", httpMethod = "POST", notes = "公交车厂管理页面中删除公交车厂")
    @SysLog("单条删除公交车厂")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除公交车厂,id:" + id);
        try {
            infoBusFactoryService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改公交车厂失败");
        }
        return R.ok();
    }

    @ApiOperation(value = "批量删除公交车厂", httpMethod = "POST", notes = "批量删除公交车厂")
    @SysLog("批量删除公交车厂")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除公交车厂,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        try {
            infoBusFactoryService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
