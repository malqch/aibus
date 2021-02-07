CREATE TABLE order_advertise_attach (
  advertise_attach_id bigint(20) NOT NULL COMMENT 'id',
  advertise_delivery_id bigint(20) DEFAULT NULL COMMENT '广告投放Id',
  advertise_position_id bigint(20) DEFAULT NULL COMMENT '广告位Id',
  show_times decimal(10,0) DEFAULT NULL COMMENT '播放时长',
  attach_type int(11) DEFAULT NULL COMMENT '素材类型',
  attach_link varchar(500) DEFAULT NULL COMMENT '素材地址',
  suffix varchar(20) DEFAULT NULL COMMENT '附件后缀',
  company_line_id bigint(20) DEFAULT NULL COMMENT '公交线路Id'
) COMMENT='广告附件表';