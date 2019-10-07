package com.uprism.chat.chatroom.stomp;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.uprism.api.SendApi;
import com.uprism.chat.chatroom.model.ChatMessage;

@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    
    @Autowired SendApi sendApi;
    
    @Autowired
    public ChatMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/join")
    public void join(ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }
    
    @MessageMapping("/user/join")
    public void joinUser(ChatMessage message) {
    	Map<String, Object> params = new HashMap<>();
    	params.put("user_id", message.getWriter());
    	params.put("user_name", message.getWriter());
    	params.put("email", "test");
    	JSONObject user = sendApi.makeUser(params);
        message.setMessage(user.toString());
        template.convertAndSend("/subscribe/user/join/" + message.getChatRoomId() + "/user/" + message.getWriter(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }
    
    @MessageMapping("/conference/{confTitle}/")
    public void conference(ChatMessage message, @DestinationVariable String confTitle) {
    	JSONObject conf = sendApi.makeConf(message.getMessage(), confTitle, "");
    	message.setMessage((conf.get("response").toString()));
    	template.convertAndSend("/subscribe/conf/room/" + message.getChatRoomId(), message);
    }
    
    @MessageMapping("/conference/{confTitle}/{confAgenda}")
    public void conference(ChatMessage message, @DestinationVariable String confTitle, @DestinationVariable String confAgenda) {
    	JSONObject conf = sendApi.makeConf(message.getMessage(), confTitle, confAgenda);
    	message.setMessage((conf.get("response").toString()));
    	template.convertAndSend("/subscribe/conf/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/conference/join")
    public void joinConference(ChatMessage message) {
    	JSONObject conf = sendApi.joinConf(message.getWriter(), message.getMessage());
    	message.setMessage(conf.get("response").toString());
    	template.convertAndSend("/subscribe/conf/join/" + message.getChatRoomId() + "/user/" 
    	+ message.getWriter().substring(message.getWriter().indexOf(".") + 1, message.getWriter().length() - 4)
    	, message);
    }
}
