package com.hospital.service;

import com.hospital.pojo.Surgery;

import java.util.HashMap;

public interface SurgeryService {
    /**
     * 释放手术椅位：实际上是结束手术，填入结束手术时间
     */
    Boolean updateChair(String pId,String dId);
    Boolean releaseChair(String cId);
    HashMap<String, Object> findOrderFinishSurgery(int pageNumber, int size, String query, int dId) ;
}
