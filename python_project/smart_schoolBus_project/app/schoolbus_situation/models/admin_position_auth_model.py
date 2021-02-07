#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db
from app.schoolbus_situation.models.admin_position_model import AdminPosition
from app.schoolbus_situation.models.info_school_model import InfoSchool


class AdminPositionAuth(db.Model, CRUDMixin):
    __tablename__ = 'admin_position_auth'
    position_auth_id = db.Column(db.Integer, primary_key=True)  # 主键
    position_id = db.Column(db.Integer, db.ForeignKey(AdminPosition.position_id), nullable=False)  # 职位id
    company_id = db.Column(db.Integer, db.ForeignKey(InfoSchool.id), nullable=False)  # 学校id
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间


