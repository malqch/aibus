#!/usr/bin/python
# -*- coding: utf-8 -*-


from sqlalchemy import text
from app import db


class ItineraryReceiptModule(object):

    @staticmethod
    def insert_itinerary_report(data):
        """提交行程回单 """
        sql = """
        insert into itinerary_report
        (itinerary_id,not_wear_mask_total,no_hand_disinfection_total,
        student_violation_total,student_violation_count,safety_officer_wear_mask, safety_officer_hand_disinfectio,
        driver_wear_mask,driver_hand_disinfection,driving_violation_total,no_authorize_warn,take_total,load_total,
        non_total,leave_total,body_temperature_abnormal_total,safety_officer_body_temperature,driver_body_temperature,
        create_user_id,create_dt,modify_user_id,modify_dt) 
        values
        (:itinerary_id,:not_wear_mask_total,:not_hand_disinfection_total,:student_violation_total,
        :student_violation_count,:safety_officer_wear_mask, :safety_officer_hand_disinfectio,
        :driver_wear_mask,:driver_hand_disinfection,:driving_violation_total,:no_authorize_warn,
        :take_total,:load_total,:non_total,:leave_total,:body_temperature_abnormal_total,
        :safety_officer_body_temperature,:driver_body_temperature,
        :create_user_id,:create_dt,:modify_user_id,:modify_dt)"""

        # 执行语句
        result = db.session.execute(text(sql),
                                    {key_: data[key_] if data[key_] and data[key_] != 'None' or data[key_] == 0 else "-1"
                                     for key_ in data})
        # # 返回结果
        return result

    @staticmethod
    def query_itinerary_list(role):
        """获取角色行程列表"""

        if str(role.get("role_id")) == "2":
            sql = """
            select ir.itinerary_id,cl.company_line_name,ir.itinerary_end_time,ir.itinerary_status,ir.report_status  
            from itinerary_receipt ir 
            join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
            join info_company_line cl on cl.company_line_id = pbs.company_line_id 
            join admin_position_auth pa on pa.company_id = cl.school_id 
            join admin_user_position up on up.position_id = pa.position_id 
            where ir.itinerary_status = '3' 
            and pa.is_deleted = '0' and up.is_deleted = '0' 
            and up.user_id = :user_id 
            order by ir.itinerary_end_time desc"""
        elif str(role.get("role_id")) == "3":
            sql = """
            select ir.itinerary_id,cl.company_line_name,ir.itinerary_end_time,ir.itinerary_status 
            from itinerary_receipt ir 
            join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
            join info_company_line cl on cl.company_line_id = pbs.company_line_id 
            join info_driver d on ir.driver_id = d.id 
            join info_safety_officer s on ir.safety_officer_id = s.id 
            where ir.itinerary_status = '3' 
            and (d.login_user_id = :user_id or s.login_user_id = :user_id) 
            order by ir.itinerary_end_time desc
            """
        elif str(role.get("role_id")) == "5":
            sql = """
            select ir.itinerary_id,cl.company_line_name,ir.itinerary_end_time,ir.itinerary_status 
            from itinerary_receipt ir 
            join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
            join info_company_line cl on cl.company_line_id = pbs.company_line_id 
            join student_line_seat ls on (ls.up_company_line_id = cl.company_line_id or ls.off_company_line_id = cl.company_line_id) join info_guardian g on ls.student_id = g.student_id 
            where g.login_user_id = :user_id and ir.itinerary_status = '3' 
            order by ir.itinerary_end_time desc
            """
        else:
            return None
        result = db.session.execute(text(sql), {"user_id": role.get("user_id")})
        # 返回结果
        return result

    @staticmethod
    def query_user_role(token):
        """ 获取用户id 和角色id """
        sql = """
        select distinct aur.user_id, aur.role_id from admin_user_token aut 
        join admin_user_role aur on aut.user_id=aur.user_id 
        where aut.token=:token
        """

        result = db.session.execute(text(sql), {"token": token})
        # 返回结果
        return result

    @staticmethod
    def query_route_details(itinerary_id):
        """ 行程回单详情"""

        sql = "select ir.*,cl.company_line_name,cl.direction from itinerary_receipt ir " \
              "join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id " \
              "join info_company_line cl on cl.company_line_id = pbs.company_line_id " \
              "where itinerary_id =:itinerary_id"
        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

    @staticmethod
    def update_route_details(data):
        """ 更新行程 上报状态 和 上报方式 """

        sql = "update itinerary_receipt set report_status='1',report_way=:report_way where itinerary_id=:itinerary_id"

        result = db.session.execute(text(sql), {"itinerary_id": data[0], "report_way": data[1]})
        # 返回结果
        return result

    @staticmethod
    def query_route_all():
        """ 获取已完成的行程中未在指定时间内提交的行程"""

        sql = "select distinct itinerary_id, itinerary_end_time from itinerary_receipt " \
              "where itinerary_status='3' and report_status='0'"

        result = db.session.execute(text(sql))
        # 返回结果
        return result

    @staticmethod
    def query_route_timestamp(itinerary_id):
        """ 行程时刻表记录【站点名称，进站时间（时分秒），出站时间（时分秒）】"""

        sql = """
        select ls.station_order,ls.bus_station_id, bs.bus_station_name, lbs.arrival_time,lbs.leave_time,
        case when lbs.arrival_bus_station_id is null then '0'  -- 未到站 
        when lbs.arrival_bus_station_id is not null and lbs.leave_time is null then '2'  -- 已到站还未离开 
        when lbs.arrival_bus_station_id is not null and lbs.leave_time is not null then '1' -- 已离站，还未到达下一站 
        end as is_arrival 
        from info_line_station ls 
        join info_bus_station bs on ls.bus_station_id = bs.bus_station_id 
        join plan_bus_service pbs on pbs.company_line_id = ls.company_line_id 
        join itinerary_receipt ir on ir.plan_service_id = pbs.plan_service_id 
        left join (select bus_station_id as arrival_bus_station_id,arrival_time,leave_time 
        from log_bus_service where itinerary_id = :itinerary_id) lbs on ls.bus_station_id = lbs.arrival_bus_station_id 
        where ir.itinerary_id = :itinerary_id and ls.is_deleted = 0 
        and bs.is_deleted = 0 and bs.is_enabled = 1 order by ls.station_order asc"""

        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

    @staticmethod
    def query_student_up_down(itinerary_id, role):
        """ 学生上下车详单"""

        sql = """
                select 
                a.student_id,a.student_name,b.up_time,b.temperature,b.is_face_mask,b.is_hand_disinfect
                ,c.off_time,d.full_name as picker_name,d.relation_student,
                case when coalesce(d.img, '')='' then '' else concat('/aibus/',d.img) end as img  
                from( 
                --学生列表 
                select s.id as student_id,pbf.full_name as student_name 
                from info_student s 
                join info_people_basic_facts pbf on pbf.id = s.basic_id 
                join student_line_seat ls on s.id = ls.student_id 
                join info_company_line cl on (cl.company_line_id = ls.up_company_line_id or cl.company_line_id = ls.off_company_line_id) 
                join plan_bus_service pbs on pbs.company_line_id = cl.company_line_id 
                join itinerary_receipt ir on ir.plan_service_id = pbs.plan_service_id 
                join info_guardian g on g.student_id = s.id 
                where ir.itinerary_id = :itinerary_id {}and cl.is_deleted = 0 and cl.is_enabled = 1) a 
                left join(
                --学生上车 
                select s.id as student_id,ld.created_date as up_time,
                rank() over(partition by s.id order by ld.created_date asc) as row_no,
                ietc2.collect_attach_value as temperature,ietc3.collect_attach_char as is_face_mask,   --  0 否 1 是
                ietc4.collect_attach_char as is_hand_disinfect  -- 0 否 1 是 
                from log_event_detail ld 
                join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
                join info_event_target iet on iet.event_target_id = ce.event_target_id 
                join info_event_type et on et.event_type_id = ce.event_type_id 
                join (
                --证件号 
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'id_no') ietc1 on ietc1.event_detail_id = ld.event_detail_id 
                join (
                --体温
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'temperature') ietc2 on ietc2.event_detail_id = ld.event_detail_id 
                join (
                --是否佩戴口罩 
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'is_face_mask') ietc3 on ietc3.event_detail_id = ld.event_detail_id 
                join (
                --是否手部消毒 
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'is_hand_disinfect') ietc4 on ietc4.event_detail_id = ld.event_detail_id 
                join info_people_basic_facts pbf on pbf.id_no = ietc1.collect_attach_char 
                join info_student s on s.basic_id = pbf.id 
                where ld.itinerary_id = :itinerary_id and ce.event_type = 2 and ce.event_detail = 1)b 
                on b.student_id = a.student_id and b.row_no = 1 
                left join(
                --下车 
                select ld.itinerary_id, s.id as student_id,ld.created_date as off_time,
                rank() over(partition by s.id order by ld.created_date desc) as row_no 
                from log_event_detail ld 
                join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
                join info_event_target iet on iet.event_target_id = ce.event_target_id 
                join info_event_type et on et.event_type_id = ce.event_type_id 
                join (
                --证件号 
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'id_no') ietc1 on ietc1.event_detail_id = ld.event_detail_id 
                join info_people_basic_facts pbf on pbf.id_no = ietc1.collect_attach_char 
                join info_student s on s.basic_id = pbf.id 
                where ld.itinerary_id = :itinerary_id and ce.event_type = 2 and ce.event_detail = 4)c 
                on c.student_id = a.student_id and c.row_no = 1 
                left join(
                --接车 
                select ld.itinerary_id,sj.student_id,sj.full_name,sj.relation_student,ietc2.collect_attach_char as img,
                rank() over(partition by sj.student_id order by ld.created_date desc) as row_no 
                from log_event_detail ld 
                join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
                join info_event_target iet on iet.event_target_id = ce.event_target_id 
                join info_event_type et on et.event_type_id = ce.event_type_id 
                join (
                --唯一身份标识
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'unique_identity') ietc1 on ietc1.event_detail_id = ld.event_detail_id 
                join (
                select ig.student_id,pbf1.id as basic_id,pbf1.full_name,ig.relation_student 
                from info_guardian ig 
                join info_people_basic_facts pbf1 on pbf1.id = ig.basic_id 
                union all 
                select ia.student_id,pbf2.id as basic_id,pbf2.full_name,ia.relation_student 
                from info_authorize ia 
                join info_people_basic_facts pbf2 on pbf2.id = ia.basic_id) sj on ietc1.collect_attach_char = sj.basic_id||'' 
                join (
                select a.* from log_event_attach a 
                join info_event_target b on a.event_target_id = b.event_target_id 
                where b.event_target_code = 'img') ietc2 on ietc2.event_detail_id = ld.event_detail_id 
                where ld.itinerary_id = :itinerary_id 
                and ce.event_type = 2 and ce.event_detail = 3) d on d.student_id = a.student_id and d.row_no = 1"""

        if str(role.get("role_id")) == "2":
            sql = sql.format('')
        elif str(role.get("role_id")) == "5":
            user_id = role.get("user_id")
            str_ = 'and g.login_user_id = {} '.format(user_id)
            sql = sql.format(str_)

        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

    @staticmethod
    def query_history_disciplinary(itinerary_id):
        """ 行程历史违纪分布 """
        sql = """select ce.collect_event,count(ld.event_detail_id) as alarm_count 
        from log_event_detail ld 
        join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
        join info_event_target iet on iet.event_target_id = ce.event_target_id 
        join info_event_type et on et.event_type_id = ce.event_type_id 
        join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id 
        join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
        join info_company_line cl on cl.company_line_id = pbs.company_line_id 
        where et.event_type_code in('student_disciplinary','driving_offences','driving_alarm','other_alarm') 
        and ir.itinerary_id=:itinerary_id 
        group by ce.collect_event"""

        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

    @staticmethod
    def query_route3_disciplinary(itinerary_id, time3):
        """ 三日内行车违规列表 """

        sql = """select ce.collect_event,ld.created_date,ied.description_content,
        pb1.full_name as driver_name,cl.direction 
        from log_event_detail ld 
        join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
        join info_event_target iet on iet.event_target_id = ce.event_target_id 
        join info_event_type et on et.event_type_id = ce.event_type_id 
        join info_event_description ied on ied.event_target_id = iet.event_target_id 
        join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id 
        join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
        join info_company_line cl on cl.company_line_id = pbs.company_line_id 
        join info_driver d on ir.driver_id = d.id 
        join info_people_basic_facts pb1 on pb1.id = d.basic_id 
        where et.event_type_code in('driving_offences','driving_alarm') 
        and ir.itinerary_id=:itinerary_id 
        and ld.created_date >= :time3
        order by ld.created_date desc"""

        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id, "time3": time3})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

    @staticmethod
    def query_student3_disciplinary(itinerary_id, time3, role):
        """ 三日内学生违纪列表 """

        if str(role.get("role_id")) == "2":
            sql = """select ce.collect_event,ld.created_date,ied.description_content,
            ietc1.collect_attach_char as student_name,cl.direction 
            from log_event_detail ld 
            join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
            join info_event_target iet on iet.event_target_id = ce.event_target_id 
            join info_event_type et on et.event_type_id = ce.event_type_id 
            join info_event_description ied on ied.event_target_id = iet.event_target_id 
            join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id 
            join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id 
            join info_company_line cl on cl.company_line_id = pbs.company_line_id 
            join (
            select a.* from log_event_attach a 
            join info_event_target b on a.event_target_id = b.event_target_id 
            where b.event_target_code = 'name') ietc1 on ietc1.event_detail_id = ld.event_detail_id 
            where et.event_type_code in('student_disciplinary') 
            and ir.itinerary_id=:itinerary_id 
            and ld.created_date >= :time3 
            order by ld.created_date desc"""
        elif str(role.get("role_id")) == "5":
            sql = """select ld.event_detail_id, ce.collect_event,ied.description_content,ld.created_date,
            cl.company_line_name,ietc1.collect_attach_char as student_name,concat('/aibus/',ietc2.collect_attach_char) as img,
            ietc3.collect_attach_char as check_permissions,ietc3.event_attach_id,cl.direction from log_event_detail ld 
            join (
            --姓名 
            select a.* from log_event_attach a 
            join info_event_target b on a.event_target_id = b.event_target_id 
            where b.event_target_code = 'name') ietc1 on ietc1.event_detail_id = ld.event_detail_id 
            left join ( 
            --车前照片 
            select a.* from log_event_attach a 
            join info_event_target b on a.event_target_id = b.event_target_id 
            where b.event_target_code = 'img1') ietc2 on ietc2.event_detail_id = ld.event_detail_id 
            join (
            --查看权限 
            select a.* from log_event_attach a 
            join info_event_target b on a.event_target_id = b.event_target_id 
            where b.event_target_code = 'check_permissions') ietc3 on ietc3.event_detail_id = ld.event_detail_id 
            join info_collect_event ce on ld.collect_event_id = ce.collect_event_id 
            join info_event_target iet on iet.event_target_id = ce.event_target_id 
            join info_event_type et on et.event_type_id = ce.event_type_id 
            join info_event_description ied on ied.event_target_id = iet.event_target_id 
            join itinerary_receipt ir on ld.itinerary_id = ir.itinerary_id 
            join plan_bus_service pbs on pbs.plan_service_id = ir.plan_service_id and pbs.is_deleted = 0 
            join info_company_line cl on cl.company_line_id = pbs.company_line_id and cl.is_deleted = 0 
            where et.event_type_code = 'student_disciplinary' and ietc1.collect_attach_char = '木二行' 
            and ld.itinerary_id = :itinerary_id 
            order by ld.created_date"""

        try:
            result = db.session.execute(text(sql), {"itinerary_id": itinerary_id, "time3": time3})
            db.session.commit()
            # 返回结果
            return result
        except:
            # 发生错误回滚
            db.session.rollback()
            db.session.remove()
            return []

