#!/usr/bin/python
# -*- coding: utf-8 -*-

from public.database import CRUDMixin
from app import db


class InfoBus(db.Model, CRUDMixin):
    __tablename__ = 'info_bus'
    bus_id = db.Column(db.Integer, primary_key=True)  # 主键
    company_id = db.Column(db.Integer, nullable=False)  # 公交车厂id
    bus_type_id = db.Column(db.Integer, nullable=True)  # 车型id
    vin_code = db.Column(db.String(64), nullable=True)  # VIN编号
    plate_code = db.Column(db.String(16), nullable=False)  # 车牌号
    bus_status = db.Column(db.Integer, nullable=True)  # 车辆状态
    run_status = db.Column(db.Integer, nullable=False)  # 行驶状态
    is_deleted = db.Column(db.Integer, nullable=True)  # 是否删除
    is_enabled = db.Column(db.Integer, nullable=False)  # 是否启用
    created_by = db.Column(db.Integer, nullable=True)
    created_date = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modified_by = db.Column(db.Integer, nullable=True)
    modified_date = db.Column(db.DateTime(), nullable=True)  # 修改时间