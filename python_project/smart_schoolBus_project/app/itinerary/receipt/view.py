#!/usr/bin/python
# -*- coding: utf-8 -*-

from gevent import monkey, spawn, joinall; monkey.patch_all(socket=False, thread=False)
import time
from flask_restful import Resource, Api
from flask import request, current_app

from app.sys.auth.authentication import auth
from itertools import groupby
from operator import itemgetter

from public.res_code import ResponseCode
from . import itinerary_receipt_bp
from .service import ItineraryReceiptService
from public.response import res


itinerary_receipt = Api(itinerary_receipt_bp)


def auto_commit():
    """
    定时任务: 自动提交行程回单
    :return:
    """
    pass


class RouteList(Resource):
    """查看回单行程列表"""

    # @auth.login_required
    def get(self):
        token = request.args.get("token")
        if not token:
            return res(code=ResponseCode.NO_LOGIN, msg="token 不能为空")
        # 根据token 获取用户id 和角色id
        role = ItineraryReceiptService.get_user_role(token)
        if role:
            # 获取行程列表
            sta, mes, data = ItineraryReceiptService.get_itinerary_list(role[-1])
            if data:
                role_id = role[0].get("role_id")
                if role_id == "2":
                    ntime = time.time()
                    # 计算 剩余上报时间
                    for d in data:
                        etime = d.get("itinerary_end_time")
                        etime = time.strptime(etime, "%Y-%m-%d %H:%M:%S")
                        etime = int(time.mktime(etime))
                        restime = etime + 20*60 - ntime
                        if restime > 0:
                            minutes = restime // 60
                            seconds = restime % 60
                            d["restime"] = "{}分{}秒".format(minutes, seconds)
                        else:
                            d["restime"] = "0秒"
                return res(code=sta, msg=mes, data=data)
            else:
                return res(code=sta, msg=mes)
        else:
            return res(code=ResponseCode.GET_BY_PARAM_ERROR, msg="无法获取用户对应的角色数据")


class RouteDetails(Resource):
    """ 行程回单详情 """

    # @auth.login_required
    def get(self):
        try:
            token = request.args.get("token")
            itinerary_id = request.args.get("itinerary_id")
            data = {}
            if not all([token, itinerary_id]):
                return res(code=ResponseCode.PARAM_ERROR, msg="参数不能为空")
            # 获取三天前的时间
            time_array = time.localtime(int(time.time()) - 3 * 24 * 3600)
            last_time = time.strftime("%Y-%m-%d %H:%M:%S", time_array)

            # 根据token 获取用户id 和角色id
            role = ItineraryReceiptService.get_user_role(token)
            if not role:
                return res(code=ResponseCode.NO_LOGIN, msg="请重新登录...")

            # itinerary_id = '1356286187257462786'
            # role = [{"role_id": '2'}]

            # 获取指定行程详情
            details = spawn(ItineraryReceiptService.get_route_details, itinerary_id)
            # 行程时刻表记录【站点名称，进站时间（时分秒），出站时间（时分秒）】
            route_timestamp = spawn(ItineraryReceiptService.get_route_timestamp, itinerary_id)
            # 学生上下车详单
            student_up_down = spawn(ItineraryReceiptService.get_student_up_down, itinerary_id, role[0])
            # 行程历史违纪分布
            history_disciplinary = spawn(ItineraryReceiptService.get_history_disciplinary, itinerary_id)

            # 三日内行车违规列表
            route3_disciplinary = spawn(ItineraryReceiptService.get_route3_disciplinary, itinerary_id, last_time)
            # 三日内学生违纪列表
            student3_disciplinary = spawn(ItineraryReceiptService.get_student3_disciplinary, itinerary_id, last_time, role[0])

            gev = [details, route_timestamp, student_up_down,
                   history_disciplinary, route3_disciplinary, student3_disciplinary]
            joinall(gev)

            for g_ in gev:
                key_ = g_.value[0]
                val = g_.value[1]
                data[key_] = val

            return res(code=ResponseCode.SUCCEED, msg="查询成功", data=data)
        except Exception as e:
            current_app.logger.exception('message info is {0}'.format(e))
            return res(code=ResponseCode.ERROR, msg="执行错误")


class RouteCommit(Resource):
    """ 行程回单提交-手动提交 """

    # @auth.login_required
    def post(self):
        args = request.get_json()
        token = args.get("token")
        # 根据token 值判断执行权限
        role = ItineraryReceiptService.get_user_role(token)
        # 手动提交只有 <学校行政管理人员> 有权限操作
        if role and str(role[0].get("role_id")) == "2":
            # 执行行程单提交
            sta, mes = ItineraryReceiptService.commit_itinerary_report(args)
            if sta == 0:
                # 更新行程 上报状态 和 上报方式: '1' 手动提交
                sta, mes = ItineraryReceiptService.update_itinerary_upload_way([args.get("itinerary_id"), '1'])
            return res(code=sta, msg=mes)

        else:
            return res(code=11111, msg="没有执行权限")


class RouteCommitAuto(Resource):
    """ 行程回单提交-自动提交 """

    def get(self):
        # 获取已完成行程中未在指定时间内提交的行程
        now_time = time.time()
        data = ItineraryReceiptService.route_no_commmit()
        if not data:
            return res(code=11111, msg="没有未提交数据")
        for it in data:
            itinerary_id = it.get("itinerary_id")
            itinerary_end_time = it.get("itinerary_end_time")
            itinerary_end_time = time.strptime(itinerary_end_time, "%Y-%m-%d %H:%M:%S")
            itinerary_end_time = int(time.mktime(itinerary_end_time))
            interval = now_time - itinerary_end_time
            if interval > 20*60:
                # 获取回单详情并提交
                details = ItineraryReceiptService.get_route_details(itinerary_id)[1]
                if details:

                    sta, mes = ItineraryReceiptService.commit_itinerary_report(details[0])
                    print(">>>>>>>>>>>>>>>>>>>> :", sta)
                    # 更新行程上报 状态/方式: '0' 自动提交
                    if sta == 0:
                        sta, mes = ItineraryReceiptService.update_itinerary_upload_way([itinerary_id, '0'])
            else:
                continue
        return res(code=0, msg="ok")


itinerary_receipt.add_resource(RouteList, '/route_list/')
itinerary_receipt.add_resource(RouteDetails, '/route_details/')
itinerary_receipt.add_resource(RouteCommit, '/route_commit/')
itinerary_receipt.add_resource(RouteCommitAuto, '/auto_commit/')

