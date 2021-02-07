#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import current_app
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer, SignatureExpired, BadSignature
import hashlib
from public.database import CRUDMixin
from app import db



class InfoSafetyOfficer(db.Model, CRUDMixin):
    __tablename__ = 'info_safety_officer'
    id = db.Column(db.Integer, primary_key=True)                            # 主键
    basic_id = db.Column(db.Integer)                                        # 基本信息主键
    no_criminal_record_photo= db.Column(db.String(128), nullable=False)     # 无犯罪记录照片
    mobile_number = db.Column(db.String(32))                                # 手机号
    login_user_id = db.Column(db.String(32))                                # 系统登录账号id
    is_primary = db.Column(db.String(1))                                    # 是否为主岗
    create_user_id = db.Column(db.Integer)                                  # 创建人
    create_dt = db.Column(db.DateTime)                                      # 创建时间
    modify_user_id = db.Column(db.Integer)                                  # 更新人
    modify_dt = db.Column(db.DateTime)                                      # 更新时间