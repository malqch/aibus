CREATE TABLE info_line_station (
  line_station_id bigint(20) NOT NULL COMMENT 'id',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id',
  bus_station_id bigint(20) DEFAULT NULL COMMENT '公交车站Id',
  station_order int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (line_station_id)
) COMMENT='线路车站表';