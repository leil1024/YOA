<!--:data="menuData"-->
<!--show-checkbox-->
<!--:node-key="menuData.objId"-->
<!--:default-checked-keys="['292eb2096a82f836e37548e2fd6ced79']"-->
<!--:props="defaultProps">-->
<template>
    <div style="height:420px;overflow: auto">
        <el-collapse v-model="activeName" @change="handleChange" accordion>
            <el-collapse-item :title="role.name" :name="role.objId" v-for="(role,index) in roleData">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>菜单权限</span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="setRoleMenu">更改菜单</el-button>
                    </div>
                    <div>
                        <el-tree
                                :data="menuData"
                                show-checkbox
                                default-expand-all
                                node-key="objId"
                                ref="tree"
                                :default-checked-keys="roleMenuData"
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
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: "PerMenu",
        data() {
            return {
                //角色对应的菜单
                roleMenuData:[],
                //所有角色
                roleData:[],
                //所有菜单
                menuData:[],
                //选中的菜单ID
                selectMenuId:[],
                //角色ID，用来添加菜单请求
                roleId:'',
                activeName: '1',
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                dialogVisible:false
            }
        },
        methods:{
            //点击折叠面板返回点击的角色ID
            //根据角色ID请求角色对应的菜单ID
            handleChange(val) {
                if (val){
                    this.getMenu()
                    this.roleId = val;
                    this.roleMenuData = []
                    // console.log(val)
                    var formdata = new FormData();
                    formdata.append("roleid",val)
                    postRequest("/roleMenu/menuId",formdata).then(res => {
                        console.log(res.data)
                        this.roleMenuData = res.data
                        console.log(this.roleMenuData)
                    })
                }
            },

            //获取所有角色
            getRole(){
                getRequest("/role/allrole").then(res => {
                    console.log("roleData")
                    console.log(res.data)
                    this.roleData = res.data
                })
            },

            //获取所有菜单
            getMenu(){
                getRequest("/AllMenu").then(res => {
                    this.menuData = res.data
                })
            },

            //获取选中的菜单ID
            handleCheckChange(click, checked) {
                //输出被选中的菜单的ID，数组
                // console.log(checked.checkedKeys)
                //如果数组的长度不为0,添加到selectMenuId
                if (checked.checkedKeys.length !== 0){
                    this.selectMenuId = checked.checkedKeys
                }
            },

            //更改角色对应的菜单
            setRoleMenu(){
                //封装参数
                var formdata = new FormData;
                formdata.append("roleid",this.roleId)
                formdata.append("selectMenuIdList",this.selectMenuId)

                //删除取消选择的菜单
                //如果选择的菜单少于后台返回的该角色的菜单，默认为删除
                if (this.roleMenuData.length > this.selectMenuId.length){
                    postRequest("roleMenu/delRoleMenu",formdata)
                }else {
                    //添加选中菜单
                    postRequest("/roleMenu/setRoleMenu",formdata)
                }
                this.getRole()
            }

        },
        mounted() {
            this.getRole();
        }
    }
</script>

<style scoped>
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