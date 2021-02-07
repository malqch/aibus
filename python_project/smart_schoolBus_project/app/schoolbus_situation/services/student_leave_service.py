#!/usr/bin/python
# -*- coding: utf-8 -*-
import random
import uuid
from datetime import datetime
from app.schoolbus_situation.models.student_leave_model import StudentLeave
from app.schoolbus_situation.models.student_line_seat_model import StudentLineSeat
from public.res_code import ResponseCode
from public.format import format_result, format_result2one


class StudentLeaveService(object):

    def get_student_leave(self, login_user_id):
        """
        查询请假
        """
        result = format_result(StudentLeave().get_student_leave(login_user_id))
        return ResponseCode.SUCCEED, '执行成功', result

    def add_student_leave(self,login_user_id, info):
        """
        申请请假
        :return:
        """
        student_result = format_result2one(StudentLineSeat().get_student_id(login_user_id))
        student_id = student_result['student_id']
        now_time = datetime.now().strftime("%Y-%m-%d")
        start_date = (info.get("start_date"))
        start_time_frame = info.get("start_time_frame")
        end_date = (info.get("end_date"))
        end_time_frame = info.get("end_time_frame")
        if start_date == end_date and start_time_frame == end_time_frame:
            leave_result = StudentLeaveService.insert_one_student_leave(login_user_id, student_id, now_time, start_date, start_time_frame)
            if not leave_result:
                return ResponseCode.EXIST, '执行失败', '当前时间段已请假,请核实'
        elif start_date == end_date and start_time_frame == '1' and end_time_frame == '2':
            flag = 0
            for time_frame in ["1","2"]:
                leave_result = StudentLeaveService.insert_one_student_leave(login_user_id, student_id, now_time, start_date,
                                                             time_frame)
                if not leave_result:
                    flag += 1
            if flag == 2:
                return ResponseCode.EXIST, '执行失败', '当前时间段已请假,请核实'
        elif start_date > end_date or (start_date == end_date and start_time_frame > end_time_frame):
            return ResponseCode.ERROR, '执行失败', "开始日期大于结束日期"
        else:
            interval_num_list = (int(end_date) - int(start_date) + 1) * ["1","2"]
            if start_time_frame == "1" and end_time_frame == "1":
                interval_num_list = interval_num_list[:-1]
            elif start_time_frame == "2" and end_time_frame == "1":
                interval_num_list = interval_num_list[1:-1]
            elif start_time_frame == "2" and end_time_frame == "2":
                interval_num_list = interval_num_list[1:]
            flag = 0
            for time_frame in interval_num_list:
                leave_result = StudentLeaveService.insert_one_student_leave(login_user_id, student_id, now_time, start_date,
                                                             time_frame)
                if not leave_result:
                    flag += 1
                if time_frame == "2":
                    start_date = str(int(start_date) + 1)

            if flag == len(interval_num_list):
                return ResponseCode.EXIST, '执行失败', '当前时间段以请假,请核实'

        return ResponseCode.SUCCEED, '执行成功', None


    @staticmethod
    def insert_one_student_leave(login_user_id, student_id, now_time, leave_date, time_frame):
        is_exists = format_result2one(StudentLeave().get_student_leave_by_student_id(student_id, leave_date, time_frame))
        if is_exists:
            return False
        obj = StudentLeave()
        obj.leave_date = leave_date
        obj.time_frame = time_frame
        obj.student_id = student_id
        result = obj.insert_student_leave(login_user_id, now_time)
        return True






