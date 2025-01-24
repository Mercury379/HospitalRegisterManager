package com.hospital.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;
import java.time.DateTimeException;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("doctor")
public class Doctor implements Serializable {
    @TableId(value = "d_id")
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("dPassword")
    private String dPassword;
    @JsonProperty("dName")
    private String dName;
    @JsonProperty("dGender")
    private String dGender;
    @JsonProperty("dCard")
    private String dCard;
    @JsonProperty("dPhone")
    private String dPhone;
    @JsonProperty("dPost")
    private String dPost;
    @JsonProperty("dIntroduction")
    private String dIntroduction;
    @JsonProperty("dSection")
    private String dSection;
    @JsonProperty("dArea")
    private String dArea;
    @JsonProperty("dPrice")
    private double dPrice;
    private Date dBirthday;
    /**
     * 是否已排班，排班id
     */
    @TableField(exist = false)
    private String arrangeId;
    @TableField(exist = false)
    private Patient patient;
}
