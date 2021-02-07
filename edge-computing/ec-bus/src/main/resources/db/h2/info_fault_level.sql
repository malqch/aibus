CREATE TABLE info_fault_level (
  fault_level_id bigint(20) NOT NULL COMMENT '@desc 主键',
  fault_level_name varchar(20) DEFAULT NULL COMMENT '@desc 故障级别',
  fault_level_code varchar(20) DEFAULT NULL COMMENT '@desc 故障级别',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (fault_level_id)
) COMMENT='@desc 故障级别表';