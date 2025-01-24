package com.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("orders")
public class Orders {
    @TableId(value = "o_id")
    @JsonProperty("oId")
    private String oId;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("oRegisterTime")
    private String oRegisterTime;
    @JsonProperty("oSignTime")
    private Date oSignTime;
    @JsonProperty("oStatus")
    private int oStatus;
    @JsonProperty("oRecord")
    private String oRecord;
    @JsonProperty("oDiagnosis")
    private String oDiagnosis;
    @JsonProperty("oAdvice")
    private String oAdvice;
    @JsonProperty("oDrug")
    private String oDrug;
    @JsonProperty("oItem")
    private String oItem;
    @JsonProperty("oMaterial")
    private String oMaterial;
    @JsonProperty("oTotalPrice")
    private double oTotalPrice;
    @JsonProperty("oPriceStatus")
    private int oPriceStatus;
    @JsonProperty("oPayTime")
    private Date oPayTime;

    @JsonProperty("countGender")
    @TableField(exist = false)
    private Integer countGender;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Doctor doctor;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Patient patient;
    @TableField(exist = false)
    private Integer countSection;
    @JsonProperty("dName")
    @TableField(exist = false)
    private String dName;
    @JsonProperty("dSection")
    @TableField(exist = false)
    private String dSection;
    @JsonProperty("pName")
    @TableField(exist = false)
    private String pName;

}
