CREATE TABLE order_delivery_area (
  delivery_area_id bigint(20) NOT NULL COMMENT 'id',
  advertise_delivery_id bigint(20) DEFAULT NULL COMMENT '广告投放Id',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id',
  line_station_id bigint(20) DEFAULT NULL COMMENT '线路车站Id'
) COMMENT='投放范围表';