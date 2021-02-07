#!/usr/bin/python
# -*- coding: utf-8 -*-

from flask import current_app
from itsdangerous import TimedJSONWebSignatureSerializer as Serializer, SignatureExpired, BadSignature
import hashlib
from sqlalchemy import text
from public.database import CRUDMixin
from app import db


class Users(db.Model, CRUDMixin):
    __tablename__ = 'admin_user'
    user_id = db.Column(db.Integer, primary_key=True)  # 用户id
    user_name = db.Column(db.String(32), nullable=False)  # 用户名
    login_name = db.Column(db.String(32), nullable=False)  # 登录名
    password = db.Column(db.String(100), nullable=False)  # 登录名
    email = db.Column(db.String(64), nullable=True)  # 邮箱
    mobile = db.Column(db.String(11), nullable=True)  # 电话
    salt = db.Column(db.String(20), nullable=False)  # 加盐值
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    is_enabled = db.Column(db.String(1), nullable=False)  # 是否可用
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
    roles = db.relationship('UserRole', backref='users', lazy='select')

    @staticmethod
    def check_user_id(id):
        return Users.query.get(int(id))

    def update_password(self, new_password, salt):
        passwd_sha = hashlib.sha256(salt.encode('utf8'))
        passwd_sha.update(new_password.encode('utf-8'))
        self.password = passwd_sha.hexdigest()

    def verify_password(self, password, salt):
        # 密码加密对比
        passwd_sha = hashlib.sha256(salt.encode('utf8'))
        passwd_sha.update(password.encode('utf-8'))
        if passwd_sha.hexdigest() == self.password:
            return True
        else:
            return False

    def generate_auth_token(self, expiration=1800):
        s = Serializer(current_app.config['SECRET_KEY'], expires_in=expiration)
        return s.dumps({'id': str(self.user_id)})

    @staticmethod
    def verify_auth_token(token):
        s = Serializer(current_app.config['SECRET_KEY'])
        try:
            data, header = s.loads(token, return_header=True)
        except SignatureExpired:
            return None  # valid token, but expired
        except BadSignature as e:
            return None  # invalid token
        user = Users.query.get(data['id'])
        return user

    def query_area_info(self, login_user_id):
        sql = r" select u.user_id, r.role_code, s.area_code, ia.area_name from " \
              r" admin_user u join admin_user_role ur on u.user_id = ur.user_id " \
              r" join admin_role r on r.role_id = ur.role_id join " \
              r" admin_user_position up on u.user_id = up.user_id join " \
              r" admin_position_auth pa on up.position_id = pa.position_id join " \
              r" info_school s on s.id = pa.company_id join info_area ia on " \
              r" ia.area_code = s.area_code where u.user_id = :login_user_id and " \
              r" pa.is_deleted = '0' and ia.is_deleted = 0; "
        result = db.session.execute(text(sql), {'login_user_id': login_user_id})
        return result

    @staticmethod
    def query_user_info(role):
        """根据用户角色获取用户信息: 5-家长, 2-学校行政管理人员, 3-校车工作人员"""

        if str(role.get("role_id")) == "2":
            sql = "select user_name, mobile from admin_user where user_id=:user_id"
        elif str(role.get("role_id")) == "3":
            sql = """
            select u.user_id,u.user_name, u.mobile, coalesce(d.is_primary,so.is_primary) as is_primary 
            from admin_user u 
            left join info_driver d on u.user_id = d.login_user_id and d.is_deleted = '0' 
            left join info_safety_officer so on u.user_id = so.login_user_id and so.is_deleted = '0' 
            where u.user_id=:user_id
            """
        elif str(role.get("role_id")) == "5":
            sql = """
            select pbf2.full_name as student_name,c.class_name,pbf1.full_name as guardian_name
            ,g.relation_student,g.mobile_number 
            from info_guardian g 
            join info_people_basic_facts pbf1 on g.basic_id = pbf1.id 
            join info_student s on g.student_id = s.id 
            join info_people_basic_facts pbf2 on s.basic_id = pbf2.id 
            join info_classes c on s.classes_id = c.id 
            where g.login_user_id =:user_id
            """
        else:
            return None

        result = db.session.execute(text(sql), {"user_id": role.get("user_id")})
        return result


class Roles(db.Model, CRUDMixin):
    __tablename__ = 'admin_role'
    role_id = db.Column(db.Integer, primary_key=True)  # 角色id
    name = db.Column(db.String(32), nullable=False)  # 角色名
    description = db.Column(db.String(128), nullable=True)  # 描述
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    is_enabled = db.Column(db.String(1), nullable=False)  # 是否可用
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间
    role_code = db.Column(db.String(32), nullable=True)  # 角色码
    users = db.relationship('UserRole', backref='roles', lazy='select')


    def query_role_code(self, role_id):
        role = Roles.query.filter_by(role_id=role_id).first()
        return role


class UserRole(db.Model, CRUDMixin):
    __tablename__ = 'admin_user_role'
    id = db.Column(db.Integer, primary_key=True)  # 主键
    user_id = db.Column(db.Integer,  db.ForeignKey(Users.user_id), nullable=False)  # 用户id
    role_id = db.Column(db.Integer, db.ForeignKey(Roles.role_id), nullable=False)  # 角色id
    is_deleted = db.Column(db.String(1), nullable=False)  # 是否删除
    create_user_id = db.Column(db.Integer, nullable=True)
    create_dt = db.Column(db.DateTime(), nullable=True)  # 创建时间
    modify_user_id = db.Column(db.Integer, nullable=True)
    modify_dt = db.Column(db.DateTime(), nullable=True)  # 修改时间


    def query_role_id(self, user_id):
        u_role = UserRole.query.filter_by(user_id=user_id).first()
        return u_role


class Captcha(db.Model, CRUDMixin):
    __tablename__ = 'admin_captcha'
    uuid = db.Column(db.String(36), primary_key=True)  # 主键
    code = db.Column(db.String(6), nullable=False)  # 验证码
    expire_time = db.Column(db.DateTime(), nullable=False)  # 有效期


class UserToken(db.Model, CRUDMixin):
    __tablename__ = 'admin_user_token'
    user_id = db.Column(db.Integer, primary_key=True)  # 主键
    token = db.Column(db.String(256), primary_key=False)  # 令牌
    expire_time = db.Column(db.DateTime(), nullable=False)  # 过期时间
    update_time = db.Column(db.DateTime(), nullable=True)  # 修改时间


    def save_token(self, user_id, token, expire_time):
        user_token = UserToken()
        user_token.user_id = user_id
        user_token.token = token
        user_token.expire_time = expire_time
        db.session.add(user_token)


    def update_token_time(self, token, expire_time, update_time):
        token.expire_time = expire_time
        token.update_time = update_time

    def delete_token(self, user_id):
        u_token = UserToken.query.filter_by(user_id=user_id).first()
        if u_token:
            db.session.delete(u_token)





