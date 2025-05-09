package ru.controller;

import enw.ParamDto;
import enw.ParamHitDto;
import enw.ViewStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Slf4j
public class StatController {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final StatService statService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void hit(@RequestBody ParamHitDto paramHitDto) {
        log.info("Поступил запрос POST /hit на создание hit {}", paramHitDto);
        statService.hit(paramHitDto);
        log.info("Запрос /hit успешно обработан \uD83D\uDC4D ");

    }

    @GetMapping("/stats")
    public List<ViewStats> getAllStats(
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime start,
            @RequestParam @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        log.info("Запрос /stats поступил");

        List<ViewStats> stats = statService.getStat(new ParamDto(start, end, uris, unique));
        log.info("Запрос /stats обработан успешно \uD83D\uDC4D");
        return stats;
    }
}
