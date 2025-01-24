package com.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.mapper.DrugMapper;
import com.hospital.mapper.MaterialMapper;
import com.hospital.pojo.Drug;
import com.hospital.pojo.Material;
import com.hospital.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
@Service("materialService")
public class MaterialServiceImpl implements MaterialService {
    @Resource
    private MaterialMapper materialMapper;
    @Override
    public HashMap<String, Object> findAllMaterial(int pageNumber, int size, String query) {
        Page<Material> page = new Page<>(pageNumber, size);
        QueryWrapper<Material> wrapper = new QueryWrapper<>();
        wrapper.like("m_name", query);
        IPage<Material> iPage = this.materialMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("size", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("material", iPage.getRecords()); //查询到的记录
        return hashMap;
    }

    @Override
    public Material findMaterial(String mId) {
        return this.materialMapper.selectById(mId);
    }
}
