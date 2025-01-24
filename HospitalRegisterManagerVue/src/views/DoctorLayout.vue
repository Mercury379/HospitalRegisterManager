<template>
    <div>
        <div class="indexPeople">
            <div class="userImage">
                <i class="iconfont icon-r-user2" style="font-size: 132px"></i>
            </div>

            <div class="userFont">
                <div class="spanCure">
                    <span>就诊概况</span>
                </div>
                <div class="spanPeople">
                    <span>今天手术人数：{{ orderPeople }}</span>
                </div>
            </div>
        </div>

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
    name: "DoctorLayout",
    data() {
        return {
            userId: 1,
            orderPeople: 1,
        };
    },
    methods: {
        //token解码
        tokenDecode(token) {
            if (token !== null) return jwtDecode(token);
        },
        //根据医生id返回今日手术人数
        requestPeople() {
            request
                .get("order/orderPeopleByDid", {
                    params: {
                        dId: this.userId,
                    },
                })
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("数据请求失败");
                    this.orderPeople = res.data.data;
                });
        },
    },
    created() {
        this.userId = this.tokenDecode(getToken()).dId;
        this.requestPeople();
    },
    mounted() {
        
    }
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
    float: left;
    position: relative;
    left: 40px;
    top: 10px;
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
</style>