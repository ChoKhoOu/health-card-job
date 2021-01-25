package top.chokhoou.healthcardjob.common.handler;

import cn.hutool.core.exceptions.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import top.chokhoou.healthcardjob.common.Result;
import top.chokhoou.healthcardjob.util.exception.BusinessException;
import top.chokhoou.healthcardjob.util.exception.ServiceException;

/**
 * @author ChoKhoOu
 * @date 2021/1/25
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({Exception.class})
    public final Result handleException(Exception ex, WebRequest request) {
        log.error("unexpected exception", ex);
        return Result.failed("服务繁忙");
    }

    @ExceptionHandler({ServiceException.class})
    public final Result handleServiceException(ServiceException ex, WebRequest request) {
        log.error("service exception", ex);
        return Result.failed("服务繁忙");
    }

    @ExceptionHandler({BusinessException.class})
    public final Result handleBusinessException(BusinessException ex, WebRequest request) {
        log.info("business exception", ex);
        return Result.failed(ex.getMessage());
    }

    @ExceptionHandler({ValidateException.class})
    public final Result handleValidException(ValidateException ex, WebRequest request) {
        log.info("param exception", ex);
        return Result.failed(ex.getMessage());
    }
}
