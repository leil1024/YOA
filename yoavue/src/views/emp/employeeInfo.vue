<template>
    <div>
        <AddEmpInfo/>
        <el-table
                :data="employeeData"
                border
                style="width: 100%;">
            <el-table-column
                    fixed
                    prop="name"
                    label="姓名"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="gender"
                    label="姓别"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="jobNumber"
                    label="工号"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="birthday"
                    label="出生日期"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="phonenum"
                    label="电话"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="地址"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="nation"
                    label="民族"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="university"
                    label="毕业院校"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="education"
                    label="学历"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="entryTime"
                    label="入职时间"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="department"
                    label="所属部门"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="position"
                    label="职位"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="contractKstime"
                    label="合同开始时间"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="contractJstime"
                    label="合同结束时间"
                    width="300">
            </el-table-column>
            <el-table-column
                    prop="timeLimit"
                    label="合同期限(年)"
                    width="300">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button type="text" size="small" @click="handleEdit">编辑</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div>
            <el-drawer
                    title="我是标题"
                    :visible.sync="drawer"
                    :direction="direction"
                    :before-close="handleClose">
                <span>我来啦!</span>
            </el-drawer>
        </div>
    </div>
</template>

<script>
    import {getRequest} from "../../axios";
    import AddEmpInfo from "../../components/emp/AddEmpInfo";
    import EditEmpInfo from "../../components/emp/EditEmpInfo";

    export default {
        name: "employeeInfo",
        components:{
            AddEmpInfo
        },
        data() {
            return {
                //员工数据
                employeeData: [],
                drawer: false,
                direction: 'rtl',
            }
        },
        methods: {
            handleEdit(){
                this.drawer = true
            },
            handleClick(row) {
                console.log(row);
            },
            getEmployeeData(){
                getRequest("/employee/").then(res => {
                    console.log(res.data)
                    this.employeeData = res.data
                })
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
        },
        mounted() {
            this.getEmployeeData()
        }

    }
</script>

<style scoped>

</style>