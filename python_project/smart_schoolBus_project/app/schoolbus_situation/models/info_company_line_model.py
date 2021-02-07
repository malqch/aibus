#!/usr/bin/python
# -*- coding: utf-8 -*-


from sqlalchemy import text

from public.database import CRUDMixin
from sqlalchemy import text
from app import db
from app.schoolbus_situation.models.info_bus_company_model import InfoBusCompany
from app.schoolbus_situation.models.info_school_model import InfoSchool


class InfoCompanyLine(db.Model, CRUDMixin):
    __tablename__ = 'info_company_line'
    company_line_id = db.Column(db.Integer, primary_key=True)  # 主键
    company_id = db.Column(db.Integer, db.ForeignKey(InfoBusCompany.id), nullable=False)  # 公交公司id
    school_id = db.Column(db.Integer, db.ForeignKey(InfoSchool.id), nullable=False)  # 校车id
    company_line_name = db.Column(db.String(20), nullable=False)  # 公交线路名称
    company_line_code = db.Column(db.String(20), nullable=False)  # 公交线路编码
    summer_start_time = db.Column(db.DateTime(), nullable=False)  # 夏季开始时间
    summer_end_time = db.Column(db.DateTime(), nullable=False)  # 夏季结束时间
    summer_first_time = db.Column(db.DateTime(), nullable=False)  # 夏季首班时间
    summer_last_time = db.Column(db.DateTime(), nullable=False)  # 夏季末班时间
    winter_first_time = db.Column(db.DateTime(), nullable=False)  # 冬季首班时间
    winter_last_time = db.Column(db.DateTime(), nullable=False)  # 冬季末班时间
    line_type = db.Column(db.Integer, nullable=False)  # 线路类型
    direction = db.Column(db.String(5), nullable=True)  # 方向（上行/下行）
    line_mileage = db.Column(db.Float, nullable=True)  # 线路里程
    is_deleted = db.Column(db.Integer, nullable=False)  # 是否删除
    is_enabled = db.Column(db.Integer, nullable=False)  # 是否可用
    created_by = db.Column(db.Integer, nullable=True)
    created_date = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modified_by = db.Column(db.Integer, nullable=True)
    modified_date = db.Column(db.DateTime(), nullable=True)  # 修改时间

    def get_itinerary_info(self, itinerary_id):
        """
        获取行程信息(行程id,行程主备线路,行程上下行,行程状态,下次车检时间,校车状态等)(有数据)
        :param company_line_id: 线路id
        :param itinerary_keys: 包含起始时间(begin_date, end_date前端传回)
        :param now_time: 当前时间
        :return:
        """
        sql = u'select ir.itinerary_id,ir.is_backup,ir.itinerary_direction,ir.itinerary_status, ' \
              u'br.next_review_time,ir.bus_status ' \
              u'from itinerary_receipt ir ' \
              u'join plan_bus_service pbs on ir.plan_service_id = pbs.plan_service_id ' \
              u'join info_company_line l on pbs.company_line_id = l.company_line_id ' \
              u'left join (select bus_id,max(next_review_time) ' \
              u'as next_review_time from info_bus_annual_review group by bus_id) br on ir.bus_id = br.bus_id ' \
              u'where ir.itinerary_id = :itinerary_id ' \
              u'and l.is_enabled = 1 and l.is_deleted = 0 ' \
              u'order by ir.predict_depart_time DESC limit 1;'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
        return result

    def get_line_station_list(self, itinerary_id, company_line_id):
        """
        线路车站列表(已有数据)
        :return:
        """
        sql = u'select ls.bus_station_id,bs.bus_station_name, ' \
              u'case when lbs.arrival_bus_station_id is null ' \
              u'then :num1 ' \
              u'when lbs.arrival_bus_station_id is not null and lbs.leave_time is null ' \
              u'then :num2 ' \
              u'when lbs.arrival_bus_station_id is not null and lbs.leave_time is not null ' \
              u'then :num3 ' \
              u'end as is_arrival ' \
              u'from info_line_station ls ' \
              u'join info_bus_station bs on ls.bus_station_id = bs.bus_station_id ' \
              u'left join (' \
              u'select bus_station_id as arrival_bus_station_id, leave_time ' \
              u'from log_bus_service ' \
              u'where itinerary_id = :itinerary_id ) lbs on ls.bus_station_id = lbs.arrival_bus_station_id ' \
              u'where ls.company_line_id = :company_line_id and ls.is_deleted = 0 ' \
              u'and bs.is_deleted = 0 and bs.is_enabled = 1 ' \
              u'order by ls.station_order asc;'
        result = db.session.execute(text(sql), {"num1": "0", "num2": "2", "num3": "1", "itinerary_id": itinerary_id, "company_line_id": company_line_id})
        return result

    def get_driver_and_safey_officer(self, itinerary_id, now_time):
        """
        获取司机安全员信息(司机id,司机姓名,是否为主岗,照片,驾驶证类型,无犯罪记录证明;
                        安全员id,安全员姓名,是否为主岗,照片,无犯罪记录证明)
        :return:
        """
        sql = u'select ' \
              u'ir.driver_id,bf1.full_name as driver_name,d.is_primary as driver_is_primary, ' \
              u'bf1.take_photo as driver_take_photo, d.driving_license_class,' \
              u'd.no_criminal_record_photo as driver_no_criminal_record_photo,bf1.id_no as driver_no, le1.clock_no as driver_clock_no,' \
              u'ir.safety_officer_id,bf2.full_name as safety_officer_name,so.is_primary as safety_officer_is_primary,' \
              u'bf2.take_photo as safety_officer_take_photo,so.no_criminal_record_photo as safety_officer_no_criminal_record_photo,' \
              u'le2.clock_no as safety_officer_clock_no, bf2.id_no as safety_officer_no, so.mobile_number as safety_officer_mobile_number ' \
              u'from itinerary_receipt ir ' \
              u'join info_driver d on ir.driver_id = d.id ' \
              u'join info_people_basic_facts bf1 on d.basic_id = bf1.id ' \
              u'left join (' \
              u'select count(*) as clock_no, unique_identity from log_epidemic where clock_date= :now_time ' \
              u'group by unique_identity' \
              u') le1 on le1.unique_identity = bf1.id ' \
              u'join info_safety_officer so on ir.safety_officer_id = so.id ' \
              u'join info_people_basic_facts bf2 on so.basic_id = bf2.id ' \
              u'left join (' \
              u'select count(*) as clock_no,unique_identity from log_epidemic where clock_date = :now_time ' \
              u'group by unique_identity' \
              u') le2 on le2.unique_identity = bf2.id ' \
              u'where ir.itinerary_id = :itinerary_id;'
        result = db.session.execute(text(sql), {'itinerary_id': itinerary_id, 'now_time': now_time})
        return result

    def get_student_info(self, now_time, time_frame, company_line_id,  login_user_id, role_code):
        """
        获取学生列表(有数据)(家长\行后)
        :return:
        """
        sql1 = u'select sls.student_id, bf.full_name as student_name,bf.take_photo,' \
              u'bs1.bus_station_name as up_station_name,bs2.bus_station_name as off_station_name,' \
              u'ic.class_name,ic.teacher_name,ic.teacher_mobile_number,ig.mobile_number as guardian_mobile_number,' \
              u'bf.id_no as student_no, sls.seat_no, pbf.full_name as guardian_name, ' \
              u'le1.clock_no as guardian_clock_no, sl.student_leave_id ' \
              u'from student_line_seat sls ' \
              u'join info_bus_station bs1 on bs1.bus_station_id = sls.up_station_id ' \
              u'join info_bus_station bs2 on bs2.bus_station_id = sls.off_station_id ' \
              u'join info_student s on s.id = sls.student_id ' \
              u'join info_people_basic_facts bf on s.basic_id = bf.id ' \
              u'left join (select id as student_leave_id, student_id from student_leave where leave_date = :now_time and time_frame = :time_frame) sl ' \
              u'on sl.student_id = s.id ' \
              u'join info_classes ic on s.classes_id = ic.id ' \
              u'join info_guardian ig on s.guardian_id = ig.id ' \
              u'join info_people_basic_facts pbf on pbf.id = ig.basic_id ' \
              u'left join ( select count(*) as clock_no,unique_identity from log_epidemic ' \
              u'where clock_date= :now_time group by unique_identity'\
              u') le1 on le1.unique_identity = pbf.id ' \
              u'where (sls.up_company_line_id = :up_company_line_id or sls.off_company_line_id = :off_company_line_id) '
        sql2 = u'and ig.login_user_id = :login_user_id '
        sql3 = u'order by sls.seat_no;'
        sql = sql1 + sql3
        if role_code == "pa":
            sql = sql1 + sql2 + sql3
        result = db.session.execute(text(sql), {"now_time": now_time, "time_frame": time_frame, "login_user_id": login_user_id,
                                                "up_company_line_id": company_line_id, "off_company_line_id": company_line_id})
        return result

    def query_school_bus(self, user_id):
        """
        查询校车列表
        :param user_id:
        :return:
        """

        # sql = r" select l.company_line_id,l.company_line_name from " \
        #       r" info_company_line l join plan_bus_service pbs on " \
        #       r" pbs.company_line_id = l.company_line_id join itinerary_receipt ir " \
        #       r" on ir.plan_service_id = pbs.plan_service_id join admin_position_auth apa " \
        #       r" on l.school_id = apa.company_id join admin_user_position aup on " \
        #       r" apa.position_id = aup.position_id where aup.user_id = :user_id and " \
        #       r" aup.is_deleted = '0' and apa.is_deleted = '0' and l.is_enabled = '1' and " \
        #       r" l.is_deleted = '0' and ir.itinerary_status = '1' and " \
        #       r" ir.itinerary_date = :itinerary_date; "
        sql = r" select l.company_line_id,l.company_line_name,l.direction,a.plan_service_id,a.itinerary_id,a.itinerary_date" \
              r" from info_company_line l " \
              r" join(" \
              r" select ir.itinerary_id,pbs.company_line_id,pbs.bus_id, pbs.plan_service_id,ir.itinerary_date, " \
              r" rank() over(partition by pbs.bus_id order by " \
              r" ir.itinerary_date desc,ir.itinerary_direction desc) as row_no from " \
              r" itinerary_receipt ir join plan_bus_service pbs on ir.plan_service_id = " \
              r" pbs.plan_service_id ) a on a.company_line_id = l.company_line_id join " \
              r" admin_position_auth apa on l.school_id = apa.company_id and " \
              r" apa.is_deleted = '0' join admin_user_position aup on apa.position_id = " \
              r" aup.position_id and aup.is_deleted = '0' where aup.user_id = :user_id " \
              r" and l.is_enabled = 1 and l.is_deleted = 0 and a.row_no = '1' order by l.company_line_code; "
        result = db.session.execute(text(sql), {'user_id': user_id})
        return result

    def parents_current_itinerary_info(self, login_user_id, now_time):
        """
        获取当前行程信息1(家长)
        -- 家长查询 线路id、查询行程id
        -- 家长登陆进来后就需要查询该语句，在通过线路id来获取校车态势的数据
        --方向 ：20:01到11:00  查上行线路，传1； 11:01 到 20:00 查下行线路，传2
        :return:
        """
        sql = u'select cl.company_line_id, ir.itinerary_id,g.student_id ' \
              u'from itinerary_receipt ir ' \
              u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id ' \
              u'join student_line_seat ls on (ls.up_company_line_id = cl.company_line_id or ls.off_company_line_id = cl.company_line_id) ' \
              u'join info_guardian g on ls.student_id = g.student_id ' \
              u'where g.login_user_id = :login_user_id ' \
              u'and ir.itinerary_date = :now_time'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id, "now_time": now_time})
        return result

    def worker_current_itinerary_info(self, login_user_id, now_time):
        """
        获取当前行程信息1(校车工作人员)
        -- 车辆工作人员登陆进来后就需要查询该语句，在通过线路id来获取校车态势的数据
        --方向 ：20:01到11:00  查上行线路，传1； 11:01 到 20:00 查下行线路，传2
        :return:
        """
        sql = u'select cl.company_line_id, ir.itinerary_id ' \
              u'from itinerary_receipt ir ' \
              u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id ' \
              u'join info_driver d on ir.driver_id = d.id ' \
              u'join info_safety_officer s on ir.safety_officer_id = s.id ' \
              u'where (d.login_user_id = :login_user_id or s.login_user_id = :login_user_id) ' \
              u'and ir.itinerary_date = :now_time ' \
              u'order by ir.predict_depart_time DESC limit 1;'
        result = db.session.execute(text(sql), {"login_user_id": login_user_id, "now_time": now_time})
        return result

    def all_current_info(self, itinerary_id):
        """
        获取司机\安全员\学生的上车状态,体温,口罩,手消等
        卫生健康 ：体温、口罩、手消
        如果该查询中有记录，则说明已上传，没有记录则没有上车
            -- 证件号
            -- 体温
            -- 是否佩戴口罩 0 否 1 是
            -- 是否手部消毒 0 否 1 是
        :return:
        """
        sql = u'select ld.itinerary_id, ld.event_detail_id, ' \
              u'ietc1.collect_attach_char as id_no, ' \
              u'ietc2.collect_attach_value as temperature, ' \
              u'ietc3.collect_attach_char as is_face_mask, ' \
              u'ietc4.collect_attach_char as is_hand_disinfect ' \
              u'from log_event_detail ld ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :id_no) ' \
              u'ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :temperature) ' \
              u'ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :is_face_mask)' \
              u'ietc3 on ietc3.event_detail_id = ld.event_detail_id ' \
              u'join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :is_hand_disinfect) ' \
              u'ietc4 on ietc4.event_detail_id = ld.event_detail_id ' \
              u'where ce.event_type = 2 and ce.event_detail = 1 and ld.itinerary_id = :itinerary_id;'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id, "id_no": "id_no",
                                                "temperature": "temperature", "is_face_mask": "is_face_mask",
                                                "is_hand_disinfect": "is_hand_disinfect"})
        return result

    def get_real_time_traffic_alarm(self, itinerary_id):
        """
        获取行车实时告警信息
        行驶告警
        查询出来的 description_content 中有变量，其中：
        变量 {student_name} 替换为 查询出来的 student_name
        变量 {company_line_name} 和 变量 {driver_name} 都替换为 查询出来的 driver_name
        --姓名
        :return:
        """
        sql = u'select ietc1.event_attach_id, ce.collect_event,ld.created_date, ied.description_content, ' \
              u'pb1.full_name as driver_name,pb2.full_name as student_name, cl.company_line_name ' \
              u'from log_event_detail ld ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join info_event_description ied on ied.event_target_id = iet.event_target_id ' \
              u'join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id ' \
              u'join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id ' \
              u'join info_company_line cl on cl.company_line_id = pbs.company_line_id ' \
              u'join info_driver d on ir.driver_id = d.id ' \
              u'join info_people_basic_facts pb1 on pb1.id = d.basic_id ' \
              u'left join (select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :id_no) ' \
              u'ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'left join (select a.full_name, a.id_no from info_people_basic_facts a ' \
              u'join info_student s on s.basic_id = a.id ) pb2 on pb2.id_no = ietc1.collect_attach_char ' \
              u'where et.event_type_code in :selection ' \
              u'and ld.itinerary_id = :itinerary_id ' \
              u'order by ld.created_date desc limit 5;'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id, "id_no": "id_no", "selection":
            ("student_disciplinary","driving_offences","driving_alarm", "other_alarm")})
        return result

    def get_current_speed(self, itinerary_id):
        """
        获取当前行驶速度
        :param itinerary_id:
        :return:
        """
        sql = u'select bus_speed from log_bus_drive where itinerary_id= :itinerary_id ' \
              u'order by created_date desc limit 1;'
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
        return result

    def get_current_location(self, itinerary_id):
        """
        查询当前车速及车辆所在位置
        :param itinerary_id:
        :return:
        """
        sql = r" select bus_speed,bus_longitude || ',' || bus_latitude as " \
               " current_station_location from log_bus_drive where " \
               " itinerary_id = :itinerary_id order by created_date desc " \
               " limit 1; "
        result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
        return result

    def get_student_status(self, itinerary_id):
        """
        学生下车状态以及接车人信息
        :param itinerary_id:
        :return:
        """
        sql = u'select a.id_no, a.off_time, b.full_name as picker_name, b.relation_student, b.img, b.take_photo ' \
              u'from(' \
              u'select ld.itinerary_id, s.id as student_id, ld.created_date as off_time, pbf.id_no,' \
              u'rank() over(partition by s.id order by ld.created_date desc) as row_no ' \
              u'from log_event_detail ld ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join( select a. * from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :id_no) ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'join info_people_basic_facts pbf on pbf.id_no = ietc1.collect_attach_char ' \
              u'join info_student s on s.basic_id = pbf.id ' \
              u'where ld.itinerary_id = :itinerary_id and ce.event_type = 2 and ce.event_detail = 4) a ' \
              u'left join(select ld.itinerary_id, sj.student_id, sj.full_name, sj.relation_student,' \
              u'ietc2.collect_attach_char as img, sj.take_photo, rank() over(partition by sj.student_id order by ld.created_date desc) as row_no ' \
              u'from log_event_detail ld ' \
              u'join info_collect_event ce on ld.collect_event_id = ce.collect_event_id ' \
              u'join info_event_target iet on iet.event_target_id = ce.event_target_id ' \
              u'join info_event_type et on et.event_type_id = ce.event_type_id ' \
              u'join( select a. * from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :unique_identity) ietc1 on ietc1.event_detail_id = ld.event_detail_id ' \
              u'join(select ig.student_id, pbf1.id as basic_id, pbf1.full_name, ig.relation_student, pbf1.take_photo ' \
              u'from info_guardian ig ' \
              u'join info_people_basic_facts pbf1 on pbf1.id = ig.basic_id union all ' \
              u'select ia.student_id, pbf2.id as basic_id, pbf2.full_name, ia.relation_student, pbf2.take_photo ' \
              u'from info_authorize ia ' \
              u'join info_people_basic_facts pbf2 on pbf2.id = ia.basic_id) sj on ietc1.collect_attach_char = sj.basic_id || :num ' \
              u'join( select a.* from log_event_attach a ' \
              u'join info_event_target b on a.event_target_id = b.event_target_id ' \
              u'where b.event_target_code = :img ) ietc2 on ietc2.event_detail_id = ld.event_detail_id ' \
              u'where ld.itinerary_id = :itinerary_id and ce.event_type = 2 and ce.event_detail = 3)b on a.student_id = b.student_id and b.row_no = 1 ' \
              u'where a.row_no = 1;'
        result = db.session.execute(text(sql), {"num": "", "itinerary_id": itinerary_id, "id_no": "id_no",
                                                "img": "img", "unique_identity": "unique_identity"})

        return result


