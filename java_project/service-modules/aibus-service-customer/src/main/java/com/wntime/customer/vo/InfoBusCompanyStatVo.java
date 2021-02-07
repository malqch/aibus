package com.wntime.customer.vo;

import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @desc 公交公司表
 * 
 * @date 2020-08-25 14:04:05
 */
@Data
public class InfoBusCompanyStatVo {

	/**
	 * @desc 主键
	 */
	private Long companyId;
	/**
	 * @desc 区域主键
	 */
	private Long areaId;
	/**
	 * @desc 公交公司名称
	 */
	private String companyName;

	private List<InfoCompanyBusTypeStatVo> orderData = new ArrayList<>();

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<InfoCompanyBusTypeStatVo> getOrderData() {
		return orderData;
	}

	public void setOrderData(List<InfoCompanyBusTypeStatVo> orderData) {
		this.orderData = orderData;
	}
}
