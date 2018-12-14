package com.gitee.itapm.mapper.bean;

import com.gitee.itapm.mapper.bean.parent.AbstractDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jetty on 2018/12/13.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParamTypeRefGenericDO extends AbstractDO {

    private Integer id;

    private Integer paramTypeId;

    private Integer paramFieldId;

    public ParamTypeRefGenericDO(Integer paramTypeId, Integer paramFieldId){
        this.paramFieldId=this.paramFieldId;
        this.paramTypeId=this.paramTypeId;
    }

}