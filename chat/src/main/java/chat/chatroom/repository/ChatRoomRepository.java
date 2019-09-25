package chat.chatroom.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.socket.WebSocketSession;

import chat.chatroom.model.ChatRoom;

@Repository
public class ChatRoomRepository {
    private final ChatRoom chatRoom;

    public ChatRoomRepository() {
        chatRoom = ChatRoom.create("API Sample 채팅");
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void remove(WebSocketSession session) {
        chatRoom.remove(session);
    }
}
