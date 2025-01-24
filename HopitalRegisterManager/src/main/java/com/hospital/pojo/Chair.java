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
@TableName("chair")
public class Chair {
    @TableId(value = "c_id")
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cSection")
    private String cSection;
    @JsonProperty("cArea")
    private String cArea;
    @JsonProperty("cOrder")
    private int cOrder;
    @JsonProperty("cLocation")
    private String cLocation;
}
