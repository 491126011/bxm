package cn.kiway.bxm.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018.
 */
 @Data
public class User implements Serializable{
    private String password;
    private String id;
    private String userName;
    private Long type;
    private java.sql.Timestamp createDate;
}
