#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class InfoSchool(db.Model, CRUDMixin):
    __tablename__ = 'info_school'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    school_name = db.Column(db.String(1024), nullable=False)  # 学校名称
    education_bureau_id = db.Column(db.Integer, nullable=True)
    remark = db.Column(db.String(1024), nullable=True)  # 备注
    area_code = db.Column(db.Integer, nullable=True)  # 区域code
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
