#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class ItineraryReceipt(db.Model, CRUDMixin):
    __tablename__ = 'itinerary_receipt'
    itinerary_id = db.Column(db.Integer, primary_key=True)      # 行程回单id
    plan_service_id = db.Column(db.Integer)                     # 营运计划id
    itinerary_date = db.Column(db.String(8))                    # 行程时间
    itinerary_status = db.Column(db.String(1))                  # 行程状态
    is_backup = db.Column(db.String(1))                         # 是否为备选路线
    report_status = db.Column(db.String(1))                     # 上报状态
    report_way = db.Column(db.String(1))                        # 上报方式
    bus_id = db.Column(db.Integer)                              # 预计发车时间
    driver_id = db.Column(db.Integer)                           # 是否可用
    driver_full_name = db.Column(db.String(32))                 # 司机姓名
    driver_position = db.Column(db.String(32))                  # 司机岗位
    driver_body_temprature = db.Column(db.Float)                # 司机体温
    driver_wear_mask = db.Column(db.String(32))                 # 司机佩戴口罩
    driver_hand_disinfection = db.Column(db.String(32))         # 司机手部消毒
    driving_violation_total = db.Column(db.Integer)             # 行车违纪次数
    safety_officer_id = db.Column(db.Integer)                   # 安全员id
    safety_officer_full_name = db.Column(db.String(32))         # 安全员姓名
    safety_officer_body_temprature = db.Column(db.Float)        # 安全员体温
    safety_officer_wear_mask = db.Column(db.String(32))         # 安全员佩戴口罩
    safety_officer_hand_disinfection = db.Column(db.String(32)) # 安全员手部消毒
    bus_disinfection = db.Column(db.String(32))                 # 车内消毒
    no_authorize_warn = db.Column(db.Integer)                   # 未授权告警
    itinerary_start_time = db.Column(db.DateTime())             # 行程开始时间
    itinerary_end_time = db.Column(db.DateTime())               # 行程结束时间
    itinerary_direction = db.Column(db.String(32))              # 行程方向
    take_total = db.Column(db.Integer)                          # 应载人数
    load_total = db.Column(db.Integer)                          # 实载人数
    non_total = db.Column(db.Integer)                           # 未上车人数
    leave_total = db.Column(db.Integer)                         # 请假人数
    off_bus_total = db.Column(db.Integer)                       # 已经下车人数
    body_temprature_abnormal_total = db.Column(db.Integer)      # 体温异常人数
    not_wear_mask_total = db.Column(db.Integer)                 # 未佩戴口罩人数
    not_hand_disinfection_total = db.Column(db.Integer)         # 未手部消毒人数
    student_violation_total = db.Column(db.Integer)             # 违纪人数
    student_violation_count = db.Column(db.Integer)             # 违纪次数
    max_speed = db.Column(db.Numeric)                           # 车速记录
    create_user_id = db.Column(db.Integer)                      # 创建人
    create_dt = db.Column(db.DateTime)                          # 创建时间
    modify_user_id = db.Column(db.Integer)                      # 更新人
    modify_dt = db.Column(db.DateTime)                          # 更新时间

    @staticmethod
    def update_itinerary_status(itinerary_id, itinerary_status):
        """
        根据行程id更新行程状态
        :param itinerary_id:
        :param itinerary_status:
        :return:
        """
        sql = r'update public.itinerary_receipt set itinerary_status= :itinerary_status where itinerary_id= :itinerary_id '
        result = db.session.execute(text(sql), {'itinerary_status': itinerary_status,
                                                'itinerary_id': itinerary_id})
        return result

    @staticmethod
    def get_itinerary_id(now_time):
        """
        根据当前时间查询出所有线路下的行程
        :param now_time:
        :return:
        """
        sql = u'select ir.itinerary_id, ir.is_backup from public.itinerary_receipt ir ' \
              u'join plan_bus_service pbs on ir.plan_service_id = pbs.plan_service_id ' \
              u'join info_company_line l on pbs.company_line_id = l.company_line_id ' \
              u'where l.is_enabled = 1 and l.is_deleted = 0 ' \
              u'and pbs.begin_date <= :now_time and pbs.end_date >= :now_time ' \
              u'and ir.itinerary_date = :now_time and ir.itinerary_status = :itinerary_status;'
        result = db.session.execute(text(sql), {"now_time": now_time,
                                                "itinerary_status": '1'})
        return result

    @staticmethod
    def update_is_backup(itinerary_id, is_backup):
        """
        切换主备路线
        :param itinerary_id:
        :param is_backup:
        :return:
        """
        sql = u'update public.itinerary_receipt set is_backup = :is_backup where itinerary_id = :itinerary_id'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id, "is_backup": is_backup})
        return result

    def query_ba_tooltips(self, login_user_id, itinerary_date):
        """
        获取校车工作人员提示信息
        :param login_user_id
        :param itinerary_date
        :return:
        """
        sql = r" select ir.is_backup,br.next_review_time from itinerary_receipt ir " \
              r" join info_driver d on ir.driver_id = d.id join info_safety_officer s " \
              r" on ir.safety_officer_id = s.id left join (select bus_id, " \
              r" max(next_review_time) as next_review_time from info_bus_annual_review " \
              r" group by bus_id) br on ir.bus_id = br.bus_id where " \
              r" (d.login_user_id = :login_user_id or s.login_user_id = :login_user_id) and " \
              r" ir.itinerary_date = :itinerary_date and ir.itinerary_status in ('1', '2') " \
              r" order by ir.predict_depart_time DESC limit 1; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'itinerary_date': itinerary_date})
        return result

    def get_up_or_down_location(self, itinerary_id, login_user_id):
        """
        查询学生上车或下车车站位置
        :param itinerary_id:
        :param login_user_id
        :return:
        """
        sql = r" select ir.itinerary_id, " \
              r" case ir.itinerary_direction " \
              r" when '1' " \
              r" then bs1.bus_station_longitude || ',' || bs1.bus_station_latitude " \
              r" when '2' " \
              r" then bs2.bus_station_longitude || ',' || bs2.bus_station_latitude " \
              r" end as station_location, " \
              r" case ir.itinerary_direction " \
              r" when '1' " \
              r" then ils1.station_order " \
              r" when '2' " \
              r" then ils2.station_order " \
              r" end as station_order " \
              r" from itinerary_receipt ir " \
              r" join plan_bus_service pbs " \
              r" on ir.plan_service_id = pbs.plan_service_id and pbs.is_deleted = 0 " \
              r" join student_line_seat sls " \
              r" on (sls.up_company_line_id = pbs.company_line_id or sls.off_company_line_id = pbs.company_line_id) " \
              r" left join info_bus_station bs1 " \
              r" on sls.up_station_id = bs1.bus_station_id " \
              r" left join info_line_station ils1 on ils1.company_line_id = sls.up_company_line_id " \
              r" and ils1.bus_station_id = bs1.bus_station_id " \
              r" and ils1.is_deleted = 0 " \
              r" left join info_bus_station bs2 " \
              r" on sls.off_station_id = bs2.bus_station_id " \
              r" left join info_line_station ils2 on ils2.company_line_id = sls.off_company_line_id " \
              r" and ils2.bus_station_id = bs2.bus_station_id " \
              r" and ils2.is_deleted = 0 " \
              r" join info_guardian g " \
              r" on g.student_id = sls.student_id " \
              r" and g.is_deleted = '0' " \
              r" where ir.itinerary_id= :itinerary_id " \
              r" and g.login_user_id = :login_user_id; "
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id,
                                                "login_user_id": login_user_id})
        return result

    def is_miss_this_station(self, itinerary_id):
        """
        查询车辆是否过站,如果过站就不用计算了
        下一站的站序大于上车站的站序,则说明过站了
        :return:
        :param itinerary_id:
        """
        sql = u'select lbs.itinerary_id, lbs.next_station_id, ils.station_order ' \
              u'from log_bus_service lbs ' \
              u'join itinerary_receipt ir on ir.itinerary_id = lbs.itinerary_id ' \
              u'join plan_bus_service pbs on pbs.plan_service_id =  ir.plan_service_id ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id ' \
              u'join info_line_station ils on ils.company_line_id = cl.company_line_id ' \
              u'and ils.bus_station_id = lbs.next_station_id ' \
              u'and ils.is_deleted = 0 ' \
              u'where lbs.itinerary_id = :itinerary_id;'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
        return result
