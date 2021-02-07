#!/usr/bin/python
# -*- coding: utf-8 -*-
import time
from datetime import datetime

from app.schoolbus_situation.models.student_line_seat_log_model import StudentLineSeatLog
from app.schoolbus_situation.models.student_line_seat_model import StudentLineSeat
from public.logger import logger
from public.res_code import ResponseCode
from public.format import format_result, format_result2one


class BusStationService(object):

    def get_bus_station(self, login_user_id):
        """
        获取校车列表
        """
        result = {}
        up_off_station = format_result2one(StudentLineSeat().get_one_bus_station(login_user_id))
        bus_station_list = format_result(StudentLineSeat().get_all_bus_station(login_user_id))
        if up_off_station and bus_station_list:
            result["up_off_station"] = up_off_station
            result["bus_station_list"] = bus_station_list
        return ResponseCode.SUCCEED, '执行成功', result

    def update_bus_station(self, login_user_id, info):
        """
        修改上下车站点
        :param login_user_id:
        :param info:
        :return:
        """
        up_station_id = info.get("up_station_id")
        off_station_id = info.get("off_station_id")
        now_time = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

        # 当前仅仅是针对一个家长一个学生的情况
        student_result = format_result2one(StudentLineSeat().get_student_id(login_user_id))
        student_id = student_result['student_id']

        # 验证前端所传上下站点是否与数据库中一致
        compare_result = format_result2one(StudentLineSeat().compare_student_seat(student_id, up_station_id, off_station_id))
        if compare_result:
            logger.info("上下车站点无变化")
            return ResponseCode.EXIST, '上下车站点无变化', None

        # 将当前student_line_seat表中数据同步到student_line_seat_log表中
        student_obj = format_result2one(StudentLineSeat().get_student_line_seat(student_id))
        add_result = StudentLineSeatLog().add_student_line_seat_log(student_obj, login_user_id, now_time)

        if add_result:
            logger.info("同步到student_line_seat_log表成功")
            #  同步成功后,修改上下车站点
            update_result = StudentLineSeat().update_bus_station(login_user_id, student_id, up_station_id, off_station_id, now_time)

        return ResponseCode.SUCCEED, '执行成功', None



