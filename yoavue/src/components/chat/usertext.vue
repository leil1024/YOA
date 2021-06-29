<template>
  <div id="uesrtext">
    <textarea placeholder="按 Ctrl + Enter 发送" v-model="content" v-on:keyup="addMessage"></textarea>
<!--      <el-input placeholder="按 Enter 发送" @keyup.enter.native = "addMessage" v-model="content"></el-input>-->
<!--      <textarea placeholder="按  Enter 发送" v-model="content" @keyup.enter.native="addMessage"></textarea>-->
  </div>
</template>

<script>
import {mapState} from 'vuex'


export default {
  name: 'uesrtext',
  data () {
    return {
      content:'ksbdjas',
      list: [],
    }
  },
  methods: {
    //消息发送
  	addMessage () {
      console.log("键盘触发成功")
      if (this.$store.state.toUserId === ''){
          this.$message.error("请选择左边的用户进行聊天！")
      }else {
        let data = {
          "fromUserId": this.$store.state.userid,
          "fromUserName": this.$store.state.username,
          "context": this.context
        }
        this.list.push(data)
        // this.websocket.send("asdawd")
        console.log(this.websocket.readyState)
        this.content = ''
      }

  	}
  },
}
</script>

<style lang="scss" scoped>
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
