package com.wntime.ec.module.sys.controller;

import com.wntime.ec.common.util.BaseController;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import com.wntime.ec.common.util.param.page.Page;
import com.wntime.ec.common.util.validator.group.AddGroup;
import com.wntime.ec.common.util.validator.group.UpdateGroup;
import com.wntime.ec.module.sys.entity.OrderDeliveryArea;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryReqVo;
import com.wntime.ec.module.sys.vo.OrderDeliveryAreaQryRspVo;
import com.wntime.ec.module.sys.service.IOrderDeliveryAreaService;
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
 * 投放范围表管理
 */
@Api(value = "投放范围服务接口",tags="投放范围服务接口")
@RestController
@RequestMapping("/OrderDeliveryArea")
@Slf4j
@SuppressWarnings("all")
public class OrderDeliveryAreaController extends BaseController {

    @Autowired
    private IOrderDeliveryAreaService orderDeliveryAreaService;


    /**
    * 增加接口
    */
    @PostMapping("/insert")
    @ApiOperation(value="增加接口", notes="增加接口",response=OrderDeliveryArea.class)
    public HttpRspParam insert(@RequestBody @Validated(AddGroup.class) OrderDeliveryArea orderDeliveryArea){
        return success(orderDeliveryAreaService.insert(orderDeliveryArea));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRspParam deleteById(@PathVariable("id") Long id){
        orderDeliveryAreaService.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @PostMapping("/deleteByIds")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRspParam deleteByIds(@RequestBody Collection<Long> ids){
        orderDeliveryAreaService.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PostMapping("/updateById")
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRspParam updateById(@RequestBody @Validated({UpdateGroup.class}) OrderDeliveryArea orderDeliveryArea){
        orderDeliveryAreaService.updateById(orderDeliveryArea);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @PostMapping("/selectById/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= OrderDeliveryAreaQryRspVo.class)
    public HttpRspParam selectById(@PathVariable("id") Long id){
        OrderDeliveryAreaQryRspVo orderDeliveryAreaQryRspVo = orderDeliveryAreaService.selectById(id);
        return success(orderDeliveryAreaQryRspVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/selectByIds")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response= OrderDeliveryAreaQryRspVo[].class)
    public HttpRspParam selectByIds(@RequestBody Collection<Long> ids){
        List<OrderDeliveryAreaQryRspVo> orderDeliveryAreaQryRspVos = orderDeliveryAreaService.selectByIds(ids);
        return success(orderDeliveryAreaQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectList")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= OrderDeliveryAreaQryRspVo[].class)
    public HttpRspParam selectList(@RequestBody OrderDeliveryAreaQryReqVo orderDeliveryAreaQryReqVo){
        List<OrderDeliveryAreaQryRspVo> orderDeliveryAreaQryRspVos = orderDeliveryAreaService.selectList(orderDeliveryAreaQryReqVo);
        return success(orderDeliveryAreaQryRspVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectListByMap")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response= OrderDeliveryAreaQryRspVo[].class)
    public HttpRspParam selectListByMap(@RequestBody Map map){
        List<OrderDeliveryAreaQryRspVo> orderDeliveryAreaQryRspVos = orderDeliveryAreaService.selectListByMap(map);
        return success(orderDeliveryAreaQryRspVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response= OrderDeliveryAreaQryRspVo[].class)
    public HttpRspParam selectPage(@RequestBody Page<OrderDeliveryAreaQryRspVo, OrderDeliveryAreaQryReqVo> page){
        Page<OrderDeliveryAreaQryRspVo, OrderDeliveryAreaQryReqVo> PageResp = orderDeliveryAreaService.selectPage(page);
        return success(PageResp);
    }

    

}
