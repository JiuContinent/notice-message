package com.notice.common.annotation;

import com.notice.config.ServerBootConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Import(ServerBootConfig.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableNettyServer {
}

