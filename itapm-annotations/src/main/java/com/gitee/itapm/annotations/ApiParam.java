package com.gitee.itapm.annotations;

import com.gitee.itapm.annotations.enums.Required;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by jetty on 2018/12/11.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {

    int length() default 0;

    Required required() default Required.N;

    String defaultValue() default "";

    String example() default "";

    String desrciption() default "";
}
