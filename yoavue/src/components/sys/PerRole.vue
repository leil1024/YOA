<template>
    <div>
        <div>
            <div>
                <el-form ref="addRole" :model="addRole">
                    <el-form-item label="角色名称" prop="posName">
                        <el-input class="inputRole" size="small" v-model="addRole.inputName" placeholder="角色添加：输入名称"></el-input>
                        <el-button size="small" type="success" @click="submitForm('addRole')" round>添加</el-button>
                    </el-form-item>
                </el-form>
            </div>
            <el-table
                    :data="roleData"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="name"
                        label="角色名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="180">
                </el-table-column>
                <el-table-column label="是否启用">
                    <template slot-scope="scope">
                        <el-switch
                            v-model="scope.row.enable"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            disabled
                            :active-value="1"
                            :inactive-value="0">
                        </el-switch>
                    </template>
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
            <el-dialog title="角色信息" :visible.sync="dialogFormVisible">
                <el-form ref="form" :model="form">
                    <el-form-item label="角色名称" :label-width="formLabelWidth">
                        <el-input v-model="form.rolename" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="是否启用:" :label-width="formLabelWidth">
                        <el-switch
                                v-model="form.enabled"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                :active-value="1"
                                :inactive-value="0"
                                active-text="是"
                                inactive-text="否">
                        </el-switch>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="updatesubmitForm('form')">确 定</el-button>
                </div>
            </el-dialog>
        </div>
        <div>
            <el-pagination
                    id="page"
                    background
                    layout="prev, pager, next"
                    :current-page="PageNumber"
                    :page-size="pageSize"
                    :total="total"
                    @current-change="page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import {postRequest} from "../../axios";

    export default {
        name: "PerRole",
        data() {
            return {
                addRole:{
                  inputName:''
                },

                form:{
                    objId:'',
                    //角色名称
                    rolename:'',
                    //是否开启
                    enabled:''
                },
                //后端返回的数据
                roleData: [],
                //页码，默认1
                PageNumber: 1,
                //每页数量
                pageSize: 5,
                //总页数
                total: 0,
                formLabelWidth: '80px',
                dialogFormVisible: false
            }
        },
        methods:{
            page(PageNumber){
                postRequest('/role/rolelist?PageNumber=' + PageNumber).then(res =>{
                    // console.log("查看返回的数据")
                    // console.log(res.data.records)
                    this.roleData = res.data.records
                })
            },

            //角色添加
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //封装请求参数
                        // var fromData = new FormData();
                        // fromData.append("posName",this.pos.posName)
                        if (this.addRole.inputName === ''){
                            this.$message({
                                showClose: true,
                                message: '角色名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //发送请求
                            postRequest('/role/addRole?name=' + this.addRole.inputName).then(res=>{
                                console.log(res)
                                //添加之后重新加载数据
                                this.page(this.PageNumber)
                                //清空表单
                                this.addRole.inputName = ''
                            })
                            // this.page(1)
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },


            //编辑
            handleEdit(index, row){
                this.dialogFormVisible = true
                this.form.objId = row.objId
                this.form.rolename = row.name
                this.form.enabled = row.enable
                // console.log(row)
                // alert("编辑")
            },
            //编辑表单提交
            updatesubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.form.rolename === ''){
                            this.$message({
                                showClose: true,
                                message: '角色名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //封装参数
                            var fromData = new FormData();
                            fromData.append("objId",this.form.objId)
                            fromData.append("name",this.form.rolename)
                            fromData.append("enable",this.form.enabled)
                            //发送请求
                            postRequest('/role/upRole',fromData).then(res=>{
                                this.dialogFormVisible = false
                                this.page(this.PageNumber)                                                                                                                      ``
                            })
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },

            //删除
            handleDelete(index, row){
                postRequest("/role/delRole?objId=" + row.objId).then(res =>{
                    this.page(this.PageNumber)
                })
            }
        },
        mounted() {
            this.page(1);
        }
    }
</script>

<style scoped>
    .inputRole{
        width: 220px;
        margin-right: 10px;
    }

    #page{
        position: fixed;
        margin: 0 auto;
        text-align: center;
        bottom: 0;
        left: 50%;
    }
</style>