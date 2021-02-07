#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class InfoArea(db.Model, CRUDMixin):
    __tablename__ = 'info_area'
    area_id = db.Column(db.Integer, primary_key=True)  # 主键
    area_code = db.Column(db.Integer, nullable=False)  # 区域编码
    area_name = db.Column(db.String(64), nullable=True)  # 区域名称
    area_alias = db.Column(db.String(10), nullable=True)  # 区域简称
    parent_area_id = db.Column(db.Integer, nullable=False)  # 上级区域
    area_level = db.Column(db.Integer, nullable=True)  # 区域级别
    area_sort = db.Column(db.Integer, nullable=False)  # 排序
    is_deleted = db.Column(db.Integer, nullable=True)  # 是否删除
    is_enabled = db.Column(db.Integer, nullable=False)  # 是否启用
    created_by = db.Column(db.Integer, nullable=True)
    created_date = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modified_by = db.Column(db.Integer, nullable=True)
    modified_date = db.Column(db.DateTime(), nullable=True)  # 修改时间