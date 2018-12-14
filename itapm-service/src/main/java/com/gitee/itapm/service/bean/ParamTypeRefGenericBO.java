package com.gitee.itapm.service.bean;

import com.gitee.itapm.service.bean.parent.BaseBO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jetty on 2018/12/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamTypeRefGenericBO extends BaseBO {

    private Integer id;

    private Integer paramTypeId;

    private Integer genericTypeId;

    public ParamTypeRefGenericBO(Integer paramTypeId,Integer genericTypeId){
        this.paramTypeId=this.paramTypeId;
        this.genericTypeId=this.genericTypeId;
    }

}
