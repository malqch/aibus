# !/usr/bin/python
# -*- coding: utf-8 -*-
"""
格式化数据库查询出来的结果
"""
import datetime
import ast
from sqlalchemy.engine import ResultProxy
import decimal
import json


def _format_item_value(value):
    """
    针对查询出来的模型列表字段值，可能不是符合要求的，进行格式化
    :param value: 待解析字段值
    :return list: 解析完之后的值
    """
    # 针对大多数情况，是字符串等类型，放第一位
    if isinstance(value, (str, int, float)):
        pass
    elif isinstance(value, datetime.datetime):
        value = value.strftime('%Y-%m-%d %H:%M:%S')
    elif isinstance(value, datetime.date):
        # from public.helpers import format_date
        # value = format_date(value)
        value = value.strftime('%Y-%m-%d %H:%M:%S')
    elif isinstance(value, set):
        value = list(value)
    elif isinstance(value, decimal.Decimal):
        value = float(value)
    return value


def format_result(result_proxy):
    """
    格式化数据库查出来的结果进行字典化和列表化

    :param result_proxy: 数据库查询出来的结果集
    :return list: 结果集列表，可能为空
    """
    results = None
    if isinstance(result_proxy, ResultProxy):
        if result_proxy.rowcount:
            results = [{k: _format_item_value(str(v)) if k.endswith("id") else _format_item_value(v) for k, v in row.items()}
                       for row in result_proxy]
    return results

def format_result_id2str(result_proxy):
    """
    格式化数据库查出来的结果进行字典化和列表化,并将value数值转为字符串

    :param result_proxy: 数据库查询出来的结果集
    :return list: 结果集列表，可能为空
    """
    results = None
    if isinstance(result_proxy, ResultProxy):
        if result_proxy.rowcount:
            results = []
            for row in result_proxy:
                dict = {}
                for k, v in row.items():
                    import re
                    if re.search("id", k):
                        v = str(v)
                    dict[k] = _format_item_value(v)
                results.append(dict)
    return results


def format_result2one(result_proxy):
    """
    格式化结果，针对一条数据的情况

    :param result_proxy: 数据库查询出来的结果集
    :return dict: 结果集中的第一条数据，已经转换成字典
    """
    result = None
    if isinstance(result_proxy, ResultProxy):
        result = result_proxy.first()
    return {k: _format_item_value(str(v)) if k.endswith("id") else _format_item_value(v) for k, v in result.items()} if result else None


def format_first_cols2list(result_proxy):
    """
    解析ResultProxy类型返回值，返回list

    :param result_proxy: 数据库查询出来的结果集
    :return list: 结果集中第一个列拼凑成的列表
    """
    result = []
    if isinstance(result_proxy, ResultProxy):
        if result_proxy.rowcount:
            for row in result_proxy:
                result.append(_format_item_value(row[0]))
        result_proxy.close()
    return result


def format_dict2json(d):
    """
    将传入的字典转化为json格式的字符串
    :param d:
    :return:
    """
    i = 0
    j = len(d)
    result = "{"
    if j > 0:
        for k, v in d.iteritems():
            result += '"' + str(k) + '":"' + str(v) + '"'
            i += 1
            if i < j:
                result += ","
    result += "}"
    return result


def format_string2list(s):
    """
    格式化list 将其中包含数组格式的字符串强转为list
    :param s:['00:ea:de:fd:ac', "['vmnic1','vmnic2']"]
    :return:['00:ea:de:fd:ac', ['vmnic1', 'vmnic2']]
    """
    sz = []
    for i in s:
        if i.startswith('[') and i.endswith(']'):
            lst = ast.literal_eval(i)
            sz.append(lst)
        else:
            sz.append(i)
    return sz


def format_json2obj(s, obj):
    s_dict = json.loads(s=s)
    obj.__dict__ = s_dict
    return obj


def format_obj2json(obj):
    obj_dict = obj.__dict__
    obj_json = json.dumps(obj=obj_dict)
    return obj_json