<template xmlns="http://www.w3.org/1999/html">
    <div style="position:relative;left: 75px;height: 441px;overflow: auto">
        <div>
            <span>姓名：</span>
            <el-input
                    v-model="this.myData.name"
                    :disabled="true"
                    style="width: 200px">
            </el-input>
        </div><br/>
        <div style="background-color: cyan; position:relative;width: 125px; height: 160px;float: right;right: 35%;top:-26px">
<!--            <el-image-->
<!--                    v-if="this.$store.state.pictureUrl!==null"-->
<!--                    style="width: 125px; height: 160px"-->
<!--                    :src="this.url"-->
<!--                    :fit="this.fits"></el-image>-->
            <el-upload
                    class="avatar-uploader"
                    action="http://localhost:13370/upload"
                    :data="{userid:this.$store.state.userid,ispicture:'1'}"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                <img v-if="this.$store.state.pictureUrl" :src="this.$store.state.pictureUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </div>
        <div>
            <span>姓别：</span>
            <el-input
                    v-model="this.myData.gender"
                    :disabled="true"
                    style="width: 200px">
            </el-input>
        </div><br/>
        <div>
            <span>工号：</span>
            <el-input
                    v-model="this.myData.jobNumber"
                    :disabled="true"
                    style="width: 200px">
            </el-input>
        </div><br/>
        <div>
            <span>生日：</span>
            <el-input
                    v-model="this.myData.birthday"
                    :disabled="true"
                    style="width: 200px">
            </el-input>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>邮箱：</span>
                <el-input
                        v-model="myData.email"
                        style="width: 200px">
                </el-input>
            </div>
            <div style="display: inline;position:relative;left: 15%">
                <span>电话：</span>
                <el-input
                        v-model="myData.phonenum"
                        style="width: 200px">
                </el-input>
            </div>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>地址：</span>
                <el-input
                        v-model="myData.address"
                        style="width: 200px">
                </el-input>
            </div>
            <div style="display: inline;position:relative;left: 15%">
                <span>民族：</span>
                <el-input
                        v-model="this.myData.nation"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>毕业院校：</span>
                <el-input
                        v-model="this.myData.university"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
            <div  style="display: inline;position:relative;left: 15%">
                <span>学历：</span>
                <el-input
                        v-model="this.myData.education"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>入职时间：</span>
                <el-input
                        v-model="this.myData.entryTime"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
            <div style="display: inline;position:relative;left: 15%">
                <span>所属部门：</span>
                <el-input
                        v-model="this.myData.department"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>职位：</span>
                <el-input
                        v-model="this.myData.position"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
            <div style="display: inline;position:relative;left: 15%">
                <span>合同期限(年)：</span>
                <el-input
                        v-model="this.myData.timeLimit"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
        </div><br/>
        <div>
            <div style="display: inline">
                <span>合同开始时间：</span>
                <el-input
                        v-model="this.myData.contractKstime"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
            <div style="display: inline;position:relative;left: 15%">
                <span>合同结束时间：</span>
                <el-input
                        v-model="this.myData.contractJstime"
                        :disabled="true"
                        style="width: 200px">
                </el-input>
            </div>
        </div>
    </div>
</template>

<script>
    import {postRequest} from "../../axios";

    export default {
        name: "myEmpInfo",
        data(){
            return{
                myData:[],
                name:'',
                fits: 'fill',
                url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
            }
        },
        methods:{
            getMyData(){
                var formdata = new FormData;
                formdata.append("userid",this.$store.state.userid)
                postRequest("/employee/getMyData",formdata).then(res => {
                    this.myData = res.data
                })
                console.log(this.myData)
            },
            handleAvatarSuccess(res, file) {
                this.imageUrl = URL.createObjectURL(file.raw);
                // this.$store.state.pictureUrl = res.data
                this.$store.commit('setPictureUrl',res.data)
                location.reload()
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        },
        mounted() {
            this.getMyData()
        }
    }
</script>

<style scoped>
    .el-input{
        left: 3%;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>