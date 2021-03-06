package top.chokhoou.healthcardjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@EnableAsync
@EnableRetry
@EnableScheduling
@SpringBootApplication
public class HealthCardJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthCardJobApplication.class, args);
    }

}
