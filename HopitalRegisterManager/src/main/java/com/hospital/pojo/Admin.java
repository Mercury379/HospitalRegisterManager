package com.hospital.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("admin")
public class Admin {
    @TableId(value = "a_id")
    @JsonProperty("aId")
    private String aId;
    @JsonProperty("aPassword")
    private String aPassword;
    @JsonProperty("aName")
    private String aName;
    @JsonProperty("aGender")
    private String aGender;
    @JsonProperty("aCard")
    private String aCard;
    @JsonProperty("aPhone")
    private String aPhone;
}
