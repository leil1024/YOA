<template>
    <div>
        <div style="height: 50px;">
            <el-row>
                <el-button type="primary" round style="position: relative;
                                                       top: -10px;
                                                       float: right;
                                                       width: 150px;"
                           @click="drawer = true" icon="el-icon-circle-plus-outline">添加员工</el-button>
<!--                <el-button type="primary" round style="position: relative;-->
<!--                                                       top: -8px;-->
<!--                                                       float: right;-->
<!--                                                       width: 150px;-->
<!--                                                       right: 2%"-->
<!--                           @click="exportExcel" icon="el-icon-tickets">导出</el-button>-->
            </el-row>
        </div>
        <el-drawer
                title="我是标题"
                size="40%"
                :visible.sync="drawer"
                :direction="direction"
                :before-close="handleClose">
            <el-form
                    :model="ruleForm"
                    :rules="rules"
                    ref="ruleForm"
                    label-width="120px"
                    class="demo-ruleForm"
                    style="position:relative;left: 40px">
                <el-form-item label="员工姓名" prop="name">
                    <el-input v-model="ruleForm.name" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="gender">
                    <el-switch
                            v-model="ruleForm.gender"
                            active-value="男"
                            inactive-value="女"
                            active-text="男"
                            inactive-text="女">
                    </el-switch>
                </el-form-item>
                <el-form-item label="工号" prop="jobNumber">
                    <el-input v-model="ruleForm.jobNumber" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="出生日期" prop="birthday">
                    <el-date-picker
                            value-format="yyyy-MM-dd"
                            v-model="ruleForm.birthday"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="ruleForm.email" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phonenum">
                    <el-input v-model="ruleForm.phonenum" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address">
                    <el-input v-model="ruleForm.address" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="民族" prop="nation">
                    <el-input v-model="ruleForm.nation" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="毕业院校" prop="university">
                    <el-input v-model="ruleForm.university" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="学历" prop="education">
                    <el-select v-model="ruleForm.education" placeholder="请选择">
                        <el-option
                                v-for="item in options"
                                :key="item.label"
                                :label="item.label"
                                :value="item.label">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="入职时间" prop="entryTime">
                    <el-date-picker
                            value-format="yyyy-MM-dd"
                            v-model="ruleForm.entryTime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="部门" prop="department">
                    <el-select v-model="ruleForm.department" placeholder="请选择">
                        <el-option
                                v-for="item in enableDep"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="职位" prop="position">
                    <el-select v-model="ruleForm.position" placeholder="请选择">
                        <el-option
                                v-for="item in enablePos"
                                :key="item.posName"
                                :label="item.posName"
                                :value="item.posName">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="合同开始时间" prop="contractKstime">
                    <el-date-picker
                            value-format="yyyy-MM-dd"
                            v-model="ruleForm.contractKstime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="合同结束时间" prop="contractJstime">
                    <el-date-picker
                            value-format="yyyy-MM-dd"
                            v-model="ruleForm.contractJstime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="合同期限" prop="timeLimit">
                    <el-input v-model="ruleForm.timeLimit" style="width: 280px"></el-input>
                </el-form-item>
                <el-form-item label="是否创建系统账号" prop="gender">
                    <el-switch
                            v-model="ruleForm.accountNumber"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                            :active-value="1"
                            :inactive-value="0"
                            active-text="是"
                            inactive-text="否">
                    </el-switch>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
    </div>
</template>

<script>
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: "AddEmpInfo",
        data() {
            return {
                //弹窗
                drawer: false,
                direction: 'rtl',
                //职位信息
                enablePos:[],
                //部门信息
                enableDep:[],

                //员工表单
                ruleForm: {
                    name: '',
                    gender: '',
                    jobNumber: '',
                    birthday: '',
                    email: '',
                    phonenum: '',
                    address: '',
                    nation: '',
                    university: '',
                    education: '',
                    entryTime: '',
                    department: '',
                    position: '',
                    contractKstime: '',
                    contractJstime: '',
                    timeLimit: '',
                    accountNumber: ''
                },
                options: [{
                    label: '本科'
                }, {
                    label: '专科'
                }, {
                    label: '高中'
                }],
                rules: {
                    name: [
                        { required: true, message: '请输入员工姓名', trigger: 'blur' },
                    ],
                    jobNumber: [
                        { required: true, message: '请输入工号', trigger: 'blur' }
                    ],
                    phonenum: [
                        { required: true, message: '请输入电话', trigger: 'blur' }
                    ],
                    address: [
                        { required: true, message: '请输入地址', trigger: 'blur' }
                    ],
                    education: [
                        { required: true, message: '请输入学历', trigger: 'blur' }
                    ],
                    entryTime: [
                        { required: true, message: '请选择入职时间', trigger: 'blur' }
                    ],
                    department: [
                        { required: true, message: '请选择部门', trigger: 'blur' }
                    ],
                    position: [
                        { required: true, message: '请选择职位', trigger: 'blur' }
                    ],
                    contractKstime: [
                        { required: true, message: '请选择合同开始时间', trigger: 'blur' }
                    ],
                    contractJstime: [
                        { required: true, message: '请选择合同结束时间', trigger: 'blur' }
                    ],
                    timeLimit: [
                        { required: true, message: '请输入合同期限', trigger: 'blur' }
                    ],
                },
            }
        },
        methods: {
            //获取所有职位信息
            getEnablePos(){
                getRequest("/enablePos").then(res => {
                    this.enablePos = res.data
                })
            },

            //获取所有部门信息
            getEnableDep(){
              getRequest("/system/department/enableDep").then(res => {
                  this.enableDep = res.data
              })
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },

            //表单提交
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // alert('submit!');
                        var fromdata = new FormData;
                        fromdata.append("name",this.ruleForm.name)
                        fromdata.append("gender",this.ruleForm.gender)
                        fromdata.append("jobNumber",this.ruleForm.jobNumber)
                        fromdata.append("birthday",this.ruleForm.birthday)
                        fromdata.append("email",this.ruleForm.email)
                        fromdata.append("phonenum",this.ruleForm.phonenum)
                        fromdata.append("address",this.ruleForm.address)
                        fromdata.append("nation",this.ruleForm.nation)
                        fromdata.append("university",this.ruleForm.university)
                        fromdata.append("education",this.ruleForm.education)
                        fromdata.append("entryTime",this.ruleForm.entryTime)
                        fromdata.append("department",this.ruleForm.department)
                        fromdata.append("position",this.ruleForm.position)
                        fromdata.append("contractKstime",this.ruleForm.contractKstime)
                        fromdata.append("contractJstime",this.ruleForm.contractJstime)
                        fromdata.append("timeLimit",this.ruleForm.timeLimit)
                        fromdata.append("accountNumber",this.ruleForm.accountNumber)
                        postRequest("/employee/addEmp",fromdata)
                        if (this.ruleForm.accountNumber === 1){
                            this.$alert('系统账号已创建！<br/>'+
                                '默认账号：'+this.ruleForm.name+'<br/>'+
                                '默认密码：123456<br/>'+
                                '默认权限：普通用户', '标题名称', {
                                confirmButtonText: '确定',
                                callback: action => {
                                    this.$message({
                                        dangerouslyUseHTMLString: true,
                                        type: 'info',
                                        message: `action: ${ action }`
                                    });
                                }
                            });
                        }
                        location.reload()
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },

            //导出
            exportExcel(){
                getRequest("/employee/exportExcel")
            }
        },
        mounted() {
            this.getEnablePos()
            this.getEnableDep()
        }
    }
</script>

<style scoped>

    /*.el-drawer__body {*/
    /*    overflow: auto;*/
    /*    !* overflow-x: auto; *!*/
    /*}*/


    #app{
        position: relative;
        top: -10px;
        float: right;
        width: 150px;
    }
</style>