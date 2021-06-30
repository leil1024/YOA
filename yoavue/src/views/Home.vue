<template>
    <el-container>
        <el-header class="homeHeader">
            <div class="title">XXX</div>
            <el-badge :value="this.$store.state.unReadCount" class="item" style="position:relative;left: 35%;top: 15%">
                <i class="el-icon-chat-dot-round" style="font-size: 30px" @click="goChat()"/>
            </el-badge>
            <el-dropdown @command="handleCommand">
                  <span class="el-dropdown-link">
                      <span style="position:relative;top: -16px;margin-left: 10px;font-size: 15px">{{$store.state.username}}</span>
                      <el-avatar style="margin-left: 10px;position:relative;top: 3px" :src="this.$store.state.pictureUrl" :size="50"></el-avatar>
                  </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                    <el-dropdown-item command="setup">设置</el-dropdown-item>
                    <el-dropdown-item command="logout">注销</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-header>
        <el-container>
            <el-aside width="200px">
                <el-menu router
                        default-active="2"
                        class="el-menu-vertical-demo"
                        :unique-opened="true"
                        @select="menuClick">
                    <el-submenu :index="item.objId"
                                v-for="(item,index) in routes":key="index"
                                v-if="!item.hidden">
                        <template slot="title">
                            <i :class="item.icon"></i>
                            <span>{{item.name}}</span>
                        </template>
                        <el-menu-item :index="children.path"
                                      v-for="(children,indexj) in item.children" :key="indexj">
                            {{children.name}}
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-main>
                <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                    <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                    <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                </el-breadcrumb>
                <el-divider><i class="el-icon-s-promotion"></i></el-divider>
                <div v-if="this.$route.path==='/home'">
                    <Carousel/>
                </div>
                <div v-else>
                    <router-view/>
                </div>
            </el-main>
        </el-container>

    </el-container>
</template>

<script>
    import chat from "./chat/chat";
    import Carousel from "../components/home/Carousel";
    export default {
        name: "Home",
        components:{
          chat,
          Carousel
        },
        computed:{
            routes(){
                return this.$store.state.routes;
            }
        },
        data(){
            return{
                user:{
                    // username: this.$store.getters.username,
                    // avatar: '' || this.$store.getters.avatar,
                    // userdata: '' || this.$store.getters.userData,
                    // username: this.userdata.username
                },
                dialogTableVisible: false,
                formLabelWidth: '120px',
                unReadCount: this.$store.state.unReadCount
            }
        },
        methods: {
            menuClick(index){
                this.$router.push({path:index})
            },
            handleCommand(command){
                if (command === 'logout'){
                    //清空localStorage中的所有值
                    localStorage.clear();
                    //清空用户菜单
                    this.$store.commit('clean')
                    this.$router.push({path:'/'})
                }
            },
            goChat(){
                this.$router.push({path:'/chat'})
                console.log(this.$store.state.userData)
            }
        },
        mounted() {
            console.log(this.$route.path)
            this.initSession(99999999)
            this.$store.dispatch('initData');
        }
    }
</script>

<style>

    .el-dialog__body{
        width: 800px;
        height: 600px;
    }

    .homeHeader{
        background: cyan;
        display: flex;
        /*所有元素居中对齐*/
        align-items: center;
        justify-content: space-between;
        padding: 0 15px;
        box-sizing: border-box;
    }
    .title{
        color: white;
        font-size: 30px;
        font-family: 华文楷体;
    }
    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }
    .el-icon-arrow-down {
        font-size: 12px;
    }

    /*.el-carousel__container {*/
    /*    position: relative;*/
    /*    height: 440px;*/
    /*}*/
</style>