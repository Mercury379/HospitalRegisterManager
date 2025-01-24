<template>
<div style="width: 100%;margin-top: -10px">
    <el-card style="background-color: #fff5ee5e;">
        <table>
            <tr>
                <td>姓名：</td>
                <td><el-input v-model="patientData.pName"></el-input></td>
            </tr>
            <tr>
                <td>账号：</td>
                <td><el-input disabled v-model="patientData.pId"></el-input></td>
            </tr>
            <tr>
                <td>性别：</td>
                 <td><el-input disabled v-model="patientData.pGender"></el-input></td>
            </tr>
            <tr>
                <td>手机号：</td>
                 <td><el-input v-model="patientData.pPhone"></el-input></td>
            </tr>
            <tr>
                <td>身份证号：</td>
                 <td><el-input disabled v-model="patientData.pCard"></el-input></td>
            </tr>
            <tr>
                <td>出生日期：</td>
                 <td><el-input disabled v-model="patientData.pBirthday"></el-input></td>
            </tr>
            <tr>
                <td>年龄：</td>
                  <td><el-input disabled v-model="patientData.pAge"></el-input></td>
            </tr>

        </table>
        <br/>
        <el-button type="primary" plain @click="patientChange()" style="margin-left: 110px;">修改</el-button>
    </el-card>
</div>
</template>
<script>
import jwtDecode from "jwt-decode";
import { getToken} from "@/utils/storage.js";
import request from "@/utils/request.js";
export default {
    name: "PatientCard",
    data() {
        return {
            userId:"",
            patientData:{}
        }
    },
    methods: {
        //请求患者信息
        requestPatient(){
            request.get("doctor/findPatientById", {
                params: {
                    pId: this.userId
                }
            })
            .then(res => {
                if(res.data.status != 200)
                return this.$message.error("获取数据失败");
                this.patientData = res.data.data;
            })
        },
        //新增功能：患者个人信息修改（根据id修改姓名、手机号）
        patientChange(){        
            request.get("doctor/changePatientById", {
                params: {
                    pId: this.userId,
                    pName: this.patientData.pName,
                    pPhone: this.patientData.pPhone,

                }
            })
            .then(res => {
                
                if(res.data.status != 200)
                return this.$message.error("获取数据失败");
                this.patientData = res.data.data;
                this.$message('修改成功！');
            })
        },
    //token解码
        tokenDecode(token){
        if (token !== null)
        return jwtDecode(token);
        }
    },
    created(){
     
        //解码token信息
        this.userId = this.tokenDecode(getToken()).pId;
        this.requestPatient();
        console.log(this.userId)
    }
}
</script>
<style lang="scss" scope>
td, th {
  white-space: nowrap;
  padding: 10px;
}
</style>