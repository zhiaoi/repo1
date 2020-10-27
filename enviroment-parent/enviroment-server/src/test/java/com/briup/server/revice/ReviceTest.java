package com.briup.server.revice;


import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.briup.enviroment.pojo.Enviroment;
import com.briup.enviroment.utils.IdWorker;

import org.junit.Test;

import com.briup.gateway.gathe.GatherImpl;
import com.briup.gateway.send.SenderImpl;
import com.briup.server.database.StoreImpl;
import com.briup.server.utils.DBUtil;

public class ReviceTest {
    
    
    public static void main(String[] args) throws Exception {
//        ReviceImpl reviceImpl = new ReviceImpl();
       
    }
    
    @Test
    public void testSend() {
//        Date date = new Date(System.currentTimeMillis());
//        List<Enviroment> list = new ArrayList<>();
//        Enviroment enviroment = new Enviroment();
//        enviroment.setId(2);
//        enviroment.setSrcId("2");
//        enviroment.setSystemId("2");
//        enviroment.setRegionId("2");
//        enviroment.setDateType("2");
//        enviroment.setName("2");
//        enviroment.setCount(2);
//        enviroment.setDataStatus((byte) 2);
//        enviroment.setData(2);
//        enviroment.setReviceStatus((byte) 2);
//        enviroment.setDate(new Date(System.currentTimeMillis()));
//        list.add(enviroment);
//        GatherImpl gatherImpl = new GatherImpl();
//        Collection<Enviroment> list = gatherImpl.gathe();
//        SenderImpl senderImpl = new SenderImpl();
//        senderImpl.send(list);
    }
    
    @Test
    public void testReviceAndStore() {
//        List<Enviroment> list = new ArrayList<>();
//        Enviroment enviroment = new Enviroment();
//        enviroment.setId(1);
//        enviroment.setSrcId("100");
//        enviroment.setSystemId("101");
//        enviroment.setRegionId("2");
//        enviroment.setDateType("16");
//        enviroment.setName("湿度");
//        enviroment.setCount(1);
//        enviroment.setDataStatus((byte) 3);
//        enviroment.setData(48.4281396);
//        enviroment.setReviceStatus((byte) 1);
//        enviroment.setDate(new Date(System.currentTimeMillis()));
//        list.add(enviroment);
//        StoreImpl storeImpl = new StoreImpl();
//        storeImpl.store(list);
    }
    
    //Enviroment(id=1309842023072542720, srcId=100, systemId=101, regionId=2, dateType=16, name=湿度, count=1, dataStatus=3, data=48.4281396, reviceStatus=1, date=2018-01-19)
    
    @Test
    public void test() throws Exception {
//        Connection connection = DBUtil.getConnection();
//        System.out.println(connection);
    }
    
    @Test
    public void testSub() {
//        Enviroment enviroment = new Enviroment();
//        IdWorker idWorker = new IdWorker();  
//        enviroment.setId(idWorker.nextId());
//        enviroment.setSrcId("100");
//        enviroment.setSystemId("101");
//        enviroment.setRegionId("2");
//        enviroment.setDateType("16");
//        enviroment.setName("湿度");
//        enviroment.setCount(1);
//        enviroment.setDataStatus((byte) 3);
//        enviroment.setData(48.4281396);
//        enviroment.setReviceStatus((byte) 1);
//        enviroment.setDate(new Date(System.currentTimeMillis()));
//        StoreImpl storeImpl = new StoreImpl();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String day = storeImpl.getDay(sdf, enviroment.getDate());
//        System.out.println(day);
        
       
    }
    
    @Test
    public void testSub1() {
//        StoreImpl storeImpl = new StoreImpl();
//        storeImpl.store(null);
        ReviceImpl reviceImpl = new ReviceImpl();
        reviceImpl.revice();
    }
    
}
