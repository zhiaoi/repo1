package com.briup.gateway.gathe;

import java.util.Collection;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;
/**
 * 该接口实现类专门用来解析数据
 * @author Dell
 *
 */
public interface IGather {

    public Collection<Enviroment> gathe() throws EnviromentException;
}
