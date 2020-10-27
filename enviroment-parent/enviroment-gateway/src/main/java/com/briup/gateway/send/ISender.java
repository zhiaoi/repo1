package com.briup.gateway.send;

import java.util.Collection;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;

public interface ISender {
    void send(Collection<Enviroment> data) throws EnviromentException;
}
