#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime

from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from public.format import format_result, format_result_id2str
from public.logger import logger
from public.res_code import ResponseCode


class ItineraryInfoService(object):

    @staticmethod
    def worker_itinerary_info(login_user_id):
        """
        获取当前行程信息(校车工作人员)
        :param login_user_id:
        :return:
        """
        now_time = datetime.datetime.now().strftime("%Y%m%d")
        # now_time = datetime.datetime.now().strftime("%Y%m%d %H:%M")
        # front_time =datetime.datetime.now().strftime("%Y%m%d") + " 11:00"
        # back_time = datetime.datetime.now().strftime("%Y%m%d") + " 20:00"
        # if front_time <= now_time <= back_time:
        #     itinerary_direction = "2"
        # else:
        #     itinerary_direction = "1"
        result = format_result_id2str(InfoCompanyLine().worker_current_itinerary_info(login_user_id, now_time))
        logger.info(result)
        return ResponseCode.SUCCEED, '执行成功', result

    @staticmethod
    def parents_itinerary_info(login_user_id):
        """
        获取当前行程信息(家长)
        :param info:
        :return:
        """
        # login_user_id = str(info.get("user_id"))
        now_time = datetime.datetime.now().strftime("%Y%m%d")
        # now_time = datetime.datetime.now().strftime("%Y%m%d %H:%M")
        # front_time = datetime.datetime.now().strftime("%Y%m%d") + " 11:00"
        # back_time = datetime.datetime.now().strftime("%Y%m%d") + " 20:00"
        # itinerary_direction = "1"
        # if front_time <= now_time <= back_time:
        #     itinerary_direction = "2"
        result = format_result_id2str(InfoCompanyLine().parents_current_itinerary_info(login_user_id, now_time))
        logger.info(result)
        return ResponseCode.SUCCEED, '执行成功', result

