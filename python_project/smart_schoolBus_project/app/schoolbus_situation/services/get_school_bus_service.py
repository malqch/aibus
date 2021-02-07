#!/usr/bin/python
# -*- coding: utf-8 -*-

from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from public.res_code import ResponseCode
from public.format import format_result
from public import date_utils


class SchoolBusService(object):

    def get_school_bus(self, info):
        """
        获取校车列表
        """
        user_id = info.user_id
        # itinerary_date = date_utils.now(format_='%Y%m%d')
        data = format_result(InfoCompanyLine().query_school_bus(user_id))
        return ResponseCode.SUCCEED, '执行成功', data

