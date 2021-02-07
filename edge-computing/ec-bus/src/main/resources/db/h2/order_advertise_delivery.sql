CREATE TABLE order_advertise_delivery (
  advertise_delivery_id bigint(20) NOT NULL COMMENT 'id',
  advertise_delivery_type varchar(50) DEFAULT NULL COMMENT '投放方式',
  delivery_begin datetime DEFAULT NULL COMMENT '起始时间',
  delivery_end datetime DEFAULT NULL COMMENT '截止时间',
  check_status int(11) DEFAULT NULL COMMENT '审核状态 0 草稿，1审核中，2，通过，3，投放中，4，未通过，9 下线',
  check_suggest varchar(200) DEFAULT NULL COMMENT '审核意见',
  is_interrupt int(11) DEFAULT NULL COMMENT '是否插播  0 不是，1 是',
  interrupt_notice varchar(500) DEFAULT NULL COMMENT '插播通知',
  advertise_no varchar(100) DEFAULT NULL COMMENT '广告单号',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id'
) COMMENT='广告投放单';