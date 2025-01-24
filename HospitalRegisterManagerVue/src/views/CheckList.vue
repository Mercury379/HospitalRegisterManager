<template>
    <div>
        <!-- 卡片 -->
        <el-card style="background-color: #fff5ee9f;">

            <!-- 搜索栏及增加检查 -->
            <el-row type="flex">
                <el-col :span="6">
                    <el-input v-model="query" placeholder="请输入名称查询">
                        <el-button
                            slot="append"
                            style="font-size: 18px;"
                            @click="requestChecks"
                        > 搜索</el-button>
                    </el-input>
                </el-col>
                <el-col :span="6"></el-col>
                <el-col :span="6">
                    <el-button type="info" class="logup" style="font-size: 18px" @click="addFormVisible = true"> 增加项目</el-button>
                </el-col>
            </el-row>
            <!-- 表格 -->
            <el-table :data="checkData" stripe style="width: 100%" border>
                <el-table-column label="编号" prop="iId"></el-table-column>
                <el-table-column label="项目" prop="iName"></el-table-column>
                <el-table-column
                    label="价格/元"
                    prop="iPrice"
                ></el-table-column>
                <el-table-column label="类型" prop="iType"></el-table-column>
                <el-table-column label="操作" width="290" fixed="right">
                    <template slot-scope="scope">
                        <el-button
                            style="font-size: 18px"
                            type="success"
                            @click="modifyDialog(scope.row.iId)"
                        > 编辑</el-button>
                        <el-button
                            style="font-size: 18px"
                            type="danger"
                            @click="deleteDialog(scope.row.iId)"
                        > 删除</el-button>
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

        <!-- 增加检查项目对话框 -->
        <el-dialog title="增加检查项目" :visible.sync="addFormVisible">
            <el-form :model="addForm" :rules="rules" ref="ruleForm">
                <el-form-item label="编号" prop="iId" label-width="80px">
                    <el-input v-model.number="addForm.iId"></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="iName" label-width="80px">
                    <el-input v-model="addForm.iName"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="iPrice" label-width="80px">
                    <el-input v-model="addForm.iPrice"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="addFormVisible = false" style="font-size: 18px;"> 取 消</el-button>
                <el-button type="primary" @click="addCheck('ruleForm')"
                    style="font-size: 18px;"> 确 定</el-button
                >
            </div>
        </el-dialog>

        <!-- 修改检查项目药物对话框 -->
        <el-dialog title="修改检查项目" :visible.sync="modifyFormVisible">
            <el-form :model="modifyForm" :rules="rules" ref="ruleForm">
                <el-form-item label="编号" prop="iId" label-width="80px">
                    <el-input
                        v-model.number="modifyForm.iId"
                        disabled
                    ></el-input>
                </el-form-item>
                <el-form-item label="名称" prop="iName" label-width="80px">
                    <el-input v-model="modifyForm.iName"></el-input>
                </el-form-item>
                <el-form-item label="价格" prop="iPrice" label-width="80px">
                    <el-input v-model="modifyForm.iPrice"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="modifyFormVisible = false" style="font-size: 18px;"> 取 消</el-button>
                <el-button type="primary" @click="modifyCheck('ruleForm')"
                    style="font-size: 18px;"> 确 定</el-button
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
            checkData: [],
            total: 3,
            addFormVisible: false,
            addForm: {},
            rules: {
                //iId: [
                //    { required: true, message: "请输入编号", trigger: "blur" },
                //    {
                 //       type: "number",
                 //       message: "账号必须数字类型",
                  //      trigger: "blur",
                 //   },
                //],
                iName: [
                    { required: true, message: "请输入名称", trigger: "blur" },
                    {
                        min: 1,
                        max: 50,
                        message: "账号必须是1到50个字符",
                        trigger: "blur",
                    },
                ],
                iPrice: [
                    { required: true, message: "请输入单价", trigger: "blur" },
                ],
            },
            modifyFormVisible: false,
            modifyForm: {},
        };
    },
    methods: {
        //点击修改药物信息
        modifyCheck(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("check/modifyCheck", {
                            params: {
                                iId: this.modifyForm.iId,
                                iName: this.modifyForm.iName,
                                iPrice: this.modifyForm.iPrice,
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error("修改信息失败！");
                            this.modifyFormVisible = false;
                            this.requestChecks();
                            this.$message.success("修改检查项目信息成功！");
                            console.log(res);
                        });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
        //打开修改对话框
        modifyDialog(id) {
            request
                .get("check/findCheck", {
                    params: {
                        iId: id,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("请求数据失败");
                    this.modifyForm = res.data.data;
                    this.modifyFormVisible = true;
                    console.log(res);
                });
        },
        //删除检查操作
        deleteCheck(id) {
            request
                .get("check/deleteCheck", {
                    params: {
                        iId: id,
                    },
                })
                .then((res) => {
                    this.requestChecks();
                    console.log(res);
                });
        },
        //删除对话框
        deleteDialog(id) {
            this.$confirm("此操作将删除该检查项目信息, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.deleteCheck(id);
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
        addCheck(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request
                        .get("check/addCheck", {
                            params: {
                                iId: this.addForm.iId,
                                iName: this.addForm.iName,
                                iPrice: this.addForm.iPrice,
                            },
                        })
                        .then((res) => {
                            if (res.data.status !== 200)
                                return this.$message.error(
                                    "编号不合法或已被占用！"
                                );
                            this.addFormVisible = false;
                            this.requestChecks();
                            this.$message.success("增加检查项目成功！");
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
            this.requestChecks();
        },
        //   页码改变时触发
        handleCurrentChange(num) {
            console.log(num);
            this.pageNumber = num;
            this.requestChecks();
        },
        // 加载检查列表
        requestChecks() {
            request
                .get("check/findAllChecks", {
                    params: {
                        pageNumber: this.pageNumber,
                        size: this.size,
                        query: this.query,
                    },
                })
                .then((res) => {
                    this.checkData = res.data.data.checks;
                    this.total = res.data.data.total;
                    
                });
        },
    },
    created() {
        this.requestChecks();
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