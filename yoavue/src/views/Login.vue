<template>
    <div class="LoginDemo">
        <h3>XXX系统登录</h3>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
            <el-form-item prop="username">
                <el-input v-model="ruleForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="password" v-model="ruleForm.password" placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item prop="code" class="code">
                <el-input type="text" auto-complete="false" v-model="ruleForm.code" placeholder="点击图片更换验证码"></el-input>
                <em></em>
                <el-image :src="this.imageUrl" style="width: 100px;height: 40px;position: absolute" @click="getImg"></el-image>
            </el-form-item>
            <el-form-item label="记住我" prop="delivery">
                <el-switch v-model="remember"></el-switch>
            </el-form-item>
            <el-form-item style="text-align: center">
                <el-button type="primary" round @click="submitForm('ruleForm')">登录</el-button>
                <!--                <el-button @click="resetForm('ruleForm')">重置</el-button>-->
                <el-button type="warning" round @click="resetForm('ruleForm')">重置</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
    import {postRequest} from "../axios";

    export default {
        name: "Login",
        data() {
            return {
                imageUrl: 'http://localhost:13370/getimg',
                remember: '',
                code: '',
                ruleForm: {
                    username: '',
                    password: '',
                    code: '',
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名称', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入用户密码', trigger: 'blur'}
                    ],
                    code: [
                        {required: true, message: '请输入验证码', trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        //封装请求参数
                        var fromData = new FormData();
                        fromData.append("username",this.ruleForm.username)
                        fromData.append("password",this.ruleForm.password)
                        fromData.append("code",this.ruleForm.code)

                        //发送请求
                        postRequest('/login',fromData).then(res=>{

                            if (res.code === 200){
                                //解析token
                                let jwt = require('jsonwebtoken')
                                //将转换之后的token转化为json字符串再转为json对象
                                let tokenStr = JSON.parse(JSON.stringify(jwt.decode(res.data.token)))

                                //保存token中的用户名和token
                                this.$store.commit('setUserid',tokenStr.userid)
                                this.$store.commit('setUsername',tokenStr.username)
                                this.$store.commit('setPictureUrl',tokenStr.pictureUrl)
                                this.$store.commit('setToken',res.data.token)

                                //跳转到主页
                                this.$router.push({path:'/Home'})
                                location.reload()
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            getImg(){
                alert("暂未完成")
            }
        }
    }
</script>

<style>
    .LoginDemo {
        /*圆角边框*/
        border-radius: 25px;
        /*背景绘制位置：*/
        background-clip: padding-box;
        /*边距：上下，左右*/
        margin: 180px auto;
        /*宽度*/
        width: 350px;
        /*内边距*/
        padding: 15px 35px 15px 35px;
        /*背景颜色*/
        background: white;
        /*边框*/
        border: 1px solid #eaeaea;
        /*阴影*/
        box-shadow: 0 0 25px #cac6c6;
    }

    h3 {
        text-align: center;
    }

    .code {
        width: 240px;
    }
</style>