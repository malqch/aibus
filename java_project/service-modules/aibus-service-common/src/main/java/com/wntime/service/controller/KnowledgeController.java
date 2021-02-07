package com.wntime.service.controller;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.wntime.common.utils.R;
import com.wntime.service.entity.InfoConfigParamEntity;
import com.wntime.service.service.InfoConfigParamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ysc
 * 2020/8/28 20:02
 */

@Api(value = "知识库接口",tags = {"知识库接口"})
@RequestMapping("/service/knowledge")
@RestController
public class KnowledgeController {

    @Autowired
    private InfoConfigParamService infoConfigParamService;

    @ApiOperation(value = "获取故障车辆类型知识",httpMethod = "GET",notes = "")
    @GetMapping("/fault/stat")
    public R getFaultTypeKnowledge(){
        InfoConfigParamEntity entity = infoConfigParamService.getFaultTypeKnowledge();
        return R.ok().put("text",entity.getParamChar());
    }
}
