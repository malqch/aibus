#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class InfoBusCompany(db.Model, CRUDMixin):
    __tablename__ = 'info_bus_company'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    company_name = db.Column(db.String(1024), nullable=False)  # 公司名称
    address = db.Column(db.Integer, nullable=True)  # 公司地址
    phone_number = db.Column(db.String(1024), nullable=True)  # 公司电话
    registration_number = db.Column(db.Integer, nullable=True)  # 工商注册号
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间