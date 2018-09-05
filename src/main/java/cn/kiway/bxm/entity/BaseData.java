package cn.kiway.bxm.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018.
 */
 @Data
 @TableName("basedata")
public class BaseData implements Serializable{
    private String name;
    private String id;
    private String type;
    private Double delFlag;
    private String value;
    private String parentId;
    private java.sql.Timestamp createDate;
}
