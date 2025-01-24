<template>
  <div>
    <!-- 卡片 -->
    <el-card style="background-color: #fff5ee9f;">
      <!-- 搜索栏及增加检查 -->
      <el-row type="flex">
        <el-col :span="6">
          <el-input v-model="query" placeholder="请输入患者id查询">
            <el-button
              slot="append"
              @click="requestChairs"
              style="font-size: 18px"
            >
              搜索</el-button
            >
          </el-input>
        </el-col>
        <el-col :span="6"></el-col>
        <el-col :span="6">
          <el-button
            type="info"
            class="logup"
            style="font-size: 18px"
            @click="addFormVisible = true"
          >
            增加手术椅</el-button
          >
        </el-col>
      </el-row>
      <!-- 表格 -->
      <el-table :data="chairData" stripe style="width: 100%" border>
        <el-table-column label="椅子位" prop="cId"></el-table-column>
        <el-table-column label="所属科室" prop="cSection"></el-table-column>
        <el-table-column label="所属院区" prop="cArea"></el-table-column>
        <el-table-column label="楼层" prop="cLocation"></el-table-column>
        <!-- <el-table-column label="状态" prop="cOrder">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.cstatus == 1" type="danger">已占用</el-tag>
            <el-tag v-if="scope.row.cstatus == 0" type="success">空</el-tag>
          </template>
        </el-table-column> -->

        <el-table-column label="操作" width="260" fixed="right">
          <template slot-scope="scope">
            <el-button
              style="font-size: 18px"
              type="success"
              @click="emptyDialog(scope.row.cId)"
            >
              清空</el-button
            >
            <el-button
              style="font-size: 18px"
              type="danger"
              @click="deleteDialog(scope.row.cId)"
            >
              删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

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
    </el-card>

    <!-- 增加椅位对话框 -->
    <el-dialog title="增加椅位" :visible.sync="addFormVisible">
      <el-form :model="addForm" :rules="rules" ref="ruleForm">
        <el-form-item label="椅号" prop="cId" label-width="80px">
          <el-input v-model.number="addForm.cId"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addFormVisible = false" style="font-size: 18px;">
          取 消</el-button
        >
        <el-button
          type="primary"
          @click="addChair('ruleForm')"
          style="font-size: 18px;"
        >
          确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import request from "@/utils/request.js";

export default {
  name: "CheckList",
  data() {
    return {
      pageNumber: 1,
      size: 8,
      query: "",
      chairData: [],
      total: 3,
      addFormVisible: false,
      addForm: {},
      rules: {
        cId: [
          { required: true, message: "请输入椅号", trigger: "blur" },
          {
            type: "number",
            message: "椅号必须数字类型",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    //清空椅位操作
    emptyChair(id) {
      request
        .get("chair/emptyChair", {
          params: {
            cId: id,
          },
        })
        .then((res) => {
          this.requestChairs();
          console.log(res);
        });
    },
    //清空对话框
    emptyDialog(id) {
      this.$confirm("此操作将清空该椅位, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.emptyChair(id);
          this.$message({
            type: "success",
            message: "清空成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消清空",
          });
        });
    },

    //删除椅位操作
    deleteChair(id) {
      request
        .get("chair/deleteChair", {
          params: {
            cId: id,
          },
        })
        .then((res) => {
          this.requestChairs();
          console.log(res);
        });
    },
    //删除对话框
    deleteDialog(id) {
      this.$confirm("此操作将删除该椅位, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.deleteChair(id);
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    //点击增加确认按钮
    addChair(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          request
            .get("chair/addChair", {
              params: {
                cId: this.addForm.cId,
                pId: -1,
                dId: -1,
              },
            })
            .then((res) => {
              if (res.data.status !== 200)
                return this.$message.error("椅号或已被占用！");
              this.addFormVisible = false;
              this.requestChairs();
              this.$message.success("增加椅位成功！");
              console.log(res);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    //页面大小改变时触发
    handleSizeChange(size) {
      this.size = size;
      this.requestChairs();
    },
    //   页码改变时触发
    handleCurrentChange(num) {
      console.log(num);
      this.pageNumber = num;
      this.requestChairs();
    },
    // 加载检查列表
    requestChairs() {
      request
        .get("chair/findAllChairs", {
          params: {
            pageNumber: this.pageNumber,
            size: this.size,
            query: this.query,
          },
        })
        .then((res) => {
          this.chairData = res.data.data.chairs;
          this.total = res.data.data.total;
        });
    },
  },
  created() {
    this.requestChairs();
  },
};
</script>
<style scoped lang="scss">
.el-table {
  margin-top: 20px;
  margin-bottom: 20px;
}
.el-form {
  margin-top: 0;
}
</style>
