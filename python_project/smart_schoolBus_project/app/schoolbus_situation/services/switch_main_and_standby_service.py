#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime

from app.schoolbus_situation.models.info_company_line_model import InfoCompanyLine
from app.schoolbus_situation.models.itinerary_receipt_model import ItineraryReceipt
from public.format import format_result, format_result2one
from public.logger import logger
from public.res_code import ResponseCode


class SwitchMainAndStandbyService(object):

    @staticmethod
    def switch_main_and_standby():
        """
        切换主备路线
        :param info:
        :return:
        """
        # company_line_id = info.get("company_line_id")
        now_time = datetime.datetime.now().strftime("%Y%m%d")
        itinerary_result = ItineraryReceipt.get_itinerary_id(now_time)
        if itinerary_result:
            for itinerary in itinerary_result:
                itinerary_id = itinerary.itinerary_id
                is_backup = itinerary.is_backup
                is_backup = "0" if is_backup == "1" else "1"
                logger.info("---切换主备线路---")
                result = ItineraryReceipt.update_is_backup(itinerary_id, is_backup)
        return ResponseCode.SUCCEED, '执行成功', None



