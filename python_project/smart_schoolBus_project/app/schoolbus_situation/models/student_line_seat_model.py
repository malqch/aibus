#!/usr/bin/python
# -*- coding: utf-8 -*-
from sqlalchemy import text
from public.database import CRUDMixin
from app import db
from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from app.schoolbus_situation.models.info_bus_model import InfoBus
from app.schoolbus_situation.models.info_driver_model import InfoDriver
from app.schoolbus_situation.models.info_safety_officer_model import InfoSafetyOfficer


class StudentLineSeat(db.Model, CRUDMixin):
    __tablename__ = 'student_line_seat'
    student_id = db.Column(db.Integer, primary_key=True)  # 学生id
    up_company_line_id = db.Column(db.Integer, db.ForeignKey(InfoBus.bus_id), nullable=False)  # 上学线路/上行
    off_company_line_id = db.Column(db.Integer, db.ForeignKey(InfoCompanyLine.company_line_id), nullable=True)  # 放学线路/下行
    seat_no = db.Column(db.Integer, db.ForeignKey(InfoDriver.id), nullable=True)  # 座位号
    up_station_id = db.Column(db.Integer, db.ForeignKey(InfoSafetyOfficer.id), nullable=False)  # 上学上车站点
    off_station_id = db.Column(db.Integer, nullable=True)  # 放学上车站点
    create_user_id = db.Column(db.Integer, nullable=False)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=False)  # 更新人员
    modify_dt = db.Column(db.DateTime(), nullable=True)    # 更新时间


    def get_one_bus_station(self, login_user_id):
        """
        查询站点(家长)
        :param login_user_id:
        :return:
        """
        sql = u'select cl.company_line_name, bs1.bus_station_name as up_station_name,' \
              u'bs2.bus_station_name as off_station_name ' \
              u'from student_line_seat ls ' \
              u'join info_company_line cl on ls.up_company_line_id = cl.company_line_id ' \
              u'join info_bus_station bs1 on bs1.bus_station_id = ls.up_station_id ' \
              u'join info_bus_station bs2 on bs2.bus_station_id = ls.off_station_id ' \
              u'join info_guardian g on ls.student_id = g.student_id ' \
              u'where g.login_user_id = :login_user_id;'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id})
        return result

    def get_all_bus_station(self, login_user_id):
        """
        获取站点列表(家长)
        :param login_user_id:
        :return:
        """
        sql = u'select bs.bus_station_id, bs.bus_station_name ' \
              u'from student_line_seat ls ' \
              u'join info_line_station ils on ils.company_line_id = ls.up_company_line_id ' \
              u'join info_bus_station bs on bs.bus_station_id = ils.bus_station_id ' \
              u'join info_guardian g on ls.student_id = g.student_id ' \
              u'where g.login_user_id= :login_user_id ' \
              u'and ls.is_deleted= :is_deleted and ils.is_deleted= 0;'
        result = db.session.execute(text(sql), {"is_deleted": "0", "login_user_id": login_user_id})
        return result
    
    def get_student_id(self, login_user_id):
        """
        获取学生id
        :param login_user_id: 
        :return: 
        """
        sql = 'select * from info_guardian where login_user_id = :login_user_id;'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id})
        return result
    
    def get_student_line_seat(self, student_id):
        """
        根据student_id查询学生座位信息
        :param student_id: 
        :return: 
        """
        sql = u'select * from student_line_seat where student_id= :student_id;'
        result = db.session.execute(text(sql), {"student_id": student_id})
        return result
    
    def update_bus_station(self, login_user_id, student_id, up_station_id, off_station_id, now_time):
        """
        修改上下车站点
        :param login_user_id:
        :param student_id:
        :param up_station_id:
        :param off_station_id:
        :param now_time:
        :return:
        """
        sql = u'update student_line_seat set up_station_id= :up_station_id, off_station_id= :off_station_id,' \
              u'modify_dt= :now_time, create_user_id= :login_user_id ' \
              u'where student_id= :student_id;'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id, "student_id": student_id,
                                                "up_station_id": up_station_id, "off_station_id": off_station_id,
                                                "now_time": now_time})
        return result

    def compare_student_seat(self, student_id, up_station_id, off_station_id):
        """
        查询数据库中是否有相同的记录
        :param student_id:
        :param up_station_id:
        :param off_station_id:
        :return:
        """
        sql = u'select * from student_line_seat where student_id= :student_id ' \
              u'and up_station_id= :up_station_id and off_station_id= :off_station_id;'
        result = db.session.execute(text(sql), {"student_id": student_id, "up_station_id": up_station_id,
                                                "off_station_id": off_station_id})
        return result

