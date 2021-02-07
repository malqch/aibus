CREATE TABLE info_event_target (
  event_target_id bigint(20) NOT NULL COMMENT '@desc 主键',
  event_target_name varchar(20) DEFAULT NULL COMMENT '@desc 事件标签',
  event_target_grope varchar(32) DEFAULT NULL COMMENT '@desc 标签分类编码\r\n    @value link、value、char',
  event_target_code varchar(32) DEFAULT NULL COMMENT '@desc 事件标签编码',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (event_target_id)
)COMMENT='@desc 事件标签表';