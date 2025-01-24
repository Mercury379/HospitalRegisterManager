<template>
    <div>
        <el-card style="background-color: #fff5ee9f;">
            <el-input v-model="dName" placeholder="请输入医生姓名" style="width: 250px;margin-right: 30px;"></el-input>           
            <el-date-picker
                v-model="choosedata"
                type="date"
                placeholder="请选择日期"
                style="margin-right: 30px;"
            >
            </el-date-picker>
            <el-button
                style="font-size: 18px;margin-bottom: 30px;"
                @click="requestOrder"
            >
                搜索</el-button
            >
            <el-table :data="chairData" border stripe>
                <el-table-column label="手术座椅号" prop="cId" v-model="chairData.cId"></el-table-column>
                <el-table-column label="医生姓名" prop="dId" v-model="chairData.dId"></el-table-column>
                <el-table-column label="预计开始时间" prop="sPrestartTime" v-model="chairData.sPrestartTime"></el-table-column>
                <el-table-column label="开始时间" prop="sStartTime" v-model="chairData.sStartTime"></el-table-column>
                <el-table-column label="预计手术时间(h)" prop="sPretime" v-model="chairData.sPretime"></el-table-column>
                <el-table-column label="手术状态" prop="cOrder">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.sStartTime == null" type="danger">未开始</el-tag>
                        <el-tag v-if="scope.row.sStartTime != null && scope.row.sEndTime ==null" type="success">进行中</el-tag>
                        <el-tag v-if="scope.row.sEndTime !=null" type="success">已结束</el-tag>
                    </template>
                 </el-table-column>
                <el-table-column label="结束时间" prop="sEndTime" v-model="chairData.sEndTime"></el-table-column>

            </el-table>
        </el-card>
    </div>
</template>
<script>
import jwtDecode from "jwt-decode";
import request from "@/utils/request.js";
import { getToken } from "@/utils/storage.js";
export default {
    name: "MyChair",
    data() {
        return {
            chairData:[],
            userId:1,
            dName:'',
            choosedata:''
        }
    },
    methods: {
        convertISO8601ToFormattedDate(isoString) {  
                var date = new Date(isoString);  
                var year = date.getUTCFullYear();  
                var month = ("0" + (date.getUTCMonth() + 1)).slice(-2); // Months are zero-based  
                var day = ("0" + date.getUTCDate()).slice(-2);  
                var hours = ("0" + date.getUTCHours()).slice(-2);  
                var minutes = ("0" + date.getUTCMinutes()).slice(-2);  
                var seconds = ("0" + date.getUTCSeconds()).slice(-2);  
  
                return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;  
        },  
        //新增：请求手术信息，用户输入医生姓名和日期能进行查询，默认为''
        requestChair(){
            request.get("chair/findChairByPid", {
                params: {
                    pId: this.userId,
                    dName:this.dName,
                    data:this.choosedata,
                }
            })
            .then(res => {
                if(res.data.status !== 200)
                return this.$message.error("请求数据失败");
                this.chairData = res.data.data;
                // var date = new Date(res.data.data.sStartTime);
                // this.chairData.sStartTime=this.convertISO8601ToFormattedDate(da)
            })

        },
           //token解码
    tokenDecode(token){
      if (token !== null)
      return jwtDecode(token);
    },

    },
    created(){
           // 解码token
            this.userId = this.tokenDecode(getToken()).pId;
            this.requestChair();
    }
}
</script>