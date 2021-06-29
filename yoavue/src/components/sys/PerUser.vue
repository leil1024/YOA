<template>
    <div>
        <el-table
                :data="userData"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="username"
                    label="用户名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="jobName"
                    label="工号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="entryTime"
                    label="入职时间"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="roleName"
                    label="角色名称"
                    width="180">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                            size="mini"
                            type="danger"
                            @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="角色更改" :visible.sync="dialogFormVisible">
            <el-form ref="form" :model="form">
                <el-form-item :label-width="formLabelWidth">
<!--                    <el-input v-model="form.rolename" autocomplete="off"></el-input>-->
                    <el-select v-model="form.roleObjId" placeholder="请选择">
                        <el-option
                                v-for="item in roleData"
                                :key="item.objId"
                                :label="item.name"
                                :value="item.objId">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updatesubmitForm('form')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: "PerUser",
        data() {
            return {
                //表单提交数据
                form:{
                    roleObjId:'',
                    userObjId:''
                },
                //用户数据
                userData:[],
                //角色数据
                roleData:[],

                //编辑弹窗
                dialogFormVisible: false,
                //表格长度
                formLabelWidth: '80px',
            }
        },
        methods:{
            //获取系统用户
            getUser(){
                getRequest("/user/userList").then(res => {
                    this.userData = res.data;
                })
            },


            //编辑
            handleEdit(index, row){
                this.form.userObjId = row.id
                this.dialogFormVisible = true
                if (this.roleData.length <= 0){
                    getRequest("/role/enableRole").then(res => {
                        this.roleData = res.data
                    })
                }
            },

            //删除
            handleDelete(index, row) {
                console.log(index, row);
            },

            updatesubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.form.roleObjId === ''){
                            this.$message({
                                showClose: true,
                                message: '角色名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //封装参数
                            var fromData = new FormData();
                            fromData.append("userid",this.form.userObjId)
                            fromData.append("roleid",this.form.roleObjId)
                            //发送请求
                            postRequest('/roleuser/updateRole',fromData).then(res=>{
                                this.dialogFormVisible = false

                                //如果更改的自己用户的角色时，跳回到首页
                                //如果不是，刷新当前数据
                                if (localStorage.getItem("userid") === this.form.userObjId){
                                    this.$router.push({path:'/home'})
                                    location.reload()
                                }else {
                                    this.getUser()
                                }
                            })
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

        },
        mounted() {
            this.getUser()
        }
    }
</script>

<style scoped>

</style>