CREATE TABLE info_fault_target (
  fault_target_id bigint(20) NOT NULL COMMENT '@desc 主键',
  fault_target_name varchar(20) DEFAULT NULL COMMENT '@desc 故障标签名称',
  fault_target_grope varchar(32) DEFAULT NULL COMMENT '@desc 标签分类编码\r\n    @value link、value、char',
  fault_target_code varchar(20) DEFAULT NULL COMMENT '@desc 故障标签编码',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (fault_target_id)
) COMMENT='@desc 故障标签表';