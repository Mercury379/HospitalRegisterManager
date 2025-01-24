<template>
    <div>
        <el-card>
            <!-- <span>科室门诊</span> -->
            <el-divider></el-divider>
            <div class="sectionIndex" style="font-size: 24px">
                <b> 广埠屯主院区</b>
            </div>
            <ul>
                <li v-for="inter in inters" :key="inter">
                    <el-button
                        style="margin: 5px"
                        @click="sectionClick(inter)"
                        >{{ inter }}</el-button
                    >
                </li>
            </ul>
            <el-divider></el-divider>
            <div class="sectionIndex" style="font-size: 24px">
                <b> 光谷院区</b>
            </div>
            <ul>
                <li v-for="out in outs" :key="out">
                    <el-button style="margin: 5px" @click="sectionClick(out)">{{
                        out
                    }}</el-button>
                </li>
            </ul>
            <el-divider></el-divider>
            <div class="sectionIndex" style="font-size: 24px">
                <b> 中商广场门诊部</b>
            </div>
            <ul>
                <li v-for="woman in women" :key="woman">
                    <el-button
                        style="margin: 5px"
                        @click="sectionClick(woman)"
                        >{{ woman }}</el-button
                    >
                </li>
            </ul>
            <el-divider></el-divider>
        </el-card>
    </div>
</template>
<script>

export default {
    name: "SectionIndex",
    data() {
        return {
            inters: [
                "牙体牙髓一科",
                "牙体牙髓二科",
                "口腔预防科",
                "口腔种植科",
                "口腔综合科",
                "牙周科",
            ],
            outs: [
                "牙体牙髓科",
                "口腔粘膜科",
                "洁牙中心",
                "儿童口腔科",
                "口腔综合科",
            ],
            women: ["牙周科", "口腔修复科","口腔综合科"],
            kids: ["儿科", "儿童保健科"],
            fives: ["耳鼻咽喉科", "眼科", "口腔科"],
            chineses: ["中医科"],
            orthers: ["康复医学科", "急诊科", "皮肤性病科", "功能科"],
        };
    },
    methods: {
        //科室点击
        sectionClick(section) {
            this.$router.push({ path: "/arrangeDoctor?section=" + section });
        },
    },
    mounted() {
      
    }
};
</script>
<style scoped lang="scss">
ul li {
    display: inline;
}
.el-breadcrumb {
    margin: 8px;
}
</style>