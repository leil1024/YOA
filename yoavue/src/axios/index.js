/**
 * 配置前端的请求和响应拦截
 *
 * 1.响应拦截
 * 后端封装一个返回响应信息的类
 * 类中包含：状态码、提示信息、返回携带的数据
 * 使用element中的Message的模块进项弹窗提示
 * 如果能够能够正确的请求到后端：
 *      如果后端的业务逻辑不允许这样操作或者报错，提示错误信息
 *      如果后端的业务逻辑处理正常，提示正确信息，或者不提示
 * 如果因为服务器原因未能访问到后端接口
 *      根据状态码提示信息
 *      未知原因提示信息
 *
 * 2.请求
 *      配置post请求
 * */

/**
 * *******************************************拦截器已调用，根据后端返回信息修改使用***************************************
 * */

import axios from "axios";
import {Message} from 'element-ui';
import router from "../router";
import ElementUI from 'element-ui';
import da from "element-ui/src/locale/lang/da";


axios.defaults.baseURL = 'http://localhost:13370'

//处理响应：success为响应包含的所有信息
axios.interceptors.response.use(success => {
    // console.log(success.data.message)
        // 如果可以请求到后端业务
        if (success.status === 200) {
            // 但是后端业务逻辑处理报错
            if (success.data.code === 500 || success.data.code === 400 || success.data.code === 403) {
                //提示返回的封装信息
                // Message.error({message: success.data.message})
                // this.$message(success.data.message)
                // console.log(success.data)
                // console.log(success.data.data)
                //未生效
                Message.error({message: success.data.message})
                return success.data;
            }
            //如果所有业务逻辑正确，提示正确信息并
            if (success.data.code === 200 && success.data.status) {
                // console.log(success.data)
                // console.log(success.data.message)
                if (success.data.message != null){
                    Message.success({message: success.data.message})
                    // location.reload();
                }
                return  success.data
            }
        }else {
            return success.data
        }
    },
    //如果未能正确请求到后端接口
    error => {
        //因为服务器原因报错
        if (error.response.status === 504 || error.response.status === 404) {
            Message.error({message: "服务器异常o(╥﹏╥)o"})
        } else if (error.response.status === 403) {
            Message.error({message: "权限不住(⊙︿⊙)"})
        } else if (error.response.status === 401) {
            Message.error({message: "请登录(个_个)"})
            //跳转到登陆界面
            router.replace("/")
        } else {
            if (error.response.data.message) {
                Message.error({message: error.response.data.message})
            } else {
                Message.error({message: "未知错误(灬ꈍ ꈍ灬)"})
            }
        }
    })

//post请求
//携带url和请求参数
export const postRequest = (url, parmas) => {
    return axios({
        method: 'post',
        url: url,
        data: parmas,
        headers: {
            token: localStorage.getItem("token")
        }
    })
}

//get请求
export const getRequest = (url) => {
    return axios({
        method: 'get',
        url: url,
        headers:{
            token: localStorage.getItem("token")
        }
    })
}


