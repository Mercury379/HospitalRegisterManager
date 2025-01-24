package com.hospital.service;

import com.hospital.pojo.Chair;
import com.hospital.pojo.Chair;
import com.hospital.pojo.Surgery;

import java.util.HashMap;
import java.util.List;

public interface ChairService {
    /**
     * 查找所有空椅位
     */
    List<Chair> findNullChair();
    /**
     * 更新椅位信息
     */
    Boolean updateChair(Chair chair);
    /**
     * 根据pId查询手术信息
     */
    List<Surgery> findChairByPid(int pId);
    /**
     * 分页模糊查询所有椅位信息
     */
    HashMap<String, Object> findAllChairs(int pageNumber, int size, String query);
    /**
     * 根据id查找椅位
     */
    Chair findChair(int bId);
    /**
     * 增加椅位信息
     */
    Boolean addChair(Chair chair);
    /**
     * 删除椅位信息
     */
    Boolean deleteChair(int bId);
    /**
     * 清空椅位信息
     */
    Boolean emptyChair(int bId);
    /**
     * 统计今天手术人数
     */
    int chairPeople(String bStart);
}
