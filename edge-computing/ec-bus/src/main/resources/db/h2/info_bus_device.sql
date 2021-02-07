CREATE TABLE info_bus_device (
  device_code varchar(64) NOT NULL COMMENT '@desc 设备编号',
  device_desc_code varchar(64) DEFAULT NULL COMMENT '@desc 设备描述编码',
  bus_device_id bigint(20) DEFAULT NULL COMMENT '@desc 主键',
  device_type_id bigint(20) DEFAULT NULL COMMENT '@desc 设备类型Id',
  bus_id bigint(20) DEFAULT NULL COMMENT '@desc 公交车Id',
  device_name varchar(32) DEFAULT NULL COMMENT '@desc 设备名称',
  device_status smallint(6) DEFAULT NULL COMMENT '@desc 设备状态\r\n    @value 连接中，在线，离线',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (device_code)
) COMMENT='@desc AI设备表';