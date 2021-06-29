import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "../views/Login";
import Home from "../views/Home";
import text1 from "../views/text1";
import router from '../router'
import el from "element-ui/src/locale/lang/el";
import {Message} from 'element-ui';
import BasicInfo from "../views/sys/BasicInfo";
import employeeInfo from "../views/emp/employeeInfo";
import chat from "../views/chat/chat";


Vue.use(VueRouter)


//解决浏览器多次点击链接控制台报错问题
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
    return originalPush.call(this, location).catch(err => err)
}


export default new VueRouter({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'login',
            component: Login,
        },
        {
            path: '/home',
            name: 'home',
            component: Home,
            meta: {requireAuth:true},
            children: [
                {
                    path: '/text1',
                    name: 'text1',
                    component: text1,
                    meta: {requireAuth:true},
                },
                // {
                //     path: '/sys/basicInfo',
                //     name: '基础信息设置',
                //     component: BasicInfo,
                //     meta: {
                //         requireAuth:true,
                //         //当前菜单那个用户角色可以看到
                //         roles: ['Administrators'],
                //     },
                // },
                // {
                //     path: "/emp/employees",
                //     name: "员工信息",
                //     component: employeeInfo,
                //     meta: {requireAuth: true}
                // }
                {
                    path: '/chat',
                    name: '在线聊天',
                    component: chat,
                }
            ]
        },
    ]
})


// const router = new VueRouter({
//     routes
// })
//
// export default router

router.beforeEach((to,from,next) => {


    if (to.meta.requireAuth) { // 判断该路由是否需要登录权限
        if(localStorage.getItem('token')){ //判断本地是否存在access_token
            next();
        }else {
            if(to.path === '/'){
                next();
            }else {
                Message.error("请先登录")
                next({
                    path:'/'
                })
            }
        }
    }
    else {
        next();
    }
    /*如果本地 存在 token 则 不允许直接跳转到 登录页面*/
    if(to.fullPath === "/"){
        if(localStorage.getItem('token')){
            next({path:'/home'})
            next({
                path:from.fullPath
            });
        }else {
            next();
        }
    }
});
