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
@TableName(value = "material")
public class Material {
    @TableId(value = "m_id")
    @JsonProperty("mId")
    private String mId;
    @JsonProperty("mName")
    private String mName;
    @JsonProperty("mPrice")
    private double mPrice;
    @JsonProperty("mUnit")
    private String mUnit;
    @JsonProperty("mNumber")
    private double mNumber;
    @JsonProperty("mPublisher")
    private String mPublisher;
    @JsonProperty("mType")
    private String mType;
}
