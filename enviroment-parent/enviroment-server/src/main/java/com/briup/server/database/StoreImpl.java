package com.briup.server.database;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.pojo.Enviroment;
import com.briup.enviroment.utils.BackupUtil;
import com.briup.server.utils.DBUtil;

public class StoreImpl implements IStore{

    public static final Logger LOG = Logger.getLogger(StoreImpl.class);
    private static final String STORE_PATH;
    static {
       Properties properties = BackupUtil.getPath(StoreImpl.class.getClassLoader().getResourceAsStream("storePath.properties"), BackupUtil.COVER);
       STORE_PATH = properties.getProperty("store_path");
    }
    @SuppressWarnings("unchecked")
    @Override
    public void store(Collection<Enviroment> data) throws EnviromentException {
        Connection connection = null;
        PreparedStatement ps = null;
        File file = new File(STORE_PATH);
        try {
            //获取连接
            connection = DBUtil.getConnection(DBUtil.UN_COMMIT);
            //读取备份文件
            Object object = BackupUtil.read(file, BackupUtil.UN_DELETE);
            if (!Objects.isNull(object)) {
                data.addAll((Collection<Enviroment>)object);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int count = 0;
            String day = "0";
            LOG.info("总共有"+data.size()+"条数据");
            if (!Objects.isNull(data) && data.size() != 0) {
                for (Enviroment enviroment : data) {
                    if (!Objects.isNull(ps)) {
                        ps.executeBatch();
                        ps.clearBatch();
                    }
                    if (!Objects.equals(day, getDay(sdf, enviroment.getDate()))) {
                        day = getDay(sdf, enviroment.getDate());
                        //预定义sql语句
                        String sql = "insert into e_data_"+ day +" values(?,?,?,?,?,?,?,?,?,?,?)";
                        //获取ps对象
                         ps = connection.prepareStatement(sql);
                    }
                    
                    count++;
                    ps.setLong(1, enviroment.getId());
                    ps.setString(2, enviroment.getSrcId());
                    ps.setString(3, enviroment.getSystemId());
                    ps.setString(4, enviroment.getRegionId());
                    ps.setString(5, enviroment.getDateType());
                    ps.setString(6, enviroment.getName());
                    ps.setInt(7, enviroment.getCount());
                    ps.setByte(8, enviroment.getDataStatus());
                    ps.setDouble(9, enviroment.getData());
                    ps.setByte(10, enviroment.getReviceStatus());
                    ps.setDate(11, enviroment.getDate());
                    ps.addBatch();
                    if (count % 500 == 0) {
                        ps.executeBatch();
                        ps.clearBatch();
                    }
                }
                ps.executeBatch();
                ps.clearBatch();
                connection.commit();
                LOG.info("存储了"+data.size()+"条数据");
            }
            
        } catch (Exception e) {
            //备份到文件
            BackupUtil.store(data, file, BackupUtil.COVER);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                LOG.info("数据库回滚错误");
            }
        }finally {
            //关闭流
            DBUtil.close(connection, ps, null);
        }
    }
    
    
    public String getDay(SimpleDateFormat sdf, Date date) {
        String string = sdf.format(date);
        return string.substring(string.lastIndexOf("-") + 1);
    }


}
