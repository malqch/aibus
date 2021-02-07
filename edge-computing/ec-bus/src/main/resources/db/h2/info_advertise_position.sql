CREATE TABLE info_advertise_position (
  advertise_position_id bigint(20) NOT NULL COMMENT 'id',
  position_desc varchar(200) DEFAULT NULL COMMENT '广告位描述',
  position_code varchar(100) DEFAULT NULL COMMENT '广告位编码',
  position_group varchar(100) DEFAULT NULL COMMENT '广告位分类',
  pixel_height int(11) DEFAULT NULL COMMENT '像素高',
  pixel_width int(11) DEFAULT NULL COMMENT '像素宽',
  screen_height int(11) DEFAULT NULL COMMENT '屏幕高',
  screen_width int(11) DEFAULT NULL COMMENT '屏幕宽',
  advertise_type int(11) DEFAULT NULL COMMENT '素材类型 0 单图片；1 单视频；2 全可以',
  PRIMARY KEY (advertise_position_id)
) COMMENT='广告位表';