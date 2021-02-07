#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.student_leave_service import StudentLeaveService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, g
from public.response import res

API_STUDENT_LEAVE_VERSION_V1 = 1
API_STUDENT_LEAVE_VERSION = API_STUDENT_LEAVE_VERSION_V1

api_student_leave_v1_bp = Blueprint('api_student_leave_v1', __name__)
api_student_leave_v1 = flask_restful.Api(api_student_leave_v1_bp)


class StudentLeaveApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        login_user_id = str(g.user.user_id)
        code, msg, data = StudentLeaveService().get_student_leave(login_user_id)
        return res(code=code, msg=msg, data=data)

    @auth.login_required
    def post(self):
        info = request.get_json()
        login_user_id = str(g.user.user_id)
        code, msg, data = StudentLeaveService().add_student_leave(login_user_id, info)
        return res(code=code, msg=msg, data=data)


api_student_leave_v1.add_resource(StudentLeaveApi, '/student_leave')
