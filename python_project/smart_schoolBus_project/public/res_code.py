#!/usr/bin/python
# -*- coding: utf-8 -*-


class ResponseKey(object):
    """返回关键字."""
    STATUS = "code"
    MESSAGE = "msg"
    LEVEL = "level"


class ResponseCode(object):
    """返回代码"""

    URL_NOT_FOUND = 404             # 该请求不存在
    SUCCEED = 10000                 # 成功
    ERROR = 10001                   # 失败
    VALIDATE_FAIL = 10002           # 数据校验失败
    GET_BY_PARAM_ERROR = 10003      # 根据参数获取结果失败，数据不存在
    SYSTEM_ERROR = 500
    NO_AUTHENTICATED = 20001        # 未认证
    USER_NOT_FOUND = 10003          # 用户不存在
    PARAM_ERROR = 10004             # 参数错误
    NO_LOGIN = 10005                # 未登陆
    PASSWORD_ERROR = 10006          # 密码错误

    USER_EXIST = 10007              # 用户已存在
    ROLE_EXIST = 10008              # 角色已存在
    ROLE_NOT_EXIST = 10009          # 角色不存在

    EXIST = 20000
    OTHER = 20001




class ResponseLevel(object):
    """
    定义错误等级
    """
    INFO = "info"
    DANGER = "danger"
