package com.yoa.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yoa.entity.TChat;
import com.yoa.entity.TMsgInfo;
import com.yoa.entity.TSessionList;
import com.yoa.entity.TUser;
import com.yoa.service.ITMsgInfoService;
import com.yoa.service.ITSessionListService;
import com.yoa.service.ITUserService;
import com.yoa.uitl.CurPool;
import com.yoa.uitl.JsonUtils;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@Slf4j
@RestController
@ServerEndpoint("/websocket/{userId}/{sessionId}")
public class WebSocketController {


    private static ITSessionListService itSessionListService;

    private static ITUserService itUserService;

    private static ITMsgInfoService itMsgInfoService;

    /**
     * spring管理的是单例，和websocket的多对象冲突：
     * 当项目初始化时，会初始化websocket，spring会为其注入mapper，成功
     * 当有新的用户进行连接时，系统又会创建一个新的websocket对象,
     * spring管理的都是单例，不会给第二个websocket注入mapper，导致失败
     *
     * controller、service、dao等都是单例
     * */
    @Autowired
    public void setItSessionListService(ITSessionListService itSessionListService){
        WebSocketController.itSessionListService = itSessionListService;
    }

    @Autowired
    public void setItUserService(ITUserService itUserService){
        WebSocketController.itUserService = itUserService;
    }

    @Autowired
    public void setItMsgInfoService(ITMsgInfoService itMsgInfoService){
        WebSocketController.itMsgInfoService = itMsgInfoService;
    }



    private Session session;


    /**
     * websocket连接
     * 参数：
     *      @param  session：websocket连接信息，将将上传的参数封装进session，全局变量
     *      @param  userId：当前登陆用户的ID
     *      @param  sessionId：t_sessionList表中的会话连接ID，记录消息走向
     *
     * 定义一个map(websocket)，保存当前登陆的用户ID和当前对象，记录用户连接数量
     * 定义一个map(session)，保存当前用户的消息的会话连接ID和当前websocket的连接session
     * */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId,@PathParam(value = "sessionId") String sessionId){
        this.session = session;

        //获取当前的连接数量
        int websocketNumber = CurPool.webSockets.size();

        //登录的时候将用户信息保存，计算连接数量
        CurPool.webSockets.put(userId,this);

        //员工登陆的话会连接websocket，触发OnOpen方法，保存连接信息
        List<Object> list = new ArrayList<>();
        list.add(sessionId);
        list.add(session);
        CurPool.sessionPool.put(userId,list);
        if (CurPool.webSockets.size() == websocketNumber){
            log.info("【websocket消息】当前用户连接总数为："+CurPool.webSockets.size());
        }else {
            log.info("【websocket消息】有新的连接，连接总数为："+CurPool.webSockets.size());
        }
    }


    /**
     * 消息接收
     * @param message：当前用户发送的消息内容
     *
     * 获取当前用户连接时的websocketSession，在websocketSession中获取创建的消息会话的ID(确定发送者和接收者)
     * 根据会话ID，获取发送者与接收者
     * 将发送的消息保存在数据库中
     * 判断我方与接收方是否存在会话(t_sessionList)，如果有，则发送消息
     * */
    @OnMessage
    public void onMessage(String message){
        //获取连接时的上传的sessionId
        String sessionId = this.session.getRequestParameterMap().get("sessionId").get(0);
        System.out.println(sessionId);
        if (sessionId == null){
            log.error("sessionID 错误");
        }else {
            //根据sessionId获取sessionList的信息
            //此Session是：我方_to_接收方 的Session记录
            TSessionList byId = itSessionListService.getById(sessionId);
            System.out.println("sessionId:"+sessionId);
            TSessionList tSessionList = itSessionListService.getOne(new QueryWrapper<TSessionList>().eq("obj_id", sessionId));
            //获取发送者
            TUser fromUser = itUserService.getById(tSessionList.getUserId());
            //获取接收者
            TUser toUser = itUserService.getById(tSessionList.getToUserId());

            //消息持久化
            TMsgInfo tMsgInfo = new TMsgInfo();
            String id = UUID.randomUUID().toString().replaceAll("-","");
            tMsgInfo.setObjId(id);
            tMsgInfo.setFromUserId(tSessionList.getUserId());
            tMsgInfo.setFromUserName(fromUser.getUsername());
            tMsgInfo.setToUserId(tSessionList.getToUserId());
            tMsgInfo.setToUserName(toUser.getUsername());
            tMsgInfo.setContent(message);
            tMsgInfo.setUnReadFlag(0);
            itMsgInfoService.save(tMsgInfo);

            TMsgInfo msgInfo = itMsgInfoService.getOne(new QueryWrapper<TMsgInfo>().eq("obj_id",id));
            System.out.println(msgInfo.getCreateTime());

            List<Object> list = CurPool.sessionPool.get(tSessionList.getToUserId());
            //获取对方_to_我方的会话连接
            TSessionList sessionList = itSessionListService.getOne(new QueryWrapper<TSessionList>().eq("user_id", tSessionList.getToUserId()).eq("to_user_id", tSessionList.getUserId()));

            //设置消息未读数量，暂时无用，写在前端
//            itSessionListService.update(new UpdateWrapper<TSessionList>().set("un_read_count",sessionList.getUnReadCount()+1).eq("obj_id",tSessionList.getObjId()));
            //发送消息，接收方的消息ID，接收内容
            sendTextMessage(sessionList.getUserId(), JsonUtils.objectToJson(msgInfo));
        }
        log.info("【websocket消息】接收到客户端消息："+ message);

    }

    //消息发送
    public void sendTextMessage(String userId, String message) {
        Session session = (Session)CurPool.sessionPool.get(userId).get(1);
        String sessionId = session.getRequestParameterMap().get("sessionId").get(0);
        String userid = session.getRequestParameterMap().get("userId").get(0);
        System.out.println("获取到sessionId："+sessionId);
        System.out.println("获取到userId："+userid);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}




















