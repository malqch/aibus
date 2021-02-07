#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class InfoDriver(db.Model, CRUDMixin):
    __tablename__ = 'info_driver'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    basic_id = db.Column(db.Integer, nullable=False)  # 基本信息主键
    driving_license_no = db.Column(db.String(32), nullable=True)  # 驾驶证件号
    first_issue_date = db.Column(db.DateTime(), nullable=True)  # 初次领证时间
    driving_license_class = db.Column(db.String(32), nullable=False)  # 驾驶证类型
    mobile_number = db.Column(db.String(32), nullable=True)  # 手机号
    drivers_license_photo = db.Column(db.String(128), nullable=False)  # 驾驶证照片
    valid_period_start = db.Column(db.DateTime(), nullable=True)  # 驾驶证有效期起始时间
    valid_period_end = db.Column(db.DateTime(), nullable=False)  # 驾驶证有效期结束时间
    no_criminal_record_photo = db.Column(db.String(128), nullable=False)  # 无犯罪记录证明照片
    login_user_id = db.Column(db.String(32), nullable=False)  # 系统登录用户id
    is_primary = db.Column(db.String(1), nullable=False)  # 是否为主岗
    create_user_id = db.Column(db.Integer, nullable=True)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间