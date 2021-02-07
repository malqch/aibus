#!/usr/bin/python
# -*- coding: utf-8 -*-

from datetime import datetime
from app.sys.user.model import Roles, UserRole
from app.schoolbus_situation.models.itinerary_receipt_model import ItineraryReceipt
from app.schoolbus_situation.models.info_guardian_model import InfoGuardian
from app.schoolbus_situation.models.info_authorize_model import InfoAuthorize
from app.schoolbus_situation.models.student_leave_model import StudentLeave
from app.schoolbus_situation.models.student_line_seat_log_model import StudentLineSeatLog
from public.res_code import ResponseCode
from public.format import format_result
from public import date_utils


class ToolTipsService(object):

    def get_role_code_by_id(self, login_user_id):
        user_role = UserRole().query_role_id(login_user_id)
        role_code = user_role.roles.role_code
        return role_code

    def get_tooltips(self, info):
        """
        获取提示信息
        """
        login_user_id = str(info.user_id)
        role_code = self.get_role_code_by_id(login_user_id)
        itinerary_date = date_utils.now(format_='%Y-%m-%d')
        # create_dt = date_utils.now()
        data = dict()
        if role_code == "sa":
            data['auth_tooltips'] = format_result(InfoAuthorize().query_sa_auth_tooltips(login_user_id, itinerary_date))
            data['line_tooltips'] = format_result(StudentLineSeatLog().query_sa_line_tooltips(login_user_id, itinerary_date))
            data['leave_tooltips'] = format_result(StudentLeave().query_sa_leave_tooltips(login_user_id, itinerary_date))
        elif role_code == "ba":
            data['line_tooltips'] = format_result(ItineraryReceipt().query_ba_tooltips(login_user_id, date_utils.now(format_='%Y%m%d')))
            data['leave_students_tooltips'] = format_result(StudentLeave().query_leave_student_tooltips(login_user_id, date_utils.now(format_='%Y%m%d'), itinerary_date))
            data['health_code_tooltips'] = format_result(InfoGuardian().query_ba_health_code(login_user_id, itinerary_date))
        elif role_code == "pa":
            data['line_tooltips'] = format_result(InfoGuardian().query_pa_tooltips(login_user_id))
            data['health_code_tooltips'] = format_result(InfoGuardian().query_pa_health_code(login_user_id, itinerary_date))
        else:
            data = ""
        return ResponseCode.SUCCEED, '执行成功！', data