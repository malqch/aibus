#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.get_school_bus_service import SchoolBusService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, session, g
from public.response import res

API_SCHOOL_BUS_VERSION_V1 = 1
API_SCHOOL_BUS_VERSION = API_SCHOOL_BUS_VERSION_V1

api_school_bus_v1_bp = Blueprint('api_school_bus_v1', __name__)
api_school_bus_v1 = flask_restful.Api(api_school_bus_v1_bp)


class SchoolBusApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        """
        获取校车列表
        """
        info = g.user
        code, msg, data = SchoolBusService().get_school_bus(info)
        return res(code=code, msg=msg, data=data)


api_school_bus_v1.add_resource(SchoolBusApi, '/school_bus')
