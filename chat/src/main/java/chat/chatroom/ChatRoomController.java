package chat.chatroom;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import chat.chatroom.model.ChatRoom;
import chat.chatroom.repository.ChatRoomRepository;

@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository repository;
    private final String enterViewName;
    private final String detailViewName;
    private final AtomicInteger seq = new AtomicInteger(0);

    @Autowired
    public ChatRoomController(ChatRoomRepository repository, @Value("${viewname.chatroom.enter}") String enterViewName, @Value("${viewname.chatroom.detail}") String detailViewName) {
        this.repository = repository;
        this.enterViewName = enterViewName;
        this.detailViewName = detailViewName;
    }
    
    @GetMapping("/roomsEnter")
    public String roomEnter(Model model) {
        model.addAttribute("id", repository.getChatRoom().getId());
        return enterViewName;
    }

    @GetMapping("/rooms/{id}")
    public String room(@PathVariable String id, Model model) {
        ChatRoom room = repository.getChatRoom();
        model.addAttribute("room", room);
        model.addAttribute("member", "member" + seq.incrementAndGet());
        return detailViewName;
    }

    @GetMapping("/rooms/{id}/{memberName}")
    public String room(@PathVariable String id, @PathVariable String memberName, Model model) {
        ChatRoom room = repository.getChatRoom();
        model.addAttribute("room", room);
        model.addAttribute("member", memberName);
        return detailViewName;
    }
}
