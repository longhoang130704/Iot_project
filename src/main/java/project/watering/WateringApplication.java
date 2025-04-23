package project.watering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WateringApplication {

	public static void main(String[] args) {
		SpringApplication.run(WateringApplication.class, args);
		System.out.println("Hello world!");

	}

}
