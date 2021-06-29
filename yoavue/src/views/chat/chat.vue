<template>
    <div id="app">
        <div class="sidebar">


<!--            <card></card>-->
            <div id="card">
                <header>
                    <img class="avatar" v-bind:src="user.img" v-bind:alt="user.name">
                    <p class="name">{{user.name}}</p>
                </header>
                <footer>
                    <input class="search" type="text" v-model="$store.state.filterKey" placeholder="search user...">
                </footer>
            </div>


<!--            <list></list>-->
            <div id="list">
                <ul style="padding-left: 0px">
                    <li v-for="item in admins" :class="{ active: item.id === currentSessionId }" v-on:click="changeCurrentSessionId(item.id,item.username)"><!--   :class="[item.id === currentSessionId ? 'active':'']" -->
                        <img class="avatar" :src="item.pictureUrl" :alt="item.username">
                        <p class="name">{{item.username}}</p>
                        <el-badge  :value="item.unReadCount" class="item" style="margin-top: 8%;float: right;"></el-badge>
                    </li>
                </ul>
            </div>
        </div>


        <div class="main">


<!--            <message></message>-->
            <div id="message" v-scroll-bottom="sessions">
                <ul v-if="currentSessionId==item.id" v-for="item in sessions">
                    <li v-for="entry in item.messages">
                        <p class="time">
                            <span>{{entry.createTime | time}}</span>
                        </p>
                        <div class="main" :class="{self:entry.fromUserId !== currentSessionId}">
                            <img class="avatar" :src="entry.fromUserId === currentSessionId ? item.user.pictureUrl : img" alt="">
                            <p class="text">{{entry.content}}</p>
                        </div>
                    </li>
                </ul>
            </div>


<!--            <usertext></usertext>-->
            <div id="uesrtext">
                <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
<!--                <el-input placeholder="按 Enter 发送" @keyup.enter.native = "addMessage" v-model="content"></el-input>-->
            </div>
        </div>
    </div>
</template>

<script>
    import card from '../../components/chat/card'
    import list from '../../components/chat/list.vue'
    import message from '../../components/chat/message.vue'
    import usertext from '../../components/chat/usertext.vue'
    import {mapState} from "vuex";
    import {getRequest, postRequest} from "../../axios";

    export default {
        name: 'chat',
        data () {
            return {
                user: {
                    name: this.$store.state.username,
                    img: this.$store.state.pictureUrl
                },
                img: this.$store.state.pictureUrl,
                content:'',
                //消息列表
                list: [],
            }
        },
        computed: mapState([
            'sessions',
            'admins',
            'currentSessionId'
        ]),

        //过滤器，格式化消息发送时间
        filters:{
            time (date) {
                if (date) {
                    date = new Date(date);
                    return `${date.getHours()}:${date.getMinutes()}`;
                }

            }
        },
        directives: {/*这个是vue的自定义指令,官方文档有详细说明*/
            // 发送消息后滚动到底部,这里无法使用原作者的方法，也未找到合理的方法解决，暂用setTimeout的方法模拟
            'scroll-bottom' (el) {
                //console.log(el.scrollTop);
                setTimeout(function () {
                    el.scrollTop+=9999;
                },1)
            }
        },

        methods:{
            //在点击接受者的时候创建会话并开始会话
            changeCurrentSessionId:function (toUserId,toUserName) {
                //存储被选中用户的ID，判断消息是否有接收者
                this.$store.state.toUserId = toUserId
                this.$store.state.currentSessionId = toUserId
                console.log("点击成功，准备建立会话----")

                var fromdata = new FormData();
                fromdata.append("userId",this.$store.state.userid)
                fromdata.append("toUserId",toUserId)
                fromdata.append("listName",toUserName)

                let backupAdminsTwo = this.$store.state.admins
                let unReadCount = this.$store.state.unReadCount
                backupAdminsTwo.forEach(admin => {
                    if (admin.id === toUserId){
                        unReadCount = unReadCount-admin.unReadCount
                        admin.unReadCount = ''
                    }
                })
                this.$store.commit("INIT_UNREADCOUNT",unReadCount)

                //返回接收者的SessionID
                postRequest("/createSession",fromdata).then(res => {
                    console.log("会话建立成功，返回sessionID------"+res.data)
                    //开始会话
                    this.startSession(res.data)
                })
            },


            //开始会话
            startSession(sessionId){
                console.log("已点击用户，准备开始会话------")
                console.log("获取到sessionId------"+sessionId)
                if (this.websocket!==undefined){
                    this.websocket.close();
                }

                /**
                 * 与服务器建立连接，参数：当前登陆用户的ID、会话连接信息ID(后端数据表t_sessionList)
                 * */
                console.log("获取vue-chat-session-------")
                console.log(localStorage.getItem("vue-chat-session"))
                this.initWebsocket(this.$store.state.userid,sessionId)
                if (localStorage.getItem("vue-chat-session") === null){
                    console.log('获取后端聊天数据------')
                    this.msgList(sessionId)
                }
            },

            //获取消息数据
            msgList(sessionId) {
                console.log("拿到sessionId,获取消息数据----------")
                getRequest("/msgInfo/msgList?sessionId="+sessionId).then(res => {
                    console.log(res)
                    this.$store.state.sessions = res.data
                })
            },

            //添加消息
            addMessage (e) {
                if (e.ctrlKey && e.keyCode ===13 && this.content.length) {
                    console.log("键盘触发成功")
                    if (this.$store.state.toUserId === '') {
                        this.$message.error("请选择左边的用户进行聊天！")
                    } else {
                        let data = {
                            "fromUserId": this.$store.state.userid,
                            "fromUserName": this.$store.state.username,
                            "context": this.context
                        }
                        // this.list.push(data)
                        this.websocket.send(this.content)

                        this.$store.commit('addMessage', this.content);
                        this.content = ''
                    }
                }

            }
        },
        mounted:function() {
            this.$store.dispatch('initData');
        },
        components:{
            card,
            list,
            message,
            usertext
        }
    }
</script>

<style lang="scss" scoped>
    #app {
        border-style:solid;
        border-width:1px;
        margin-top: -15px;
        width: 800px;
        height: 500px;
        overflow: hidden;
        border-radius: 10px;
        .sidebar, .main {
            height: 100%;
        }
        .sidebar {
            float: left;
            color: #f4f4f4;
            background-color: #2e3238;
            width: 200px;
        }
        .main {
            position: relative;
            overflow: hidden;
            background-color: #eee;
        }
    }
    #card {
        padding: 12px;
        .avatar{
            width: 40px;
            height: 40px;
            vertical-align: middle;/*这个是图片和文字居中对齐*/
        }
        .name {
            display: inline-block;
            padding: 10px;
            margin-bottom: 15px;
            font-size: 16px;
        }
        .search {
            background: #26292E;
            height: 30px;
            width: 88%;
            line-height: 30px;
            padding: 0 10px;
            border: 1px solid #3a3a3a;
            border-radius: 4px;
            outline: none;/*鼠标点击后不会出现蓝色边框*/
            color: #FFF;
        }
    }
    #list {
        li {
            padding: 2px 15px;
            border-bottom: 1px solid #292C33;
            cursor: pointer;
            &:hover {
                background-color: rgba(255, 255, 255, 0.03);
            }
        }
        li.active {/*注意这个是.不是冒号:*/
            background-color: rgba(255, 255, 255, 0.1);
        }
        .avatar {
            border-radius: 2px;
            width: 30px;
            height: 30px;
            vertical-align: middle;
        }
        .name {
            display: inline-block;
            margin-left: 15px;
        }
    }
    #message {
        padding: 15px;
        max-height: 68%;
        overflow-y: scroll;
        ul {
            list-style-type: none;
            padding-left: 0px;
            li {
                margin-bottom: 15px;
            }
        }
        .time {
            text-align: center;
            margin: 7px 0;
            > span {
                display: inline-block;
                padding: 0 18px;
                font-size: 12px;
                color: #FFF;
                background-color: #dcdcdc;
                border-radius: 2px;
            }
        }
        .main {
            .avatar {
                float: left;
                margin: 0 10px 0 0;
                border-radius: 3px;
                width: 30px;
                height: 30px;

            }
            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #fafafa;
                border-radius: 4px;
                line-height: 30px;
            }
        }
        .self {
            text-align: right;
            .avatar {
                float: right;
                margin: 0 0 0 10px;
                border-radius: 3px;
                width: 30px;
                height: 30px;
            }
            .text {
                display: inline-block;
                padding: 0 10px;
                max-width: 80%;
                background-color: #b2e281;
                border-radius: 4px;
                line-height: 30px;
            }
        }
    }
    #uesrtext {
        position: absolute;
        bottom: 0;
        right: 0;
        width: 100%;
        height: 30%;
        border-top: solid 1px #DDD;
        > textarea {
            padding: 10px;
            width: 100%;
            height: 100%;
            border: none;
            outline: none;
        }
    }
</style>