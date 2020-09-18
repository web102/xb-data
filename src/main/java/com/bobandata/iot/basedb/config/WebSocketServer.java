package com.bobandata.iot.basedb.config;

import com.alibaba.fastjson.JSONObject;
import com.bobandata.iot.transport.protocol.IMasterProtocol;
import com.bobandata.iot.transport.util.TaskParam;
import com.bobandata.iot.transport.util.WebMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Constructor;

@ServerEndpoint("/websocket")
@Component
public class WebSocketServer {
    private Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private static WebSocketServer  WebSocketServer ;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private TaskParam taskParam;
    private IMasterProtocol masterProtocol;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) throws IOException {
        if(this.session!=null){
            session.close();
        }
        this.session = session;
        addOnlineCount();           //在线数加1
        logger.info("连接前段成功");
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        subOnlineCount();           //在线数减1
        logger.info("前端连接减1");
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if(message.equals("exit")){
            this.taskParam.getChannel().disconnect();
            this.session.close();
        }else if(message.equals("stop")){
//            this.masterProtocol.
        } else if(taskParam==null){
            //通过websocke接收任务参数
            TaskParam taskParam = JSONObject.parseObject(message, TaskParam.class);
            try {
                //通过消息中的规约对象，初始化一个实例
                Class clazz = Class.forName(taskParam.getRestPath());
                Constructor constructor =  clazz.getDeclaredConstructor(TaskParam.class, Session.class);
                this.masterProtocol = (IMasterProtocol) constructor.newInstance(taskParam,this.session);
                session.getBasicRemote().sendText((new WebMessage(WebMessage.Code.NORMAL.getCode(),"终端连接成功!")).toJson());
                this.taskParam=this.masterProtocol.getTaskParam();
            } catch (Exception e) {
                e.printStackTrace();
                if(this.taskParam!=null&&this.taskParam.getChannel()!=null){
                    taskParam.getChannel().disconnect();
                }
                session.getBasicRemote().sendText((new WebMessage(WebMessage.Code.ERROR.getCode(),"终端连接失败!")).toJson());
                session.close();
                return;
            }
            try {
                this.masterProtocol.executeTask(taskParam);
            } catch (Exception e) {
                logger.error("masterProtocol executeTask fail");
                e.printStackTrace();
                taskParam.getChannel().disconnect();
                session.getBasicRemote().sendText((new WebMessage(WebMessage.Code.ERROR.getCode(),"终端运行异常!")).toJson());
                session.close();

            }
        }
    }


    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}