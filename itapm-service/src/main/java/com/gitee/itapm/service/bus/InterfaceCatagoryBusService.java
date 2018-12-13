package com.gitee.itapm.service.bus;

import com.gitee.itapm.service.InterfaceCatagoryService;
import com.gitee.itapm.service.bean.InterfaceCatagoryBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jetty on 2018/12/12.
 */
@Component
public class InterfaceCatagoryBusService {

    @Autowired
    private InterfaceCatagoryService interfaceCatagoryService;

    public InterfaceCatagoryBO persist(Integer systemVesionId,String name){
        InterfaceCatagoryBO interfaceCatagoryBO=interfaceCatagoryService.queryBySystemVersionIdAndName(systemVesionId, name);
        if(interfaceCatagoryBO==null){
            interfaceCatagoryBO= interfaceCatagoryService.persist(systemVesionId,name);
        }
        return interfaceCatagoryBO;
    }

    public List<InterfaceCatagoryBO> queryBySystemVersionId(Integer systemVersionId){
        return interfaceCatagoryService.queryBySystemVersionId(systemVersionId);
    }

    public void deleteById(Integer id){
        interfaceCatagoryService.deleteById(id);
    }
}
