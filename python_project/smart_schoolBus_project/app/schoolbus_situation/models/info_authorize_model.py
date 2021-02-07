#!/usr/bin/python
# -*- coding: utf-8 -*-

from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class InfoAuthorize(db.Model, CRUDMixin):
    __tablename__ = 'info_authorize'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    basic_id = db.Column(db.Integer, nullable=False)  # 基本信息主建
    student_id = db.Column(db.Integer, nullable=True)  # 学生id
    auth_status = db.Column(db.String(32), nullable=True)  # 授权状态
    auth_number = db.Column(db.Integer, nullable=False)  # 授权序号
    relation_student = db.Column(db.String(32), nullable=True)  # 与学生关系
    mobile_number = db.Column(db.String(32), nullable=False)  # 手机号
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
    is_deleted = db.Column(db.String(1), nullable=True)


    def query_auth_info(self, login_user_id):
        """
        获取授权人列表
        :param login_user_id
        :return:
        """
        sql = r" select a.id, pbf.full_name as authorize_name, pbf.take_photo, a.mobile_number, " \
              r" a.relation_student from info_authorize a join info_people_basic_facts pbf on " \
              r" a.basic_id = pbf.id join info_student s on a.student_id = s.id join " \
              r" info_guardian g on g.student_id = s.id where g.login_user_id = :login_user_id " \
              r" and a.is_deleted = '0'; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id})
        return result


    def query_sa_auth_tooltips(self, login_user_id, create_dt):
        """
        获取有无学生变更授权人
        :param login_user_id
        :param create_dt
        :return:
        """
        sql = r" select distinct s.id, pbc2.full_name as student_name from info_authorize_log al " \
              r" join info_authorize a on a.id = al.authorize_id join info_people_basic_facts pbc1 " \
              r" on pbc1.id = a.basic_id join info_student s on a.student_id = s.id join " \
              r" info_people_basic_facts pbc2 on pbc2.id = s.basic_id join admin_position_auth pa " \
              r" on pa.company_id = s.school_id join admin_user_position up on " \
              r" up.position_id = pa.position_id where up.user_id = :login_user_id and " \
              r" pa.is_deleted = '0' and to_char(al.create_dt, 'yyyy-MM-dd') = :create_dt "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id,
                                                'create_dt': create_dt})
        return result


    def delete_auth_info(self, auth_id):
        """
        删除授权人
        :param auth_id
        :return:
        """
        auth = InfoAuthorize.query.filter_by(id=auth_id).first()
        if auth:
            auth.is_deleted = '1'
            return True
        else:
            return False

    def save_auth_info(self, uuid, basic_id, student_id, is_deleted, relation_student, mobile_number, create_dt):
        """
        增加授权人
        :param auth_id
        :return:
        """
        a_info = self
        a_info.id = uuid
        a_info.basic_id = basic_id
        a_info.student_id = student_id
        a_info.is_deleted = is_deleted
        a_info.relation_student = relation_student
        a_info.mobile_number = mobile_number
        a_info.create_dt = create_dt
        db.session.add(a_info)
        db.session.commit()
        return self.id


