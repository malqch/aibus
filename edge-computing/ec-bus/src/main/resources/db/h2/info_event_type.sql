CREATE TABLE info_event_type (
  event_type_id bigint(20) NOT NULL COMMENT '@desc 主键',
  event_type_name varchar(20) DEFAULT NULL COMMENT '@desc 事件类型',
  event_type_code varchar(32) DEFAULT NULL COMMENT '@desc 事件编码',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (event_type_id)
) COMMENT='@desc 事件类型表';