import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from "../axios"


Vue.use(Vuex)

/**
 * 使用localstorage保存用户信息
 * */
const now = new Date();

const store = new Vuex.Store({
    state: {
        userid: '' || localStorage.getItem("userid"),
        //获取用户名
        username: '' || localStorage.getItem("username"),
        //头像
        pictureUrl: '' || localStorage.getItem("pictureUrl"),
        //获取token
        token: '' || localStorage.getItem("token"),

        //菜单数据
        routes: [],
        //弹窗开关
        drawer: false,


        /**
         * 以下数据用于聊天窗口
         * */
        //保存聊天用户列表
        admins: [],
        sessions:[],
        toUserId:'',
        currentSessionId: '',
        filterKey:'',
        unReadCount:''
    },
    getters: {
        username: state => {
            if (!state.username){
                state.username = localStorage.getItem("username")
            }
            return state.username
        },
        token: state => {
            if (!state.token){
                state.token = localStorage.getItem("token")
            }
            return state.token
        },
        userid: state => {
            if (!state.userid){
                state.userid = localStorage.getItem("userid")
            }
        },
        pictureUrl: state => {
            if (!state.pictureUrl){
                state.pictureUrl = localStorage.getItem("pictureUrl")
            }
        }
    },
    mutations: {
        setUsername(state, data) {
            state.username = data
            localStorage.setItem("username", data)
        },
        setToken(state, data) {
            state.token = data
            localStorage.setItem("token", data)
        },
        setUserid(state,data){
            state.userid = data
            localStorage.setItem("userid",data)
        },
        setPictureUrl(state,data){
            state.pictureUrl = data
            localStorage.setItem("pictureUrl",data)
        },
        initRoutes(state,data) {
            state.routes = data
        },
        clean(state){
            for (let i in state){
                delete state[i]
            }
        },

        /**
         * 以下数据用于聊天窗口
         **/
        changeCurrentSessionId (state,id) {
            state.currentSessionId = id;
        },
        addMessage (state,msg) {
            console.log(state.sessions[state.currentSessionId])
            let userIdture = ''
            let name = ''
            let pictureUrl = ''
            state.sessions.forEach(res => {
                if (res.id === state.currentSessionId){
                    userIdture = res.id
                    res.messages.push({
                        content:msg,
                        createTime: new Date(),
                    })
                }
            })
            if (userIdture === '' || userIdture === null){
                state.admins.forEach(userdate => {
                    if (userdate.id === state.currentSessionId){
                        name = userdate.username
                        pictureUrl = userdate.pictureUrl
                    }
                })
                state.sessions.push({
                    id:state.currentSessionId,
                    user:{
                        name:name,
                        pictureUrl:pictureUrl
                    },
                    messages:[
                        {
                            content:msg,
                            createTime:new Date(),
                        }
                    ]
                })
            }
        },
        addMsgInfo(state,msg){
            state.sessions.forEach(res => {
                if (res.id === msg.fromUserId){
                    res.messages.push(msg)
                }
            })
        },
        INIT_DATA (state) {
            let data = localStorage.getItem('vue-chat-session');
            //console.log(data)
            if (data) {
                state.sessions = JSON.parse(data);
            }
        },
        INIT_ADMINS(state,data){
            state.admins = data;
        },
        INIT_UNREADCOUNT(state,data){
            state.unReadCount = data
        }
    },
    actions: {
        setUsername(context) {
            context.commit('setUsername')
        },
        setToken(context) {
            context.commit('setToken')
        },
        setUserid(context){
            context.commit('setUseridt')
        },
        setPictureUrl(context){
            context.commit('setPictureUrl')
        },
        setMsgInfo(context){
            context.commit('addMsgInfo')
        },
        /**
         * 以下数据用于聊天窗口
         * */
        connetc(context){

        },
        initData (context) {
            getRequest("/user/elseUser?userId="+store.state.userid).then(res => {
                console.log(res)
                if (res.data){
                    context.commit('INIT_ADMINS',res.data)
                    console.log("获取到用户列表的其他用户------")
                    console.log(res.data)
                }
            })
            context.commit('INIT_DATA')
        }
    },
    modules: {}
})

store.watch(function (state) {
    return state.sessions
},function (val) {
    console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
},{
    deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
})


export default store;
