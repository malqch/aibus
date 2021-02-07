#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.bus_station_service import BusStationService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, g
from public.response import res

API_BUS_STATION_VERSION_V1 = 1
API_BUS_STATION_VERSION = API_BUS_STATION_VERSION_V1

api_bus_station_v1_bp = Blueprint('api_bus_station_v1', __name__)
api_bus_station_v1 = flask_restful.Api(api_bus_station_v1_bp)


class BusStationApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        login_user_id = str(g.user.user_id)
        code, msg, data = BusStationService().get_bus_station(login_user_id)
        return res(code=code, msg=msg, data=data)

    @auth.login_required
    def put(self):
        login_user_id = str(g.user.user_id)
        info = request.get_json()
        code, msg, data = BusStationService().update_bus_station(login_user_id, info)
        return res(code=code, msg=msg, data=data)

api_bus_station_v1.add_resource(BusStationApi, '/bus_station')
