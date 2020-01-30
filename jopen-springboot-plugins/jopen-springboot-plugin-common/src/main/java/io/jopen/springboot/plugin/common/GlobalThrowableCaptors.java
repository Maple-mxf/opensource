package io.jopen.springboot.plugin.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author maxuefeng
 * @since 2019/11/18
 */
@ControllerAdvice
public class GlobalThrowableCaptors {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalThrowableCaptors.class);

    @Autowired
    private CommonPluginConfiguration commonPluginConfiguration;

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Object catchException(Exception e) {
        if (commonPluginConfiguration.getPrintExceptionStack()) {
            e.printStackTrace();
        }
        return new Object();
    }
}
