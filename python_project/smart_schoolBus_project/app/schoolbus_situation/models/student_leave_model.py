#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class StudentLeave(db.Model, CRUDMixin):
    __tablename__ = 'student_leave'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    student_id = db.Column(db.Integer, nullable=False)  # 学生id
    leave_reason = db.Column(db.String(128), nullable=True)  # 请假原因
    leave_date = db.Column(db.DateTime(), nullable=False)  # 请假时间
    time_frame = db.Column(db.String(1), nullable=True)  # 时段（上午/下午）
    remark = db.Column(db.String(128), nullable=True)  # 备注
    is_enabled = db.Column(db.Integer, nullable=False)  # 是否有效
    create_user_id = db.Column(db.Integer, nullable=True)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)  # 修改人
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间


    def query_sa_leave_tooltips(self, login_user_id, create_dt):
        """
        获取有无学生请假
        :param login_user_id
        :param create_dt
        :return:
        """
        sql = r" select * from student_leave sl join info_student s on " \
              r" s.id = sl.student_id join info_people_basic_facts pbs on " \
              r" pbs.id = s.basic_id join admin_position_auth pa on " \
              r" pa.company_id = s.school_id join admin_user_position up on " \
              r" up.position_id = pa.position_id where up.user_id = :login_user_id and " \
              r" pa.is_deleted = '0' and sl.leave_date = :create_dt "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'create_dt': create_dt})
        return result

    def get_student_leave(self, login_user_id):
        """
        查询请假
        :param login_user_id:
        :return:
        """
        sql = u'select sl.leave_date, sl.time_frame ' \
              u'from student_leave sl ' \
              u'join info_guardian g on sl.student_id = g.student_id ' \
              u'where g.login_user_id = :login_user_id and sl.is_enabled = 1 ' \
              u'order by sl.leave_date, sl.time_frame;'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id})
        return result

    def insert_student_leave(self, login_user_id, now_time):
        """
        插入一条请假记录
        :return:
        """
        sql = u'insert into student_leave (student_id, leave_date, time_frame, ' \
              u'is_enabled, create_user_id, create_dt) values (:student_id, :leave_date, :time_frame, ' \
              u' :is_enabled, :create_user_id, :now_time);'
        result = db.session.execute(text(sql), {"student_id":self.student_id, "is_enabled": 1,
                                                "leave_date": self.leave_date, "time_frame": self.time_frame,
                                                "create_user_id": login_user_id, "now_time": now_time})
        return result

    def get_student_leave_by_student_id(self, student_id, leave_date, time_frame):
        """
        查询对应的一条记录(有,即是已存在)
        :param student_id:
        :param leave_date:
        :param time_frame:
        :return:
        """
        sql = u'select * from student_leave ' \
              u'where student_id= :student_id ' \
              u'and leave_date= :leave_date ' \
              u'and time_frame= :time_frame;'
        result = db.session.execute(text(sql), {"student_id": student_id,
                                                "leave_date": leave_date, "time_frame": time_frame})
        return result

    def query_leave_student_tooltips(self, login_user_id, itinerary_date, leave_date):
        """
        获取校车工作人员提示信息
        :param login_user_id
        :param itinerary_date
        :return:
        """
        sql = r" select ipbs.full_name as student_name,sl.leave_date,sl.time_frame," \
              r" sl.student_id from student_leave sl join info_student s on " \
              r" s.id = sl.student_id join info_people_basic_facts ipbs on " \
              r" ipbs.id = s.basic_id join student_line_seat sls on sls.student_id " \
              r" = s.id join (select cl.company_line_id,ir.itinerary_id from " \
              r" itinerary_receipt ir join plan_bus_service pbs on pbs.plan_service_id " \
              r" = ir.plan_service_id join info_company_line cl on cl.company_line_id " \
              r" = pbs.company_line_id join info_driver d on ir.driver_id = d.id left join " \
              r" info_safety_officer s on ir.safety_officer_id = s.id where (d.login_user_id " \
              r" = :login_user_id or s.login_user_id = :login_user_id) and " \
              r" ir.itinerary_date = :itinerary_date order by ir.predict_depart_time DESC " \
              r" limit 1 ) c on (c.company_line_id = sls.up_company_line_id or " \
              r" c.company_line_id = sls.off_company_line_id) where sl.leave_date " \
              r" = to_date(:leave_date,'yyyy-MM-dd'); "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'itinerary_date': itinerary_date,
                                                'leave_date': leave_date})
        return result