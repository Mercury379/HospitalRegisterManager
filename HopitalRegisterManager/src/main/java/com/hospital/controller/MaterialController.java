package com.hospital.controller;

import com.hospital.pojo.Admin;
import com.hospital.pojo.Doctor;
import com.hospital.pojo.Drug;
import com.hospital.service.*;
import com.hospital.utils.JwtUtil;
import com.hospital.utils.ResponseData;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;
    /**
     * 分页模糊查询所有材料信息
     */
    @RequestMapping("findAllMaterials")
    public ResponseData findAllMaterials(int pageNumber, int size, String query){
        return ResponseData.success("返回所有材料信息成功", this.materialService.findAllMaterial(pageNumber, size, query));
    }
    /**
     * 根据id查找材料
     */
    @RequestMapping("findMaterial")
    public ResponseData findMaterial(String mId){
        return ResponseData.success("根据id查找材料成功", this.materialService.findMaterial(mId));
    }

}
