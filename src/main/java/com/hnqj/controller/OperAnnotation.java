package com.hnqj.controller;

/**
 * Created by wangyong on 2016/11/22.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OperAnnotation {
    //模块名
    String moduleName();
    //操作内容
    String option();
}