#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from config import config

db = SQLAlchemy()


def create_app(config_name):
    app = Flask(__name__)
    app.config.from_object(config[config_name])
    config[config_name].init_app()

    from app.sys.login.view import api_login_v1_bp, API_LOGIN_VERSION_V1
    from app.sys.user.view import api_user_v1_bp, API_USER_VERSION_V1
    from app.sys.pick_up_persion.view import api_pick_up_v1_bp, API_PICK_UP_VERSION_V1
    from app.schoolbus_situation.apis.itinerary_receipt_api import api_itinerary_receipt_v1_bp, API_ITINERARY_RECEIPT_VERSION_V1
    from app.schoolbus_situation.apis.get_school_bus_api import api_school_bus_v1_bp, API_SCHOOL_BUS_VERSION_V1
    from app.schoolbus_situation.apis.current_itinerary_info_api import api_current_itinerary_v1_bp, API_CURRENT_ITINERARY_VERSION_V1
    from app.schoolbus_situation.apis.bus_current_situation_api import api_bus_current_situation_v1_bp, API_BUS_CURRENT_SITUATION_VERSION_V1
    from app.schoolbus_situation.apis.switch_main_and_standby_api import api_main_standby_v1_bp, API_MAIN_STANDBY_VERSION_V1
    from app.schoolbus_situation.apis.history_log_api import api_history_log_v1_bp, API_HISTORY_LOG_VERSION_V1
    from app.schoolbus_situation.apis.get_tooltips_api import api_tooltips_v1_bp, API_TOOLTIPS_VERSION_V1
    from app.itinerary.receipt import itinerary_receipt_bp, ITINERARY_VERSION
    from app.schoolbus_situation.apis.bus_station_api import api_bus_station_v1_bp, API_BUS_STATION_VERSION_V1
    from app.schoolbus_situation.apis.student_leave_api import api_student_leave_v1_bp, API_STUDENT_LEAVE_VERSION_V1

    # 登陆  注册蓝本
    app.register_blueprint(
        api_login_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_LOGIN_VERSION_V1))

    # 修改密码  注册蓝本
    app.register_blueprint(
        api_user_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_USER_VERSION_V1))

    # 查询接车人  注册蓝本
    app.register_blueprint(
        api_pick_up_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_PICK_UP_VERSION_V1))

    # 更新行程状态
    app.register_blueprint(
        api_itinerary_receipt_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_ITINERARY_RECEIPT_VERSION_V1))

    # 查询校车信息蓝本
    app.register_blueprint(
        api_school_bus_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_LOGIN_VERSION_V1))

    # 获取当前行程信息(工作人员和家长)蓝本
    app.register_blueprint(
        api_current_itinerary_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_CURRENT_ITINERARY_VERSION_V1))

    # 校车实时态势轮询
    app.register_blueprint(
        api_bus_current_situation_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_BUS_CURRENT_SITUATION_VERSION_V1))

    # 主备路线切换
    app.register_blueprint(
        api_main_standby_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_MAIN_STANDBY_VERSION_V1))

    # 历史日志模块
    app.register_blueprint(
        api_history_log_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_HISTORY_LOG_VERSION_V1))

    # 查询提示信息蓝本
    app.register_blueprint(
        api_tooltips_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_TOOLTIPS_VERSION_V1))

    # 行程回单 蓝本
    app.register_blueprint(itinerary_receipt_bp,
                           url_prefix='{prefix}/v{version}/itinerary'.format(
                               prefix=app.config['URL_PREFIX'],
                               version=ITINERARY_VERSION))

    # 站点蓝本
    app.register_blueprint(
        api_bus_station_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_BUS_STATION_VERSION_V1))

    # 请假蓝本
    app.register_blueprint(
        api_student_leave_v1_bp,
        url_prefix='{prefix}/v{version}'.format(
            prefix=app.config['URL_PREFIX'],
            version=API_STUDENT_LEAVE_VERSION_V1))

    db.app = app  # hack
    db.init_app(app)
    return app
