package com.briup.enviroment.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.Properties;

import com.briup.enviroment.exception.EnviromentException;
import com.briup.enviroment.exception.ExceptionMessageEnum;

public class BackupUtil {
    
    /**
     * 添加
     */
    public static boolean APPEND = true;
    /**
     * 覆盖
     */
    public static boolean COVER = false;
    /**
     * 删除
     */
    public static boolean DELETE = true;
    /**
     * 不删除
     */
    public static boolean UN_DELETE = false;
    /**
     * 将数据备份
     * @param object 备份的数据
     * @param file  备份的位置
     * @param append 文件是否删除
     */
    public static void store(Object object, File file, boolean append) {
        
        if (Objects.isNull(file)) {
            throw new EnviromentException(ExceptionMessageEnum.PARM_IS_NULL.getMessage());
        }
        
        ObjectOutputStream oos= null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            //将数据序列化到文件中
            oos = new ObjectOutputStream(new FileOutputStream(file,append));
            oos.writeObject(object);
            oos.flush();
            oos.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 读取备份数据
     * @param file 读取的文件
     * @param delete 是否将文件删除
     * @return  读取的数据
     */
    public static Object read(File file, boolean delete) {
        
        if (file == null || !file.exists()) {
            return null;
        }
        ObjectInputStream ois= null;
        Object object = null;
        try {
            //反序列化
            ois = new ObjectInputStream(new FileInputStream(file));
            object = ois.readObject();
            ois.close();
            if (delete) {
                file.delete();
            }
            return object;
        } catch (Exception e) {
            throw new EnviromentException(ExceptionMessageEnum.PARM_IS_NULL.getMessage());
        }
    }
    
    /**
     * 读取路径
     * @param file 存放路径的位置
     * @param delete 是否删除文件
     * @return
     */
    public static Properties getPath(File file, boolean delete) {
        
        try {
            //文件为空或者不存在抛出异常
            if (Objects.isNull(file) || !file.exists()) {
                
               throw new EnviromentException(ExceptionMessageEnum.PARM_IS_NULL.getMessage());
            }
            String name = file.getName();
            name = name.substring(name.lastIndexOf("."));
            Properties properties = new Properties();
            //文件.properties结尾
            if (Objects.equals(".properties", name)) {
                
                properties.load(new FileInputStream(file));
            }
            return properties;
        } catch (IOException e) {
            throw new EnviromentException(e.getMessage());
        }
        
    }
    
public static Properties getPath(InputStream is, boolean delete) {
        
        try {
            //文件为空或者不存在抛出异常
            if (Objects.isNull(is)) {
                
               throw new EnviromentException(ExceptionMessageEnum.PARM_IS_NULL.getMessage());
            }
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            throw new EnviromentException(e.getMessage());
        }
        
    }
}
