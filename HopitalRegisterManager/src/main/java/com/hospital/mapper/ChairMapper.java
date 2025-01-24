package com.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.pojo.Chair;

public interface ChairMapper extends BaseMapper<Chair> {
    /**
     * 统计今天手术人数
     */
    int chairPeople(String bStart);
}
