<template>
  <div>
    <el-card style="background-color: #fff5ee9f;">
      <el-date-picker
        v-model="choosedata"
        type="date"
        placeholder="请选择日期"
        style="margin-right: 30px;"
      >
      </el-date-picker>
      <el-select
        v-model="payvalue"
        placeholder="请选择缴费状态"
        style="margin-right: 30px;"
      >
        <el-option
          v-for="item in options"
          :key="item.payvalue"
          :label="item.label"
          :value="item.payvalue"
        >
        </el-option>
      </el-select>
      <el-select
        v-model="value"
        placeholder="请选择挂号状态"
        style="margin-right: 30px;"
      >
        <el-option
          v-for="item in options2"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        >
        </el-option>
      </el-select>
      <el-button
        style="font-size: 18px;margin-bottom: 30px;"
        @click="requestOrder"
      >
        搜索</el-button
      >
      <el-table :data="orderData" stripe style="width: 100%" border>
        <el-table-column
          prop="oId"
          label="挂号单号"
          width="75px"
        ></el-table-column>
        <el-table-column
          prop="pId"
          label="本人id"
          width="75px"
        ></el-table-column>
        <el-table-column
          prop="pName"
          label="姓名"
          width="75px"
        ></el-table-column>
        <el-table-column
          prop="dSection"
          label="科室"
          width="75px"
        ></el-table-column>

        <el-table-column
          prop="dName"
          label="医生姓名"
          width="75px"
        ></el-table-column>

        <el-table-column
          prop="oRegisterTime"
          label="挂号时间"
          width="195px"
        ></el-table-column>
        <el-table-column
          prop="oTotalPrice"
          label="需交费用/元"
          width="80px"
        ></el-table-column>
        <el-table-column prop="oPriceStatus" label="缴费状态" width="150">
          <template slot-scope="scope">
            <el-tag type="success" v-if="scope.row.oPriceStatus === 1"
              >已缴费</el-tag
            >
            <el-tag type="danger" v-if="scope.row.oPriceStatus === 0 && scope.row.oStatus === 1">未缴费</el-tag>
            <el-button
              type="warning"
              style="font-size: 14px"
              v-if="scope.row.oPriceStatus === 0 && scope.row.oStatus === 1"
              @click="priceClick(scope.row.oId, scope.row.dId)"
            >
              点击缴费</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="oStatus" label="挂号状态" width="100px">
          <template slot-scope="scope">
            <el-tag
              type="success"
              v-if="scope.row.oStatus === 1 && scope.row.oPriceStatus === 1"
              >已完成</el-tag
            >
            <el-tag
              type="danger"
              v-if="scope.row.oStatus === 0 && scope.row.oStatus === 0"
              >未完成</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="报告单" fixed="right" width="150">
          <template slot-scope="scope">
            <el-button
              type="success"
              style="font-size: 14px"
              @click="seeReport(scope.row.oId)"
              v-if="scope.row.oStatus === 1 && scope.row.oPriceStatus === 1"
            >
              查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>
<script>
import request from "@/utils/request.js";
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";
export default {
  name: "MyOrder",
  data() {
    return {
      options: [
        {
          payvalue: "1",
          label: "已缴费",
        },
        {
          payvalue: "0",
          label: "未缴费",
        },
      ],
      payvalue: "",
      options2: [
        {
          value: "1",
          label: "已挂号",
        },
        {
          value: "0",
          label: "未挂号",
        },
      ],
      value: "",
      userId: 1,
      orderData: [],
      star: 5,
      starVisible: false,
      dId: 1,
      dName: "",
      query: "",
      choosedata: "",
    };
  },
  methods: {
    //查看报告单
    seeReport(id) {
      window.location.href = "http://localhost:9281/patient/pdf?oId=" + id;
    },
    //点击缴费按钮
    priceClick(oId, dId) {
      request
        .get("order/updatePrice", {
          params: {
            oId: oId,
          },
        })
        .then((res) => {
          if (res.data.status !== 200) {
            this.$message.error("请求数据失败");
            return;
          }
          this.$message.success("单号 " + oId + " 缴费成功！");
          // request
          //   .get("admin/findDoctor", {
          //     params: {
          //       dId: dId,
          //     },
          //   })
          //   .then((res) => {
          //     if (res.data.status !== 200)
          //       return this.$message.error("请求数据失败");
          //     this.dId = res.data.data.dId;
          //     this.dName = res.data.data.dName;
          //   });
          // this.starVisible = true;
          this.requestOrder();
        });
    },
    //请求挂号信息
    requestOrder() {
      request
        .get("patient/findOrderByPid", {
          params: {
            pId: this.userId,
            choosedata: this.choosedata, //新增用户输入的日期choosedata，格式为yyyy-mm-dd，默认为''
            payvalue: this.payvalue, //新增用户输入的缴费状态payvalue，0表示未缴费,1表示已缴费，默认为''
            value: this.value, //新增用户输入的挂号状态value，0表示未挂号，1表示已挂号，默认为''
          },
        })
        .then((res) => {
          if (res.data.status !== 200) this.$message.error("请求数据失败");
          this.orderData = res.data.data;
          console.log(this.orderData.oId);
          console.log(this.orderData.pName);
          console.log(res);
        });
    },
    //token解码
    tokenDecode(token) {
      if (token !== null) return jwtDecode(token);
    },
  },
  created() {
    // 解码token
    //this.orderData.pName = this.tokenDecode(getToken()).pName;
    //this.orderData.pCard = this.tokenDecode(getToken()).pCard;
    this.userId = this.tokenDecode(getToken()).pId;
    console.log(this.orderData.pName);
    //this.orderData.pName = "dasda"
    this.requestOrder();
  },
};
</script>
<style lang="scss" scoped>
.el-dialog div {
  text-align: center;
  margin-bottom: 8px;
}
</style>
