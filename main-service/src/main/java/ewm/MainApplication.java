package ewm;

import client.StatClient;
import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = {"ewm"})
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}