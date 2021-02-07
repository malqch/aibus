#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db
from app.schoolbus_situation.models.admin_position_model import AdminPosition
from app.sys.user.model import Users


class AdminUserPosition(db.Model, CRUDMixin):
    __tablename__ = 'admin_user_position'
    user_position_id = db.Column(db.Integer, primary_key=True)  # 主键
    user_id = db.Column(db.Integer, db.ForeignKey(Users.user_id), nullable=False)  # 用户id
    position_id = db.Column(db.Integer, db.ForeignKey(AdminPosition.position_id), nullable=False)  # 职位id
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间