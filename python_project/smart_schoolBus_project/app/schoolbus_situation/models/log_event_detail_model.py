#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class LogEventDetail(db.Model, CRUDMixin):
    __tablename__ = 'log_event_detail'
    event_detail_id = db.Column(db.Integer, primary_key=True)       # 主键
    itinerary_id = db.Column(db.Integer)                            # 行程id
    collect_event_id = db.Column(db.Integer)                        # 事件采集id
    bus_device_id = db.Column(db.Integer)                           # 设备id
    people_id_no = db.Column(db.String(32))                         # 人员身份证号
    bus_drive_id = db.Column(db.Integer)                            # 行驶日志id
    event_type_id = db.Column(db.Integer)                           # 事件类型id
    event_target_id = db.Column(db.Integer)                         # 事件标签id
    event_level_id = db.Column(db.Integer)                          # 事件级别id
    device_type_id = db.Column(db.Integer)                          # 设备类型id
    id_enabled = db.Column(db.String(1))                            # 是否启用
    created_date = db.Column(db.DateTime())                         # 创建时间
    modified_date = db.Column(db.DateTime())                        # 更新时间

    def get_drive_alarm(self, company_line_ids, login_user_id, begin_date, end_date, event_type_code="driving_alarm"):
        """
        -- collect_event 告警类型
        -- description_content 告警描述
        -- created_date 告警时间
        -- company_line_name 线路名称
        -- driver_name 司机名称
        -- 事件类型：event_type_code
        -- 行驶告警 driving_alarm
        -- 司机违规 driving_offences
        :param company_line_id_list:
        :param login_user_id:
        :param begin_date:
        :param end_date:
        :param event_type_code:
        :return:
        """
        sql1 = u'select et.event_type_code, ld.event_detail_id, ce.collect_event,ied.description_content,ld.created_date,' \
               u'cl.company_line_name,pb.full_name as driver_name,ir.itinerary_direction, ' \
               u'coalesce(ietc1.collect_attach_char, ietc2.collect_attach_char) as img,' \
               u'ietc3.collect_attach_char as student_name ' \
               u'from log_event_detail ld ' \
               u'left join (' \
               u'select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :img ' \
               u') ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
               u'left join (' \
               u'select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :img1 ' \
               u') ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
               u'left join (' \
               u'select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :name ' \
               u') ietc3 on ietc3.event_detail_id = ld.event_detail_id ' \
               u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
               u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
               u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
               u'join info_event_description ied on ied.event_target_id = iet.event_target_id ' \
               u'join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id ' \
               u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id ' \
               u'join info_company_line cl on cl.company_line_id = pbs.company_line_id ' \
               u'join admin_position_auth pa on pa.company_id = cl.school_id and pa.is_deleted= :is_deleted ' \
               u'join admin_user_position up on up.position_id = pa.position_id and up.is_deleted= :is_deleted ' \
               u'join info_driver d on ir.driver_id = d.id ' \
               u'join info_people_basic_facts pb on pb.id = d.basic_id ' \
               u'where et.event_type_code = :event_type_code ' \
               u'and up.user_id = :login_user_id '
        sql2 = u'and ld.created_date between :begin_date and :end_date ' \
               u'order by ld.created_date;'
        sql3 = u'and cl.company_line_id in :company_line_ids '
        sql = sql1 + sql2
        if company_line_ids[0]:
            sql = sql1 + sql3 + sql2
        result = db.session.execute(text(sql), {"img": "img", "img1": "img1", "name": "name", "is_deleted": "0", "login_user_id": login_user_id, "company_line_ids": company_line_ids,
                                                "begin_date": begin_date, "end_date": end_date, "event_type_code": event_type_code})
        return result

    def get_school_bus_review_time(self, login_user_id, company_line_ids):
        """
        获取校车年检状态时间
        :param login_user_id
        :return:
        """
        sql1 = u'select distinct cl.company_line_name, bar.review_time ' \
              u'from info_bus b ' \
              u'join info_bus_annual_review bar on bar.bus_id = b.bus_id ' \
              u'join plan_bus_service bs on b.bus_id = bs.bus_id ' \
              u'join info_company_line cl on cl.company_line_id = bs.company_line_id ' \
              u'join admin_position_auth pa on pa.company_id = cl.school_id ' \
              u'join admin_user_position up on up.position_id = pa.position_id ' \
              u'where up.user_id = :login_user_id and pa.is_deleted = :is_deleted ' \
              u'and b.is_deleted = 0 and b.is_enabled = 1 '
        sql2 = u'and cl.company_line_id in :company_line_ids '
        # sql3 = u'and bar.review_time between :begin_date and :end_date;'
        sql = sql1
        if company_line_ids[0]:
            sql = sql1 + sql2
        result = db.session.execute(text(sql), {"is_deleted": "0", "login_user_id": login_user_id, "company_line_ids": company_line_ids})
        return result

    def get_school_bus_registration(self, login_user_id, company_line_ids):
        """
        获取校车备案日志
        :param login_user_id
        :return:
        """
        sql1 = u'select distinct cl.company_line_name,b.registration_date,case when b.plate_code is null ' \
              u'then :no_record else :is_record end as is_registration ' \
              u'from info_bus b ' \
              u'join plan_bus_service bs on b.bus_id = bs.bus_id ' \
              u'join info_company_line cl on cl.company_line_id = bs.company_line_id ' \
              u'join admin_position_auth pa on pa.company_id = cl.school_id ' \
              u'join admin_user_position up on up.position_id = pa.position_id ' \
              u'where up.user_id = :login_user_id ' \
              u'and pa.is_deleted = :is_deleted ' \
              u'and b.is_deleted = 0 '
        sql2 = u'and cl.company_line_id in :company_line_ids;'
        # sql3 = u'and b.registration_date between :begin_date and :end_date;'
        sql = sql1
        if company_line_ids[0]:
            sql = sql1 + sql2
        result = db.session.execute(text(sql), {"is_deleted": "0", "login_user_id": login_user_id, "no_record": "未备案",
                                                "is_record": "已备案", "company_line_ids": company_line_ids})
        return result

    def get_authorizer_change_log(self, login_user_id, company_line_ids, begin_date, end_date):
        """
        获取授权人变更日志
        :param login_user_id:
        :return:
        """
        sql1 = u'select al.id, pbc2.full_name as student_name,pbc1.full_name as authorize_name,al.change_type, al.create_dt ' \
              u'from info_authorize_log al join info_authorize a on a.id = al.authorize_id ' \
              u'join info_people_basic_facts pbc1 on pbc1.id = a.basic_id ' \
              u'join info_student s on a.student_id = s.id ' \
              u'join info_people_basic_facts pbc2 on pbc2.id = s.basic_id ' \
              u'join admin_position_auth pa on pa.company_id = s.school_id ' \
              u'join admin_user_position up on up.position_id = pa.position_id ' \
              u'join student_line_seat ls on ls.student_id = s.id '\
              u'where up.user_id = :login_user_id and pa.is_deleted = :is_deleted '
        sql2 = u'and (ls.up_company_line_id in :company_line_ids or ls.off_company_line_id in :company_line_ids) '
        sql3 = u'and al.create_dt between :begin_date and :end_date;'
        sql = sql1 + sql3
        if company_line_ids[0]:
            sql = sql1 + sql2 + sql3
        result = db.session.execute(text(sql), {"is_deleted": "0", "login_user_id": login_user_id, "company_line_ids": company_line_ids,
                                                "begin_date": begin_date, "end_date": end_date})
        return result

    def worker_get_student_disciplinary(self, login_user_id, begin_date, end_date, company_line_ids):
        """
        获取学生违纪列表(行后查询)
        :return:
        """
        sql1 = u'select ld.event_detail_id, ce.collect_event,ied.description_content,ld.created_date, ' \
              u'cl.company_line_name,ietc1.collect_attach_char as student_name,ietc2.collect_attach_char as img, ' \
              u'ietc3.collect_attach_char as check_permissions,ietc3.event_attach_id ' \
              u'from log_event_detail ld ' \
              u'join (' \
              u'select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :name' \
              u') ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'left join (' \
              u'select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :img1) ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :check_permissions) ietc3 on ietc3.event_detail_id = ld.event_detail_id ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join info_event_description ied on ied.event_target_id = iet.event_target_id ' \
              u'join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id ' \
              u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id and pbs.is_deleted = 0 ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id and cl.is_deleted = 0 ' \
              u'join admin_position_auth pa on pa.company_id = cl.school_id and pa.is_deleted = :is_deleted ' \
              u'join admin_user_position up on up.position_id = pa.position_id and up.is_deleted = :is_deleted ' \
              u'where et.event_type_code = :event_type_code ' \
              u'and up.user_id = :login_user_id '
        sql2 = u'and cl.company_line_id in :company_line_ids '
        sql3 = u'and ld.created_date between :begin_date and :end_date ' \
              u'order by ld.created_date;'
        sql = sql1 + sql3
        if company_line_ids[0]:
            sql = sql1 + sql2 + sql3
        result = db.session.execute(text(sql), {"name":"name", "img1":"img1", "check_permissions":"check_permissions",
                                                "event_type_code":"student_disciplinary", "login_user_id": login_user_id,
                                                "company_line_ids": company_line_ids, "begin_date": begin_date,
                                                "end_date": end_date, "is_deleted": "0"})
        return result

    def parents_get_student_disciplinary(self, begin_date, end_date, company_line_ids, student_name, event_type_code="student_disciplinary"):
        """
        获取学生违纪列表(家长查询)
        :param login_user_id:
        :param begin_date:
        :param end_date:
        :param company_line_id:
        :param student_name:
        :return:
        """
        sql1 = u'select ld.event_detail_id, ce.collect_event,ied.description_content,ld.created_date, ' \
              u'cl.company_line_name,ietc1.collect_attach_char as student_name,ietc2.collect_attach_char as img,' \
              u'ietc3.collect_attach_char as check_permissions,ietc3.event_attach_id ' \
              u'from log_event_detail ld ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :name) ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'left join (' \
              u'select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :img1) ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :check_permissions) ietc3 on ietc3.event_detail_id = ld.event_detail_id ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join info_event_description ied on ied.event_target_id = iet.event_target_id ' \
              u'join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id ' \
              u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id and pbs.is_deleted = 0 ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id and cl.is_deleted = 0 ' \
              u'where et.event_type_code = :event_type_code ' \
              u'and ietc1.collect_attach_char = :student_name '
        sql2 = u'and cl.company_line_id in :company_line_ids '
        sql3 = u'and ld.created_date between :begin_date and :end_date ' \
               u'order by ld.created_date;'
        sql = sql1 + sql3
        if company_line_ids[0]:
            sql = sql1 + sql2 + sql3

        result = db.session.execute(text(sql), {"name":"name", "img1":"img1", "check_permissions":"check_permissions",
                                                "event_type_code": event_type_code, "student_name": student_name,
                                                "company_line_ids": company_line_ids, "begin_date": begin_date,
                                                "end_date": end_date})
        return result

    def worker_update_check_permissions(self, event_attach_id):
        """
        行后通过家长查看图片的申请
        :return:
        """
        sql = u'update log_event_attach set collect_attach_char = "2" ' \
              u'where event_attach_id = :event_attach_id and collect_attach_char = "1";'
        result = db.session.execute(text(sql), {"event_attach_id": event_attach_id})
        return result

    def parents_update_check_permissions(self, event_attach_id):
        """
        家长提交查询图片的申请
        :return:
        """
        sql = u' update log_event_attach set collect_attach_char = "1" ' \
              u'where event_attach_id = :event_attach_id and collect_attach_char = "0";'
        result = db.session.execute(text(sql), {"event_attach_id": event_attach_id})
        return result

    @staticmethod
    def get_student_name_by_login_user_id(login_user_id):
        """
        通过登录人用户id查询对应学生的学生姓名
        :param login_user_id:
        :return:
        """
        sql = u'select ipbf.full_name as student_name from info_people_basic_facts ipbf ' \
              u'where ipbf.id in (select ist.basic_id from info_student ist where ist.id in ' \
              u'(select ig.student_id from info_guardian ig where ig.login_user_id= :login_user_id));'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id})
        return result

    @staticmethod
    def get_company_line(login_user_id):
        """

        :param login_user_id:
        :return:
        """
        sql = u'select l.company_line_id, l.company_line_name, l.direction ' \
              u'from info_company_line l ' \
              u'join plan_bus_service pbs on pbs.company_line_id=l.company_line_id ' \
              u'join admin_position_auth apa on l.school_id=apa.company_id ' \
              u'join admin_user_position aup on apa.position_id=aup.position_id ' \
              u'where aup.user_id= :login_user_id ' \
              u'and aup.is_deleted= :is_deleted and apa.is_deleted= :is_deleted ' \
              u'and l.is_enabled= 1 and l.is_deleted= 0;'
        result = db.session.execute(text(sql), {"is_deleted": "0", "login_user_id": login_user_id})
        return result

    @staticmethod
    def parents_get_student_info(itinerary_id, student_name):
        """
        获取单个学生的违纪情况(家长)
        :param itinerary_id:
        :param student_name:
        :return:
        """
        sql = u'select ld.event_detail_id, ce.collect_event,ied.description_content,ld.created_date, ' \
               u'cl.company_line_name,ietc1.collect_attach_char as student_name,ietc2.collect_attach_char as img,' \
               u'ietc3.collect_attach_char as check_permissions,ietc3.event_attach_id ' \
               u'from log_event_detail ld ' \
               u'join (select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :name) ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
               u'left join (' \
               u'select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :img1) ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
               u'join (select a.* from log_event_attach a ' \
               u'join info_event_target b on a.event_target_id = b.event_target_id ' \
               u'where b.event_target_code = :check_permissions) ietc3 on ietc3.event_detail_id = ld.event_detail_id ' \
               u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
               u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
               u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
               u'join info_event_description ied on ied.event_target_id = iet.event_target_id ' \
               u'join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id ' \
               u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id and pbs.is_deleted = 0 ' \
               u'join info_company_line cl on cl.company_line_id = pbs.company_line_id and cl.is_deleted = 0 ' \
               u'where et.event_type_code = :event_type_code ' \
               u'and ietc1.collect_attach_char = :student_name '\
               u'and ir.itinerary_id = :itinerary_id '\
               u'order by ld.created_date;'

        result = db.session.execute(text(sql),
                                    {"name": "name", "img1": "img1", "check_permissions": "check_permissions",
                                     "event_type_code": "student_disciplinary", "student_name": student_name,
                                     "itinerary_id": itinerary_id})
        return result



