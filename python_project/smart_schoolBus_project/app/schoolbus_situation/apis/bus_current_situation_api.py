#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.bus_current_situation_service import BusCurrentSituation
from app.schoolbus_situation.services.get_tooltips_service import ToolTipsService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, g
from public.response import res

API_BUS_CURRENT_SITUATION_VERSION_V1 = 1
API_BUS_CURRENT_SITUATION_VERSION = API_BUS_CURRENT_SITUATION_VERSION_V1

api_bus_current_situation_v1_bp = Blueprint('api_bus_current_situation_v1', __name__)
api_bus_current_situation_v1 = flask_restful.Api(api_bus_current_situation_v1_bp)


class BusCurrentSituationApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        info = request.args
        login_user_id = str(g.user.user_id)
        role_code = ToolTipsService().get_role_code_by_id(login_user_id)
        code, msg, data = BusCurrentSituation().get_bus_current_situation(info, login_user_id, role_code)
        return res(code=code, msg=msg, data=data)


class NextStationArriveMin(flask_restful.Resource):

    @auth.login_required
    def get(self):
        info = g.user
        param = request.args
        code, msg, data = BusCurrentSituation().get_next_station_arrive_min(info, param)
        return res(code=code, msg=msg, data=data)


api_bus_current_situation_v1.add_resource(BusCurrentSituationApi, '/bus_current_situation')
api_bus_current_situation_v1.add_resource(NextStationArriveMin, '/next_station')