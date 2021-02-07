CREATE TABLE info_bus_station (
  bus_station_id bigint(20) NOT NULL,
  bus_station_name varchar(50) DEFAULT NULL COMMENT '公交车站名称',
  bus_station_code varchar(50) DEFAULT NULL COMMENT '公交车站编码',
  bus_station_longitude decimal(10,6) DEFAULT NULL COMMENT '经度',
  bus_station_latitude decimal(10,6) DEFAULT NULL COMMENT '纬度',
  bus_station_deviation int(11) DEFAULT NULL COMMENT '偏差(米)',
  PRIMARY KEY (bus_station_id)
) COMMENT='公交车站表';