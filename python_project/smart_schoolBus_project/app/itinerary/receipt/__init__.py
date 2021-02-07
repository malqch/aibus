#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import Blueprint

# 创建蓝图对象

itinerary_receipt_bp = Blueprint('itinerary_receipt_bp', __name__)
ITINERARY_VERSION = 1

from .view import *