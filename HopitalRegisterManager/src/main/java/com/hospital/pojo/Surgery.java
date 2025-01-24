package com.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("surgery")
public class Surgery {
    @TableId(value = "s_id")
    @JsonProperty("sId")
    private String sId;
    @JsonProperty("oId")
    private String oId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("sPrestartTime")
    private String sPrestartTime;
    @JsonProperty("sStartTime")
    private String sStartTime;
    @JsonProperty("sPretime")
    private int sPretime;
    @JsonProperty("sEndTime")
    private String sEndTime;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("sMaterial")
    private String sMaterial;
    @JsonProperty("sItem")
    private String sItem;
    @JsonProperty("sDrug")
    private String sDrug;
    @JsonProperty("sTotalPrice")
    private double sTotalPrice;
    @JsonProperty("sPayTime")
    private String sPayTime;
    @JsonProperty("sPayStatus")
    private String sPayStatus;

    @JsonProperty("countGender")
    @TableField(exist = false)
    private Integer countGender;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Doctor doctor;
    //多表查询用
    @TableField(exist = false)//声明不是数据库里面的字段
    private Patient patient;
    @TableField(exist = false)//声明不是数据库里面的字段
    private Orders orders;
    @TableField(exist = false)
    private Integer countSection;
    @JsonProperty("dName")
    @TableField(exist = false)
    private String dName;
    @JsonProperty("pName")
    @TableField(exist = false)
    private String pName;
}
