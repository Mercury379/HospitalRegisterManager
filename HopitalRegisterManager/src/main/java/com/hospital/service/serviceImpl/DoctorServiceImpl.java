package com.hospital.service.serviceImpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.mapper.ArrangeMapper;
import com.hospital.mapper.DoctorMapper;
import com.hospital.pojo.Arrange;
import com.hospital.pojo.Doctor;
import com.hospital.service.DoctorService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

@Service("DoctorService")
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorMapper doctorMapper;

    @Resource
    private ArrangeMapper arrangeMapper;

    /**
     * 登录数据校验
     * */
    @Override
    public Doctor login(String dId, String dPassword){
        Doctor doctor = this.doctorMapper.selectById(dId);
        //String password = Md5Util.getMD5(dPassword);
        if (doctor == null) {
            return null;
        } else {
            if ((doctor.getDPassword()).equals(dPassword)) {
                return doctor;
            }
        }
        return null;
    }
    /**
     * 分页模糊查询所有医护人员信息
     */
    @Override
    public HashMap<String, Object> findAllDoctors(int pageNumber, int size, String query) {
        Page<Doctor> page = new Page<>(pageNumber, size);
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        IPage<Doctor> iPage = this.doctorMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("doctors", iPage.getRecords()); //查询到的记录
        return hashMap;
    }

    /**
     * 根据id查找医生
     */
    @Override
    public Doctor findDoctor(int dId) {
        return this.doctorMapper.selectById(dId);
    }

    /**
     * 增加医生信息
     */
    @Override
    public Boolean addDoctor(Doctor doctor) {
        //如果账号已存在则返回false
        List<Doctor> doctors = this.doctorMapper.selectList(null);
        for (Doctor doctor1 : doctors) {
            if (doctor.getDId() == doctor1.getDId()) {
                return false;
            }
        }
        this.doctorMapper.insert(doctor);
        return true;
    }

    /**
     * 删除医生信息
     */
    @Override
    public Boolean deleteDoctor(int dId) {
        Doctor doctor = new Doctor();
        doctor.setDId(String.valueOf(dId));
        //doctor.setDStatus("0");
        this.doctorMapper.updateById(doctor);
        return true;
    }

    /**
     * 修改医生信息
     */
    @Override
    public Boolean modifyDoctor(Doctor doctor) {
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("d_id", doctor.getDId());
//        this.doctorMapper.update(doctor, queryWrapper);
        int i = this.doctorMapper.updateById(doctor);
        System.out.println("影响行数："+i);
        return true;
    }
    /**
     * 根据科室查询所有医生信息
     */
    @Override
    public HashMap<String, Object> findDoctorBySection(String dSection){
//        HashMap<String, Object> hashMap = new HashMap<>();
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("d_section", dSection).eq("d_state", 1);
//        List<Doctor> doctors = this.doctorMapper.selectList(queryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("doctors", this.doctorMapper.findDoctorBySection(dSection));
        return map;

    }
    /**
     * 分页根据科室查询所有医生信息
     */
    @Override
    public HashMap<String, Object> findDoctorBySectionPage(int pageNumber, int size, String query, String arrangeDate, String dSection) {
        Page<Doctor> page = new Page<>(pageNumber, size);
        QueryWrapper<Doctor> wrapper = new QueryWrapper<>();
        wrapper.select("d_id", "d_name", "d_gender", "d_post", "d_section").like("d_name", query).eq("d_section", dSection);
        //wrapper.select("d_id", "d_name", "d_gender", "d_post", "d_section").like("d_name", query).eq("d_section", dSection).orderByDesc("d_state");
        IPage<Doctor> iPage = this.doctorMapper.selectPage(page, wrapper);
        List<Doctor> records = iPage.getRecords();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("doctors", records); //查询到的记录

        // 查询医生是否已排班
        for (Doctor doctor : records) {
            Arrange arrange = arrangeMapper.selectOne(
                    new QueryWrapper<Arrange>().eq("ar_time", arrangeDate).eq("d_id", doctor.getDId())
            );
            if(arrange != null) {
                doctor.setArrangeId(arrange.getArId());
            }

        }
        return hashMap;
    }
    /**
     * 重置医生密码
     */
    @Override
    public Boolean resetDoctorPassword(String dId) {
        boolean i = this.doctorMapper.updatePassword(dId);
        return true;
    }
    /**
     * 修改个人信息
     */
    @Override
    public int changeDoctorById(String dId, String dName, String dPhone, String dIntroduction) {
        return doctorMapper.changeDoctorById(dId,dName,dPhone,dIntroduction);
    }
    /**
     * 查询所有医生不分页
     */
    @Override
    public List<Doctor> findAll(){
        return this.doctorMapper.selectList(null);
    }
}
