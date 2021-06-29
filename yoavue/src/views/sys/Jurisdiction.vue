<template>
<!--权限管理页面。暂时设定两个角色：普通用户和系统管理员，根据角色的不同来限定使用的页面功能-->
    <div>
        <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
            <el-tab-pane label="权限角色" name="first">
                <PerRole/>
            </el-tab-pane>
            <el-tab-pane label="角色菜单" name="second">
                <PerMenu/>
            </el-tab-pane>
            <el-tab-pane label="用户权限" name="third">
                <PerUser/>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import PerRole from "../../components/sys/PerRole";
    import PerMenu from "../../components/sys/PerMenu";
    import PerUser from "../../components/sys/PerUser";

    export default {
        name: "Jurisdiction",
        components:{
            PerRole,
            PerMenu,
            PerUser
        },
        data() {
            return {
                activeName: 'third'
            };
        },
        methods: {
            handleClick(tab, event) {
                console.log(tab, event);
            }
        }
    }
</script>

<style scoped>

</style>