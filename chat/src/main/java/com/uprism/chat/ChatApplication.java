package com.uprism.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication(scanBasePackages = "com.uprism.api,com.uprism.chat")
public class ChatApplication extends ServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@Controller
	public static class MainController {
		
		@GetMapping("/")
		public String main() {
			return "redirect:/roomsEnter";
		}
	}

}
