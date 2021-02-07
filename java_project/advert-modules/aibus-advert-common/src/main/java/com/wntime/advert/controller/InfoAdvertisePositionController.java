package com.wntime.advert.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wntime.advert.entity.InfoAdvertiseTargetEntity;
import com.wntime.advert.vo.AdvertisePositionVo;
import com.wntime.common.annotation.SysLog;
import com.wntime.common.utils.DateUtils;
import com.wntime.common.validator.ValidatorUtils;
import com.wntime.controller.AbstractController;
import com.wntime.service.common.service.ConfigParamInfoService;
import com.wntime.service.common.vo.ConfigParamVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wntime.advert.entity.InfoAdvertisePositionEntity;
import com.wntime.advert.service.InfoAdvertisePositionService;
import com.wntime.common.utils.PageUtils;
import com.wntime.common.utils.R;



/**
 * ${comments}
 *
 * @author ysc
 * @email example@gmail.com
 * @date 2020-11-05 14:17:58
 */
@Api(value = "广告位管理", tags = {"广告位管理"})
@RestController
@RequestMapping("advert/operation/advertisePosition")
public class InfoAdvertisePositionController extends AbstractController {

    @Autowired
    private InfoAdvertisePositionService infoAdvertisePositionService;

    @Autowired
    private ConfigParamInfoService configParamInfoService;

    /**
     * 列表
     */
    @ApiOperation(value = "广告位列表获取", httpMethod = "GET", notes = "")
    @GetMapping("/list")
    public R list(){
        List<AdvertisePositionVo> list = infoAdvertisePositionService.listAll();
        return R.ok().put("list", list);
    }


    /**
     * 分页列表
     */
    @ApiOperation(value = "分页查询", httpMethod = "GET", notes = "分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "page", value = "请求页码", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "limit", value = "每页数量", required = true, dataType = "int"),
            @ApiImplicitParam(paramType = "query", name = "positionGroup", value = "广告位分类", required = false, dataType = "string"),
    })
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {
        PageUtils page = infoAdvertisePositionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{advertisePositionId}")
//    @RequiresPermissions("advert:infoadvertiseposition:info")
    public R info(@PathVariable("advertisePositionId") Long advertisePositionId){
		InfoAdvertisePositionEntity infoAdvertisePosition = infoAdvertisePositionService.getById(advertisePositionId);

        return R.ok().put("infoAdvertisePosition", infoAdvertisePosition);
    }

    /**
     * 保存
     */
    @SysLog("保存广告位")
    @ApiOperation(value = "单条新增", httpMethod = "POST", notes = "单条新增")
    @PostMapping("/save")
    public R save(@RequestBody InfoAdvertisePositionEntity entity) {
        ValidatorUtils.validateEntity(entity);
        QueryWrapper<InfoAdvertisePositionEntity> QueryWrapper = new QueryWrapper<InfoAdvertisePositionEntity>()
                .eq("position_code",entity.getPositionCode());

        InfoAdvertisePositionEntity get = infoAdvertisePositionService.getOne(QueryWrapper);
        if(get != null){
            return R.error("该广告位编码已存在！");
        }

        entity.setCreatedBy(getUserId());
        entity.setCreatedDate(DateUtils.getTimestamp());
        infoAdvertisePositionService.save(entity);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改广告位")
    @ApiOperation(value = "单条修改", httpMethod = "PUT", notes = "单条修改")
    @PutMapping("/update")
    public R update(@RequestBody InfoAdvertisePositionEntity entity) {
        ValidatorUtils.validateEntity(entity);

        QueryWrapper<InfoAdvertisePositionEntity> QueryWrapper = new QueryWrapper<InfoAdvertisePositionEntity>()
                .ne("advertise_position_id",entity.getAdvertisePositionId())
                .eq("position_code",entity.getPositionCode());

        InfoAdvertisePositionEntity get = infoAdvertisePositionService.getOne(QueryWrapper);
        if(get != null){
            return R.error("该广告位编码已存在！");
        }
        entity.setModifiedBy(getUserId());
        entity.setModifiedDate(DateUtils.getTimestamp());
        infoAdvertisePositionService.updateById(entity);

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advert:infoadvertiseposition:delete")
    public R delete(@RequestBody Long[] advertisePositionIds){
		infoAdvertisePositionService.removeByIds(Arrays.asList(advertisePositionIds));

        return R.ok();
    }

}
