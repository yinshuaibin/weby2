package com.ferret.socket.interceptor;

import com.ferret.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 拦截器,对websocket请求的握手前后的动作进行处理.
 * @author cc;
 * @since 2018/3/17;
 */
@Slf4j
@Component
public class WebSocketInterceptor implements HandshakeInterceptor {



    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        if (request instanceof ServletServerHttpRequest){
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            if (session != null){
                //使用username区分WebSocketHandler，以便定向发送消息
               User user = (User) session.getAttribute("user");
               attributes.put("user",user);
               return true;
            }else{
                throw new RuntimeException("尚未登录错误!");
            }
        }else {
            throw new RuntimeException("请求方式错误!");
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }

}
