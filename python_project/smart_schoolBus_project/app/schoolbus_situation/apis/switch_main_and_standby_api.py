#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful

from app.schoolbus_situation.services.switch_main_and_standby_service import SwitchMainAndStandbyService
from app.sys.auth.authentication import auth
from flask import Blueprint, request
from public.response import res

API_MAIN_STANDBY_VERSION_V1 = 1
API_MAIN_STANDBY_VERSION = API_MAIN_STANDBY_VERSION_V1

api_main_standby_v1_bp = Blueprint('api_main_standby_v1', __name__)
api_main_standby_v1 = flask_restful.Api(api_main_standby_v1_bp)


class SwitchMainAndStandbyApi(flask_restful.Resource):

    @auth.login_required
    def put(self):
        code, msg, data = SwitchMainAndStandbyService.switch_main_and_standby()
        return res(code=code, msg=msg, data=data)


api_main_standby_v1.add_resource(SwitchMainAndStandbyApi, '/main_standby')


