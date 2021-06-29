package com.yoa.uitl;

import com.yoa.entity.TUser;
import com.yoa.controller.WebSocketController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 统一管理session、websocket、curUser
 */
public class CurPool {

//    public static CopyOnWriteArraySet<WebSocket> webSockets =new CopyOnWriteArraySet<>();
    //用于显示连接数
    public static Map<String, WebSocketController> webSockets = new ConcurrentHashMap<>();
    // list 里面第一个存sessionId，第二个存session
    public static Map<String, List<Object>> sessionPool = new ConcurrentHashMap<>();
    // 当前登录用户x
    public static Map<String, TUser> curUserPool = new ConcurrentHashMap<>();
}
