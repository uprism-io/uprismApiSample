package chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class UprismApiSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(UprismApiSampleApplication.class, args);
	}

	@Controller
	public static class MainController {
		@GetMapping("/")
		public String main() {
			return "redirect:/chat/roomsEnter";
		}
	}

}
