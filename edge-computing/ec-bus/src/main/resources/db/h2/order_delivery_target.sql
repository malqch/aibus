CREATE TABLE order_delivery_target (
  delivery_target_id bigint(20) NOT NULL COMMENT 'id',
  advertise_delivery_id bigint(20) DEFAULT NULL COMMENT '广告投放Id',
  advertise_target_id bigint(20) DEFAULT NULL COMMENT '广告标签Id',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id'
) COMMENT='投放标签表';