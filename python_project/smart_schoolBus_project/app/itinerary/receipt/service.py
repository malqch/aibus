# -*- coding: utf-8 -*-

from flask import current_app
from public.res_code import ResponseCode
from public.format import format_result
from .model import ItineraryReceiptModule


class ItineraryReceiptService(object):

    @staticmethod
    def get_itinerary_list(role):

        try:
            data = format_result(ItineraryReceiptModule.query_itinerary_list(role))

            return 0, "", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return 1, "error", ""

    @staticmethod
    def get_user_role(token):

        try:
            role = format_result(ItineraryReceiptModule.query_user_role(token))
            return role
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return ""

    @staticmethod
    def get_route_details(itinerary_id):

        try:
            data = format_result(ItineraryReceiptModule.query_route_details(itinerary_id))
            return "route_details", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "route_details", []

    @staticmethod
    def commit_itinerary_report(data):

        try:
            ItineraryReceiptModule.insert_itinerary_report(data)
            return 0, "提交成功"
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return 1, "提交失败"

    @staticmethod
    def update_itinerary_upload_way(data):

        try:
            ItineraryReceiptModule.update_route_details(data)
            return 0, "上报成功"
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return 1, "上报失败"

    @staticmethod
    def route_no_commmit():
        """ 获取已完成的行程中未在指定时间内提交的行程"""
        try:
            data = format_result(ItineraryReceiptModule.query_route_all())
            return data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return ""

    @staticmethod
    def get_route_timestamp(itinerary_id):
        """ 行程时刻表记录【站点名称，进站时间（时分秒），出站时间（时分秒）】"""
        try:
            data = format_result(ItineraryReceiptModule.query_route_timestamp(itinerary_id))

            return "route_timestamp", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "route_timestamp", []

    @staticmethod
    def get_student_up_down(itinerary_id, role):
        """ 学生上下车详单"""
        try:
            data = format_result(ItineraryReceiptModule.query_student_up_down(itinerary_id, role))

            return "student_up_down", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "student_up_down", []

    @staticmethod
    def get_history_disciplinary(itinerary_id):
        """ 行程历史违纪分布　"""
        try:
            data = format_result(ItineraryReceiptModule.query_history_disciplinary(itinerary_id))
            # if not data:
            #     data = [{"event_type_name": "行驶告警", "alarm_count": 1},
            #             {"event_type_name": "驾驶违规", "alarm_count": 4}]

            return "history_disciplinary", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "history_disciplinary", []

    @staticmethod
    def get_route3_disciplinary(itinerary_id, time3):
        """ 三日内行车违规列表 """
        try:
            data = format_result(ItineraryReceiptModule.query_route3_disciplinary(itinerary_id, time3))
            # if not data:
            #     data = [{"collect_event": "行驶超速", "created_date": "2021-01-24 20:28:49",
            #              "description_content": "行驶过程中超速", "driver_name": "崔司机", "direction": 2}]
            return "route3_disciplinary", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "route3_disciplinary", []

    @staticmethod
    def get_student3_disciplinary(itinerary_id, time3, role):
        """ 三日内学生违纪列表 """
        try:
            data = format_result(ItineraryReceiptModule.query_student3_disciplinary(itinerary_id, time3, role))
            # if not data:
            #     data = [{"collect_event": "嬉戏", "created_date": "2021-01-24 20:28:49",
            #              "description_content": "行驶过程中超速", "student_name": "崔司机", "direction": 2}]
            return "student3_disciplinary", data
        except Exception as e:
            # 捕获异常并写入日志
            current_app.logger.exception('message info is {0}'.format(e))
            return "student3_disciplinary", []

