<template>
    <div>
        <!-- <div class="indexImage">
        <img src="@/assets/hospital.jpeg" class="layoutImage"/>
        <span>今天预约挂号总人数：{{orderPeople}}</span>
      </div> -->
        <div class="indexPeople">
            <div class="userImage">
                <i class="iconfont icon-r-user2" style="font-size: 132px"></i>
            </div>

            <div class="userFont">
                <div class="spanCure">
                    <span>就诊概况</span>
                </div>
                <div class="spanPeople">
                    <span>前方排队人数：{{ orderPeople }}</span>
                </div>
            </div>
        </div>
        <el-button @click="qiandao" class="check">
            <i class="iconfont icon-qiandao" style="font-size: 132px"></i><br/>
            签到
            </el-button>

        <el-row>
            <el-col :span="24">
                <img src="@/assets/back.jpg" style="width: 1100px; margin-left: 80px;border-radius: 30px;">
            </el-col>
        </el-row>
    </div>
</template>
<script>
import request from "@/utils/request.js";
import jwtDecode from "jwt-decode";
import { getToken } from "@/utils/storage.js";
export default {
    name: "PatientLayout",
    data() {
        return {
            orderPeople: 4,
            chairPeople: 1,
            userId: 1,
        };
    },
    methods: {
        //根据患者id返回排队人数
        requestPeople() {
            request
                .get("order/orderPeople",{
                    params: {
                        pId: this.userId,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("数据请求失败");
                   // this.orderPeople = res.data.data;
                })
                .catch((err) => {
                    console.error(err);
                });
        },
        //患者签到，根据id进行签到
        qiandao() {
            request
                .get("order/qiandao",{
                    params:{
                        pId:this.userId,
                    }
                })
                .then((res) => {                    
                    if (res.data.status !== 200)
                        return this.$message.error("数据请求失败");
                    this.$message('签到成功！');
                })
                .catch((err) => {
                    console.error(err);
                });
        },
         //token解码
        tokenDecode(token) {
        if (token !== null) return jwtDecode(token);
        },
    },
    created() {
        this.userId = this.tokenDecode(getToken()).pId;
        this.requestPeople();     
    },
};
</script>
<style lang="scss" scoped>
.userFont {
    height: 150px;
    width: 250px;
    float: right;
    color: white;
    .spanCure {
        font-size: 15px;
        margin-top: 60px;
        margin-bottom: 15px;
    }
    .spanPeople {
        font-size: 18px;
    }
}

.userImage {
    height: 150px;
    width: 150px;
    font-size: 130px;
    color: white;
    position: relative;
    left: 40px;
    top: 10px;
    float: left;
}
.indexPeople {
    height: 200px;
    width: 440px;
    background-color: rgba(184, 211, 224, 0.777);
    float: left;
    margin: 30px;
    margin-left: 150px;
    border-radius: 30px;
}
.indexPeople2{
    height: 200px;
    width: 440px;

    margin: 30px;
}
.check {
    background-color:rgba(184, 211, 224, 0.777) ;
    margin-left: 30px;
    margin-top: 30px;
    height:200px;
    width:440px;
    font-size: 30px;
    border-radius: 30px;
}
</style>