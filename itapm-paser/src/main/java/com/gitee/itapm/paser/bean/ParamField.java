package com.gitee.itapm.paser.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by jetty on 2018/12/11.
 */
@Data
@ToString(exclude = "subParam")
@NoArgsConstructor
public class ParamField {

    /**
     * 参数名称
     *
     */
    private String name;

    /**
     * 参数类型
     *
     */
    private String type;
    /**
     * 长度
     */
    private Integer length;

    /**
     * 是否必填 Y/N
     */
    private String required;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 例子
     */
    private String  example;

    /**
     * 描述信息
     */
    private String  desrciption;

    /**
     * paramDirectory
     *
     */
    private String directory;

    /**
     * 关联的泛型的类名,参数关联泛型的类名
     *
     */
    private List<String> refGenericClassNameList;
    /**
     * 泛型或者自定义类关联的泛型或自定义类
     */
    private List<String> genericRefGenericClassNameList;

}
