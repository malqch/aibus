#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class InfoGuardian(db.Model, CRUDMixin):
    __tablename__ = 'info_guardian'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    basic_id = db.Column(db.Integer, nullable=False)  # 基本信息主键
    student_id = db.Column(db.Integer, nullable=False)  # 学生id
    seq_number = db.Column(db.Integer)  # 顺位
    relation_student = db.Column(db.String(32))  # 与学生关系
    mobile_number = db.Column(db.String(32))  # 手机号
    login_user_id = db.Column(db.String(32), nullable=False)  # 登录账号id
    create_user_id = db.Column(db.Integer)  # 创建人
    create_dt = db.Column(db.DateTime)  # 创建时间
    modify_user_id = db.Column(db.Integer)  # 更新人
    modify_dt = db.Column(db.DateTime)  # 更新时间


    def query_pa_tooltips(self, login_user_id):
        """
        获取家长提示信息
        :param login_user_id
        :return:
        """
        sql = r" select s1.bus_station_name as up_station_name,s2.bus_station_name " \
              r" as off_station_name,b.full_name from info_guardian g join " \
              r" info_student s on g.student_id = s.id join info_people_basic_facts b " \
              r" on s.basic_id = b.id join student_line_seat ss on " \
              r" g.student_id = ss.student_id join info_bus_station s1 on " \
              r" ss.up_station_id = s1.bus_station_id join info_bus_station s2 on " \
              r" ss.off_station_id = s2.bus_station_id where g.login_user_id = :login_user_id;"
        result = db.session.execute(text(sql), {'login_user_id': login_user_id})
        return result


    def query_pa_health_code(self, login_user_id, clock_date):
        """
        是否提示家长需要上传健康码
        :param login_user_id
        :return:
        """
        sql = r" select a.param_code as is_epidemic, b.clock_no from( select p.param_code " \
              r" from info_config_param p where p.param_group = 'epidemic' ) a left join( " \
              r" select count(1) as clock_no from log_epidemic le join info_people_basic_facts " \
              r" pbf on pbf.id = le.id join info_guardian g on g.basic_id = pbf.id where " \
              r" g.login_user_id = :login_user_id and le.health_qr_code is not null and " \
              r" le.clock_date = :clock_date ) b on 1 = 1; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'clock_date': clock_date})
        return result

    def query_ba_health_code(self, login_user_id, clock_date):
        """
        是否提示校车工作人员需要上传健康码
        :param login_user_id
        :return:
        """
        sql = r" select a.param_code as is_epidemic, b.clock_no from( select p.param_code " \
              r" from info_config_param p where p.param_group = 'epidemic' ) a left join( " \
              r" select count(1) as clock_no from log_epidemic le join info_people_basic_facts " \
              r" pbf on pbf.id = le.id left join info_driver d on d.basic_id = pbf.id " \
              r" left join info_safety_officer iso on iso.basic_id = pbf.id where ( " \
              r" d.login_user_id = :login_user_id or iso.login_user_id = :login_user_id) and " \
              r" le.health_qr_code is not null and le.clock_date = :clock_date ) b on 1 = 1 "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'clock_date': clock_date})
        return result
