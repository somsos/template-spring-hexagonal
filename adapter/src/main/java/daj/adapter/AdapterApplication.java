package daj.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@SpringBootApplication(
	scanBasePackages = {
		"daj.product",
		"daj.user",
		"daj.adapter.product",
		"daj.adapter.common",
		"daj.adapter.user",
		"daj.adapter.errorHandler",
	}
)
@RestController
public class AdapterApplication {

	public static void main(String[] args) {
		log.info("Some fix to my error.");
		SpringApplication.run(AdapterApplication.class, args);
	}

	@GetMapping("/test")
	public Map<String, String> test() {
		return Map.of("message", "-2222 Some random change 2222-");
	}

}
