#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.worker_parents_itinerary_service import ItineraryInfoService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, session, g
from public.response import res

API_CURRENT_ITINERARY_VERSION_V1 = 1
API_CURRENT_ITINERARY_VERSION = API_CURRENT_ITINERARY_VERSION_V1

api_current_itinerary_v1_bp = Blueprint('api_current_itinerary_v1', __name__)
api_current_itinerary_v1 = flask_restful.Api(api_current_itinerary_v1_bp)


class WorkerItineraryInfoApi(flask_restful.Resource):
    """
    获取当前行程信息(工作人员)
    """
    @auth.login_required
    def get(self):
        login_user_id = g.user.user_id
        code, msg, data = ItineraryInfoService.worker_itinerary_info(login_user_id)
        return res(code=code, msg=msg, data=data)


class ParentsItineraryInfoApi(flask_restful.Resource):
    """
    获取当前行程信息(家长)
    """
    @auth.login_required
    def get(self):
        login_user_id = g.user.user_id
        code, msg, data = ItineraryInfoService.parents_itinerary_info(login_user_id)
        return res(code=code, msg=msg, data=data)


api_current_itinerary_v1.add_resource(WorkerItineraryInfoApi, '/worker_itinerary')
api_current_itinerary_v1.add_resource(ParentsItineraryInfoApi, '/parents_itinerary')


