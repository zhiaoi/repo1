package com.briup.gateway;



import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.briup.enviroment.pojo.Enviroment;
import com.briup.gateway.gathe.GatherImpl;
import com.briup.gateway.send.SenderImpl;

public class Application {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(8);
        
        executorService.scheduleAtFixedRate(()->{
            GatherImpl gatherImpl = new GatherImpl();
            Collection<Enviroment> gathe = gatherImpl.gathe();
            SenderImpl senderImpl = new SenderImpl();
            senderImpl.send(gathe);
            
        }, 1, 10, TimeUnit.SECONDS);
     }
}
