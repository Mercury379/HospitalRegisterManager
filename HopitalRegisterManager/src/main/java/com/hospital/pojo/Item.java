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
@TableName(value = "item")
public class Item {
    @TableId(value = "i_id")
    @JsonProperty("iId")
    private String iId;
    @JsonProperty("iName")
    private String iName;
    @JsonProperty("iPrice")
    private double iPrice;
    @JsonProperty("iType")
    private String iType;
}
