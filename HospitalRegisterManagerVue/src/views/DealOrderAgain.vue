<template>
  <div>
    <el-card>
      <el-row>
        <el-col :span="7">
          挂号单号：<el-input
            disabled
            v-model="oId"
            class="orderInput"
          ></el-input>
        </el-col>
        <el-col :span="7">
          患者账号：<el-input
            disabled
            v-model="pId"
            class="orderInput"
          ></el-input
        ></el-col>
        <el-col :span="7">
          患者姓名：<el-input
            disabled
            v-model="pName"
            class="orderInput"
          ></el-input>
        </el-col>
        <el-col :span="3">
          <el-button
            type="success"
            style="font-size: 18px;"
            icon="iconfont icon-r-paper"
            @click="submitClick"
          >
            提交</el-button
          >
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="7">
          患者性别：<el-input
            disabled
            v-model="pGender"
            class="orderInput"
          ></el-input>
        </el-col>
        <el-col :span="7">
          联系方式：<el-input
            disabled
            v-model="pPhone"
            class="orderInput"
          ></el-input
        ></el-col>
        <el-col :span="7">
          医生姓名：<el-input
            disabled
            v-model="dName"
            class="orderInput"
          ></el-input>
        </el-col>
        <el-col :span="3">
          <el-button
            type="success"
            style="font-size: 18px;"
            @click="openAdvice"
          >
            <i class="iconfont icon-r-add" style="font-size: 20px;"></i>
            诊断/意见</el-button
          >
        </el-col>
      </el-row>
      <!-- 药物表格 -->
      <el-row>
        <el-col :span="12">
          <el-input
            v-model="queryDrug"
            placeholder="请输入名称查询"
            class="drugInput"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="requestDrug"
            ></el-button>
          </el-input>
          <el-table :data="drugData" stripe border>
            <el-table-column label="编号" prop="drId"></el-table-column>
            <el-table-column label="名称" prop="drName"></el-table-column>
            <el-table-column label="剩余数量" prop="drNumber"></el-table-column>
            <el-table-column label="单位" prop="drUnit"></el-table-column>
            <el-table-column label="单价" prop="drPrice"></el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  style="font-size: 14px;"
                  @click="addDrug(scope.row.drId)"
                >
                  <i class="iconfont icon-r-add" style="font-size: 16px;"></i>
                  增加</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            background
            layout="total, sizes, prev, pager, next"
            :total="total"
            :page-size="size"
            :page-sizes="[1, 2, 4, 8, 16]"
          >
          </el-pagination>
          <el-row></el-row>
        </el-col>
        <!-- 右边已选择的药物 -->
        <el-col :span="12" class="drugRigth">
          <el-table stripe border :data="drugBuyData" class="rigthTable">
            <el-table-column label="编号" prop="drId"></el-table-column>
            <el-table-column label="名称" prop="drName"></el-table-column>
            <el-table-column label="单价" prop="drPrice"></el-table-column>
            <el-table-column label="数量" prop="drNum"></el-table-column>
            <el-table-column label="小记" prop="drSum"></el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  style="font-size: 14px;"
                  icon="iconfont icon-r-delete"
                  @click="deleteDrug(scope.row.drId)"
                >
                  移除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-tag>共计：{{ drugTotalPrice }}元</el-tag>
        </el-col>
      </el-row>
      <!-- 检查项目编写 -->
      <el-row>
        <el-col :span="12">
          <el-input
            v-model="queryCheck"
            placeholder="请输入名称查询"
            class="drugInput"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="requestCheck"
            ></el-button>
          </el-input>
          <el-table stripe border :data="checkData">
            <el-table-column label="编号" prop="iId"></el-table-column>
            <el-table-column label="项目" prop="iName"></el-table-column>
            <el-table-column label="价格" prop="iPrice"></el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  style="font-size: 14px;"
                  @click="addCheck(scope.row.iId)"
                >
                  <i class="iconfont icon-r-add" style="font-size: 16px;"></i>
                  增加</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            @size-change="checkSizeChange"
            @current-change="checkCurrentChange"
            background
            layout="total, prev, pager, next"
            :total="checkTotal"
            :page-size="checkSize"
          >
          </el-pagination>
        </el-col>
        <!-- 右边已选择的检查 -->
        <el-col :span="12" class="drugRigth">
          <el-table stripe border class="rigthTable" :data="checkBuyData">
            <el-table-column label="编号" prop="iId"></el-table-column>
            <el-table-column label="项目" prop="iName"></el-table-column>
            <el-table-column label="价格" prop="iPrice"></el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  style="font-size: 14px;"
                  icon="iconfont icon-r-delete"
                  @click="deleteCheck(scope.row.iId)"
                >
                  移除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-tag>共计：{{ checkTotalPrice }}元</el-tag>
        </el-col>
      </el-row>
      <!-- 材料 -->
      <el-row>
        <el-col :span="12">
          <el-input
            v-model="queryMaterial"
            placeholder="请输入名称查询"
            class="drugInput"
          >
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="requestMaterial"
            ></el-button>
          </el-input>
          <el-table :data="materialData" stripe border>
            <el-table-column label="编号" prop="mId"></el-table-column>
            <el-table-column label="名称" prop="mName"></el-table-column>
            <el-table-column label="剩余数量" prop="mNumber"></el-table-column>
            <el-table-column label="单位" prop="mUnit"></el-table-column>
            <el-table-column label="单价" prop="mPrice"></el-table-column>
            <el-table-column label="操作" width="140" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="success"
                  style="font-size: 14px;"
                  @click="addMaterial(scope.row.mId)"
                >
                  <i class="iconfont icon-r-add" style="font-size: 16px;"></i>
                  增加</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            background
            layout="total, sizes, prev, pager, next"
            :total="materialtotal"
            :page-size="materialsize"
            :page-sizes="[1, 2, 4, 8, 16]"
          >
          </el-pagination>
          <el-row></el-row>
        </el-col>
        <!-- 右边已选择的材料 -->
        <el-col :span="12" class="drugRigth">
          <el-table stripe border :data="materialBuyData" class="rigthTable">
            <el-table-column label="编号" prop="mId"></el-table-column>
            <el-table-column label="名称" prop="mName"></el-table-column>
            <el-table-column label="单价" prop="mPrice"></el-table-column>
            <el-table-column label="数量" prop="mNum"></el-table-column>
            <el-table-column label="小记" prop="mSum"></el-table-column>
            <el-table-column label="操作" width="140" fixed="right">
              <template slot-scope="scope">
                <el-button
                  type="danger"
                  style="font-size: 14px;"
                  icon="iconfont icon-r-delete"
                  @click="deleteMaterial(scope.row.mId)"
                >
                  移除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
          <el-tag>共计：{{ materialTotalPrice }}元</el-tag>
        </el-col>
      </el-row>
    </el-card>

    <!-- 病因编写对话框 -->
    <el-dialog title="诊断及医生建议编写" :visible.sync="adviceFormVisible">
      <el-input
        type="textarea"
        :rows="8"
        placeholder="请输入内容"
        v-model="advice"
      >
      </el-input>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adviceFormVisible = false" style="font-size: 18px;">
          取 消</el-button
        >
        <el-button type="primary" @click="holdAdvice">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";
import request from "@/utils/request.js";
export default {
  name: "dealOrderAgain",
  data() {
    return {
      oId: 1,
      pId: 1,
      dId: 0,
      pName: "aa",
      pGender: "as",
      pPhone: "asd",
      dName: "",
      drugData: [],
      size: 4,
      pageNumber: 1,
      total: 3,
      queryDrug: "",
      drugBuyData: [],
      drugTotalPrice: 0,
      //病因
      advice: "",
      //项目表格数据
      checkData: [],
      queryCheck: "",
      checkTotal: 3,
      checkSize: 4,
      checkPageNumber: 1,
      checkTotalPrice: 0,
      checkBuyData: [],
      //材料表格数据
      materialData: [],
      queryMaterial: "",
      materialTotal: 3,
      materialSize: 4,
      materialPageNumber: 1,
      materialTotalPrice: 0,
      materialBuyData: [],
      // 病因编写对话框显示参数
      adviceFormVisible: false,
    };
  },
  methods: {
    //根据id减少药物数量
    reduceDrugNumber(drId, usedNumber) {
      request
        .get("drug/reduceDrugNumber", {
          params: {
            drId: drId,
            usedNumber: usedNumber,
          },
        })
        .then((res) => {
          if (res.data.status !== 200) {
            this.$message.error("药物数量不足！！");
          }
        });
    },
    //点击提交按钮
    submitClick() {
      for (let i = 0; i < this.drugBuyData.length; i++) {
        this.reduceDrugNumber(
          this.drugBuyData[i].drId,
          this.drugBuyData[i].drNum
        );
      }
      let data = {
        oId: this.dataPackage().oId,
        //   pId: this.pId,
        //   dId: this.dId,
        oAdvice: this.dataPackage().oAdvice,
        oDrug: this.dataPackage().oDrug,
        oItem: this.dataPackage().oItem,
        oMaterial: this.dataPackage().oMaterial,
        oTotalPrice: this.dataPackage().oTotalPrice,
      };
      request
        .post("order/updateOrderByAdd", data)
        .then((res) => {
          console.log(res);
          if (res.data.status !== 200) {
            this.$message.error("请求信息错误");
            return;
          }
          this.$message.success("提交成功！请登录系统自助缴费！");
          request
            .get("order/findTotalPrice", {
              params: {
                oId: data.oId,
              },
            })
            .then((res) => {
              if (res.data.status === 200)
                this.$message.success("提交成功！请登录系统自助缴费！");
            });

          this.$router.push("/orderToday");
          console.log(res.data);
        })
        .catch((err) => {
          console.error(err);
        });
    },
    //封装数据
    dataPackage() {
      let oDrug = " // ";
      let oItem = " // ";
      let oTotalPrice = 0;
      let oId = this.oId;
      let oMaterial=" // "
      let oAdvice = this.advice;
      for (let i = 0; i < this.drugBuyData.length; i++) {
        oDrug +=
          this.drugBuyData[i].drName +
          "*" +
          this.drugBuyData[i].drPrice +
          "(元)*" +
          this.drugBuyData[i].drNum +
          " ";
      }
      for (let i = 0; i < this.checkBuyData.length; i++) {
        oItem +=
          this.checkBuyData[i].iName +
          "*" +
          this.checkBuyData[i].iPrice +
          "(元) ";
      }
      for (let i = 0; i < this.materialBuyData.length; i++) {
        oMaterial +=
          this.materialBuyData[i].mName +
          "*" +
          this.materialBuyData[i].mPrice +
          "(元) ";
      }
      oItem += " 项目总价" + this.checkTotalPrice + "元 ";
      oDrug += " 药物总价" + this.drugTotalPrice + "元 ";
      oMaterial += " 材料总价" + this.materialTotalPrice + "元 ";
      oTotalPrice = this.checkTotalPrice + this.drugTotalPrice+this.materialTotalPrice;
      return { oId, oAdvice, oDrug, oItem, oMaterial, oTotalPrice };
    },
    //点击病因保存按钮
    holdAdvice() {
      this.adviceFormVisible = false;
      this.$message.success("信息保存成功！");
    },
    //打开病因编写对话框
    openAdvice() {
      this.adviceFormVisible = true;
    },
    //检查列表点击移除按钮
    deleteCheck(iId) {
      for (let i = 0; i < this.checkBuyData.length; i++) {
        if (this.checkBuyData[i].iId === iId) {
          this.checkTotalPrice -= this.checkBuyData[i].iPrice; //药物价格总计
          this.checkBuyData.splice(i, 1); //！！！！！！删除数组中下标为i的元素
        }
      }
    },
    //检查列表点击增加按钮
    addCheck(iId) {
      request
        .get("check/findCheck", {
          params: {
            iId: iId,
          },
        })
        .then((res) => {
          if (res.data.status != 200) this.$message.error("信息请求失败");
          //后端传过来的是对象，表格绑定的数组
          this.checkBuyData.push({
            iId: res.data.data.iId,
            iPrice: res.data.data.iPrice,
            iName: res.data.data.iName,
          });
          this.checkTotalPrice += res.data.data.iPrice; //药物价格总计
          console.log(res.data.data);
        });
    },
    //药物页面大小切换时触发
    checkSizeChange(size) {
      this.checkSize = size;
      this.requestCheck();
    },
    //检查页数切换时触发
    checkCurrentChange(num) {
      this.checkPageNumber = num;
      this.requestCheck();
    },
    //请求检查项目
    requestCheck() {
      request
        .get("check/findAllChecks", {
          params: {
            size: this.checkSize,
            pageNumber: this.checkPageNumber,
            query: this.queryCheck,
          },
        })
        .then((res) => {
          if (res.data.status != 200) this.$message.error("获取信息失败");
          this.checkData = res.data.data.checks;
          this.checkTotal = res.data.data.total;
          console.log(res);
        });
    },
    //请求材料项目
    requestMaterial() {
      request
        .get("material/findAllMaterials", {
          params: {
            size: this.materialSize,
            pageNumber: this.materialPageNumber,
            query: this.queryMaterial,
          },
        })
        .then((res) => {
          if (res.data.status != 200) this.$message.error("获取信息失败");
          this.materialData = res.data.data.material;
          this.materialTotal = res.data.data.total;
          console.log(res);
        });
    },
    //材料列表点击移除按钮
    deleteMaterial(mId) {
      for (let i = 0; i < this.materialBuyData.length; i++) {
        if (this.materialBuyData[i].mId === mId) {
          this.materialTotalPrice -= this.materialBuyData[i].mPrice; //材料价格总计
          this.materialBuyData.splice(i, 1); //！！！！！！删除数组中下标为i的元素
        }
      }
    },
    //材料列表点击增加按钮
    addMaterial(mId) {
      request
        .get("material/findMaterial", {
          params: {
            mId: mId,
          },
        })
        .then((res) => {
          // if (res.data.status != 200)
          //   return this.$message.error("信息请求失败");
          // //后端传过来的是对象，表格绑定的数组
          // this.materialBuyData.push({
          //   mId: res.data.data.mId,
          //   mPrice: res.data.data.mPrice,
          //   mName: res.data.data.mName,
          // });
          // this.materialTotalPrice += res.data.data.mPrice; //药物价格总计
          // console.log(res.data.data);
          if (res.data.status != 200) this.$message.error("信息请求失败");
          //后端传过来的是对象，表格绑定的数组
          for (let i = 0; i < this.materialBuyData.length; i++) {
            if (this.materialBuyData[i].mId === res.data.data.mId) {
              for (let j = 0; j < this.materialData.length; j++) {
                if (
                  this.materialData[j].mId === res.data.data.mId &&
                  this.materialData[j].mNumber > 0
                ) {
                  this.materialData[j].mNumber -= 1;
                  this.materialBuyData[i].mNum += 100;
                  this.materialBuyData[i].mSum =
                    this.materialBuyData[i].mPrice * this.materialBuyData[i].mNum;
                  this.materialTotalPrice += this.materialBuyData[i].mPrice; //药物价格总计
                  //return;
                }
              }
              return;
            }
          }
          for (let j = 0; j < this.materialData.length; j++) {
            if (
              this.materialData[j].mId === res.data.data.mId &&
              this.materialData[j].mNumber <= 0
            )
              return;
          }
          this.materialBuyData.push({
            mId: res.data.data.mId,
            mPrice: res.data.data.mPrice,
            mName: res.data.data.mName,
            mNum: 100,
            mSum: res.data.data.mPrice,
          });
          for (let j = 0; j < this.materialData.length; j++) {
            if (this.materialData[j].mId === res.data.data.mId)
              this.materialData[j].mNumber -= 1;
          }
          this.materialTotalPrice += res.data.data.mPrice; //药物价格总计

          console.log(res.data.data);
        });
    },
    //材料页面大小切换时触发
    materialSizeChange(size) {
      this.materialSize = size;
      this.requestMaterial();
    },
    //材料页数切换时触发
    materialCurrentChange(num) {
      this.materialPageNumber = num;
      this.requestMaterial();
    },
    //药物列表点击移除按钮
    deleteDrug(drId) {
      for (let i = 0; i < this.drugBuyData.length; i++) {
        if (this.drugBuyData[i].drId === drId) {
          for (let j = 0; j < this.drugData.length; j++) {
            if (this.drugData[j].drId === drId) this.drugData[j].drNumber += 1;
          }
          this.drugBuyData[i].drNum -= 1;
          this.drugBuyData[i].drSum =
            this.drugBuyData[i].drPrice * this.drugBuyData[i].drNum;
          this.drugTotalPrice -= this.drugBuyData[i].drPrice; //药物价格总计
          if (this.drugBuyData[i].drNum === 0) this.drugBuyData.splice(i, 1); //！！！！！！删除数组中下标为i的元素
        }
      }
    },
    //药物列表点击增加按钮
    addDrug(drId) {
      request
        .get("drug/findDrug", {
          params: {
            drId: drId,
          },
        })
        .then((res) => {
          if (res.data.status != 200) this.$message.error("信息请求失败");
          //后端传过来的是对象，表格绑定的数组
          for (let i = 0; i < this.drugBuyData.length; i++) {
            if (this.drugBuyData[i].drId === res.data.data.drId) {
              for (let j = 0; j < this.drugData.length; j++) {
                if (
                  this.drugData[j].drId === res.data.data.drId &&
                  this.drugData[j].drNumber > 0
                ) {
                  this.drugData[j].drNumber -= 1;
                  this.drugBuyData[i].drNum += 1;
                  this.drugBuyData[i].drSum =
                    this.drugBuyData[i].drPrice * this.drugBuyData[i].drNum;
                  this.drugTotalPrice += this.drugBuyData[i].drPrice; //药物价格总计
                  //return;
                }
              }
              return;
            }
          }
          for (let j = 0; j < this.drugData.length; j++) {
            if (
              this.drugData[j].drId === res.data.data.drId &&
              this.drugData[j].drNumber <= 0
            )
              return;
          }
          this.drugBuyData.push({
            drId: res.data.data.drId,
            drPrice: res.data.data.drPrice,
            drName: res.data.data.drName,
            drNum: 1,
            drSum: res.data.data.drPrice,
          });
          for (let j = 0; j < this.drugData.length; j++) {
            if (this.drugData[j].drId === res.data.data.drId)
              this.drugData[j].drNumber -= 1;
          }
          this.drugTotalPrice += res.data.data.drPrice; //药物价格总计

          console.log(res.data.data);
        });
    },
    //药物页面大小切换时触发
    handleSizeChange(size) {
      this.size = size;
      this.requestDrug();
    },
    //药物页数切换时触发
    handleCurrentChange(num) {
      this.pageNumber = num;
      this.requestDrug();
    },

    //获取药物列表
    requestDrug() {
      request
        .get("drug/findAllDrugs", {
          params: {
            size: this.size,
            pageNumber: this.pageNumber,
            query: this.queryDrug,
          },
        })
        .then((res) => {
          if (res.data.status != 200) this.$message.error("获取信息失败");
          this.drugData = res.data.data.drugs;
          this.total = res.data.data.total;

          console.log(res);
        });
    },
    //获取患者信息
    requestPatient() {
      request
        .get("doctor/findPatientById", {
          params: {
            pId: this.pId,
          },
        })
        .then((res) => {
          console.log(res);
          if (res.data.status != 200) this.$message.error("获取信息失败");
          this.pName = res.data.data.pName;
          this.pGender = res.data.data.pGender;
          this.pPhone = res.data.data.pPhone;
        });
    },
    //token解码
    tokenDecode(token) {
      if (token !== null) return jwtDecode(token);
    },
  },
  created() {
    this.dName = this.tokenDecode(getToken()).dName;
    this.dId = this.tokenDecode(getToken()).dId;
    this.oId = this.$route.query.oId;
    this.pId = this.$route.query.pId;
    this.requestPatient();
    this.requestDrug();
    this.requestCheck();
    this.requestMaterial();
  },
};
</script>
<style lang="scss">
// 让文字居中
.drugRigth {
  text-align: center;
}
.el-tag {
  margin: 8px;
}
.rigthTable {
  margin-top: 56px;
  margin-left: 8px;
}
.drugInput {
  margin-top: 8px;
  margin-bottom: 8px;
}
.el-row {
  margin: 5px;
}
.orderInput {
  width: 240px;
}
.el-pagination {
  margin: 8px;
}
</style>
