#!/usr/bin/python
# -*- coding: utf-8 -*-

import json
import os
import traceback
from app import create_app
from flask import request
from app import db
from public.res_code import ResponseCode
from public.logger import logger
from public.response import res

# 重设系统默认编码为utf-8
app = create_app(os.getenv('FLASK_CONFIG') or 'default')


@app.before_request
def before_request():
    logger.info(request.url)
    logger.info(request.data)


@app.errorhandler(404)
def page_not_found(error):
    logger.info(error)
    return res(code=ResponseCode.URL_NOT_FOUND, msg='地址未找到！')


@app.errorhandler(Exception)
def exception_request(error):
    logger.error('Exception handler:{0}'.format(traceback.format_exc()))
    # 发生错误回滚
    db.session.rollback()
    db.session.remove()
    return res(code=ResponseCode.ERROR, msg='执行错误！')


@app.after_request
def after_request(response):
    logger.info('after_request:{0}'.format(response.data))
    if 'status' not in response.data.decode('utf-8'):
        # 如果发生异常
        db.session.rollback()
        db.session.remove()
        return res(code=ResponseCode.ERROR, msg='执行错误！')
    if json.loads(response.data)['status'] == str(ResponseCode.ERROR):
        # 如果业务逻辑中返回了不可预知错误
        db.session.rollback()
        db.session.remove()
    return response


if __name__ == "__main__":
    app.run(host=app.config['HOST'], port=app.config['PORT'], threaded=True)