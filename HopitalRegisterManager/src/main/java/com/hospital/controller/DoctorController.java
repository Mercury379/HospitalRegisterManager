package com.hospital.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hospital.pojo.Doctor;
import com.hospital.pojo.Drug;
import com.hospital.pojo.Patient;
import com.hospital.service.DoctorService;
import com.hospital.service.OrderService;
import com.hospital.service.PatientService;
import com.hospital.utils.JwtUtil;
import com.hospital.utils.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PatientService patientService;
    /**
     * 登录数据验证
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData login(@RequestParam(value = "dId") String dId, @RequestParam(value = "dPassword") String dPassword) {
        Doctor doctor = this.doctorService.login(dId, dPassword);
        if (doctor != null) {
            Map<String,String> map = new HashMap<>();
            map.put("dName", doctor.getDName());
            map.put("dId", String.valueOf(doctor.getDId()));
            String token = JwtUtil.getToken(map);
            map.put("token", token);
            //response.setHeader("token", token);
            return ResponseData.success("登录成功", map);
        } else {
            return ResponseData.fail("登录失败，密码或账号错误");
        }
    }
    /**
     * 查看当天挂号列表
     */
    @RequestMapping("findOrderByNull")
    public ResponseData findOrderByNull(@Param(value = "dId") int dId, @RequestParam(value = "oRegisterTime") String oStart){
        System.out.println("账号时间为"+dId+oStart);
        return ResponseData.success("返回当天挂号信息成功", this.orderService.findOrderByNull(dId,oStart));

    }
    /**
     * 根据患者id查询患者信息
     */
    @RequestMapping("findPatientById")
    public ResponseData findPatientById(int pId){
        return ResponseData.success("返回患者信息成功！",
                this.patientService.findPatientById(pId));
    }
    /**
     * 修改患者信息
     */
    @RequestMapping("changePatientById")
    //@ResponseBody
    public ResponseData changePatientById(@RequestParam(value = "pId") String pId, @RequestParam(value = "pName") String pName,@RequestParam(value = "pPhone") String pPhone) {
        this.patientService.changePatientById(pId,pName,pPhone);
        return ResponseData.success("修改个人信息成功");
    }
    /**
     * 分页根据科室查询所有医生信息
     */
    @RequestMapping("findDoctorBySectionPage")
    public ResponseData findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection){
        return ResponseData.success("分页根据科室查询所有医生信息成功", this.doctorService.findDoctorBySectionPage(pageNumber, size, query, arrangeDate, dSection));
    }
}
