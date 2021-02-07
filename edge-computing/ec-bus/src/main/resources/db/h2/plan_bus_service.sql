CREATE TABLE plan_bus_service (
  plan_service_id bigint(20) NOT NULL COMMENT 'id',
  bus_id bigint(20) DEFAULT NULL COMMENT '公交车Id',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id',
  begin_date datetime DEFAULT NULL COMMENT '起始时间',
  end_date datetime DEFAULT NULL COMMENT '截止时间',
  PRIMARY KEY (plan_service_id)
) COMMENT='营运计划表';