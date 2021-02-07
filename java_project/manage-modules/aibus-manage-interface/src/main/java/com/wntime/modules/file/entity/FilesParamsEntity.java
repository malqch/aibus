package com.wntime.modules.file.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件类型参数表
 * 
 * @author wing
 * @email wangyangyang@wntime.com
 * @date 2019-10-14 10:34:29
 */
@TableName("files_params")
public class FilesParamsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Long id;
	/**
	 * 名称
	 */
	private String typeName;
	/**
	 * 文件分类 1 安装程序；2 文件
	 */
	private String fileClass;
	/**
	 * 是否启用
	 */
	private String isActive;
	/**
	 * 默认安装路径
	 */
	private String defaultInstallPath;
	/**
	 * 是否已删除
	 */
	private String isDelete;
	/**
	 * 创建人ID
	 */
	private Long createUserId;
	/**
	 * 创建时间
	 */
	private Date createDt;
	/**
	 * 修改人ID
	 */
	private Long modifyUserId;
	/**
	 * 修改时间
	 */
	private Date modifyDt;

	private String fileSuffixes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getFileClass() {
		return fileClass;
	}

	public void setFileClass(String fileClass) {
		this.fileClass = fileClass;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDefaultInstallPath() {
		return defaultInstallPath;
	}

	public void setDefaultInstallPath(String defaultInstallPath) {
		this.defaultInstallPath = defaultInstallPath;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public Long getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(Long modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	public Date getModifyDt() {
		return modifyDt;
	}

	public void setModifyDt(Date modifyDt) {
		this.modifyDt = modifyDt;
	}

	public String getFileSuffixes() {
		return fileSuffixes;
	}

	public void setFileSuffixes(String fileSuffixes) {
		this.fileSuffixes = fileSuffixes;
	}
}
