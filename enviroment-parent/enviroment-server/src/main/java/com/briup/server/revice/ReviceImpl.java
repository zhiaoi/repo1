package com.briup.server.revice;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;
import com.briup.enviroment.utils.BackupUtil;
import com.briup.server.database.IStore;
import com.briup.server.database.StoreImpl;

public class ReviceImpl implements IRevicer {

    public static final Logger LOG = Logger.getLogger(ReviceImpl.class);
    private static final Integer PORT;
    static {
        Properties properties = BackupUtil.getPath(ReviceImpl.class.getClassLoader().getResourceAsStream("storePath.properties"), BackupUtil.UN_DELETE);
        PORT = Integer.parseInt(properties.getProperty("port"));
    }
    @SuppressWarnings({ "unchecked", "resource" })
    @Override
    public void revice() throws EnviromentException {
        ExecutorService pool = Executors.newCachedThreadPool();
        try {
            // 创建服务端
            ServerSocket serverSocket= new ServerSocket(PORT);
            LOG.info("等待客户端发送过来的数据");
            while (true) {
                // 获取客户端
                Socket socket = serverSocket.accept();//放到execute（）外面
                pool.execute(()->{
                    
                    ObjectInputStream ois = null;
                    PrintWriter pw = null;
                    try {
                        // 读取客户端发送过来的数据
                        ois = new ObjectInputStream(socket.getInputStream());
                        List<Enviroment> dataList = (List<Enviroment>) ois.readObject();
                        IStore storeImpl = new StoreImpl();
                        storeImpl.store(dataList);
                        // 关闭读取流
                        socket.shutdownInput();
                        // 给客户端发送数据
                        pw = new PrintWriter(socket.getOutputStream());
                        pw.write("服务端接收成功");
                        pw.flush();
                        pw.close();
                        ois.close();
                        socket.close();
                    }  catch (Exception e) {
                        throw new EnviromentException(e.getMessage());
                    }
                });
               
            }
        } catch (Exception e) {
            throw new EnviromentException(e.getMessage());
        } 

    }

}
