package address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "address,api")
public class AddressApplication extends ServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(AddressApplication.class, args);
	}
}
