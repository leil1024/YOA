<!--基础信息设置页面-->
<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="部门管理" name="first">
                <Department/>
            </el-tab-pane>
            <el-tab-pane label="职位管理" name="second">
                <Position/>
            </el-tab-pane>
            <el-tab-pane label="职称管理" name="third">职称管理</el-tab-pane>
            <el-tab-pane label="奖惩规则" name="fourth">奖惩规则</el-tab-pane>
<!--            <el-tab-pane label="权限管理" name="five"><Jurisdiction/></el-tab-pane>-->
        </el-tabs>
    </div>
</template>

<script>
    import Position from "../../components/sys/Position";
    import Jurisdiction from "./Jurisdiction";
    import Department from "../../components/sys/Department";

    export default {
        name: "BasicInfo",
        data() {
            return {
                activeName: 'first'
            };
        },
        components: {
            Position,
            Jurisdiction,
            Department
        }
    }
</script>

<style scoped>

</style>