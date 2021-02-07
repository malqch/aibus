#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class StudentLineSeatLog(db.Model, CRUDMixin):
    __tablename__ = 'student_line_seat_log'
    log_id = db.Column(db.Integer, primary_key=True)  # 主键
    student_id = db.Column(db.Integer, nullable=False)  # 学生id
    up_company_line_id = db.Column(db.Integer, nullable=False)  # 上学线路/上行
    off_company_line_id = db.Column(db.Integer, nullable=False)  # 放学线路/下行
    seat_no = db.Column(db.Integer, nullable=True)  # 座位号
    up_station_id = db.Column(db.Integer, nullable=True)  # 上学上车站点
    off_station_id = db.Column(db.Integer, nullable=True)  # 放学下车站点
    create_user_id = db.Column(db.Integer, nullable=False)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间


    def query_sa_line_tooltips(self, login_user_id, create_dt):
        """
        获取有无学生调整线路
        :param login_user_id
        :param create_dt
        :return:
        """
        # sql = r" select * from student_line_seat_log ls join info_student s on " \
        #       r" ls.student_id = s.id join info_people_basic_facts pbc on " \
        #       r" pbc.id = s.basic_id join admin_position_auth pa on " \
        #       r" pa.company_id = s.school_id join admin_user_position up on " \
        #       r" up.position_id = pa.position_id where up.user_id = :login_user_id and " \
        #       r" pa.is_deleted = '0' and ls.create_dt = :create_dt "
        sql = r" select s.student_id, pbc.full_name as student_name, s.up_station_id," \
              r" s.off_station_id, s1.bus_station_name as up_station_name, " \
              r" s2.bus_station_name as off_station_name, aa.up_station_id as " \
              r" original_up_station_id, aa.off_station_id as original_off_station_id, " \
              r" s3.bus_station_name as original_up_station_name, s4.bus_station_name as " \
              r" original_off_station_name from student_line_seat s join( select * from( " \
              r" select ls.*,rank() over(partition by ls.student_id order by ls.create_dt desc) " \
              r" as row_no from student_line_seat_log ls ) a ) aa on aa.student_id = " \
              r" s.student_id and aa.row_no = 1 join info_bus_station s1 on s1.bus_station_id = " \
              r" s.up_station_id join info_bus_station s2 on s2.bus_station_id = s.off_station_id" \
              r" join info_bus_station s3 on s3.bus_station_id = aa.up_station_id join " \
              r" info_bus_station s4 on s4.bus_station_id = aa.off_station_id join " \
              r" info_student stu on s.student_id = stu.id join info_people_basic_facts pbc on " \
              r" pbc.id = stu.basic_id join admin_position_auth pa on pa.company_id = stu.school_id " \
              r" and pa.is_deleted = '0' join admin_user_position up on up.position_id = pa.position_id " \
              r" and up.is_deleted = '0' where  up.user_id = :login_user_id and " \
              r" to_char(aa.create_dt, 'yyyy-MM-dd') = :create_dt; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'create_dt': create_dt})
        return result

    def add_student_line_seat_log(self, student_obj, login_user_id, now_time):
        """
        插入一条学生座位日志
        :return:
        """
        sql = u'insert into student_line_seat_log (student_id, up_company_line_id, off_company_line_id, seat_no, ' \
              u'up_station_id, off_station_id, create_user_id, create_dt) values (:student_id, :up_company_line_id, ' \
              u':off_company_line_id, :seat_no, :up_station_id, :off_station_id, :create_user_id, :now_time);'

        result = db.session.execute(text(sql), {"student_id": student_obj["student_id"], "up_company_line_id": student_obj["up_company_line_id"],
                                                "off_company_line_id": student_obj["off_company_line_id"], "seat_no": student_obj["seat_no"],
                                                "up_station_id": student_obj["up_station_id"], "off_station_id": student_obj["off_station_id"],
                                                "create_user_id": login_user_id, "now_time": now_time})
        return result
