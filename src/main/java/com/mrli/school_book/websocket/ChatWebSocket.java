package com.mrli.school_book.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/chat/{userid}")
@Component
public class ChatWebSocket {

    private  static Map<String, ChatWebSocket> USER_SOCKET = new HashMap<>();
    private  static Map<String, Session> USER_SESSION = new HashMap<>();


    @OnOpen
    public void open(Session session, @PathParam("userid")String  userid) {
        System.out.println("用户"+userid+" 登录");
        USER_SOCKET.put(userid, this);
        USER_SESSION.put(userid, session);
    }

    @OnMessage
    public void message(Session session, String message, boolean last) throws IOException {
        System.out.println("sessionId:"+session.getId()+"-------"+message+"------"+last);
        // session.getBasicRemote().sendText("接受成功" + message);
        if (!message.equals("hello")) {

        }
    }



}
