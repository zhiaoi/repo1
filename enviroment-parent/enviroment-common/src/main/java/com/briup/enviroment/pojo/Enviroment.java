package com.briup.enviroment.pojo;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Enviroment implements Serializable, Cloneable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 环境数据编号
     */
    private long id;
    /**
     * 发送端id
     */
    private String srcId; 
    /**
     * 树莓派id
     */
    private String systemId;
    /**
     *试验箱区域id
     */
    private String regionId;
    /**
     * 数据类型
     */
    private String dateType;
    /**
     * 数据类型名 
     *     二氧化碳
     *     光照强度
     *     湿度
     *     温度
     */
    private String name;
    /**
     * 传感器个数
     */
    private int count;
    /**
     * 数据状态
     *      3代表接收数据
     *      6代表发送数据
     */
    private byte dataStatus;
    /**
     * 环境数据
     */
    private double data;
    /**
     * 接收数据的标志
     *      1代表成功
     */
    private byte reviceStatus;
    /**
     * 传感器采集数据时间
     */
    private Date date;
    
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
