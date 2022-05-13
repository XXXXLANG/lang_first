package com.example.livesystemapp.service.WebSocket;

import com.example.livesystemapp.util.Param.MyToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.net.URI;

@Component
@Slf4j
public class WSClient {

    public static MyWebSocketClient webSocketClient;

    public static void startWS() {
        try {
            if (WSClient.webSocketClient != null) {
                WSClient.webSocketClient.close();
            }
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //设置消息大小最大为10M
            container.setDefaultMaxBinaryMessageBufferSize(10*1024*1024);
            container.setDefaultMaxTextMessageBufferSize(10*1024*1024);
            // 客户端，开启服务端websocket。
            String uri = "ws://127.0.0.1:8080/websocket/"+new MyToken().getToken();
            webSocketClient = new MyWebSocketClient(URI.create(uri));
            webSocketClient.connect();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
    }
}