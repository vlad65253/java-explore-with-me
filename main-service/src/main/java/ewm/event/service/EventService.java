package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.NewEventDto;

public interface EventService {

    EventFullDto createEvent(Long userId, NewEventDto eventDto);
}
