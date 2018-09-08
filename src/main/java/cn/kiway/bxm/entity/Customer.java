package cn.kiway.bxm.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * Created by ym on Tue Sep 04 15:40:31 CST 2018.
 */
 @Data
public class Customer implements Serializable{
    private String money;
    @NotBlank
    @ApiParam("手机号")
    @Pattern(regexp="^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$",message="手机号码错误！")
    private String phone;
    @ApiParam(hidden = true)
    private Long level = 1L;
    @NotBlank
    @ApiParam("身份证")
    @Pattern(regexp="(^(\\d{14}|\\d{17})(\\d|[xX])$)?" ,message="身份证号码错误！")
    private String idCard;
    @ApiParam("银行卡号")
    @Pattern(regexp="^\\d{19}$" ,message="银行卡号错误！")
    private String bankCard;
    @ApiParam("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;
    @ApiParam(hidden = true)
    private String id;
    @ApiParam(hidden = true)
    private java.sql.Timestamp createDate;
}
