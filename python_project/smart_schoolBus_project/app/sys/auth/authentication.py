#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import g, request, current_app, session, app
from flask_httpauth import HTTPBasicAuth
from functools import wraps
from app.sys.user.model import Users, Captcha, Roles, UserToken, UserRole
from public.logger import logger
from public import date_utils
from datetime import datetime, timedelta
from config import Config
from public.response import ResponseCode, res
from public.format import format_result2one, format_result

auth = HTTPBasicAuth()
msg = '登陆成功!'
code = 10001


@auth.verify_password
def verify_password(username, password):
    result = False
    global msg, code
    if username is None or username is '':
        msg = '用户名不能为空!'
        return result

    # 验证token
    result = check_token(username)
    # 验证密码
    if not result and not password:
        code = 10005
        msg = 'token认证失败！'
        return result
    if not result and password is not None and password is not '':
        result = check_password(username, password)
    return result


@auth.error_handler
def auth_error():
    return res(code=code, msg=msg)


def check_token(username):
    """
    验证token是否具有 有效性
    :param username: token 信息
    :return:
    """
    global msg, code
    # 验证token
    user = Users.verify_auth_token(username)
    if user:
        user_token = UserToken.query.filter_by(user_id=user.user_id, token=username).first()
        if not user_token:
            code = 10005
            msg = '你的账号已在其他地方登录,请重新登录！'
            return False
        if user_token.expire_time < datetime.now():
            code = 10005
            msg = 'token已过期，请重新登陆！'
            return False
        g.user = user
        # 判断行后人员岗位信息是否匹配
        area_info = format_result2one(Users().query_area_info(int(user.user_id))) or dict()
        if area_info and area_info.get('role_code') == 'sa' and not area_info.get('area_name'):
            code = 10005
            msg = '未查询到岗位信息！'
            return False
        g.user.area = area_info
        # 更新token有效期
        expired_date = (datetime.now() + timedelta(minutes=30)).strftime("%Y-%m-%d %H:%M:%S")
        UserToken().update_token_time(user_token, expired_date, date_utils.now())
        user_role = UserRole().query_role_id(user.user_id)
        if user_role:
            # g.user.role_id = user_role.role_id
            g.user.role_code = user_role.roles.role_code
        else:
            g.user.role_code = None
        # role = Roles().query_role_code(user.roles[0].role_id)
        # g.user.role_id = user.roles[0].role_id
        # g.user.role_code = role.role_code
        # token过期时间控制
        g.user.token = username
        return True


def check_password(username, password):
    """
    验证用户名密码的有效性
    :param username: 用户名
    :param password: 密码
    :return:
    """

    user = Users.query.filter_by(login_name=username).first()
    # 判断登入次数
    # result = AuditLogService.get_login_num(user.id)
    # if result['count'] > 5:
    #     logger.info(username + '登入次数超过限制!')
    #     global msg
    #     msg = '登入次数超过限制!'
    #     return False
    # 验证用户名密码
    global msg, code
    if user is None or (not password or not user.verify_password(password, user.salt)):
        logger.info('%s 用户密码不正确！' % username)
        # audit_log = AuditLog()
        # audit_log.business_id = user.id
        # audit_log.user_id = user.id
        # audit_log.content = ActionConstant.USER_LOGIN
        # audit_log.create_time = date_utils.now()
        # audit_log.result = '认证失败'
        # audit_log.create_log(audit_log)
        # global msg
        msg = '认证失败!'
        return False
    else:
        g.user = user
        area_info = format_result2one(Users().query_area_info(user.user_id)) or dict()
        if area_info and area_info.get('role_code') == 'sa' and not area_info.get('area_name'):
            code = 10005
            msg = '未查询到岗位信息！'
            return False
        g.user.area = area_info
        user_role = UserRole().query_role_id(user.user_id)
        if user_role:
            # g.user.role_id = user_role.role_id
            g.user.role_code = user_role.roles.role_code
        else:
            g.user.role_code = None
        # 判断行后人员岗位信息是否匹配
        token = str(g.user.generate_auth_token(Config.SESSION_EXP_TIME), encoding='utf-8')
        g.user.token = token
        # 保存token信息到数据库
        user_token = UserToken.query.filter_by(user_id=user.user_id, token=token).first()
        expired_date = (datetime.now() + timedelta(minutes=30)).strftime("%Y-%m-%d %H:%M:%S")
        if not user_token:
            # 先删除在插入
            UserToken().delete_token(user.user_id)
            UserToken().save_token(user.user_id, token, expired_date)
        else:
            UserToken().update_token_time(user_token, expired_date, date_utils.now())
        msg = '登陆成功!'
        return True


def verify_code(func):
    @wraps(func)
    def wrapper(*args, **kwargs):
        # 获取验证码及uuid
        captcha_info = Captcha.query.filter_by(code=request.args.get('code'), uuid=request.args.get('uuid')).first()
        global msg
        if not captcha_info:
            msg = '验证码不正确！'
            return res(code=ResponseCode.ERROR, msg=msg)
        elif captcha_info.expire_time.strftime("%Y-%m-%d %H:%M:%S") < date_utils.now():
            msg = '验证码已过期！'
            return res(code=ResponseCode.ERROR, msg=msg)
        else:
            return func(*args, **kwargs)
    return wrapper