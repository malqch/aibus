package com.wntime.event.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.event.entity.InfoEventLevelEntity;
import com.wntime.event.service.InfoEventLevelService;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * @author Mark
 * 
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Api(value = "事件级别管理", tags = {"事件级别管理"})
@RestController
@RequestMapping("event/event-level")
public class InfoEventLevelController extends AbstractController {
    @Autowired
    private InfoEventLevelService infoEventLevelService;

    /**
     * 分页列表
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "eventLevelName", value = "事件级别名称", required = false, dataType = "string")
    })
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoEventLevelService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表查询", httpMethod = "GET", notes = "列表查询")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", infoEventLevelService.list(
                new QueryWrapper<InfoEventLevelEntity>()
                        .eq("is_enabled", Constant.Enabled.ENABLE.getValue())
                        .eq("is_deleted", Constant.Deleted.UNDELETED.getValue())));
    }


    /**
     * 信息
     */
    @ApiOperation(value = "单条查询", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        InfoEventLevelEntity entity = infoEventLevelService.getById(id);
        if(entity.getIsDeleted().equals(Constant.Deleted.DELETED.getValue())){
            return R.error("该数据已被删除");
        }
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @SysLog("保存事件级别")
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody InfoEventLevelEntity entity) {
        ValidatorUtils.validateEntity(entity);
        UniqueCheckHelper.assertIsUnique(infoEventLevelService,"event_level_code",entity.getEventLevelCode(),"事件级别编码已存在！");

        entity.setIsDeleted(0);
        entity.setCreatedBy(getUserId());
        entity.setCreatedDate(DateUtils.getTimestamp());
        infoEventLevelService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改事件级别")
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody InfoEventLevelEntity entity) {
        ValidatorUtils.validateEntity(entity);
        UniqueCheckHelper.assertIsUnique(infoEventLevelService,"event_level_code",entity.getEventLevelCode(),"event_level_id",entity.getEventLevelId(),"事件级别编码已存在！");

        entity.setModifiedBy(getUserId());
        entity.setModifiedDate(DateUtils.getTimestamp());
        infoEventLevelService.updateById(entity);

        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除事件级别")
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "管理页面中删除")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoEventLevelService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @SysLog("批量删除事件级别")
    @ApiOperation(value = "批量删除", httpMethod = "DELETE", notes = "批量删除")
    @DeleteMapping("/list/delete")
    public R deleteBatch(@RequestBody DeleteBatchVo deleteBatchVo) {
        logger.info("批量删除,:" + Arrays.toString(deleteBatchVo.getIds()));

        if (deleteBatchVo.getIds() == null || deleteBatchVo.getIds().length == 0 || StringUtils.isEmpty(deleteBatchVo.getValidatePassword())) {
            return R.error("参数不能为空!");
        }

        //校验当前用户的密码是否正确
        if (!validatePassword(deleteBatchVo.getValidatePassword())) {
            return R.error("密码不正确");
        }
        try {
            infoEventLevelService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }


}
