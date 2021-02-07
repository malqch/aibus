package com.wntime.ec.module.sys.controller;

import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.common.util.param.page.Page;
import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import com.wntime.ec.module.sys.entity.InfoDeviceType;
import com.wntime.ec.module.sys.service.IInfoDeviceTypeService;
import com.wntime.ec.module.sys.vo.InfoDeviceTypeQryReqVo;
import com.wntime.ec.module.sys.vo.InfoDeviceTypeQryRspVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @desc 设备类型管理
 */
@Api(value = "@desc 设备类型接口",tags="@desc 设备类型接口")
@RestController
@RequestMapping("/InfoDeviceType")
@Slf4j
@SuppressWarnings("all")
public class InfoDeviceTypeController extends BaseController {

    @Autowired
    private IInfoDeviceTypeService infoDeviceTypeService;


    /**
    * 增加接口
    */
    @PostMapping("/insert")
    @ApiOperation(value="增加接口", notes="增加接口",response=InfoDeviceType.class)
    public HttpRspParam insert(@RequestBody @Validated(AddGroup.class) InfoDeviceType infoDeviceType){
        return success(infoDeviceTypeService.insert(infoDeviceType));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRspParam deleteById(@PathVariable("id") Long id){
        infoDeviceTypeService.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @PostMapping("/deleteByIds")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRspParam deleteByIds(@RequestBody Collection<Long> ids){
        infoDeviceTypeService.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PostMapping("/updateById")
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRspParam updateById(@RequestBody @Validated({UpdateGroup.class}) InfoDeviceType infoDeviceType){
        infoDeviceTypeService.updateById(infoDeviceType);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @PostMapping("/selectById/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= InfoDeviceTypeQryRspVo.class)
    public HttpRspParam selectById(@PathVariable("id") Long id){
        InfoDeviceTypeQryRspVo infoDeviceTypeQryRspVo = infoDeviceTypeService.selectById(id);
        return success(infoDeviceTypeQryRspVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/selectByIds")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response= InfoDeviceTypeQryRspVo[].class)
    public HttpRspParam selectByIds(@RequestBody Collection<Long> ids){
        List<InfoDeviceTypeQryRspVo> infoDeviceTypeQryRspVos = infoDeviceTypeService.selectByIds(ids);
        return success(infoDeviceTypeQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectList")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoDeviceTypeQryRspVo[].class)
    public HttpRspParam selectList(@RequestBody InfoDeviceTypeQryReqVo infoDeviceTypeQryReqVo){
        List<InfoDeviceTypeQryRspVo> infoDeviceTypeQryRspVos = infoDeviceTypeService.selectList(infoDeviceTypeQryReqVo);
        return success(infoDeviceTypeQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectListByMap")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoDeviceTypeQryRspVo[].class)
    public HttpRspParam selectListByMap(@RequestBody Map map){
        List<InfoDeviceTypeQryRspVo> infoDeviceTypeQryRspVos = infoDeviceTypeService.selectListByMap(map);
        return success(infoDeviceTypeQryRspVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response= InfoDeviceTypeQryRspVo[].class)
    public HttpRspParam selectPage(@RequestBody Page<InfoDeviceTypeQryRspVo, InfoDeviceTypeQryReqVo> page){
        Page<InfoDeviceTypeQryRspVo, InfoDeviceTypeQryReqVo> PageResp = infoDeviceTypeService.selectPage(page);
        return success(PageResp);
    }

    

}
