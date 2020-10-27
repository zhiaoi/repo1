package com.briup.server.database;

import java.util.Collection;

import org.w3c.dom.events.EventException;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;

public interface IStore {

    void store(Collection<Enviroment> data) throws EnviromentException;
}
