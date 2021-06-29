<template>
  <div id="list">
  	<ul style="padding-left: 0px">
  		<li v-for="item in admins" :class="{ active: item.id === currentSessionId }" v-on:click="changeCurrentSessionId(item.id,item.username)"><!--   :class="[item.id === currentSessionId ? 'active':'']" -->
  			<img class="avatar" :src="item.pictureUrl" :alt="item.username">
  			<p class="name">{{item.username}}</p>
  		</li>
  	</ul>
  </div>
</template>

<script>
import {mapState} from 'vuex'
import {postRequest} from "../../axios";


export default {
  name: 'list',
  data () {
    return {
      
    }
  },
  computed: mapState([
  'sessions',
  'admins',
  'currentSessionId'
	]),
  methods:{
  	//在点击接受者的时候创建会话并开始会话
  	changeCurrentSessionId:function (toUserId,toUserName) {
  		//存储被选中用户的ID，判断消息是否有接收者
  		this.$store.state.toUserId = toUserId
		console.log("点击成功，准备建立会话----")
		//
		var fromdata = new FormData();
		fromdata.append("userId",this.$store.state.userid)
		fromdata.append("toUserId",toUserId)
		fromdata.append("listName",toUserName)
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
		this.initWebsocket(this.$store.state.userid,sessionId)
		 console.log("---------------------")
		 console.log(this.websocket.readyState)
		 console.log("---------------------")
		this.msgList(sessionId)
	 },

	  //获取消息数据
	  msgList(sessionId) {

	  }
  }
}
</script>

<style lang="scss" scoped>
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
</style>
