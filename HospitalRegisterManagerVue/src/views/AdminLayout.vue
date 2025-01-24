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
                    <span>今天预约挂号总人数：{{ orderPeople }}</span>
                </div>
            </div>
        </div>
        <div class="indexPeople">
            <div class="userImage">
                <i class="iconfont icon-r-home" style="font-size: 132px"></i>
            </div>

            <div class="userFont">
                <div class="spanCure">
                    <span>手术概况</span>
                </div>
                <div class="spanPeople">
                    <span>今天手术总台数：{{ chairPeople }}</span>
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

export default {
    name: "AdminLayout",
    data() {
        return {
            orderPeople: 1,
            chairPeople: 1,
        };
    },
    methods: {
        requestPeople() {
            request
                .get("order/orderPeople")
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("数据请求失败");
                    this.orderPeople = res.data.data;
                })
                .catch((err) => {
                    console.error(err);
                });
        },
        requestChair() {
            request
                .get("chair/chairPeople")
                .then((res) => {
                    if (res.data.status !== 200)
                        return this.$message.error("数据请求失败");
                    this.chairPeople = res.data.data;
                })
                .catch((err) => {
                    console.error(err);
                });
        },
    },
    created() {
        this.requestPeople();
        this.requestChair();
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
    margin-left: 120px;
    border-radius: 30px;
}
</style>