import os
from werkzeug.utils import secure_filename
from app.schoolbus_situation.models.info_people_basic_facts_model import InfoPeopleBasicFacts
from app.schoolbus_situation.models.log_epidemic_model import LogEpidemic
from app.sys.user.model import Users
from public.res_code import ResponseCode
from public.format import format_result
from public.logger import logger
from public import date_utils

basepath = os.path.dirname(__file__)

class UserService(object):


    @staticmethod
    def update_user_password(info, old_password, new_password):
        """
        修改用户密码
        :param user_id:
        :param old_password:
        :param new_password:
        :return:
        """
        user = Users.check_user_id(info.user_id)
        if user:
            flag = user.verify_password(old_password, user.salt)
            if flag:
                user.update_password(new_password, user.salt)
                return ResponseCode.SUCCEED, "修改成功！", user.user_id
            else:
                return ResponseCode.ERROR, "原密码不正确！", None
        else:
            return ResponseCode.USER_NOT_FOUND, "用户不存在！", None

    @staticmethod
    def get_user_info(data):
        """
        根据用户角色获取用户信息
        :param data:
        """
        try:
            data = format_result(Users.query_user_info(data))
            return 0, "查询成功", data
        except Exception as e:
            return 1, "查询失败", []

    @staticmethod
    def upload_health_code(info, f):
        login_user_id = info.user_id
        f_info = format_result(InfoPeopleBasicFacts().query_id_by_user(login_user_id))
        if not f_info:
            return ResponseCode.GET_BY_PARAM_ERROR, '未查询到当前登录人信息！', None
        if not f:
            return ResponseCode.PARAM_ERROR, '请选择要上传的选择健康码！', None
        try:
            # 保存健康码到服务器
            dir_path = '/usr/local/aibus/data/health_code'
            upload_path = os.path.join(dir_path, secure_filename(f.filename))
            f.save(upload_path)
            f_id = f_info[0].get('id')
            clock_date = create_dt = date_utils.now()
            # 保存健康码信息到数据库
            LogEpidemic().save_log_epidemic(f_id, clock_date, dir_path, login_user_id, create_dt)
            return ResponseCode.SUCCEED, '上传成功！', None
        except Exception as e:
            logger.error(f'健康码上传失败：{str(e)}')
            return ResponseCode.ERROR, '上传失败！', str(e)

