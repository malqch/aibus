CREATE TABLE info_update_list (
  update_list_id bigint(20) NOT NULL COMMENT '@desc 主键',
  update_type varchar(32) DEFAULT NULL COMMENT '@desc 更新类型',
  update_desc varchar(128) DEFAULT NULL COMMENT '@desc 更新描述',
  original_file_name varchar(128) DEFAULT NULL COMMENT '@desc 文件名称',
  update_url text COMMENT '@desc 文件路径',
  is_published smallint(6) DEFAULT NULL COMMENT '@desc 是否发布',
  publish_date datetime DEFAULT NULL COMMENT '@desc 发布时间',
  is_deleted smallint(6) DEFAULT NULL COMMENT '@desc 是否删除',
  created_by bigint(20) DEFAULT NULL COMMENT '@desc 创建人',
  created_date datetime DEFAULT NULL COMMENT '@desc 创建时间',
  modified_by bigint(20) DEFAULT NULL COMMENT '@desc 修改人',
  modified_date datetime DEFAULT NULL COMMENT '@desc 修改时间',
  PRIMARY KEY (update_list_id)
) COMMENT='@desc 更新信息表';
INSERT INTO info_update_list  VALUES (1, '1', '', '', '', 1, '1990-01-01 01:01:01', 0, 1, '1990-01-01 01:01:01', 1, '1990-01-01 01:01:01');
INSERT INTO info_update_list  VALUES (2, '3', '', '', '', 1, '1990-01-01 01:01:01', 0, 1, '1990-01-01 01:01:01', 1, '1990-01-01 01:01:01');