#!/usr/bin/python
# -*- coding: utf-8 -*-
from datetime import datetime
import re
from flask import json
from math import radians, cos, sin, asin, sqrt
from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from app.schoolbus_situation.models.itinerary_receipt_model import ItineraryReceipt
from app.schoolbus_situation.models.itinerary_receipt_model import ItineraryReceipt
from app.schoolbus_situation.models.plan_bus_service_model import PlanBusService
from app.schoolbus_situation.models.student_line_seat_model import StudentLineSeat
from public.format import format_result, format_result2one, format_result_id2str
from public.logger import logger
from public.res_code import ResponseCode


class BusCurrentSituation(object):

    @staticmethod
    def get_bus_current_situation(info, login_user_id, role_code):
        """
        校车实时态势轮询
        :param info: info中包含线路id
        :param role_code
        :return:
        """
        bus_current_situation_info = {"itinerary_result": [], "company_line_result": [], "driver_safety_result": [],
                                      "student_result": [], "driving_current_alarm_result": []}
        itinerary_id = info.get("itinerary_id")
        company_line_id = info.get("company_line_id")
        now_time = datetime.now().strftime("%Y%m%d")
        logger.info("当前时间" + now_time)

        # 行程信息
        itinerary_result = format_result_id2str(InfoCompanyLine().get_itinerary_info(itinerary_id))
        all_current_result = []
        if itinerary_result:
            # itinerary_id = itinerary_result[0]['itinerary_id']

            # 行驶速度(当前车速)
            bus_speed_obj = format_result2one(InfoCompanyLine().get_current_speed(itinerary_id))
            bus_speed = bus_speed_obj
            if bus_speed_obj:
                bus_speed = bus_speed_obj['bus_speed']
            itinerary_result[0]["bus_speed"] = bus_speed
            bus_current_situation_info["itinerary_result"] = itinerary_result

        # 线路车站列表
        company_line_result = format_result_id2str(InfoCompanyLine().get_line_station_list(itinerary_id, company_line_id))
        if company_line_result:
            bus_current_situation_info["company_line_result"] = company_line_result

        # 司机安全员信息
        driver_safety_result = format_result_id2str(InfoCompanyLine().get_driver_and_safey_officer(itinerary_id, now_time))
        if driver_safety_result:
            for driver_safety in driver_safety_result:
                driver_safety["driver_is_face_mask"] = []
                driver_safety["driver_is_hand_disinfect"] = []
                driver_safety["driver_temperature"] = 0
                driver_safety["safety_officer_is_face_mask"] = []
                driver_safety["safety_officer_is_hand_disinfect"] = []
                driver_safety["safety_officer_temperature"] = 0
                if driver_safety["driver_no_criminal_record_photo"]:
                    driver_safety["driver_no_criminal_record_photo"] = "/aibus/" + driver_safety[
                        "driver_no_criminal_record_photo"]
                if driver_safety["driver_take_photo"]:
                    driver_safety["driver_take_photo"] = "/aibus/" + driver_safety["driver_take_photo"]
                if driver_safety["safety_officer_no_criminal_record_photo"]:
                    driver_safety["safety_officer_no_criminal_record_photo"] = "/aibus/" + \
                                                                               driver_safety[
                                                                                   "safety_officer_no_criminal_record_photo"]
                if driver_safety["safety_officer_take_photo"]:
                    driver_safety["safety_officer_take_photo"] = "/aibus/" + driver_safety[
                        "safety_officer_take_photo"]

            bus_current_situation_info["driver_safety_result"] = driver_safety_result

        # 司机\安全员\学生的上车状态,体温,口罩,手消毒
        all_current_result = format_result_id2str(InfoCompanyLine().all_current_info(itinerary_id))
        # bus_current_situation_info["all_current_result"] = all_current_result

        # 学生下车状态以及接车人信息
        student_and_guardian_result = format_result_id2str(InfoCompanyLine().get_student_status(itinerary_id))

        # 整合司机安全员学生状态 all_current_result   driver_safety_result
        if driver_safety_result and all_current_result:
            for driver_safety in driver_safety_result:
                for all_current in all_current_result:
                    if driver_safety["driver_no"] == all_current["id_no"]:
                        driver_safety["driver_is_face_mask"] = all_current["is_face_mask"]
                        driver_safety["driver_is_hand_disinfect"] = all_current["is_hand_disinfect"]
                        driver_safety["driver_temperature"] = all_current["temperature"]
                    if driver_safety["safety_officer_no"] == all_current["id_no"]:
                        driver_safety["safety_officer_is_face_mask"] = all_current["is_face_mask"]
                        driver_safety["safety_officer_is_hand_disinfect"] = all_current["is_hand_disinfect"]
                        driver_safety["safety_officer_temperature"] = all_current["temperature"]
            bus_current_situation_info["driver_safety_result"] = driver_safety_result

        # 行车实时告警列表(最大5条) [告警类型,告警描述,告警时间(时分秒)]
        driving_current_alarm_result = format_result_id2str(InfoCompanyLine().get_real_time_traffic_alarm(itinerary_id))
        if driving_current_alarm_result:
            for driving_current_alarm in driving_current_alarm_result:
                driver_name = driving_current_alarm['driver_name']
                student_name = driving_current_alarm['student_name']
                obj = driving_current_alarm["description_content"]
                if bool(re.search("driver", obj)) and driver_name:
                    change_obj = obj.replace("{driver_name}", driver_name)
                elif bool(re.search("company", obj)) and driver_name:
                    change_obj = obj.replace("{company_line_name}", driver_name)
                elif bool(re.search("student", obj)) and student_name:
                    change_obj = obj.replace("{student_name}", student_name)
                else:
                    change_obj = None
                driving_current_alarm["description_content"] = change_obj
            bus_current_situation_info["driving_current_alarm_result"] = driving_current_alarm_result
        logger.info("itinerary_result:--- %s" %itinerary_result)

        # 学生列表
        time_frame = "2"
        if datetime.now().strftime("%H:%M") < "12:00":
            time_frame = '1'
        student_result = format_result_id2str(InfoCompanyLine().get_student_info(now_time, time_frame, company_line_id, login_user_id, role_code))
        if student_result:
            for student in student_result:
                student["student_is_face_mask"] = []
                student["student_is_hand_disinfect"] = []
                student["student_temperature"] = 0
                student["student_off_time"] = None
                student["student_picker_name"] = None
                student["student_relation"] = None
                student["student_picker_photo"] = None
                if student["take_photo"]:
                    student["take_photo"] = "/aibus/" + student["take_photo"]
            bus_current_situation_info["student_result"] = student_result

        # 根据学生体温情况,统计学生上车人数,以及学生请假人数
        load_total = 0
        leave_total = 0
        if all_current_result and student_result:
            for student in student_result:
                for all_current in all_current_result:
                    if student["student_no"] == all_current["id_no"]:
                        student["student_is_face_mask"] = all_current["is_face_mask"]
                        student["student_is_hand_disinfect"] = all_current["is_hand_disinfect"]
                        student["student_temperature"] = all_current["temperature"]
                        if all_current["temperature"]:
                            load_total += 1
                        if student["student_leave_id"]:
                            leave_total += 1
                        if all_current["temperature"] and student["student_leave_id"]:
                            leave_total -= 1
                        break  # 这里的break表示取此学生的第一次触发事件

            bus_current_situation_info["student_result"] = student_result

        # 整合学生信息和学生下车状态以及接车人信息(学生下车了才有后续的数据)
        if student_result and student_and_guardian_result:
            for student in student_result:
                for student_and_guardian in student_and_guardian_result:
                    if student["student_no"] == student_and_guardian["id_no"]:
                        student["student_off_time"] = student_and_guardian.get("off_time")
                        student["student_picker_name"] = student_and_guardian.get('picker_name')
                        student["student_relation"] = student_and_guardian.get("relation_student")
                        student["student_picker_photo"] = student_and_guardian.get("take_photo")
                        if student_and_guardian.get("take_photo"):
                            student["student_picker_photo"] = "/aibus/" + student_and_guardian.get("take_photo")

        if itinerary_result:
            itinerary = itinerary_result[0]
            itinerary['load_total'] = load_total
            itinerary['leave_total'] = leave_total
            bus_current_situation_info['itinerary_result'] = itinerary_result


        # 校车实时态势信息汇总日志记录
        logger.info(bus_current_situation_info)
        return ResponseCode.SUCCEED, '执行成功', bus_current_situation_info

    def get_next_station_arrive_min(self, info, param):
        """
        预计到站时间
        :param info:
        :param param:
        :return:
        """
        data = dict()
        login_user_id = info.user_id
        itinerary_id = param.get("itinerary_id")
        # 查询当前车速及车辆所在位置
        current_location = format_result2one(InfoCompanyLine().get_current_location(itinerary_id))
        if not current_location:
            return ResponseCode.GET_BY_PARAM_ERROR, '未查询到当前校车位置信息！', login_user_id
        bus_speed = current_location.get('bus_speed')
        current_station_location = current_location.get('current_station_location').split(',')
        lon1, lat1 = float(current_station_location[0]), float(current_station_location[1])
        # 查询车辆下一站
        next_station = format_result2one(ItineraryReceipt().is_miss_this_station(itinerary_id))
        next_station_order = next_station['station_order']
        # 查询学生上下车站位置
        up_or_down_location = format_result2one(ItineraryReceipt().get_up_or_down_location(itinerary_id, login_user_id))
        if up_or_down_location:
            station_order = up_or_down_location['station_order']
            if next_station_order > station_order:
                return ResponseCode.OTHER, '车已过站', login_user_id
        if not up_or_down_location:
            return ResponseCode.GET_BY_PARAM_ERROR, '未查询到学生上下车站位置信息！', login_user_id
        station_location = up_or_down_location.get('station_location').split(',')
        lon2, lat2 = float(station_location[0]), float(station_location[1])
        distance_util = self.haversine(lon1, lat1, lon2, lat2)
        res = self.get_estimated_time_of_arrival(distance_util, bus_speed)
        data['arrive_time'] = res
        return ResponseCode.SUCCEED, '执行成功', data

    def haversine(self, lon1, lat1, lon2, lat2):
        """
        计算两点经纬度之间的距离
        """
        # 将十进制度数转化为弧度
        lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
        # haversine公式
        dlon = lon2 - lon1
        dlat = lat2 - lat1
        a = sin(dlat / 2) ** 2 + cos(lat1) * cos(lat2) * sin(dlon / 2) ** 2
        c = 2 * asin(sqrt(a))
        # 地球平均半径，单位为公里
        r = 6371
        return c * r * 1000  # 单位为米

    def get_estimated_time_of_arrival(self, distance_util, bus_speed):
        """
        计算预计到达时间
        """
        # 千米/小时转成米/分钟
        speed = bus_speed * 1000 / 60
        # 计算到达时间
        arrive_time = distance_util // speed
        return arrive_time





