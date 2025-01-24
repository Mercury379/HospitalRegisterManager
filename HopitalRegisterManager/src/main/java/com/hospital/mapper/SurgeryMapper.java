package com.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.pojo.Orders;
import com.hospital.pojo.Surgery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurgeryMapper extends BaseMapper<Surgery> {
    Integer releaseSurgery(@Param("cId")String cId);
    List<Surgery> findSurgeryByDid(@Param("dId") String dId);
}
