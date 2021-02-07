CREATE TABLE local_info_bus (
  local_id int(10) NOT NULL COMMENT '本地ID',
  bus_id bigint(20) DEFAULT NULL COMMENT '@desc 主键',
  factory_id bigint(20) DEFAULT NULL COMMENT '@desc 公交车厂Id',
  bus_type_id bigint(20) DEFAULT NULL COMMENT '@desc 主键',
  plate_code varchar(16) DEFAULT NULL COMMENT '@desc 车牌照号',
  vin_code varchar(64) DEFAULT NULL COMMENT '@desc VIN编号',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  is_enabled smallint(6) DEFAULT NULL COMMENT '@desc 是否启用',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (local_id)
) COMMENT='@desc 公交车表';
INSERT INTO local_info_bus(local_id) values(1);