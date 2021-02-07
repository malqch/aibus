package com.wntime.ec.module.sys.controller;

import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.common.util.param.page.Page;
import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import com.wntime.ec.module.sys.entity.InfoAdvertisePosition;
import com.wntime.ec.module.sys.vo.InfoAdvertisePositionQryReqVo;
import com.wntime.ec.module.sys.vo.InfoAdvertisePositionQryRspVo;
import com.wntime.ec.module.sys.service.IInfoAdvertisePositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.Collection;
import java.util.List;

/**
 * 广告位表管理
 */
@Api(value = "广告位服务接口",tags="广告位服务接口")
@RestController
@RequestMapping("/InfoAdvertisePosition")
@Slf4j
@SuppressWarnings("all")
public class InfoAdvertisePositionController extends BaseController {

    @Autowired
    private IInfoAdvertisePositionService infoAdvertisePositionService;


    /**
    * 增加接口
    */
    @PostMapping("/insert")
    @ApiOperation(value="增加接口", notes="增加接口",response=InfoAdvertisePosition.class)
    public HttpRspParam insert(@RequestBody @Validated(AddGroup.class) InfoAdvertisePosition infoAdvertisePosition){
        return success(infoAdvertisePositionService.insert(infoAdvertisePosition));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRspParam deleteById(@PathVariable("id") Long id){
        infoAdvertisePositionService.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @PostMapping("/deleteByIds")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRspParam deleteByIds(@RequestBody Collection<Long> ids){
        infoAdvertisePositionService.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PostMapping("/updateById")
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRspParam updateById(@RequestBody @Validated({UpdateGroup.class}) InfoAdvertisePosition infoAdvertisePosition){
        infoAdvertisePositionService.updateById(infoAdvertisePosition);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @PostMapping("/selectById/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= InfoAdvertisePositionQryRspVo.class)
    public HttpRspParam selectById(@PathVariable("id") Long id){
        InfoAdvertisePositionQryRspVo infoAdvertisePositionQryRspVo = infoAdvertisePositionService.selectById(id);
        return success(infoAdvertisePositionQryRspVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/selectByIds")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response= InfoAdvertisePositionQryRspVo[].class)
    public HttpRspParam selectByIds(@RequestBody Collection<Long> ids){
        List<InfoAdvertisePositionQryRspVo> infoAdvertisePositionQryRspVos = infoAdvertisePositionService.selectByIds(ids);
        return success(infoAdvertisePositionQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectList")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoAdvertisePositionQryRspVo[].class)
    public HttpRspParam selectList(@RequestBody InfoAdvertisePositionQryReqVo infoAdvertisePositionQryReqVo){
        List<InfoAdvertisePositionQryRspVo> infoAdvertisePositionQryRspVos = infoAdvertisePositionService.selectList(infoAdvertisePositionQryReqVo);
        return success(infoAdvertisePositionQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectListByMap")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoAdvertisePositionQryRspVo[].class)
    public HttpRspParam selectListByMap(@RequestBody Map map){
        List<InfoAdvertisePositionQryRspVo> infoAdvertisePositionQryRspVos = infoAdvertisePositionService.selectListByMap(map);
        return success(infoAdvertisePositionQryRspVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response= InfoAdvertisePositionQryRspVo[].class)
    public HttpRspParam selectPage(@RequestBody Page<InfoAdvertisePositionQryRspVo, InfoAdvertisePositionQryReqVo> page){
        Page<InfoAdvertisePositionQryRspVo, InfoAdvertisePositionQryReqVo> PageResp = infoAdvertisePositionService.selectPage(page);
        return success(PageResp);
    }

    

}
