
from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class InfoAuthorizeLog(db.Model, CRUDMixin):
    __tablename__ = 'info_authorize_log'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    authorize_id = db.Column(db.Integer, nullable=True)  # 授权人id
    change_type = db.Column(db.Integer, nullable=True)  # 变更类型（新增1/删除2）
    create_user_id = db.Column(db.String(32), nullable=True)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)  # 更新人
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间

    def save_auth_log(self, authorize_id, change_type, create_user_id, create_dt):
        a_log = self
        a_log.authorize_id = authorize_id
        a_log.change_type = change_type
        a_log.create_user_id = create_user_id
        a_log.create_dt = create_dt
        db.session.add(a_log)


