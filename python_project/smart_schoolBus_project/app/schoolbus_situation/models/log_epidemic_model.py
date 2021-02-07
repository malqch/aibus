from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class LogEpidemic(db.Model, CRUDMixin):
    __tablename__ = 'log_epidemic'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    unique_identity = db.Column(db.Integer, nullable=True)  # 身份唯一标识
    clock_date = db.Column(db.DateTime(), nullable=True)  # 打卡时间
    body_temperature = db.Column(db.Float, nullable=True)  # 打卡时间
    health_qr_code = db.Column(db.String(2048), nullable=True)  # 体温
    itinerary_qr_code = db.Column(db.String(2048), nullable=True)  # 行程码
    create_user_id = db.Column(db.Integer, nullable=True)  # 创建人
    create_dt = db.Column(db.DateTime(), nullable=True)
    modify_user_id = db.Column(db.Integer, nullable=True)  # 修改时间
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 更新时间

    def save_log_epidemic(self, unique_identity, clock_date, health_qr_code, create_user_id, create_dt):
        log_e = self
        log_e.unique_identity = unique_identity
        log_e.clock_date = clock_date
        log_e.health_qr_code = health_qr_code
        log_e.create_user_id = create_user_id
        log_e.create_dt = create_dt
        db.session.add(log_e)


