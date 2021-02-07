#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from flask import session
from app.sys.auth.authentication import auth
from flask import Blueprint, g, current_app, request
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer
from app.sys.auth.authentication import verify_code
from app.sys.user.model import Users, UserToken
from public.logger import logger
from public.res_code import ResponseCode
from public.response import res

API_LOGIN_VERSION_V1 = 1
API_LOGIN_VERSION = API_LOGIN_VERSION_V1

api_login_v1_bp = Blueprint('api_login_v1', __name__)
api_login_v1 = flask_restful.Api(api_login_v1_bp)


class LoginApi(flask_restful.Resource):

    @verify_code
    @auth.login_required
    def get(self):
        result = dict()
        result['token'] = g.user.token
        result['user_id'] = g.user.user_id
        result['login_name'] = g.user.login_name
        result['user_name'] = g.user.user_name
        result['role_code'] = g.user.area.get('role_code') or g.user.role_code
        result['area_code'] = g.user.area.get('area_code')
        result['area_name'] = g.user.area.get('area_name')
        return res(code=ResponseCode.SUCCEED, msg='登陆成功!', data=result, business_id=g.user.user_id)


class LogoutApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        token = request.authorization.username
        if token != 'undefined':
            user = Users.verify_auth_token(token)
            if not user:
                logger.error(f'token：{token}认证失败！')
                return res(code=ResponseCode.NO_AUTHENTICATED, msg='token认证失败！')
            # 销毁token值
            Serializer(current_app.config['SECRET_KEY'], expires_in=1)
            UserToken().delete_token(user.user_id)
            return res(code=ResponseCode.SUCCEED, msg='登出成功!', business_id=user.user_id)


api_login_v1.add_resource(LoginApi, '/login')
api_login_v1.add_resource(LogoutApi, '/logout')

