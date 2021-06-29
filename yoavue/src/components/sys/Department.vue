<template>
    <div>
        <el-input
                placeholder="输入部门进行过滤"
                v-model="filterText">
        </el-input>
        <div style="height: 10px"></div>
        <el-tree
                class="filter-tree"
                :data="DepData"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree">
             <span class="custom-tree-node" slot-scope="{ node, data }" style="display: flex;justify-content: space-between;width: 30%">
                <span>{{ node.label }}</span>
                <span>
                  <el-button
                          type="text"
                          class="depbtn"
                          size="mini"
                          @click="() => append(data)">
                    <i class="el-icon-circle-plus-outline" @click="dialogTableVisible = true"></i>
                  </el-button>
                  <el-button
                          type="text"
                          class="depbtn"
                          size="mini"
                          @click="() => remove(data)">
                    <i class="el-icon-remove-outline"></i>
                  </el-button>
                </span>
             </span>
        </el-tree>

        <el-dialog title="部门添加" :visible.sync="dialogFormVisible">
            <el-form ref="depinfo" :model="depinfo">
                <el-form-item label="部门名称" :label-width="formLabelWidth">
                    <el-input v-model="depinfo.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="adddatasubmitForm('depinfo')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: "Department",
        data() {
            return {
                filterText: '',
                DepData: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                depinfo: {
                    name:'',
                    parentId:''
                },
                dialogFormVisible: false,
                formLabelWidth: '80px'
            }
        },

        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },

        mounted() {
            this.getDepData();
        },
        methods: {
            getDepData(){
                getRequest("/system/department/").then(res=>{
                    // console.log(res)
                    this.DepData = res.data

                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },

            //添加部门，打开添加面板
            append(data){
                this.dialogFormVisible = true
                this.depinfo.parentId = data.id
            },
            //部门添加请求
            adddatasubmitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        if (this.depinfo.name === ''){
                            this.$message({
                                showClose: true,
                                message: '部门名称不能为空',
                                type: 'error'
                            });
                        }else {
                            //封装参数
                            var fromData = new FormData();
                            fromData.append("name",this.depinfo.name)
                            fromData.append("parentId",this.depinfo.parentId)
                            //发送请求
                            postRequest('/system/department/appDep',fromData).then(res=>{
                                this.dialogFormVisible = false
                                this.getDepData();
                                this.depinfo.name=''
                            })
                        }
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            remove(data){
                postRequest("/system/department/delDep/" + data.id).then(res => {
                    this.getDepData()
                })
            }
        },
    }
</script>

<style scoped>

    .depbtn{
        font-size: 17px;
    }
</style>