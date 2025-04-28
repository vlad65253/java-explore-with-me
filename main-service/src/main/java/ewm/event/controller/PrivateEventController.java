package ewm.event.controller;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.NewEventDto;
import ewm.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable Long userId,
                                    @RequestBody NewEventDto eventDto){
        return eventService.createEvent(userId, eventDto);
    }

}
