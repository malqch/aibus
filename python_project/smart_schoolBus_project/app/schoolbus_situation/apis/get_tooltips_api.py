#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.get_tooltips_service import ToolTipsService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, session, g
from public.response import res

API_TOOLTIPS_VERSION_V1 = 1
API_TOOLTIPS_VERSION = API_TOOLTIPS_VERSION_V1

api_tooltips_v1_bp = Blueprint('api_tooltips_v1', __name__)
api_tooltips_v1 = flask_restful.Api(api_tooltips_v1_bp)


class ToolTipsApi(flask_restful.Resource):
    """
    获取提示信息
    :param:
    :return:
    """
    @auth.login_required
    def get(self):
        info = g.user
        code, msg, data = ToolTipsService().get_tooltips(info)
        return res(code=code, msg=msg, data=data)


api_tooltips_v1.add_resource(ToolTipsApi, '/tooltips')