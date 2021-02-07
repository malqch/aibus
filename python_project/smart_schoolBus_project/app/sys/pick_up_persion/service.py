
import time, random, os
from werkzeug.utils import secure_filename
from app.schoolbus_situation.models.info_authorize_model import InfoAuthorize
from app.schoolbus_situation.models.info_authorize_log_model import InfoAuthorizeLog
from app.schoolbus_situation.models.info_people_basic_facts_model import InfoPeopleBasicFacts
from app.schoolbus_situation.models.student_line_seat_model import StudentLineSeat
from public.res_code import ResponseCode
from public.format import format_result, format_result2one
from public import date_utils
from public.logger import logger


class PickUpPersionService(object):

    def get_pick_up_persion(self, info):
        """
        获取授权人列表
        """
        user_id = info.user_id
        data = format_result(InfoAuthorize().query_auth_info(user_id))
        if data:
            data = [{k: '/aibus/' + v if k=='take_photo' else v for k, v in auth.items()} for auth in data]
        #if data:
        #    data = [{key: str(value) if key == 'id' else value for key, value in a.items()} for a in data]
        return ResponseCode.SUCCEED, '执行成功', data

    def drop_pick_up_persion(self, info, param):
        """
        删除授权人
        """
        user_id = info.user_id
        auth_id = param.get('id')
        create_dt = date_utils.now()
        # if not auth_id:
        #     return ResponseCode.PARAM_ERROR, '未获取到授权人id！', None
        auth = InfoAuthorize().delete_auth_info(auth_id)
        if not auth:
            return ResponseCode.ERROR, '删除失败,未查询到该授权人信息！', None
        InfoAuthorizeLog().save_auth_log(auth_id, '2', user_id, create_dt)
        return ResponseCode.SUCCEED, '删除成功！', None

    def add_pick_up_persion(self, info, param, auth_photo):
        """
        增加授权人
        """
        user_id = info.user_id
        if not param:
            return ResponseCode.PARAM_ERROR, f'参数:{param}错误！', None
        full_name = param.get("full_name")
        relation_student = param.get("relation_student")
        mobile_number = param.get("mobile_number")
        take_photo = os.path.join('/usr/local/aibus/data/auth_photo', secure_filename(auth_photo.filename))
        # 检查是否有同名的授权人信息
        check_result = format_result(InfoPeopleBasicFacts().check_full_name(user_id, full_name, relation_student))
        if check_result:
            return ResponseCode.GET_BY_PARAM_ERROR, f'授权人:{full_name}已存在！', None
        create_dt = date_utils.now()
        uuid = int(str(time.time()).replace('.', ''))
        st_info = format_result2one(StudentLineSeat().get_student_id(user_id))
        # 查询学生id
        if not st_info or not st_info.get('student_id'):
            return ResponseCode.GET_BY_PARAM_ERROR, f'未查询到{full_name}家学生信息！', None
        try:
            # 保存授权人照片到服务器
            dir_path = '/usr/local/aibus/data/auth_photo'
            upload_path = os.path.join(dir_path, secure_filename(auth_photo.filename))
            auth_photo.save(upload_path)
            f_id = InfoPeopleBasicFacts().save_people_basic_facts(uuid, full_name, take_photo, create_dt)
            a_id = InfoAuthorize().save_auth_info(uuid, f_id, st_info.get('student_id'), '0', relation_student, mobile_number, create_dt)
            InfoAuthorizeLog().save_auth_log(a_id, '1', user_id, create_dt)
            return ResponseCode.SUCCEED, '添加成功！', None
        except Exception as e:
            logger.error(f'授权人添加失败：{str(e)}')
            return ResponseCode.ERROR, '添加失败！', str(e)


