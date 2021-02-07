
from sqlalchemy import text
from public.database import CRUDMixin
from app import db
from app.schoolbus_situation.models.info_authorize_model import InfoAuthorize


class InfoPeopleBasicFacts(db.Model, CRUDMixin):
    __tablename__ = 'info_people_basic_facts'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    id_no = db.Column(db.String(32), nullable=True)  # 身份证号
    full_name = db.Column(db.String(32), nullable=True)  # 姓名
    sex = db.Column(db.String(32), nullable=True)  # 性别
    age = db.Column(db.Integer, nullable=True)  # 年龄
    take_photo = db.Column(db.String(128), nullable=True)  # 身份验证照片
    residential_address = db.Column(db.String(128), nullable=True)  # 居住地址
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
    category = db.Column(db.String(32), nullable=True)  # 人员类别
    is_deleted = db.Column(db.String(1), nullable=False, default='0')

    def save_people_basic_facts(self, uuid, full_name, take_photo, create_dt):
        """
        增加授权人信息
        :param uuid:
        :param full_name:
        :param take_photo:
        :param create_dt:
        :return:
        """
        pbf = self
        pbf.id = uuid
        pbf.full_name = full_name
        pbf.take_photo = take_photo
        pbf.create_dt = create_dt
        db.session.add(pbf)
        db.session.commit()
        return self.id

    def check_full_name(self, login_user_id, full_name, relation_student):
        """
        判断授权人是否存在
        :param full_name: 授权人姓名
        :return:
        """
        sql = r" select pbf.id, pbf.full_name, g.student_id as authorize_name from info_people_basic_facts pbf " \
              r" join info_authorize a on pbf.id = a.basic_id join info_guardian g " \
              r" on g.student_id = a.student_id where a.is_deleted = '0' and " \
              r" g.login_user_id = :login_user_id and pbf.full_name = :full_name " \
              r" and a.relation_student = :relation_student; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'full_name': full_name,
                                                'relation_student': relation_student})
        return result

    def query_id_by_user(self, login_user_id):
        """
        查询当前登录人（家长，司机，安全员）在info_people_basic_facts表中的id
        :param login_user_id:
        :return:
        """
        sql = r" select pbf.* from info_people_basic_facts pbf left join " \
              r" info_guardian g on pbf.id = g.basic_id left join info_driver d on " \
              r" pbf.id = d.basic_id left join info_safety_officer sa on " \
              r" pbf.id = sa.basic_id where g.login_user_id = :login_user_id " \
              r" or d.login_user_id = :login_user_id or sa.login_user_id = :login_user_id "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id})
        return result
