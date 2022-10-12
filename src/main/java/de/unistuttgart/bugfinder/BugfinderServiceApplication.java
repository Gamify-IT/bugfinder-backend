package de.unistuttgart.bugfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BugfinderServiceApplication {
    public static final String GAME_NAME = "BUGFINDER";

    public static void main(String[] args) {
        SpringApplication.run(BugfinderServiceApplication.class, args);
    }
}
