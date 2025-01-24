package com.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.pojo.Patient;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface PatientMapper extends BaseMapper<Patient> {
    /**
     * 统计患者男女人数
     */
    Integer patientAge(@Param("startAge") int startAge, @Param("endAge") int endAge);

    int changePatientById(@Param("pId") String pId, @Param("pName") String pName, @Param("pPhone") String pPhone);
}
