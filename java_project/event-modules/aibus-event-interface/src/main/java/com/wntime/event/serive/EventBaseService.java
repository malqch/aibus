package com.wntime.event.serive;

import com.wntime.event.vo.*;

import java.util.List;

public interface EventBaseService {

    List<InfoCollectEventVo> queryCollectEvent();

    List<InfoEventExtendVo> queryEventExtend();

    List<InfoEventLevelVo> queryEventLevel();

    List<InfoEventTargetVo> queryEventTarget();

    List<InfoEventTypeVo> queryEventType();
}
