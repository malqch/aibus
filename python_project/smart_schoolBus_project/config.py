#!/usr/bin/python
# -*- coding: utf-8 -*-

# 系统参数配置

import os

basedir = os.path.abspath(os.path.dirname(__file__))


class Config(object):
    PORT = 5000
    HOST = '0.0.0.0'
    URL_PREFIX = '/sc_bus/api'
    SECRET_KEY = 'AUTOPLAT'
    SESSION_EXP_TIME = 3600 * 24
    MAX_CONTENT_LENGTH = 16 * 1024 * 1024

    # 数据库配置

    SQLALCHEMY_DATABASE_NAME = "postgres"
    SQLALCHEMY_DATABASE_USERNAME = "postgres"
    SQLALCHEMY_DATABASE_PASSWORD = "123456"
    SQLALCHEMY_DATABASE_IP = "localhost"
    SQLALCHEMY_DATABASE_PORT = 5432
    # SQLALCHEMY_DATABASE_SCHEMA_NAME = "cloud_user"
    SQLALCHEMY_POOL_SIZE = 20
    SQLALCHEMY_MAX_OVERFLOW = 10
    MAX_MULTI_THREADING_NUM = 20
    SQLALCHEMY_COMMIT_ON_TEARDOWN = True
    SQLALCHEMY_TRACK_MODIFICATIONS = True
    SQLALCHEMY_ECHO = True
    SQLALCHEMY_POOL_RECYCLE = 600

    @staticmethod
    def init_app():
        pass


class DevelopmentConfig(Config):
    DEBUG = True
    SQLALCHEMY_DATABASE_URI = "postgresql+psycopg2://%s:%s@%s:%d/%s" % (
        Config.SQLALCHEMY_DATABASE_USERNAME,
        Config.SQLALCHEMY_DATABASE_PASSWORD,
        Config.SQLALCHEMY_DATABASE_IP,
        Config.SQLALCHEMY_DATABASE_PORT,
        Config.SQLALCHEMY_DATABASE_NAME)


class TestingConfig(Config):
    TESTING = True
    SQLALCHEMY_DATABASE_URI = "postgresql+psycopg2://%s:%s@%s:%d/%s" % (
        Config.SQLALCHEMY_DATABASE_USERNAME,
        Config.SQLALCHEMY_DATABASE_PASSWORD,
        Config.SQLALCHEMY_DATABASE_IP,
        Config.SQLALCHEMY_DATABASE_PORT,
        Config.SQLALCHEMY_DATABASE_NAME)


class ProductionConfig(Config):
    SQLALCHEMY_DATABASE_URI = "postgresql+psycopg2://%s:%s@%s:%d/%s" % (
        Config.SQLALCHEMY_DATABASE_USERNAME,
        Config.SQLALCHEMY_DATABASE_PASSWORD,
        Config.SQLALCHEMY_DATABASE_IP,
        Config.SQLALCHEMY_DATABASE_PORT,
        Config.SQLALCHEMY_DATABASE_NAME)


config = {
    'development': DevelopmentConfig,
    'testing': TestingConfig,
    'production': ProductionConfig,
    # 'default': ProductionConfig,
    'default': DevelopmentConfig
}
