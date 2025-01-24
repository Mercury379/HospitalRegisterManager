package com.hospital.controller;

import com.hospital.pojo.Chair;
import com.hospital.pojo.Chair;
import com.hospital.service.ChairService;
import com.hospital.service.SurgeryService;
import com.hospital.utils.ResponseData;
import com.hospital.utils.TodayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chair")
public class ChairController {
    @Autowired
    private ChairService chairService;
    @Autowired
    private SurgeryService surgeryService;
    /**
     * 查找所有空椅位
     */
    @RequestMapping("findNullChair")
    public ResponseData findNullChair(){
        return ResponseData.success("查找所有空椅位成功", this.chairService.findNullChair());
    }

    /**
     * 增加椅位信息
     */
    @RequestMapping("updateChair")
    public ResponseData updateChair(Chair chair) {
        if (this.chairService.updateChair(chair))
            return ResponseData.success("增加椅位成功！");
        return ResponseData.fail("增加椅位失败！");
    }
    /**
     * 根据pId查询手术
     */
    @RequestMapping("findChairByPid")
    public ResponseData findChairByPid(@RequestParam(value = "pId") int pId){
        return ResponseData.success("根据pId查询手术成功", this.chairService.findChairByPid(pId)) ;
    }
    /**
     * 分页模糊查询所有椅位信息
     */
    @RequestMapping("findAllChairs")
    public ResponseData findAllChairs(int pageNumber, int size, String query){
        return ResponseData.success("返回所有椅位信息成功", this.chairService.findAllChairs(pageNumber, size, query));
    }
    /**
     * 根据id查找椅位
     */
    @RequestMapping("findChair")
    public ResponseData findChair(int bId){
        return ResponseData.success("根据id查找椅位成功", this.chairService.findChair(bId));
    }
    /**
     * 增加椅位信息
     */
    @RequestMapping("addChair")
    @ResponseBody
    public ResponseData addChair(Chair chair) {
        Boolean bo = this.chairService.addChair(chair);
        if (bo) {
            return ResponseData.success("增加椅位信息成功");
        }
        return ResponseData.fail("增加椅位信息失败！椅号或已被占用");
    }
    /**
     * 删除药物信息
     */
    @RequestMapping("deleteChair")
    public ResponseData deleteChair(@RequestParam(value = "bId") int bId) {
        Boolean bo = this.chairService.deleteChair(bId);
        if (bo){
            return ResponseData.success("删除椅位信息成功");
        }
        return ResponseData.fail("删除椅位信息失败");
    }
    /**
     * 清空椅位信息
     */
    @RequestMapping("emptyChair")
    public ResponseData emptyChair(int bId){
        if(this.chairService.emptyChair(bId))
            return ResponseData.success("清空椅子位信息成功");
        return ResponseData.fail("清空椅位信息失败");
    }
    /**
     * 统计今天挂号人数
     */
    @RequestMapping("chairPeople")
    public ResponseData chairPeople(){
        String bStart = TodayUtil.getTodayYmd();
        return ResponseData.success("统计今天手术人数成功", this.chairService.chairPeople(bStart));
    }
    /**
     * 释放椅位
     */
    @RequestMapping("releaseChair")
    public ResponseData releaseChair(@RequestParam(value = "cId") String cId) {
        if (this.surgeryService.releaseChair(cId))
            return ResponseData.success("释放手术椅成功！");
        return ResponseData.fail("释放手术椅失败！");
    }
}
