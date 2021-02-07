#!/usr/bin/python
# -*- coding: utf-8 -*-
from flask import json

from app.schoolbus_situation.models.itinerary_receipt_model import ItineraryReceipt
from public.logger import logger
from public.res_code import ResponseCode


class ItineraryReceiptService(object):

    @staticmethod
    def update_itinerary_status(info):
        """
        更新行程状态
        :param info: info中包含行程id和行程状态码(1:未开始,2:进行中,3:已结束)
        :return:
        """
        itinerary_id = info.get("itinerary_id")
        itinerary_status = info.get("itinerary_status")
        result = ItineraryReceipt.update_itinerary_status(itinerary_id, itinerary_status)
        logger.info(result)
        return ResponseCode.SUCCEED, '执行成功', None

