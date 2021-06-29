import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './axios/index';
import axios from "axios";
import jwt from 'jsonwebtoken';
import {initMenu} from "./uitls/menu";
import Chat from './uitls/chat';

//关闭浏览器端console中的消息提示
Vue.config.productionTip = false
Vue.prototype.$axios = axios
Vue.use(ElementUI)
Vue.use(router)
Vue.use(Chat);

router.beforeEach((to, from, next) => {
    if (window.localStorage.getItem("token")){
        initMenu(router,store);
        next();
    }
    else {
        if (to.path === '/'){
            next();
        }
    }

})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
