package com.briup.gateway.send;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;
import com.briup.enviroment.utils.BackupUtil;
import com.briup.gateway.gathe.GatherImpl;

public class SenderImpl implements ISender{
    
    private static final String IP;
    private static final int PORT;
    private static final String SEND_PATH;
    
    public static final Logger LOG = Logger.getLogger(SenderImpl.class);
    static {
//        Properties properties = BackupUtil.getPath(new File("src/main/resources/path.properties"), BackupUtil.UN_DELETE);
        Properties properties = BackupUtil.getPath(GatherImpl.class.getClassLoader().getResourceAsStream("path.properties"), BackupUtil.UN_DELETE);
        IP = properties.getProperty("ip");
        PORT = Integer.parseInt(properties.getProperty("port"));
        SEND_PATH = properties.getProperty("send_path");
    }
    @Override
    public void send(Collection<Enviroment> data) throws EnviromentException {
        File file = new File(SEND_PATH);
        Socket socket = null;
        ObjectOutputStream oos = null;
        BufferedReader br = null;
        try {
            socket = new Socket(IP,PORT);
            
            LOG.info("等待给服务端发送数据");
            oos = new ObjectOutputStream(socket.getOutputStream());
            
            //先读备份文件内容
            Object object = BackupUtil.read(file, BackupUtil.UN_DELETE);
            if (!Objects.isNull(object)) {
                //有内容，添加到集合中
                data.addAll((Collection<Enviroment>) object);
            }
            oos.writeObject(data);
            oos.flush();
            LOG.info("给服务端发送完成");
            
            socket.shutdownOutput();
            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line= null;
            line = br.readLine();
            LOG.info(line);
            
        } catch (IOException e) {
            //发生异常将数据备份到file文件
            BackupUtil.store(data, file, BackupUtil.COVER);
        }finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (oos != null) {
                    oos.close();
                }
                
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new EnviromentException(e.getMessage());
            }
        }
    }

}
