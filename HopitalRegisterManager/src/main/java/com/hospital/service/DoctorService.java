package com.hospital.service;

import com.hospital.pojo.Doctor;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface DoctorService {
    /**
     * 登录数据校验
     * */
    Doctor login(String dId, String dPassword);
    /**
     * 分页模糊查询所有医生信息
     */
    HashMap<String, Object> findAllDoctors(int pageNumber, int size, String query);
    /**
     * 根据id查找医生
     */
    Doctor findDoctor(int dId);
    /**
     * 增加医生信息
     */
    Boolean addDoctor(Doctor doctor);
    /**
     * 删除医生信息
     */
    Boolean deleteDoctor(int dId);
    /**
     * 修改医生信息
     */
    Boolean modifyDoctor(Doctor doctor);
    /**
     * 根据科室查询所有医生信息
     */
     HashMap<String, Object> findDoctorBySection(String dSection);
    /**
     * 分页根据科室查询所有医生信息
     */
    HashMap<String, Object> findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection);
    int changeDoctorById(String dId, String dName, String dPhone, String dIntroduction);
    /**
     * 查询所有医生不分页
     */
    List<Doctor> findAll();
    /**
     * 重置医生密码
     */
    Boolean resetDoctorPassword(String dId);

}
