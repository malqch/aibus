

package com.wntime.common.exception;

import com.wntime.common.utils.MessageConstant;
import com.wntime.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        logger.error(e.getMessage(), e);
        return R.error(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(MessageConstant.SC_NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public R handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return R.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return R.error("参数" + e.getName() + "转换异常，请检查输入参数");
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException || e instanceof MethodArgumentTypeMismatchException) {
            logger.error(e.getMessage(), e);
            return R.error();
        }
        logger.error(e.getMessage(), e);
        return R.error();
    }

    @ExceptionHandler(BindException.class)
    public R handBindException(BindException e) {
        List<FieldError> allError = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError error : allError) {
            sb.append(error.getField()).append(":").append(error.getDefaultMessage()).append(";");
        }
        return R.error(sb.toString());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R handUploadException(MaxUploadSizeExceededException e) {
        FileUploadBase.FileSizeLimitExceededException fileException = (FileUploadBase.FileSizeLimitExceededException) e.getCause().getCause();
        return R.error("最大上传大小为" + fileException.getPermittedSize() / 1024 / 1024 + "MB");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        return R.error("未提供请求参数:" + e.getParameterName());
    }

    @ExceptionHandler(ConstraintViolationException.class)
	public R handleConstraintViolationException(ConstraintViolationException e){
		List<String> defaultMsg = e.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		return R.error(defaultMsg.get(0));
	}

    @ExceptionHandler(DataAccessException.class)
    public R handDataAccessException(DataAccessException e) {
        logger.error(e.getMessage(), e);
        return R.error("数据更新发生异常");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        String message = "";

        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                errors.forEach(p -> {
                    FieldError fieldError = (FieldError) p;
                    logger.error("Data check failure : object{" + fieldError.getObjectName() + "},field{" + fieldError.getField() +
                            "},errorMessage{" + fieldError.getDefaultMessage() + "}");

                });
                if (errors.size() > 0) {
                    FieldError fieldError = (FieldError) errors.get(0);
                    message =fieldError.getField() + fieldError.getDefaultMessage();
                }
            }
        }
        return R.error(message);
    }
}
