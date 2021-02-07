#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask_restful import Resource, Api
from app.sys.auth.authentication import auth
from flask import Blueprint, g, current_app, request
from app.sys.user.service import UserService
from app.itinerary.receipt.service import ItineraryReceiptService
from public.res_code import ResponseCode
from public.response import res

API_USER_VERSION_V1 = 1
API_USER_VERSION = API_USER_VERSION_V1

api_user_v1_bp = Blueprint('api_user_v1', __name__)
api_user_v1 = Api(api_user_v1_bp)


class UpdatePassword(Resource):

    @auth.login_required
    def post(self):
        info = g.user
        args = request.get_json()
        old_password = args['old_password']
        new_password = args['new_password']
        if old_password is None or new_password is None:
            return res(ResponseCode.PARAM_ERROR, msg='参数错误！')
        code, msg, data = UserService.update_user_password(info, old_password, new_password)
        return res(code=code, msg=msg, business_id=data)


class UserInfoMes(Resource):
    """ 用户信息 """

    # @auth.login_required
    def get(self):
        token = request.args.get("token")
        if not token:
            return res(code=ResponseCode.PARAM_ERROR, msg="参数不能为空")

        # 根据用户ID获取角色
        role = ItineraryReceiptService.get_user_role(token)
        if role:
            code, mes, data = UserService.get_user_info(role[0])
            return res(code=code, msg=mes, data=data)
        else:
            return res(code=222222, msg="查询角色数据为空")


class UploadPhotos(Resource):

    @auth.login_required
    def post(self):
        """ 健康码上传 """
        info = g.user
        f = request.files.get('health_code')
        code, mes, data = UserService.upload_health_code(info, f)
        return res(code=code, msg=mes, data=data)


api_user_v1.add_resource(UpdatePassword, '/ch_pwd')
api_user_v1.add_resource(UserInfoMes, '/user_info/')
api_user_v1.add_resource(UploadPhotos, '/upload_code')
