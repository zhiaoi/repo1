package com.briup.gateway.gathe;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.exception.ExceptionMessageEnum;
import com.briup.enviroment.pojo.Enviroment;
import com.briup.enviroment.utils.BackupUtil;
import com.briup.enviroment.utils.IdWorker;

public class GatherImpl implements IGather {
    
    private static final String SRC_PATH;
    private static final String SKIP_PATH;
    private static final String DATA_PATH;
    
    public static final Logger LOG = Logger.getLogger(GatherImpl.class);
    static {
        
//        Properties properties = BackupUtil.getPath(new File("src/main/resources/path.properties"), BackupUtil.UN_DELETE);
        Properties properties = BackupUtil.getPath(GatherImpl.class.getClassLoader().getResourceAsStream("path.properties"), BackupUtil.UN_DELETE);
        SRC_PATH = properties.getProperty("src_path");
        SKIP_PATH = properties.getProperty("skip_path");
        DATA_PATH = properties.getProperty("data_path");
        
        
    }
   
    @Override
    public Collection<Enviroment> gathe() throws EnviromentException {
        List<Enviroment> list = new ArrayList<>();
        RandomAccessFile raf =null;
        IdWorker idWorker = new IdWorker();
        
        try {
            raf = new RandomAccessFile(SRC_PATH, "r");
            
            List<Enviroment> listData =(List<Enviroment>) BackupUtil.read(new File(DATA_PATH), BackupUtil.UN_DELETE);
            if (!Objects.isNull(listData)) {
                
                list.addAll(listData);
            }
            Long skip = 0L;
            //读取跳过的字符数
            skip =(Long) BackupUtil.read(new File(SKIP_PATH), BackupUtil.UN_DELETE);
            skip = skip == null ? 0 : skip;
            raf.seek(skip);
            String line = null;
            while ((line = raf.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                }
               List<Enviroment> list2 = parse(line,idWorker);
               list.addAll(list2);
            }
            
        } catch (Exception e) {
          //发生异常时，备份读取的数据
            BackupUtil.store(list, new File(DATA_PATH), BackupUtil.COVER);
           
        }finally {
            try {
                //备份下一次开始读的位置
                BackupUtil.store(raf.getFilePointer(), new File(SKIP_PATH), BackupUtil.COVER);
                if (raf !=null) {
                    raf.close();
                }
            } catch (IOException e) {
              LOG.info("关闭流错误");
            }
        }
        return list;
    }
    
    public List<Enviroment> parse(String line, IdWorker id) {
        List<Enviroment> list =new ArrayList<>();
        Enviroment enviroment = new Enviroment();
        
        Enviroment enviroment2 =null;
        if (line == null || "".equals(line)) {
            throw new EnviromentException(ExceptionMessageEnum.PARM_IS_NULL.getMessage() );
        }
        String[] split = line.split("\\|");
        if (split.length >= 9) {
            enviroment.setId(id.nextId());
            enviroment.setSrcId(split[0]);
            enviroment.setSystemId(split[1]);
            enviroment.setRegionId(split[2]);
            enviroment.setDateType(split[3]);
            enviroment.setDataStatus(Byte.parseByte(split[5]));
            enviroment.setReviceStatus(Byte.parseByte(split[7]));
            enviroment.setDate(new Date(Long.parseLong(split[8])));
            
            //如果是16 前四位是温度数据,中间四位是湿度数据。如果不是16 前四位就是对应的数据
            if ("1280".equals(split[3])) {
                //1280代表CO2数据
                enviroment.setName("二氧化碳");
                String substring = split[6].substring(0,4);
                enviroment.setData(Integer.parseInt(substring, 16));
            }
            if ("256".equals(split[3])) {
                //256代表光 照强度数据
                enviroment.setName("光照强度");
                String substring = split[6].substring(0,4);
                enviroment.setData(Integer.parseInt(substring, 16));
            }
            if ("16".equals(split[3])) {
                //16代表温度和湿度数据
                //温度数据转换公式： ((float)value＊0.00268127)-46.85
                //湿度数据转换公式： ((float)value*0.00190735)-6
                enviroment.setName("温度");
                String substring1 = split[6].substring(0,4);
                enviroment.setData(Integer.parseInt(substring1, 16) * 0.00268127 - 46.85);
                
                if (split[6].length() >=  8) {
                    enviroment2 =new Enviroment();
                    enviroment2.setId(id.nextId());
                    enviroment2.setSrcId(split[0]);
                    enviroment2.setSystemId(split[1]);
                    enviroment2.setRegionId(split[2]);
                    enviroment2.setDateType(split[3]);
                    enviroment2.setDataStatus(Byte.parseByte(split[5]));
                    enviroment2.setReviceStatus(Byte.parseByte(split[7]));
                    enviroment2.setDate(new Date(Long.parseLong(split[8])));
                    String substring2 = split[6].substring(4,8);
                    enviroment2.setData(Integer.parseInt(substring2, 16) * 0.00190735 - 6);
                    enviroment2.setName("湿度");
                    list.add(enviroment2);
                }
               
            }
            
            list.add(enviroment);    
        }
        
        return list;
    }

}
