package com.gitee.itapm.paser;

import com.gitee.itapm.annotations.ApiParam;
import com.gitee.itapm.annotations.enums.ParamDirectoryType;
import com.gitee.itapm.paser.bean.ParamField;
import com.gitee.itapm.paser.bean.Parameter;
import com.gitee.itapm.paser.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jetty on 2018/12/11.
 */
public class ParamParseEngine {

    public static final Map <String,Parameter> cache=new ConcurrentHashMap<>();

    public static List<Parameter> parse(Class[] classes,Map<String,Class> contextMap){
        List<Parameter> parameterList=new ArrayList<>();
        for(Class clazz: classes){
            Parameter cacheParameter=cache.get(clazz.getCanonicalName());
            if(cacheParameter!=null){
                parameterList.add(cacheParameter);
                continue;
            }
            List<Field> allfieldList= ReflectionUtils.findAllFields(clazz);
            List<Field> annotationFieldList=getApiParamAnnotationFieldList(allfieldList);
            List<ParamField> paramList=new ArrayList<>();
            for(Field field: annotationFieldList){
                field.setAccessible(true);
                ParamField param=convert2Param(field,contextMap,null);
                paramList.add(param);
            }
            parameterList.add(new Parameter(clazz.getCanonicalName(),paramList));
        }
        return parameterList;
    }



    public static List<Parameter> parse(ParameterizedType parameterizedType,Map<String,Class> contextMap){
        List<Parameter> parameterList=new ArrayList<>();
        if(parameterizedType ==null){
            return parameterList;
        }
        Class genericTypeClass=null;
        try{
             genericTypeClass=(Class)parameterizedType.getActualTypeArguments()[0];
        }catch (Exception e){
        }
        Class clazz=(Class)parameterizedType.getRawType();
        List<Field> allfieldList= ReflectionUtils.findAllFields(clazz);
        List<Field> annotationFieldList=getApiParamAnnotationFieldList(allfieldList);
        List<ParamField> paramList=new ArrayList<>();
        for(Field field: annotationFieldList){
            field.setAccessible(true);
            ParamField param=convert2Param(field,contextMap,genericTypeClass);
            paramList.add(param);
        }
        parameterList.add(new Parameter(clazz.getCanonicalName(),paramList));

        return parameterList;
    }




    private static ParamField convert2Param(Field field,Map<String,Class> contextMap,Class genericClassType) {
        ParamField param = new ParamField();
        ApiParam apiParam = field.getAnnotation(ApiParam.class);
        param.setDefaultValue(apiParam.defaultValue());
        param.setDesrciption(apiParam.desrciption());
        param.setExample(apiParam.example());
        param.setLength(apiParam.length());
        param.setRequired(apiParam.required().toString());
        String type=getType(field.getGenericType().toString());
        param.setType(type);
        param.setName(field.getName());
        param.setRefGenericClassNameList(getGenericParamTypeList(field, contextMap));

        if (isDefineField(field)) {//自定义的类当作泛型处理
            contextMap.put(getType(field.getGenericType().getTypeName()),field.getType());
            param.getRefGenericClassNameList().add(field.getGenericType().getTypeName());
        }
        if("T".equals(type)&&genericClassType!=null){
            param.setType(getType(genericClassType.getCanonicalName()));
            contextMap.put(getType(genericClassType.getCanonicalName()),genericClassType);
            param.getRefGenericClassNameList().add(genericClassType.getCanonicalName());
        }

        return param;
    }


    private static boolean isDefineField(Field field){
        String type=field.getGenericType().toString();
        if(type.startsWith("java")){//jdk自带的类型
            return false;
        } else if(!type.contains(".")){//基本类型 没有。
            return false;
        }
        return true;

    }


    private static String getType(String genericType){
        String returnType=genericType;
        if(returnType.endsWith("class java.lang.String")){
            returnType="String";
        }
        if(returnType.contains("java.lang.")) {
            returnType= returnType.replaceAll("java.lang.", "");
        }
        if(returnType.contains("java.util.")) {
            returnType= returnType.replaceAll("java.util.", "");
        }
        if(returnType.contains("java.math.")) {
            returnType= returnType.replaceAll("java.math.", "");
        }
        if(returnType.contains("class ")){
            returnType= returnType.replaceAll("class\\s","");
        }
        return returnType;
    }

    private static List<Field> getApiParamAnnotationFieldList(List<Field> allFieldList){
        List<Field> annotationFieldList=new ArrayList<>();
        for(Field field: allFieldList){
            if(field.getAnnotation(ApiParam.class)!=null){
                annotationFieldList.add(field);
            }
        }
        return annotationFieldList;
    }



    private static List<String> getGenericParamTypeList(Field field,Map<String,Class> contextMap){
        List<String> list=new ArrayList<>();
        Type type= field.getGenericType();
        if(type instanceof ParameterizedType){
            ParameterizedType parameterizedType=(ParameterizedType)type;
            Type[] types=parameterizedType.getActualTypeArguments();
            for(Type argument: types){
                if(argument instanceof Class){
                    Class clazz=(Class)argument;
                    contextMap.put(clazz.getCanonicalName(),clazz);
                    list.add(clazz.getCanonicalName());
                }
            }
        }
        return list;
    }
}
