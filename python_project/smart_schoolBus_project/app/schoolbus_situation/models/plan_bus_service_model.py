#!/usr/bin/python
# -*- coding: utf-8 -*-
from sqlalchemy import text
from public.database import CRUDMixin
from app import db
from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from app.schoolbus_situation.models.info_bus_model import InfoBus
from app.schoolbus_situation.models.info_driver_model import InfoDriver
from app.schoolbus_situation.models.info_safety_officer_model import InfoSafetyOfficer


class PlanBusService(db.Model, CRUDMixin):
    __tablename__ = 'plan_bus_service'
    plan_service_id = db.Column(db.Integer, primary_key=True)  # 主键
    bus_id = db.Column(db.Integer, db.ForeignKey(InfoBus.bus_id), nullable=False)  # 公交车id
    company_line_id = db.Column(db.Integer, db.ForeignKey(InfoCompanyLine.company_line_id), nullable=True)  # 公交线路id
    driver_id = db.Column(db.Integer, db.ForeignKey(InfoDriver.id), nullable=True)  # 司机id
    safety_id = db.Column(db.Integer, db.ForeignKey(InfoSafetyOfficer.id), nullable=False)  # 安全员id
    begin_date = db.Column(db.DateTime(), nullable=True)  # 开始时间
    end_date = db.Column(db.DateTime(), nullable=False)  # 截止删除
    is_deleted = db.Column(db.Integer, nullable=True)  # 是否时间
    is_enabled = db.Column(db.Integer, nullable=False)  # 是否启用
    created_by = db.Column(db.Integer, nullable=True)
    created_date = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modified_by = db.Column(db.Integer, nullable=True)
    modified_date = db.Column(db.DateTime(), nullable=True)  # 修改时间

    @staticmethod
    def get_plan_bus_service(company_line_id):
        """
        根据线路id查询出校车服务计划
        :param company_line_id:
        :return:
        """
        sql = u'select * from public.plan_bus_service where company_line_id := company_line_id'
        result = db.session.execute(text(sql), {"company_line_id": company_line_id})
        return result
