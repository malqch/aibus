package com.wntime.modules.monitor.aspect;

import com.wntime.common.exception.RRException;
import com.wntime.common.utils.ShiroUtils;
import com.wntime.modules.monitor.controller.BusCompanyController;
import com.wntime.modules.monitor.controller.BusDetailController;
import com.wntime.modules.monitor.controller.CarFactoryController;
import com.wntime.modules.sys.vo.UserPositionVo;
import com.wntime.service.common.service.VideoTunnelService;
import org.apache.shiro.session.Session;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 79448
 * @date 2020/9/17 13:58
 */
@Aspect
@Component
public class AuthAspect {

    @Resource
    private VideoTunnelService videoTunnelService;

    @Pointcut("execution(* com.wntime.modules.monitor.controller.*.*(..))")
    public void pointCut() {
    }

    /**
     * 拦截大屏所有接口判断数据权限
     * @param joinpoint
     */
    @Before("pointCut()")
    public void before(JoinPoint joinpoint) {
        Session session = ShiroUtils.getSession();
        AtomicReference<Boolean> hasAuth = new AtomicReference<>(false);
        Object positionAuth = session.getAttribute("positionAuth");
        Optional.ofNullable(positionAuth)
                .filter(ArrayList.class::isInstance)
                .map(ArrayList.class::cast)
                .ifPresent(positionAuthList -> {
                    hasAuth.set(positionAuthList.stream().anyMatch(item -> {
                                String systemAuth = ((UserPositionVo) item).getSystemAuth();
                                return (("branch".equals(systemAuth) || "group".equals(systemAuth))
                                        && (joinpoint.getTarget() instanceof BusCompanyController || joinpoint.getTarget() instanceof BusDetailController))
                                        || (joinpoint.getTarget() instanceof CarFactoryController && "factory".equals(systemAuth));
                            }
                    ));
                });
        if (!hasAuth.get()) {
           throw new RRException("无权限访问");
        }
    }

    @Pointcut("execution(* com.wntime.modules.monitor.controller.BusDetailController.deviceStatus(..))")
    public void pointCut1(){}

    /**
     * 拦截设备状态接口，延长实时视频的自动关闭时间
     * @param joinPoint
     */
    @After(value="pointCut1()")
    public void after(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        if(args.length==1) {
            long busId = (long) args[0];
            videoTunnelService.updateBusTunnelPort(busId);
        }
    }
}
