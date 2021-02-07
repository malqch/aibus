package com.wntime.ec.common.util;

import com.wntime.ec.common.util.exception.BusinessException;
import com.wntime.ec.common.util.exception.ExceptionConstant;
import com.wntime.ec.common.util.param.http.HttpRspParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Set;

/**
 * controller层统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     *
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     *
     * @param model
     */
    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("author", "wing");
    }


    @ExceptionHandler(Exception.class)
    public HttpRspParam handlerException(Exception e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new HttpRspParam(1, new HashMap(), ExceptionConstant.ERROR_MSG);
    }

    @ExceptionHandler(BusinessException.class)
    public HttpRspParam handlerException(BusinessException e) {
        e.printStackTrace();
        log.warn(e.getMessage());
        return new HttpRspParam(e.getCode() == -1 ? 1 : e.getCode(), new HashMap(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public HttpRspParam handlerException(MethodArgumentNotValidException e) {
        String defaultMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn(defaultMessage);
        return new HttpRspParam(1, new HashMap(), defaultMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public HttpRspParam handlerException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();

        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
            log.warn(constraintViolation.getMessage());
            return new HttpRspParam(1, new HashMap(), constraintViolation.getMessage());
        }
        log.warn(e.getMessage());
        return new HttpRspParam(1, new HashMap(), "error");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public HttpRspParam handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return new HttpRspParam(1, new HashMap(), "already exists");
    }

    @ExceptionHandler(AuthenticationException.class)
    public HttpRspParam handleAuthorizationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return new HttpRspParam(ExceptionConstant.SC_UNAUTHORIZED, new HashMap(), "login invalid");
    }

    @ExceptionHandler(AuthorizationException.class)
    public HttpRspParam handleAuthorizationException(AuthorizationException e) {
        log.error(e.getMessage(), e);
        return new HttpRspParam(ExceptionConstant.SC_FORBIDDEN, new HashMap(), "permission denied");
    }


}
