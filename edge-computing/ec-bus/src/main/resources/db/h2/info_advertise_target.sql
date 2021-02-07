CREATE TABLE info_advertise_target (
  advertise_target_id bigint(20) NOT NULL COMMENT 'id',
  advertise_target_name varchar(100) DEFAULT NULL COMMENT '广告标签',
  advertise_target_grope varchar(100) DEFAULT NULL COMMENT '广告分类编码 link、value、char',
  advertise_target_code varchar(100) DEFAULT NULL COMMENT '广告标签编码',
  PRIMARY KEY (advertise_target_id)
) COMMENT='广告标签表';