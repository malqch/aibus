package com.wntime.service.controller;

import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.entity.InfoUpdateListEntity;
import com.wntime.service.common.service.InfoUpdateListService;
import com.wntime.service.vo.InfoUpdateListVo;
import com.wntime.service.common.util.RequestUtil;
import com.wntime.service.common.vo.DeleteBatchVo;
import com.wntime.util.IDUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 更新包管理
 *
 * @author peishuaipeng
 * 2019-12-13 10:40:34
 */
@Api(value = "更新包管理", tags = {"更新包管理"})
@RestController
@RequestMapping("update/package")
public class InfoUpdateListController extends AbstractController {

    @Value("${path.updatePackage}")
    private String updateDir;

    @Value(("${path.requestUpdatePackageUrl}"))
    private String requestUpdatePackageUrl;

    @Autowired
    private InfoUpdateListService infoUpdateListService;

    @ApiOperation(value = "更新包管理", httpMethod = "GET", notes = "获取更新包管理")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int")
    })
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        logger.info("开始查询更新包管理");
        PageUtils page = infoUpdateListService.queryPageList(params);
        return R.ok().put("page", page);
    }

    @ApiOperation(value = "根据id获取更新包管理", httpMethod = "GET", notes = "根据id获取更新包管理")
    @GetMapping("/get/{id}")
    public R getById(@PathVariable("id") String id) {
        logger.info("根据id获取更新包管理,id:" + id);
        long idL = Long.parseLong(id);
        List<Object> list = infoUpdateListService.getDetailById(idL);
        if (list.size() > 0) {

            return R.ok().put("data", list.get(0));
        }
        return R.ok().put("data", null);
    }

    @ApiOperation(value = "选择更新类型", httpMethod = "GET", notes = "选择更新类型")
    @GetMapping("/queryUpdateType")
    public R queryUpdateType(@RequestParam Map<String, Object> params) {
        logger.info("选择更新类型");
        Object result = infoUpdateListService.queryUpdateType(params);
        return R.ok().put("selection", result);
    }

    @ApiOperation(value = "保存更新包管理", httpMethod = "POST", notes = "保存更新包管理")
    @SysLog("保存更新包管理")
    @PostMapping("/save")
    public R save(@RequestParam(value = "updateFile", required = false) MultipartFile updateFile, HttpServletRequest request) {
        logger.info("保存更新包管理");

        InfoUpdateListVo infoUpdateListVo = new InfoUpdateListVo();
        RequestUtil.paramToObject(request, infoUpdateListVo);

        ValidatorUtils.validateEntity(infoUpdateListVo);

        InfoUpdateListEntity infoUpdateList = new InfoUpdateListEntity();
        infoUpdateList.setUpdateListId(null);
        infoUpdateList.setIsDeleted(0);
        infoUpdateList.setUpdateType(infoUpdateListVo.getUpdateType());
        infoUpdateList.setUpdateDesc(infoUpdateListVo.getUpdateDesc());
        infoUpdateList.setIsPublished(infoUpdateListVo.getIsPublished());
        infoUpdateList.setCreatedDate(DateUtils.getTimestamp());
        infoUpdateList.setCreatedBy(getUserId());

        if (updateFile != null && !updateFile.isEmpty()) {
            try {
                String suffix = updateFile.getOriginalFilename().substring(updateFile.getOriginalFilename().lastIndexOf("."));
                String fileName = IDUtil.createTimeID() + suffix;
                String filePath = updateDir + File.separator + fileName;
                FileUtils.forceMkdir(new File(updateDir));
                updateFile.transferTo(new File(filePath));
                infoUpdateList.setUpdateUrl(requestUpdatePackageUrl + "/" + fileName);
                infoUpdateList.setOriginalFileName(updateFile.getOriginalFilename());
            } catch (Exception e) {
                logger.error("上传文件出错");
                e.printStackTrace();
                return R.error("上传文件出错");
            }
        } else {
            return R.error("请上传更新包!");
        }

        if (infoUpdateListVo.getIsPublished() == 1) {
            infoUpdateList.setPublishDate(DateUtils.getTimestamp());
            infoUpdateListService.updatePublishStatus(infoUpdateListVo.getUpdateType());
        }
        boolean n = infoUpdateListService.save(infoUpdateList);
        if (!n) {
            return R.error("保存出错");
        }

        return R.ok();
    }


    @ApiOperation(value = "修改更新包管理", httpMethod = "POST", notes = "修改更新包管理")
    @SysLog("修改更新包管理")
    @PostMapping("/update")
    public R update(@RequestParam(value = "updateFile", required = false) MultipartFile updateFile, HttpServletRequest request) {
        logger.info("修改更新包管理");

        InfoUpdateListVo infoUpdateListVo = new InfoUpdateListVo();
        RequestUtil.paramToObject(request, infoUpdateListVo);

//        ValidatorUtils.validateEntity(infoUpdateListVo);

        InfoUpdateListVo tmp = (InfoUpdateListVo) infoUpdateListService.getDetailById(Long.valueOf(infoUpdateListVo.getUpdateListId())).get(0);
        if (tmp.getPublishDate() != null && tmp.getPublishDate().toString().length() > 1) {
            return R.error("已发布的不能修改发布状态");
        }

        InfoUpdateListEntity infoUpdateList = new InfoUpdateListEntity();
        infoUpdateList.setUpdateListId(Long.valueOf(infoUpdateListVo.getUpdateListId()));
        infoUpdateList.setUpdateType(infoUpdateListVo.getUpdateType());
        infoUpdateList.setUpdateDesc(infoUpdateListVo.getUpdateDesc());
        infoUpdateList.setIsPublished(infoUpdateListVo.getIsPublished());
        infoUpdateList.setPublishDate(infoUpdateListVo.getPublishDate());
        infoUpdateList.setCreatedDate(null);
        infoUpdateList.setCreatedBy(null);
        infoUpdateList.setModifiedDate(DateUtils.getTimestamp());
        infoUpdateList.setModifiedBy(getUserId());

        if (updateFile != null && !updateFile.isEmpty()) {
            try {
                String suffix = updateFile.getOriginalFilename().substring(updateFile.getOriginalFilename().lastIndexOf("."));
                String fileName = IDUtil.createTimeID() + suffix;
                String filePath = updateDir + File.separator + fileName;
                FileUtils.forceMkdir(new File(updateDir));
                updateFile.transferTo(new File(filePath));
                infoUpdateList.setUpdateUrl(requestUpdatePackageUrl + "/" + fileName);
                infoUpdateList.setOriginalFileName(updateFile.getOriginalFilename());
            } catch (Exception e) {
                logger.error("上传文件出错");
                e.printStackTrace();
                return R.error("上传文件出错");
            }
        }

        if (infoUpdateListVo.getIsPublished() == 1) {
            infoUpdateList.setPublishDate(DateUtils.getTimestamp());
            infoUpdateListService.updatePublishStatus(infoUpdateListVo.getUpdateType());
        }

        boolean n = infoUpdateListService.updateById(infoUpdateList);
        if (n) {
            return R.ok();
        } else {
            return R.error("更新出错");
        }
    }

    @ApiOperation(value = "删除更新包管理", httpMethod = "POST", notes = "删除更新包管理")
    @SysLog("删除更新包管理")
    @PostMapping("/delete/{id}")
    public R delete(@PathVariable("id") String id) {
        logger.info("删除更新包管理,id:" + id);
        long idL = Long.parseLong(id);
        boolean n = infoUpdateListService.delById(idL);
        if (n) {
            return R.ok();
        } else {
            return R.error("删除出错");
        }
    }

    @ApiOperation(value = "批量删除更新包", httpMethod = "POST", notes = "批量删除更新包")
    @SysLog("批量删除更新包")
    @PostMapping("/deleteBatch")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除更新包,id:" + deleteBatchVo);
        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword()))
            return R.error("参数不能为空!");

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) return R.error("密码不正确");

        boolean n = infoUpdateListService.deleteBatch(deleteBatchVo.getIds());
        if (n) {
            return R.ok("批量删除成功!");
        } else {
            return R.error("批量删除出错!");
        }
    }

}
