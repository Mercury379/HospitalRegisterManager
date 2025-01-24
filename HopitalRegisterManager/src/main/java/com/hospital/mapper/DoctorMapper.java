package com.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.pojo.Doctor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface DoctorMapper extends BaseMapper<Doctor> {

    List<Doctor> findDoctorBySection(String dSection);
    Boolean updatePassword(@Param("dId")String dId);
    int changeDoctorById(@RequestParam(value = "dId") String dId, @RequestParam(value = "dName") String dName, @RequestParam(value = "dPhone") String dPhone, @RequestParam(value = "dIntroduction")String dIntroduction);
}
