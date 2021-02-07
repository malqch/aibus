
import os
import flask_restful
import json, jsonschema
from app.sys.pick_up_persion.service import PickUpPersionService
from app.sys.auth.authentication import auth
from flask import Blueprint, request, session, g
from public.response import res, ResponseCode

API_PICK_UP_VERSION_V1 = 1
API_PICK_UP_VERSION = API_PICK_UP_VERSION_V1

api_pick_up_v1_bp = Blueprint('api_pick_up_v1', __name__)
api_pick_up_v1 = flask_restful.Api(api_pick_up_v1_bp)

basedir = os.path.abspath(os.path.dirname(__file__))


class PickUpPersionApi(flask_restful.Resource):

    @auth.login_required
    def get(self):
        """
        获取授权人列表
        """
        info = g.user
        code, msg, data = PickUpPersionService().get_pick_up_persion(info)
        return res(code=code, msg=msg, data=data)

    @auth.login_required
    def delete(self):
        """
        删除授权人
        """
        info = g.user
        param = request.args
        code, msg, data = PickUpPersionService().drop_pick_up_persion(info, param)
        return res(code=code, msg=msg, data=data)

    @auth.login_required
    def post(self):
        """
        新增授权人
        """
        info = g.user
        auth_photo = request.files.get('take_photo')
        if not auth_photo:
            return res(code=ResponseCode.PARAM_ERROR, msg='请上传授权人照片！', data=None)
        with open(basedir + "/pick_up_persion.json") as pick_up_persion_json:
            validate_schema = pick_up_persion_json.read()
        param = request.form
        try:
            jsonschema.validate(param, json.loads(validate_schema))
        except Exception as e:
            return res(code=ResponseCode.PARAM_ERROR, msg='参数错误:' + str(e.message), data=None)
        code, msg, data = PickUpPersionService().add_pick_up_persion(info, param, auth_photo)
        return res(code=code, msg=msg, data=data)


api_pick_up_v1.add_resource(PickUpPersionApi, '/pick_up_persion')
