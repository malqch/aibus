#!/usr/bin/env python
# coding: utf-8

import logging
import os
from concurrent_log_handler import ConcurrentRotatingFileHandler as RotatingFileHandler


basedir = os.path.abspath(os.path.dirname(__file__))
logFile = '../logs/smart_schoolBus_project.log'

logging.basicConfig(level=logging.DEBUG,
                    format='%(asctime)s - %(module)s - %(process)d - %(threadName)s - %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S')

Rthandler = RotatingFileHandler(logFile, maxBytes=10*1024*1024, backupCount=10)
Rthandler.setLevel(logging.INFO)
formatter = logging.Formatter('%(asctime)s - %(module)s - %(process)d - %(threadName)s - %(levelname)s - %(message)s')
Rthandler.setFormatter(formatter)

logger = logging.getLogger('')
logger.addHandler(Rthandler)
