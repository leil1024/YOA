<template>
    <div>
        <div>
            <el-form ref="pos" :model="pos">
                <el-form-item label="职位名称" prop="posName">
                    <el-input class="inputPos" size="small" v-model="pos.posName" placeholder="职位添加：输入名称"></el-input>
                    <el-button size="small" type="success" @click="submitForm('pos')" round>添加</el-button>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <!--                    :data="tableData"-->
            <!--循环-->
            <el-table
                    :data="positionData"
                    stripe
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="posName"
                        label="职位名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="创建时间"
                        width="180">
                </el-table-column>
<!--                <el-table-column-->
<!--                        prop="enable"-->
<!--                        label="是否启用">-->
<!--                </el-table-column>-->
                <el-table-column label="启用状态">
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
        </div>
        <div>
            <el-pagination id="page"
                           background
                           layout="prev, pager, next"
                           :current-page="PageNumber"
                           :page-size="pageSize"
                           :total="total"
                           @current-change="page"
            >
            </el-pagination>
        </div>
        <!--编辑弹出框-->
        <el-dialog title="职位编辑" :visible.sync="dialogFormVisible" width="30%">
            <el-form ref="upposition" :model="upposition">
                <el-form-item label="职位名称:" :label-width="formLabelWidth">
                    <el-input v-model="upposition.posName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="是否启用:" :label-width="formLabelWidth">
                        <el-switch
                                v-model="upposition.enable"
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
                <el-button type="primary" @click="updatesubmitForm('upposition')">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: "Position",
        data() {
            return {
                //后端返回的数据信息
                positionData:[],
                //分页默认当前页码
                PageNumber:1,
                //每页数量
                pageSize: 5,
                //总页数，默认0
                total: 0,
                pos: {
                    posName: ''
                },
                upposition: {
                    id: '',
                    posName: '',
                    createTime: '',
                    enable: ''
                },
                dialogFormVisible: false,
                formLabelWidth: '80px'
            }
        },
        //获取后端数据
        methods:{
            //分页函数
            page(PageNumber){
                postRequest("/position?PageNumber=" + PageNumber).then(res=>{
                    // console.log(res)
                    // console.log("name")
                    // console.log(res.data.records[0].posName)
                    // let resMap = []
                    this.positionData = res.data.records
                    // for (let i in res.data.records){
                    //     this.positionData.push(res.data.records[i])
                    // }
                    this.PageNumber = res.data.current
                    this.total = res.data.total
                    this.pageSize = res.data.size
                })
            },
            //职位添加提交
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //封装请求参数
                        // var fromData = new FormData();
                        // fromData.append("posName",this.pos.posName)
                        if (this.pos.posName === ''){
                            this.$message({
                                showClose: true,
                                message: '职位名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //发送请求
                            postRequest('/addposition?posName=' + this.pos.posName).then(res=>{
                                console.log(res)
                                //添加之后重新加载数据
                                this.page(this.PageNumber)
                                //清空表单
                                this.pos.posName = ''
                            })
                            // this.page(1)
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            //编辑按钮，开启编辑弹窗
            handleEdit(index, row) {
                //更改弹窗状态：改为开启
                this.dialogFormVisible = true
                //设置参数，用于携带参数修改数据
                //设置id
                this.upposition.id = row.id
                //默认选中的职位名称
                this.upposition.posName = row.posName
                //默认时间
                this.upposition.createTime = row.createTime
                //默认当前职位的状态
                this.upposition.enable = row.enable

            },
            //编辑提交表单
            updatesubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.upposition.posName === ''){
                            this.$message({
                                showClose: true,
                                message: '职位名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //封装参数
                            var fromData = new FormData();
                            fromData.append("id",this.upposition.id)
                            fromData.append("posName",this.upposition.posName)
                            fromData.append("enable",this.upposition.enable)
                            //发送请求
                            postRequest('/upposition',fromData).then(res=>{
                                this.dialogFormVisible = false
                                this.page(this.PageNumber)
                            })
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },


            //删除按钮
            handleDelete(index, row) {
                // console.log(index, row);
                postRequest("/delposition?id=" + row.id).then(res => {
                    // console.log(res)
                    this.page(this.PageNumber)
                    //刷新网页
                })
            }
        },
        mounted() {
            this.page(1);
        }
    }
</script>

<style scoped>

    .inputPos{
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