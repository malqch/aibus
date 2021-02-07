package com.wntime.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.StringJoiner;

/**
 * 系统参数表
 *
 * @author buxl
 * @email
 * @date 2019-09-27 14:50:20
 */
@TableName("admin_parameters")
public class AdminParametersEntity extends BaseEntity {
    private static final long serialVersionUID = -1130931802066730096L;
    /**
     * 参数类型
     */
    @TableId
    private String paramType;
    /**
     * 参数编码
     */
    private String paramCode;
    /**
     * 参数名称
     */
    private String paramName;

    private String paramEname;
    /**
     * 是否缓存到系统中
     */
    private Boolean isCache;
    /**
     * 父级参数代码
     */
    private String parentParamCode;
    /**
     * 显示顺序
     */
    private Integer showOrder;

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamEname() {
        return paramEname;
    }

    public void setParamEname(String paramEname) {
        this.paramEname = paramEname;
    }

    public Boolean getCache() {
        return isCache;
    }

    public void setCache(Boolean cache) {
        isCache = cache;
    }

    public String getParentParamCode() {
        return parentParamCode;
    }

    public void setParentParamCode(String parentParamCode) {
        this.parentParamCode = parentParamCode;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AdminParametersEntity.class.getSimpleName() + "[", "]")
                .add("paramType='" + paramType + "'")
                .add("paramCode='" + paramCode + "'")
                .add("paramName='" + paramName + "'")
                .add("paramEname='" + paramEname + "'")
                .add("isCache=" + isCache)
                .add("parentParamCode='" + parentParamCode + "'")
                .add("showOrder=" + showOrder)
                .toString();
    }
}
