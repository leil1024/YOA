import Vue from 'vue'
import el from "element-ui/src/locale/lang/el";
import store from "../store";
import {on} from "element-ui/src/utils/dom";



let base={}
base.install = function (Vue,options) {

    let onMessageUserId = '';

    let curSessionId;

    //初始化连接，参数：
    Vue.prototype.initWebsocket = function (userId,sessionId) {
        this.websocket = new WebSocket("ws://localhost:13370/websocket/"+userId+"/"+sessionId);
        this.websocket.onopen = this.websocketonopen;
        this.websocket.onerror = this.websocketonerror;
        this.websocket.onmessage = this.websocketonmessage;
        console.log("in initwebsocket----------")
        console.log(this.websocket.readyState)
    }

    Vue.prototype.websocketonopen = function () {
        console.log("websocket连接成功");
    }

    Vue.prototype.websocketonerror = function (e) {
        console.log("websocket连接异常",e)
    }

    Vue.prototype.websocketonmessage = function (e) {
        let data =JSON.parse(e.data)
        let pictureUrl = '';
        console.log("接收到消息数据------")
        console.log(data)
        // console.log(data.fromUserId)
        store.state.sessions.forEach(res => {
            if (res.id === data.fromUserId){
                res.messages.push(data)
                onMessageUserId = res.id
            }
        })
        //如果onMessageUserId为空，则证明之前没有与该用户的聊天信息，localStorage不存在，则重新添加
        if (onMessageUserId === '' || onMessageUserId === null){
            //获取用户头像
            store.state.admins.forEach(userdata => {
                if (userdata.id === data.fromUserId){
                    pictureUrl = userdata.pictureUrl;
                }
            })
            store.state.sessions.push({
                id:data.fromUserId,
                user:{
                    name:data.fromUserName,
                    pictureUrl:pictureUrl
                },
                messages:[
                    {
                        content:data.content,
                        createTime:new Date(),
                        fromUserId:data.fromUserId
                    }
                ]
            })
        }
        //改变消息未读数量
        let backupAdmins = store.state.admins;
        //消息总数
        let unReadNUllMsgSun = ''
        let unReadMsgSum = ''
        backupAdmins.forEach(admin => {
            if (admin.id === data.fromUserId){
                if (admin.unReadCount === null || admin.unReadCount === ''){
                    admin.unReadCount = 1
                }else {
                    admin.unReadCount = admin.unReadCount + 1
                }
            }
            if (admin.unReadCount !== null){
                console.log("遍历消息数量---"+admin.unReadCount)
                unReadMsgSum = unReadMsgSum + admin.unReadCount
            }
        })
        store.commit('INIT_UNREADCOUNT',unReadMsgSum)
        console.log(store.state.unReadCount)
        store.commit('INIT_ADMINS',backupAdmins)
    }
    //判断关闭
    Vue.prototype.websocketclose = function (e) {
        if (this.$store.state.userid != null){
            if (curSessionId != null){
                this.initWebsocket(this.$store.state.userid,curSessionId)
            }else {
                this.initWebsocket(this.$store.state.userid,99999999)
            }
        }
        console.log("connection closed",e);
        console.log(e);
    }

    Vue.prototype.websocketsend = function(){
        this.websocket.send(params)
    }

    Vue.prototype.initSession = function (sessionId) {
        if (this.websocket !== undefined){
            this.websocket.close()
        }
        this.initWebsocket(this.$store.state.userid,sessionId)
        console.log("in chat.js------------")
        console.log(this.websocket.readyState)
    }
}
export default base;