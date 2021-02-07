#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class AdminPosition(db.Model, CRUDMixin):
    __tablename__ = 'admin_position'
    position_id = db.Column(db.Integer, primary_key=True)  # 主键
    name = db.Column(db.String(32), nullable=False)  # 职位名称
    description = db.Column(db.String(128), nullable=True)  # 职位描述
    system_auth = db.Column(db.String(32), nullable=True)  # 系统授权
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    is_enabled = db.Column(db.String(1), nullable=False)  # 是否启用
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
