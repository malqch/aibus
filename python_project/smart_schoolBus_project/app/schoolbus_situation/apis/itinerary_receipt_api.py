#!/usr/bin/python
# -*- coding: utf-8 -*-

import flask_restful
from app.schoolbus_situation.services.itinerary_receipt_service import ItineraryReceiptService
from app.sys.auth.authentication import auth
from flask import Blueprint, request
from public.response import res

API_ITINERARY_RECEIPT_VERSION_V1 = 1
API_ITINERARY_RECEIPT_VERSION = API_ITINERARY_RECEIPT_VERSION_V1

api_itinerary_receipt_v1_bp = Blueprint('api_itinerary_receipt_v1', __name__)
api_itinerary_receipt_v1 = flask_restful.Api(api_itinerary_receipt_v1_bp)


class Itinerary_Receipt_Api(flask_restful.Resource):
    """
    行程回单相关接口
    """
    @auth.login_required
    def put(self):
        info = request.get_json()
        itinerary_receipt = ItineraryReceiptService()
        code, msg, data = itinerary_receipt.update_itinerary_status(info)
        return res(code=code, msg=msg, data=data)


api_itinerary_receipt_v1.add_resource(Itinerary_Receipt_Api, '/itinerary_receipt')


