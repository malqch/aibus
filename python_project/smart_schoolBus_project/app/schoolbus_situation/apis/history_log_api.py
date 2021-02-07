#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful

from app.schoolbus_situation.services.get_tooltips_service import ToolTipsService
from app.schoolbus_situation.services.history_log_service import HistoryLogService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, session, g
from public.response import res

API_HISTORY_LOG_VERSION_V1 = 1
API_HISTORY_LOG_VERSION = API_HISTORY_LOG_VERSION_V1

api_history_log_v1_bp = Blueprint('api_history_log_v1', __name__)
api_history_log_v1 = flask_restful.Api(api_history_log_v1_bp)


class HistoryLogApi(flask_restful.Resource):

    @auth.login_required
    def post(self):
        """
        历史日志查询接口
        :return:
        """
        login_user_id = str(g.user.user_id)
        role_code = ToolTipsService().get_role_code_by_id(login_user_id)
        info = request.get_json()
        code, msg, data = HistoryLogService.get_history_log(info, login_user_id, role_code)
        return res(code=code, msg=msg, data=data)


class CheckPermissionChangeApi(flask_restful.Resource):

    @auth.login_required
    def put(self):
        """
        更新查询图片的权限
        :return:
        """
        info = request.get_json()
        login_user_id = str(g.user.user_id)
        role_code = ToolTipsService().get_role_code_by_id(login_user_id)
        code, msg, data = HistoryLogService.update_check_permissions(role_code, info)
        return res(code=code, msg=msg, data=data)


class GetCompanyLine(flask_restful.Resource):

    @auth.login_required
    def get(self):
        """
        历史日志查询接口
        :return:
        """
        login_user_id = str(g.user.user_id)
        code, msg, data = HistoryLogService.get_company_line(login_user_id)
        return res(code=code, msg=msg, data=data)

class ParentsGetStudentInfo(flask_restful.Resource):

    @auth.login_required
    def get(self):
        info = request.args
        login_user_id = str(g.user.user_id)
        code, msg, data = HistoryLogService.parents_get_student_disciplinary(login_user_id, info)
        return res(code=code, msg=msg, data=data)



api_history_log_v1.add_resource(HistoryLogApi, '/history_log')
api_history_log_v1.add_resource(CheckPermissionChangeApi, '/permission_change')
api_history_log_v1.add_resource(GetCompanyLine, '/company_line')
api_history_log_v1.add_resource(ParentsGetStudentInfo, '/parents_student_info')


