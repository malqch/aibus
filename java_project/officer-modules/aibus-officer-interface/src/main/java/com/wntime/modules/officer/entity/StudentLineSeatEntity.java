package com.wntime.modules.officer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @desc 学生线路与坐位
 * 
 * @author raute
 * @email admin@wntime.com
 * @date 2021-01-20 20:24:11
 */
@Data
@TableName("student_line_seat")
public class StudentLineSeatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * @desc 主键
	 */
	@TableId
	private Long id;
	/**
	 * @desc 学生id
	 */
	private Long studentId;
	/**
	 * 上学线路/上行
	 */
	private Long upCompanyLineId;
	/**
	 * 放学线路/下行
	 */
	private Long offCompanyLineId;
	/**
	 * @desc 座位号
	 */
	private Long seatNo;
	/**
	 * @desc 上学上车站点
	 */
	private Long upStationId;
	/**
	 * @desc 放学下车站点
	 */
	private Long offStationId;
	/**
	 * @desc 创建人
	 */
	private Long createUserId;
	/**
	 * @desc 创建时间
	 */
	private Date createDt;
	/**
	 * @desc 更新人
	 */
	private Long modifyUserId;
	/**
	 * @desc 更新时间
	 */
	private Date modifyDt;
	private String isDeleted;

	@TableField(exist = false)
	private String createUserName;

	@TableField(exist = false)
	private String modifiedUserName;

	private Long defaultUpStationId;
	private Long defaultOffStationId;
}
