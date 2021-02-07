package com.wntime.ec.module.sys.controller;

import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.common.util.param.page.Page;
import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import com.wntime.ec.module.sys.entity.InfoEventTarget;
import com.wntime.ec.module.sys.vo.InfoEventTargetQryReqVo;
import com.wntime.ec.module.sys.vo.InfoEventTargetQryRspVo;
import com.wntime.ec.module.sys.service.IInfoEventTargetService;
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
 * @desc 事件标签表管理
 */
@Api(value = "@desc 事件标签服务接口",tags="@desc 事件标签服务接口")
@RestController
@RequestMapping("/InfoEventTarget")
@Slf4j
@SuppressWarnings("all")
public class InfoEventTargetController extends BaseController {

    @Autowired
    private IInfoEventTargetService infoEventTargetService;


    /**
    * 增加接口
    */
    @PostMapping("/insert")
    @ApiOperation(value="增加接口", notes="增加接口",response=InfoEventTarget.class)
    public HttpRspParam insert(@RequestBody @Validated(AddGroup.class) InfoEventTarget infoEventTarget){
        return success(infoEventTargetService.insert(infoEventTarget));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRspParam deleteById(@PathVariable("id") Long id){
        infoEventTargetService.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @PostMapping("/deleteByIds")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRspParam deleteByIds(@RequestBody Collection<Long> ids){
        infoEventTargetService.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PostMapping("/updateById")
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRspParam updateById(@RequestBody @Validated({UpdateGroup.class}) InfoEventTarget infoEventTarget){
        infoEventTargetService.updateById(infoEventTarget);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @PostMapping("/selectById/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= InfoEventTargetQryRspVo.class)
    public HttpRspParam selectById(@PathVariable("id") Long id){
        InfoEventTargetQryRspVo infoEventTargetQryRspVo = infoEventTargetService.selectById(id);
        return success(infoEventTargetQryRspVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/selectByIds")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response= InfoEventTargetQryRspVo[].class)
    public HttpRspParam selectByIds(@RequestBody Collection<Long> ids){
        List<InfoEventTargetQryRspVo> infoEventTargetQryRspVos = infoEventTargetService.selectByIds(ids);
        return success(infoEventTargetQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectList")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoEventTargetQryRspVo[].class)
    public HttpRspParam selectList(@RequestBody InfoEventTargetQryReqVo infoEventTargetQryReqVo){
        List<InfoEventTargetQryRspVo> infoEventTargetQryRspVos = infoEventTargetService.selectList(infoEventTargetQryReqVo);
        return success(infoEventTargetQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectListByMap")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= InfoEventTargetQryRspVo[].class)
    public HttpRspParam selectListByMap(@RequestBody Map map){
        List<InfoEventTargetQryRspVo> infoEventTargetQryRspVos = infoEventTargetService.selectListByMap(map);
        return success(infoEventTargetQryRspVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response= InfoEventTargetQryRspVo[].class)
    public HttpRspParam selectPage(@RequestBody Page<InfoEventTargetQryRspVo, InfoEventTargetQryReqVo> page){
        Page<InfoEventTargetQryRspVo, InfoEventTargetQryReqVo> PageResp = infoEventTargetService.selectPage(page);
        return success(PageResp);
    }

    

}
