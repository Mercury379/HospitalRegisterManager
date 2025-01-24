package com.hospital.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital.mapper.ItemMapper;
import com.hospital.pojo.Item;
import com.hospital.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Service("ItemService")
public class ItemServiceImpl implements ItemService {

    @Autowired
    @Resource
    private ItemMapper itemMapper;
    /**
     * 分页模糊查询所有检查信息
     */
    @Override
    public HashMap<String, Object> findAllChecks(int pageNumber, int size, String query) {
        Page<Item> page = new Page<>(pageNumber, size);
        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.like("i_name", query);
        IPage<Item> iPage = this.itemMapper.selectPage(page, wrapper);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("total", iPage.getTotal());       //总条数
        hashMap.put("size", iPage.getPages());       //总页数
        hashMap.put("pageNumber", iPage.getCurrent());//当前页
        hashMap.put("checks", iPage.getRecords()); //查询到的记录
        return hashMap;
    }
    /**
     * 根据id查找检查
     * @return
     */
    @Override
    public Item findCheck(int chId){
        return this.itemMapper.selectById(chId);
    }
    /**
     * 增加检查信息
     */
    @Override
    public Boolean addCheck(Item checks){
        //如果账号已存在则返回false
        List<Item> checks1 = this.itemMapper.selectList(null);
        for (Item checks2 : checks1) {
            if (checks.getIId() == checks2.getIId()) {
                return false;
            }
        }
        this.itemMapper.insert(checks);
        return true;
    }
    /**
     * 删除检查信息
     */
    @Override
    public Boolean deleteCheck(int chId) {
        this.itemMapper.deleteById(chId);
        return true;
    }
    /**
     * 修改检查信息
     */
    @Override
    public Boolean modifyCheck(Item checks) {
        int i = this.itemMapper.updateById(checks);
        System.out.println("影响行数："+i);
        return true;
    }
}


