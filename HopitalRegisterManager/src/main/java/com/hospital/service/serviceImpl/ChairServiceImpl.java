package com.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.mapper.ChairMapper;
import com.hospital.mapper.SurgeryMapper;
import com.hospital.pojo.Chair;
import com.hospital.pojo.Surgery;
import com.hospital.service.ChairService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("ChairService")
public class ChairServiceImpl implements ChairService {
    @Resource
    private ChairMapper chairMapper;
    @Resource
    private SurgeryMapper surgeryMapper;

    /**
     * 查找所有空椅位
     * @return
     */
    @Override
    public List<Chair> findNullChair(){
        QueryWrapper<Chair> wrapper = new QueryWrapper<>();
        //wrapper.select("c_id").eq("b_state", 0);
        wrapper.select("c_id");
        return this.chairMapper.selectList(wrapper);
    }

    /**
     * 增加椅位信息
     */
    @Override
    /**
     * 更新椅位信息
     */
    public Boolean updateChair(Chair chair){
        Chair chair1 = this.chairMapper.selectById(chair.getCId());
        this.chairMapper.updateById(chair);
        return true;
    }
    /**
     * 根据pId查询挂号
     */
    public List<Surgery> findChairByPid(int pId){
        QueryWrapper<Surgery> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id", pId);
        List<Surgery> surgeries=this.surgeryMapper.selectList(wrapper);
        return surgeries;
    }
    /**
     * 分页模糊查询所有检查信息
     */
    @Override
    public HashMap<String, Object> findAllChairs(int pageNumber, int size, String query) {
        Page<Chair> page = new Page<>(pageNumber, size);
        QueryWrapper<Chair> wrapper = new QueryWrapper<>();
        wrapper.like("c_id", query);
        IPage<Chair> iPage = this.chairMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("size", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("chairs", iPage.getRecords()); //查询到的记录
        return hashMap;
    }
    /**
     * 根据id查找检查
     */
    @Override
    public Chair findChair(int bId){
        return this.chairMapper.selectById(bId);
    }
    /**
     * 增加椅位信息
     */
    @Override
    public Boolean addChair(Chair chair){
        //如果账号已存在则返回false
        List<Chair> chairs = this.chairMapper.selectList(null);
        for (Chair chair1 : chairs) {
            if (chair1.getCId() == chair.getCId()) {
                return false;
            }
        }
       // chair.setCState(0);
        this.chairMapper.insert(chair);
        return true;
    }
    /**
     * 删除椅位信息
     */
    @Override
    public Boolean deleteChair(int bId) {
        this.chairMapper.deleteById(bId);
        return true;
    }
    /**
     * 清空椅位信息
     */
    public Boolean emptyChair(int bId){
        UpdateWrapper<Chair> wrapper = new UpdateWrapper<>();
        wrapper.set("p_id", -1).set("d_id", -1).set("b_reason", null).set("b_start", null).set("b_state", 0).eq("b_id", bId);
        this.chairMapper.update(null, wrapper);
        return true;

    }
    /**
     * 统计今天挂号人数
     */
    @Override
    public int chairPeople(String bStart){
        return this.chairMapper.chairPeople(bStart);
    }
}
