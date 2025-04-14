package ru;

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
@ComponentScan(basePackages = {"client", "ru"})
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
        StatClient statClient = context.getBean(StatClient.class);

        ParamHitDto hitDto = ParamHitDto.builder()
                .app("ewm-main-service")
                .uri("/events")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.now())
                .build();

        ParamHitDto hitDto2 = ParamHitDto.builder()
                .app("ewm-main-service")
                .uri("/test")
                .ip("121.0.0.1")
                .timestamp(LocalDateTime.now())
                .build();

        statClient.hit(hitDto.getApp(), hitDto.getUri(), hitDto.getIp());
        statClient.hit(hitDto2.getApp(), hitDto2.getUri(), hitDto2.getIp());

        ParamDto paramDto = new ParamDto(
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1),
                List.of(hitDto.getUri(), hitDto2.getUri()),
                false);

        List<ViewStats> result = statClient.getStat(paramDto);
    }
}