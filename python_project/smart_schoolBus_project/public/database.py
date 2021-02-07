#!/usr/bin/python
# -*- coding: utf-8 -*-
import datetime
from contextlib import contextmanager

from app import db
from sqlalchemy.orm import class_mapper, sessionmaker

from public.res_code import ResponseCode, ResponseLevel
from public.logger import logger


class CRUDMixin(object):
    def __repr__(self):
        return "<{}>".format(self.__class__.__name__)

    @classmethod
    def page(cls, args):
        """
        通用分页查询模块，使用组合sql查询

        :param args: 传递的request参数解析
        :return: 查询出来的条目
        """
        q_names = args['q_names']
        q_values = args['q_values']
        page_query = cls.query
        q_type = args['q_type']

        # 校验查询参数键与值是否匹配
        if q_names is None and q_values is None:
            page_query = page_query
        elif q_names and q_values and len(q_names) == 1 and len(q_values) == 1 and q_type is None:
            query = ''.join(['cls.', q_names[0], '.like(u"%', q_values[0], '%")'])
            page_query = page_query.filter(eval(query))
        elif q_names and q_values and len(q_names) == 2 and len(q_values) == 2 and (q_type == "or" or q_type == "and"):
            query1 = ''.join(['cls.', q_names[0], '.like(u"%', q_values[0], '%")'])
            query2 = ''.join(['cls.', q_names[1], '.like(u"%', q_values[1], '%")'])
            if q_type == "or":
                page_query = page_query.filter(db.or_(eval(query1), eval(query2)))
            else:
                page_query = page_query.filter(db.and_(eval(query1), eval(query2)))
        else:
            from public.response import res
            return res(ResponseCode.VALIDATE_FAIL, u"查询参数错误，请重试!", ResponseLevel.DANGER)

        o_names = args['o_names']
        o_values = args['o_values']
        # 校验排序参数键与值是否匹配
        if o_names is None and o_values is None:
            pass
        elif len(o_names) == 1 and len(o_values) == 1:
            page_query = page_query.order_by(" %s %s " % (o_names[0], o_values[0]))
        elif len(o_names) == 2 and len(o_values) == 2:
            page_query = page_query.order_by(" %s %s, %s %s " % (o_names[0], o_values[0], o_names[1], o_values[1]))
        else:
            from public.response import res
            return res(ResponseCode.VALIDATE_FAIL, u"排序参数错误，请重试!", ResponseLevel.DANGER)

        # 获取总数
        total_count = page_query.count()
        # 分页参数
        data = page_query.paginate(args['page'], args['per_page'], False).items
        from public.response import res_page
        return res_page(args, data=data, total_count=total_count)


def model_to_dict(obj, visited_children=None, back_relationships=None, keys=[]):
    """
    实现模型自动to_dict功能
    引用自：http://stackoverflow.com/questions/23554119/convert-sqlalchemy-orm-result-to-dict
    """
    if visited_children is None:
        visited_children = set()
    if back_relationships is None:
        back_relationships = set()
    serialized_data = {}
    for c in obj.__table__.columns:
        name = c.name
        if hasattr(obj, name):
            if name not in keys:
                value = getattr(obj, c.name)
            else:
                continue
        else:
            # 针对python中关键字冲突，添加后下划线以避免冲突情况，例如class => class_
            name = "%s_" % name
            value = getattr(obj, name)

        if isinstance(value, (datetime.date, datetime.time)):
            serialized_data[name] = datetime.datetime.strftime(value, '%Y-%m-%d %H:%M:%S' if isinstance(value, (
                datetime.time, datetime.datetime)) else '%Y-%m-%d')
        elif isinstance(value, set):
            serialized_data[name] = list(value)
        else:
            serialized_data[name] = value

    relationships = class_mapper(obj.__class__).relationships
    visitable_relationships = [(name, rel) for name, rel in relationships.items() if name not in back_relationships]
    for name, relation in visitable_relationships:
        if relation.backref:
            back_relationships.add(relation.backref)
        relationship_children = getattr(obj, name)
        if relationship_children is not None:
            if relation.uselist:
                children = []
                for child in [c for c in relationship_children if c not in visited_children]:
                    visited_children.add(child)
                    children.append(model_to_dict(child, visited_children, back_relationships))
                serialized_data[name] = children
            else:
                serialized_data[name] = model_to_dict(relationship_children, visited_children, back_relationships)
    return serialized_data


@contextmanager
def new_session():
    """
    上下文管理器生成session，为事务生成专用的会话
    """
    # 初始化事务会话类
    session = sessionmaker(bind=db.engine)
    s = session()
    logger.info("New transaction and session {} begin!".format(s))
    try:
        yield s
    except Exception as e:
        s.rollback()
        logger.info("Transaction and session {} {}!".format(s, e))
        logger.info("Transaction and session {} finished!".format(s))
