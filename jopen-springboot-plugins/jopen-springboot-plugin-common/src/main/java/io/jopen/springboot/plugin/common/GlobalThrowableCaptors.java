package io.jopen.springboot.plugin.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author maxuefeng
 * @since 2019/11/18
 */
@ControllerAdvice
public class GlobalThrowableCaptors {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalThrowableCaptors.class);

    @Autowired
    private Environment env;


}
