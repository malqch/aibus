CREATE TABLE info_collect_fault (
  collect_event_id bigint(20) NOT NULL COMMENT '@desc 主键',
  collect_event varchar(20) DEFAULT NULL COMMENT '@desc 采集内容',
  collect_code varchar(20) DEFAULT NULL COMMENT '@desc 采集编码',
  fault_type_id bigint(20) DEFAULT NULL COMMENT '@desc 故障类型Id',
  fault_target_id bigint(20) DEFAULT NULL COMMENT '@desc 故障标签Id',
  fault_level_id bigint(20) DEFAULT NULL COMMENT '@desc 故障级别Id',
  fault_type varchar(64) DEFAULT NULL COMMENT '@desc 故障ID',
  fault_detail varchar(64) DEFAULT NULL COMMENT '@desc 故障索引码',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (collect_event_id)
) COMMENT='@desc 故障采集表';