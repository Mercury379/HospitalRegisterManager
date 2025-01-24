package com.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.mapper.SurgeryMapper;
import com.hospital.pojo.Orders;
import com.hospital.pojo.Surgery;
import com.hospital.service.SurgeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("SurgeryService")
public class SurgeryServiceImpl implements SurgeryService {
    @Resource
    private SurgeryMapper surgeryMapper;
    @Override
    public Boolean releaseChair(String cId) {
        Integer integer=this.surgeryMapper.releaseSurgery(cId);
        System.out.println(integer);
        return true;
    }

    @Override
    public HashMap<String, Object> findOrderFinishSurgery(int pageNumber, int size, String query, int dId) {
        Page<Surgery> page = new Page<>(pageNumber, size);
        List<Surgery> surgeries=surgeryMapper.findSurgeryByDid("0000000"+String.valueOf(dId));
        QueryWrapper<Surgery> wrapper = new QueryWrapper<>();
        wrapper.like("p_id", query).eq("d_id", dId);
        IPage<Surgery> iPage = this.surgeryMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("pages", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("orders", surgeries); //查询到的记录

        return hashMap;
    }

    @Override
    public Boolean updateChair(String pId, String dId) {
        /**
         * 用QueryWrapper如果不把外键的值也传进来，会报错
         * 用UpdateWrapper就正常
         */
        UpdateWrapper<Surgery> wrapper = new UpdateWrapper<>();
        wrapper.eq("p_id", pId).eq("d_id",dId).set("o_price_status", 1);
        int i = this.surgeryMapper.update(null, wrapper);
        System.out.println("影响行数"+i);
        return true;
    }
}
