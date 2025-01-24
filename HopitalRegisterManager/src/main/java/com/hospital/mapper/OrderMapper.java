package com.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hospital.pojo.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper extends BaseMapper<Orders> {
    /**
     * 统计今天排队人数
     */
    int orderPeople(String pId);
    /**
     * 签到
     */
    Boolean qiandao(@Param(value = "pId") String pId);

    /**
     * 统计今天某个医生挂号人数
     */
    int orderPeopleByDid(@Param("oStart") String oStart, @Param("dId") int dId);
    /**
     * 统计挂号男女人数
     */
    List<String> orderGender();
    /**
     * 根据挂号单号查询挂号
     */
    Orders findOrderByOid(int oId);
    /**
     * 增加诊断及医生意见
     */
    Integer updateOrderByAdd(Orders order);
    /**
     * 统计过去20天挂号科室人数
     */
    List<String> orderSection(@Param("startTime") String startTime, @Param("endTime") String endTime);
    /**
     * 查看当天挂号列表
     */
    List<Orders> findOrderByNull(@Param("dId") int dId, @Param("oStart") String oStart);
    /**
     * 查看当天历史列表
     */
    List<Orders> findOrderByDid(@Param("dId") int dId);
    /**
     * 根据pId查询挂号
     */
    List<Orders> findOrderByPid(int pId);


}
