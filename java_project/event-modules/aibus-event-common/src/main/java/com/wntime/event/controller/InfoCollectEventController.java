package com.wntime.event.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.exception.RRException;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.event.entity.InfoCollectEventEntity;
import com.wntime.event.entity.InfoEventDescriptionEntity;
import com.wntime.event.service.InfoCollectEventService;
import com.wntime.event.service.InfoEventDescriptionService;
import com.wntime.event.vo.InfoCollectEventForm;
import com.wntime.service.common.util.UniqueCheckHelper;
import com.wntime.service.common.vo.DeleteBatchVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;


/**
 * @author Mark
 * @desc 事件采集表
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Api(value = "事件采集管理", tags = {"事件采集管理"})
@RestController
@RequestMapping("event/event-collect")
public class InfoCollectEventController extends AbstractController {
    @Autowired
    private InfoCollectEventService infoCollectEventService;
    @Autowired
    private InfoEventDescriptionService infoEventDescriptionService;

    /**
     * 分页列表
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "collectEvent", value = "事件采集名称", required = false, dataType = "string")
    })
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        try {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getKey().toLowerCase().contains("id")) {
                    String value = (String) entry.getValue();
                    if (value != null && !value.equals("")) {
                        entry.setValue(Long.parseLong(value));
                    } else {
                        entry.setValue((Long) null);
                    }
                }
            }
        } catch (NumberFormatException e) {
            return R.error(e.getMessage());
        }

        PageUtils page = infoCollectEventService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表查询", httpMethod = "GET", notes = "列表查询")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", infoCollectEventService.list(
                new QueryWrapper<InfoCollectEventEntity>()
                        .eq("is_enabled", Constant.Enabled.ENABLE.getValue())
                        .eq("is_deleted", Constant.Deleted.UNDELETED.getValue())));
    }


    /**
     * 信息
     */
    @ApiOperation(value = "单条查询", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        InfoCollectEventEntity entity = infoCollectEventService.getDetailById(id);
        if (entity.getIsDeleted().equals(Constant.Deleted.DELETED.getValue())) {
            return R.error("该数据已被删除");
        }
        return R.ok().put("data", entity);
    }

    /**
     * 详细信息
     */
    @ApiOperation(value = "单条详细信息查询", httpMethod = "GET", notes = "单条详细信息查询")
    @GetMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id) {
        InfoCollectEventEntity entity = infoCollectEventService.getDetailWithExtendById(id);
        if (entity.getIsDeleted().equals(Constant.Deleted.DELETED.getValue())) {
            return R.error("该数据已被删除");
        }
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @SysLog("保存事件采集")
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody InfoCollectEventForm form) {

        ValidatorUtils.validateEntity(form);
        UniqueCheckHelper.assertIsUnique(infoCollectEventService,"collect_code",form.getCollectCode(),"事件采集编码已存在！");

        InfoCollectEventEntity otherEntity = infoCollectEventService.getOne(new QueryWrapper<InfoCollectEventEntity>()
                .eq("event_target_id", form.getEventTargetId())
                .eq("is_deleted",0));
        if(otherEntity != null){
            throw new RRException("标签已被使用!");
        }

        InfoCollectEventEntity entity = new InfoCollectEventEntity();
        BeanUtil.copyProperties(form, entity);

        Timestamp timestamp = DateUtils.getTimestamp();
        entity.setIsDeleted(0);
        entity.setCreatedBy(getUserId());
        entity.setCreatedDate(timestamp);
        infoCollectEventService.save(entity);

        InfoEventDescriptionEntity infoEventDescriptionEntity = new InfoEventDescriptionEntity();
        infoEventDescriptionEntity.setEventTargetId(form.getEventTargetId());
        infoEventDescriptionEntity.setDescriptionContent(form.getDescriptionContent());
        infoEventDescriptionEntity.setCreatedBy(getUserId());
        infoEventDescriptionEntity.setCreatedDate(timestamp);
        infoEventDescriptionService.save(infoEventDescriptionEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改事件采集")
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody InfoCollectEventForm form) {

        ValidatorUtils.validateEntity(form);
        UniqueCheckHelper.assertIsUnique(infoCollectEventService,"collect_code",form.getCollectCode(),
                "collect_event_id",form.getCollectEventId(),"事件采集编码已存在！");

        InfoCollectEventEntity entity = new InfoCollectEventEntity();
        BeanUtil.copyProperties(form, entity);

        Timestamp timestamp = DateUtils.getTimestamp();
        entity.setModifiedBy(getUserId());
        entity.setModifiedDate(timestamp);
        infoCollectEventService.updateById(entity);

        Long eventDescriptionId = form.getEventDescriptionId();
        if (eventDescriptionId != null) {
            InfoEventDescriptionEntity infoEventDescriptionEntity = new InfoEventDescriptionEntity();
            infoEventDescriptionEntity.setEventDescriptionId(eventDescriptionId);
            infoEventDescriptionEntity.setEventTargetId(form.getEventTargetId());
            infoEventDescriptionEntity.setDescriptionContent(form.getDescriptionContent());
            infoEventDescriptionEntity.setModifiedBy(getUserId());
            infoEventDescriptionEntity.setModifiedDate(timestamp);
            infoEventDescriptionService.updateById(infoEventDescriptionEntity);
        } else {
            InfoEventDescriptionEntity infoEventDescriptionEntity = new InfoEventDescriptionEntity();
            infoEventDescriptionEntity.setEventTargetId(form.getEventTargetId());
            infoEventDescriptionEntity.setDescriptionContent(form.getDescriptionContent());
            infoEventDescriptionEntity.setCreatedBy(getUserId());
            infoEventDescriptionEntity.setCreatedDate(timestamp);
            infoEventDescriptionService.save(infoEventDescriptionEntity);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除事件采集")
    @ApiOperation(value = "单条删除", httpMethod = "DELETE", notes = "管理页面中删除")
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id) {
        logger.info("单条删除,id:" + id);
        try {
            infoCollectEventService.delById(id, getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("修改失败");
        }
        return R.ok();
    }

    @SysLog("批量删除事件采集")
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
            infoCollectEventService.deleteBatch(deleteBatchVo.getIds(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("批量删除出错!");
        }

        return R.ok("批量删除成功!");
    }

}
