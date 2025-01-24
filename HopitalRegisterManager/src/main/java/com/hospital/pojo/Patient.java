package com.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("patient")
public class Patient {
    @TableId(value = "p_id")
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pPassword")
    //@TableField(select = false)
    private String pPassword;
    @JsonProperty("pName")
    private String pName;
    @JsonProperty("pGender")
    private String pGender;
    @JsonProperty("pBirthday")
    private Date pBirthday;
    @JsonProperty("pCard")
    private String pCard;
    @JsonProperty("pPhone")
    private String pPhone;

    @TableField(exist = false)
    private int pAge;

    public int getpAge() {
        Calendar cal = Calendar.getInstance();
        if(pBirthday!=null) {
            return cal.get(Calendar.YEAR) - this.pBirthday.getYear() - 1900;
        }else{
            return 0;
        }
    }
}
