#!/usr/bin/env python
# -*- coding: utf-8 -*-
import decimal
import json
import time
from datetime import datetime

from flask import g

__author__ = 'wfluo'


class DecimalEncoder(json.JSONEncoder):
    # 序列化datetime类型数据
    def default(self, obj):
        if isinstance(obj, decimal.Decimal):
            return "%.2f" % obj
        elif isinstance(obj, datetime.datetime):
            return obj.strftime('%Y-%m-%d %H:%M:%S')
        else:
            return json.JSONEncoder.default(self, obj)


# 返回当前时间字符串
# @param format: str, 时间格式定义字符串，默认值为'%Y-%m-%d %H:%M:%S'
# @return: str, 格式化的当前时间值，如2014-05-05 22:22:22
def now(format_='%Y-%m-%d %H:%M:%S'):
    return time.strftime(format_)

