CREATE TABLE info_collect_event (
  collect_event_id bigint(20) NOT NULL COMMENT '@desc 主键',
  collect_event varchar(20) DEFAULT NULL COMMENT '@desc 采集内容',
  collect_code varchar(20) DEFAULT NULL COMMENT '@desc 采集编码',
  event_type_id bigint(20) DEFAULT NULL COMMENT '@desc 事件类型Id',
  event_target_id bigint(20) DEFAULT NULL COMMENT '@desc 事件标签Id',
  event_level_id bigint(20) DEFAULT NULL COMMENT '@desc 事件级别Id',
  device_type_id bigint(20) DEFAULT NULL COMMENT '@desc 设备类型Id',
  event_type smallint(6) DEFAULT NULL COMMENT '@desc 事件大类',
  event_detail smallint(6) DEFAULT NULL COMMENT '@desc 事件小类',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (collect_event_id)
) COMMENT='@desc 事件采集表';