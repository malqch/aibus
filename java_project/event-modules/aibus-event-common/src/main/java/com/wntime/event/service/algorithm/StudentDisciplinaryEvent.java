package com.wntime.event.service.algorithm;

import com.wntime.common.utils.R;
import com.wntime.event.form.EventReportForm;
import com.wntime.event.service.EventDealAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("studentDisciplinaryEvent")
public class StudentDisciplinaryEvent extends AbstractEventHandle implements EventDealAlgorithm {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 学生违纪
     * @param eventReportForm
     */
    @Override
    public R deal(EventReportForm eventReportForm) {
        //添加家长是否有查看的权限的标签
        eventReportForm.getTags().put("check_permissions","0");
        return R.ok();
    }
}
