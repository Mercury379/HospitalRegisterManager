<template>
  <div>
    <el-card style="background-color: #fff5ee9f;">
      <!-- 搜索栏 -->
      <el-row type="flex">
        <el-col :span="6">
          <el-input v-model="query" placeholder="请输入患者姓名查询">
            <el-button
              slot="append"
              icon="iconfont icon-r-find"
              @click="requestOrders"
            ></el-button>
          </el-input>
        </el-col>
      </el-row>
      <el-table :data="orderData" stripe border>
        <el-table-column label="挂号单号" prop="oId"></el-table-column>
        <el-table-column label="患者id" prop="pId"></el-table-column>
        <el-table-column label="医生id" prop="dId"></el-table-column>
        <el-table-column label="椅子号" prop="cId"></el-table-column>

        <!-- <el-table-column label="医生姓名" prop="dName"></el-table-column> -->
        <el-table-column
          label="手术预计开始时间"
          prop="sPrestartTime"
        ></el-table-column>
        <el-table-column label="手术预计时长" prop="sPretime"></el-table-column>
        <el-table-column
          label="手术开始时间"
          prop="sStartTime"
        ></el-table-column>
        <el-table-column
          label="手术结束时间时间"
          prop="sEndTime"
        ></el-table-column>
        <!-- <el-table-column label="挂号状态" prop="oStatus">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.oStatus === 1" type="success"
              >已完成</el-tag
            >
            <el-tag v-if="scope.row.oStatus === 0" type="danger">未完成</el-tag>
          </template>
        </el-table-column> -->
        <!-- 分页 -->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          background
          layout="total, sizes, prev, pager, next, jumper"
          :current-page="pageNumber"
          :page-size="size"
          :page-sizes="[1, 2, 4, 8, 16]"
          :total="total"
        >
        </el-pagination>
        <el-table-column label="操作" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button
              type="warning"
              style="font-size: 18px"
              @click="ChairDiag(scope.row.pId, scope.row.dId)"
              v-if="scope.row.sPrestartTime === null"
            >
              申请手术椅</el-button
            >
          </template>
        </el-table-column>

        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <el-button
              type="warning"
              style="font-size: 18px"
              @click="ChairDiag(scope.row.pId, scope.row.dId)"
            >
              开始</el-button
            >
            <el-button
              type="warning"
              style="font-size: 18px"
              @click="ChairDiag2(scope.row.pId, scope.row.dId)"
            >
              结束</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 手术对话框 -->
    <el-dialog title="释放手术椅" :visible.sync="ChairFormVisible">
      <el-form class="findPassword" :model="chairForm">
        <el-form-item label="患者账号" label-width="80px" prop="pId">
          <el-input v-model="chairForm.pId" disabled></el-input>
        </el-form-item>
        <el-form-item label="医生账号" label-width="80px">
          <el-input v-model="chairForm.dId" disabled></el-input>
        </el-form-item>
        <el-form-item label="椅子号" label-width="80px">
          <el-input v-model="chairForm.cId" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="ChairFormVisible = false" style="font-size: 18px;">
          取 消</el-button
        >
        <el-button type="primary" @click="chairClick" style="font-size: 18px;">
          确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";

import request from "@/utils/request.js";
export default {
  name: "InChair",
  data() {
    return {
      userId: 1,
      userName: "",
      pageNumber: 1,
      size: 4,
      query: "",
      total: 3,
      orderData: [],
      //申请手术对话框
      ChairFormVisible: false,
      chairForm: {},
      nullChair: [],
    };
  },
  methods: {
    //开始手术
    ChairDiag(p, d){
      request
        .get("chair/ChairDiag", {
          params: {
            pId: p,
            dId: d,
          },
        })
        .then((res) => {
          if (res.data.status !== 200) return this.$message.error("error");
          this.$message.success("手术开始！");
          this.requestOrders();
        });
    },
    //结束手术
    ChairDiag2(p, d){
      request
        .get("chair/ChairDiag2", {
          params: {
            pId: p,
            dId: d,
          },
        })
        .then((res) => {
          if (res.data.status !== 200) return this.$message.error("error");
          this.$message.success("手术结束！");
          this.requestOrders();
        });
    },
    //点击释放椅子位确认按钮
    chairClick() {
      request
        .get("chair/updateChair", {
          params: {
            cId: this.chairForm.cId,
          },
        })
        .then((res) => {
          if (res.data.status !== 200) return this.$message.error("释放失败");
          this.ChairFormVisible = false;
          this.$message.success("释放手术椅成功！");
          this.requestOrders();
          console.log(res);
        });
    },
    //打开释放手术椅对话框
    ChairDiag(pId, dId, cId) {
      this.chairForm.pId = pId;
      this.chairForm.dId = dId;
      this.chairForm.cId = cId;
      this.ChairFormVisible = true;
      this.requestChairs();
    },
    //页面大小改变时触发
    handleSizeChange(size) {
      console.log(size);
      this.size = size;
      this.requestOrders();
    },
    //   页码改变时触发
    handleCurrentChange(num) {
      console.log(num);
      this.pageNumber = num;
      this.requestOrders();
    },
    //query改为用户输入的姓名，进行模糊查询，默认为''
    requestOrders() {
      request
        .get("order/findOrderFinishSurgery", {
          params: {
            dId: this.userId,
            pageNumber: this.pageNumber,
            size: this.size,
            query: this.query,
          },
        })
        .then((res) => {
          if (res.data.status !== 200)
            return this.$message.error("数据请求失败");
          this.orderData = res.data.data.orders;
          this.total = res.data.data.total;
        });
    },
    //token解码
    tokenDecode(token) {
      if (token !== null) return jwtDecode(token);
    },
  },
  created() {
    //解码token信息
    this.userId = this.tokenDecode(getToken()).dId;
    this.userName = this.tokenDecode(getToken()).dName;
    console.log(this.userId);
    console.log(this.userName);
    //获取订单信息
    this.requestOrders();
  },
};
</script>
<style lang="scss" scoped>
.el-table {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
