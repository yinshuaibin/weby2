package com.ferret.socket.interceptor;

import com.ferret.bean.User;
import com.ferret.utils.ConstantsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 * 对websocket连接状态的判断和处理
 * @author cc;
 * @since 2018/3/19;
 * @version 1.0
 */
@Slf4j
@Component
public class ConnectedChannelInterceptor extends ChannelInterceptorAdapter {

    /**
     * 在信息发送到channel之前触发
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand stompCommand = accessor.getCommand();
        if (stompCommand == null) {
            return null;
        }
        //这里的map对应HandshakeInterceptor拦截器的attributes
        Map<String,Object> map = accessor.getSessionAttributes();
        String username = ((User)map.get(ConstantsUtils.SESSION_USER)).getUsername();
        // String accountId = accessor.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();
       // 判断客户端的连接状态
        switch (accessor.getCommand()) {
            case CONNECT:
                log.info("{}发送连接请求................",username);
                // connect(username);
                break;
            case CONNECTED:
                log.info("{}连接成功...................",username);
                break;
            case DISCONNECT:
                log.info("{}断开连接....................",username);
                //disconnect(sessionId, accountId, accessor);
                break;
            case SUBSCRIBE:
                log.info("用户{},订阅了{}",username,accessor.getDestination());
                break;
            default:
                break;
        }
        return message;
    }

    /**
     * 在信息发送到channel后立即触发
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

    }

    /**
     * 在发送信息结束后触发
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        super.afterSendCompletion(message, channel, sent, ex);
    }

    @Override
    public boolean preReceive(MessageChannel channel) {
        return super.preReceive(channel);
    }

    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        return super.postReceive(message, channel);
    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        super.afterReceiveCompletion(message, channel, ex);
    }


    //连接成功
    private void connect(String sessionId, String accountId) {
        log.debug(" STOMP Connect [sessionId: " + sessionId + "]");
        // -- 伪代码------
        // 存放至redis等缓存.
        // 若在多个浏览器登录，直接覆盖保存
        // redis.put(cacheName ,cacheName+accountId,sessionId);
    }

    //断开连接
    private void disconnect(String sessionId, String accountId, StompHeaderAccessor sha) {
//        logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
//        sha.getSessionAttributes().remove(ConstantsUtils.SESSIONID);
//        sha.getSessionAttributes().remove(ConstantsUtils.SKEY_ACCOUNT_ID);
//        //ehcache移除
//        String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
//        if (CacheManager.containsKey(cacheName,cacheName+accountId) ){
//            CacheManager.remove(cacheName ,cacheName+accountId);
//        }
    }
}
