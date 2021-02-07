package com.wntime.event.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.common.utils.Constant;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.event.entity.LogEventDetailEntity;
import com.wntime.event.service.LogEventDetailService;
import com.wntime.event.vo.LogEventDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;



/**
 * @desc 事件日志表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2020-08-25 13:34:24
 */
@Api(value = "事件日志管理", tags = {"事件日志管理"})
@RestController
@RequestMapping("event/log-detail")
public class LogEventDetailController extends AbstractController {
    @Autowired
    private LogEventDetailService logEventDetailService;

    /**
     * 分页列表
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
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
        try {
            String startTimeStr = (String) params.get("startTime");
            String endTimeStr = (String) params.get("endTime");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(StringUtils.hasLength(startTimeStr)) {
                Date startTime = simpleDateFormat.parse(startTimeStr);
                params.put("startTime",startTime);
            }else{
                params.put("startTime",(Date)null);
            }
            if(StringUtils.hasLength(endTimeStr)) {
                Date endTime = simpleDateFormat.parse(endTimeStr);
                params.put("endTime",endTime);
            }else{
                params.put("endTime",(Date)null);
            }


        }catch (ParseException e){
            return R.error(e.getMessage());
        }
        PageUtils page = logEventDetailService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @ApiOperation(value = "列表查询", httpMethod = "GET", notes = "列表查询")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("list", logEventDetailService.list(
                new QueryWrapper<LogEventDetailEntity>()
                        .eq("is_enabled", Constant.Enabled.ENABLE.getValue())));
    }


    /**
     * 信息
     */
    @ApiOperation(value = "单条查询", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        LogEventDetailVo entity = logEventDetailService.getDetailById(id);
        return R.ok().put("data", entity);
    }

    /**
     * 信息
     */
    @ApiOperation(value = "单条查询带附件", httpMethod = "GET", notes = "单条查询")
    @GetMapping("/detail/{id}")
    public R infoWithAttach(@PathVariable("id") Long id) {
        LogEventDetailVo entity = logEventDetailService.getDetailWithAttachById(id);
        return R.ok().put("data", entity);
    }

    /**
     * 保存
     */
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody LogEventDetailEntity entity) {
        entity.setCreatedDate(DateUtils.getTimestamp());
        logEventDetailService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody LogEventDetailEntity entity) {
        ValidatorUtils.validateEntity(entity);
        entity.setModifiedDate(DateUtils.getTimestamp());
        logEventDetailService.updateById(entity);

        return R.ok();
    }

}
