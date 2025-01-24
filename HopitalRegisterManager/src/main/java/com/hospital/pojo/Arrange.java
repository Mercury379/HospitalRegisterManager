package com.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("arrange")
public class Arrange {
    @TableId(value = "ar_id")
    @JsonProperty("arId")
    private String arId;
    @JsonProperty("arTime")
    private java.sql.Date arTime;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    /**
     * 多表查询用
     */
    @TableField(exist = false)
    private Doctor doctor;
}
