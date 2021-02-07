#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime
import re

from app.schoolbus_situation.models.log_event_detail_model import LogEventDetail
from public.format import format_result_id2str
from public.logger import logger
from public.res_code import ResponseCode


class HistoryLogService(object):

    @staticmethod
    def get_history_log(info, login_user_id, role_code):
        """
        获取日志列表(行驶告警\司机违规\学生违纪\校车年检\校车备案\授权人变更)
        :param info:
        :return:
        """
        log_result = {"driving_alarm": [], "driving_offences": [], "other_alarm": [], "student_disciplinary_result": [], "school_bus_review": [],
                    "school_bus_registration": [], "authorizer_change_log": []}
        company_line_ids = (info.get("company_line_id_1"), info.get("company_line_id_2"))
        begin_date = info.get("begin_date")
        end_date = info.get("end_date") + " 23:59:59"

        # 行驶告警列表
        driving_alarm_result = format_result_id2str(LogEventDetail().
                                             get_drive_alarm(company_line_ids, login_user_id,
                                                             begin_date, end_date))
        if driving_alarm_result:
            for driver in driving_alarm_result:
                driver_name = driver['driver_name']
                student_name = driver['student_name']
                obj = driver["description_content"]
                if bool(re.search("{", obj)):
                    change_obj = obj.replace("{driver_name}", driver_name)
                    change_obj = change_obj.replace("{company_line_name}", driver_name)
                    if student_name:
                        change_obj = change_obj.replace("{student_name}", student_name)
                    driver["description_content"] = change_obj
            log_result['driving_alarm'] = driving_alarm_result

        # 司机违规
        driving_offences_result = format_result_id2str(LogEventDetail().
                                                get_drive_alarm(company_line_ids, login_user_id,
                                                                begin_date, end_date, event_type_code="driving_offences"))
        if driving_offences_result:
            for driver in driving_offences_result:
                driver_name = driver['driver_name']
                if driver["img"]:
                    driver["img"] = "/aibus/" + driver["img"]
                obj = driver["description_content"]
                if bool(re.search("driver_name", obj)):
                    change_obj = obj.replace("{driver_name}", driver_name)
                    driver["description_content"] = change_obj
            log_result['driving_offences'] = driving_offences_result

        # 其他告警
        other_alarm_result = format_result_id2str(LogEventDetail().
                                                get_drive_alarm(company_line_ids, login_user_id,
                                                                begin_date, end_date, event_type_code="other_alarm"))
        if other_alarm_result:
            for other_alarm in other_alarm_result:
                student_name = other_alarm['student_name']
                if other_alarm["img"]:
                    other_alarm["img"] = "/aibus/" + other_alarm["img"]
                obj = other_alarm["description_content"]
                if bool(re.search("student_name", obj)):
                    change_obj = obj.replace("{student_name}", student_name)
                    other_alarm["description_content"] = change_obj
            log_result['other_alarm'] = other_alarm_result


        # 学生违纪
        student_disciplinary_result = []
        if role_code == "sa":  # 行后查询学生违纪记录
            student_disciplinary_result = format_result_id2str(LogEventDetail().worker_get_student_disciplinary(login_user_id, begin_date, end_date, company_line_ids))
        elif role_code == 'pa':  # 家长查询学生违纪记录
            # 查询相应家长对应的学生姓名(目前是一个家长对应一个学生)
            student_info = format_result_id2str(LogEventDetail().get_student_name_by_login_user_id(login_user_id))
            logger.info(student_info)
            if student_info:
                student_name = student_info[0]["student_name"]
                student_disciplinary_result = format_result_id2str(LogEventDetail().parents_get_student_disciplinary(begin_date, end_date, company_line_ids, student_name))

        if student_disciplinary_result:
            for student in student_disciplinary_result:
                student_name = student['student_name']
                if student["img"]:
                    student["img"] = "/aibus/" + student["img"]
                obj = student["description_content"]
                if bool(re.search("student_name", obj)):
                    change_obj = obj.replace("{student_name}", student_name)
                    student["description_content"] = change_obj

            log_result['student_disciplinary_result'] = student_disciplinary_result

        # 校车年检
        school_bus_review_result = format_result_id2str(LogEventDetail().get_school_bus_review_time(login_user_id, company_line_ids))
        if school_bus_review_result:
            log_result['school_bus_review'] = school_bus_review_result

        # 校车备案
        school_bus_registration_result = format_result_id2str(LogEventDetail().get_school_bus_registration(login_user_id, company_line_ids))
        if school_bus_registration_result:
            log_result['school_bus_registration'] = school_bus_registration_result

        # 授权人变更
        authorizer_change_log_result = format_result_id2str(LogEventDetail().get_authorizer_change_log(login_user_id, company_line_ids, begin_date, end_date))
        if authorizer_change_log_result:
            log_result['authorizer_change_log'] = authorizer_change_log_result
        logger.info(log_result)
        return ResponseCode.SUCCEED, '查询成功', log_result

    @staticmethod
    def update_check_permissions(role_code, info):
        """
        更新查询图片的权限(行后\家长)
        :param login_user_id:
        :return:
        """
        event_attach_id = info.get("event_attach_id")
        if role_code == 'sa':  # 行后通过家长查看图片申请
            result = LogEventDetail().worker_update_check_permissions(event_attach_id)
        elif role_code == 'pa':  # 家长申请查看图片
            result = LogEventDetail().parents_update_check_permissions(event_attach_id)
        else:
            pass
        return ResponseCode.SUCCEED, '执行成功', None

    @staticmethod
    def get_company_line(login_user_id):
        """
        获取线路id
        :param login_user_id:
        :return:
        """
        results = []
        company_line_result = format_result_id2str(LogEventDetail.get_company_line(login_user_id))
        for i in range(len(company_line_result)-1, -1, -1):
            for j in range(len(company_line_result)-2, -1, -1):
                if company_line_result[i]["company_line_name"] == company_line_result[j]["company_line_name"] and i != j:
                    if company_line_result[i]["company_line_id"] != company_line_result[j]["company_line_id"]:
                        value = company_line_result[i]["company_line_id"] + "&" + company_line_result[j]["company_line_id"]
                        _dict = {"company_line_ids": value,"company_line_name" :company_line_result[i]["company_line_name"]}
                        results.append(_dict)
            company_line_result.remove(company_line_result[i])
        return  ResponseCode.SUCCEED, '执行成功', results

    @staticmethod
    def parents_get_student_disciplinary(login_user_id, info):
        itinerary_id = info.get("itinerary_id")
        student_info = format_result_id2str(LogEventDetail().get_student_name_by_login_user_id(login_user_id))
        logger.info(student_info)
        student_disciplinary_result = []
        if student_info:
            student_name = student_info[0]["student_name"]
            student_disciplinary_result = format_result_id2str(
                LogEventDetail().parents_get_student_info(itinerary_id, student_name))
            logger.info(student_disciplinary_result)
            if student_disciplinary_result:
                for student in student_disciplinary_result:
                    obj = student["description_content"]
                    if bool(re.search("student_name", obj)):
                        change_obj = obj.replace("{student_name}", student_name)
                        student["description_content"] = change_obj
            else:
                student_disciplinary_result = []

        return ResponseCode.SUCCEED, '执行成功', student_disciplinary_result








