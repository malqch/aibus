

package com.wntime.modules.sys.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 登录表单
 */

@ApiModel(value="分页表单对象",description="分页表单")
public class SysPageForm implements Serializable{

    private static final long serialVersionUID = 8185297053049427374L;
    @ApiModelProperty(value="当前页数",name="page",example="1")
    private Integer page;

    @ApiModelProperty(value="每页条数",name="limit",example="10")
    private Integer limit;

    @ApiModelProperty(value="名称(用户名|菜单名)",name="name",example="admin")
    private String name;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
