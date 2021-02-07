
"""
执行定时任务文件
"""
import datetime
# apscheduler 通过pytz来设置时区
import pytz
from apscheduler.schedulers.blocking import BlockingScheduler

# 此处不能用相对导入，而且导入的包里的__init__.py中也不能有from . import XX 语句，否则无法运行
from app.itinerary.receipt.view import auto_commit


scheduler = BlockingScheduler()
# 1. 按日期一次性执行
# scheduler.add_job(insert_uuid, 'date', run_date=datetime.datetime(2018, 8, 29, 16, 5, 0))

# 2. 按时间间隔循环执行
# scheduler.add_job(main_level_five_minute, 'interval', minutes=5)
# scheduler.add_job(main_level_one_hour, 'interval', hours=1)
# scheduler.add_job(main_level_one_day, 'interval', seconds=11)

# 每天19:50 定时执行
scheduler.add_job(auto_commit, 'cron', day_of_week='0, 1, 2, 3, 4, 5, 6', hour=19, minute=50, second=0, timezone=pytz.timezone('Asia/Shanghai'))



scheduler.start()


# 加在定时任务模块的末尾，判断是否运行在uwsgi模式下, 然后阻塞mule主线程.
try:
    import uwsgi
    while True:
        sig = uwsgi.signal_wait()
        print(sig)

except Exception as err:
    pass
