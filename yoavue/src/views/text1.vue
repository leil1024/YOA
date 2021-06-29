<template>
    <div style="height:420px;overflow: auto">
    <el-collapse v-model="activeName" @change="handleChange" accordion>
        <el-collapse-item :title="role.name" :name="role.objId" v-for="(role,index) in roleData">
            <el-card class="box-card">
                <div slot="header" class="clearfix">
                    <span>菜单权限</span>
                    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                </div>
                <div>
                    <el-tree
                            :data="menuData"
                            show-checkbox
                            default-expand-all
                            node-key="objId"
                            ref="tree"
                            @check="(click, checked)=>{handleCheckChange(click, checked)}"
                            highlight-current
                            :props="defaultProps">
                    </el-tree>
                </div>
            </el-card>
        </el-collapse-item>
    </el-collapse>
    </div>
</template>

<script>
    import {getRequest} from "../axios";

    export default {
        name: "text1",
        data() {
            return {
                //角色对应的菜单
                roleMenuData: ['292eb2096a82f836e37548e2fd6ced79', 6],
                //所有角色
                roleData: [],
                //所有菜单
                menuData: [],
                activeName: '1',
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        methods:{
            //折叠面板返回角色ID
            handleChange(val) {
                if (val){
                    console.log(this.menuData)
                    // // console.log(val)
                    // var formdata = new FormData();
                    // formdata.append("roleid",val)
                    // postRequest("/roleMenu/menuId",formdata).then(res => {
                    //     // console.log(res.data)
                    //     res.data.forEach(data => {
                    //         this.roleMenuData.push(data)
                    //     })
                    // })
                    // console.log(this.roleMenuData)
                }
            },

            //获取所有角色
            getRole(){
                getRequest("/role/allrole").then(res => {
                    console.log("roleData")
                    console.log("role数据")
                    console.log(res.data)
                    this.roleData = res.data
                })
            },

            handleCheckChange(click, checked) {
                if (checked.checkedKeys.length !== 0){
                    console.log(checked.checkedKeys)
                }
            },

            //获取所有菜单
            getMenu(){
                getRequest("/AllMenu").then(res => {
                    this.menuData = res.data
                })
            },

            getCheckedNodes() {
                console.log(this.$refs.tree.getCheckedNodes());
            },
            getCheckedKeys() {
                console.log(this.$refs.tree.getCheckedKeys());
            },
            setCheckedNodes() {
                this.$refs.tree.setCheckedNodes([{
                    id: 5,
                    label: '二级 2-1'
                }, {
                    id: 9,
                    label: '三级 1-1-1'
                }]);
            },
            setCheckedKeys() {
                this.$refs.tree.setCheckedKeys([3]);
            },
            resetChecked() {
                this.$refs.tree.setCheckedKeys([]);
            }
        },
        mounted() {
            this.getRole();
            this.getMenu();
        }
    }
</script>

<style>

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 18px;
    }

    .clearfix:before,
    .clearfix:after {
        display: table;
        content: "";
    }
    .clearfix:after {
        clear: both
    }

    .box-card {
        width: 480px;
    }

</style>