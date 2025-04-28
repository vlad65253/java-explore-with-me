package ewm.event.service;

import ewm.event.dto.EventFullDto;
import ewm.event.dto.NewEventDto;
import ewm.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public EventFullDto createEvent(Long userId, NewEventDto eventDto) {

        return null;
        //ТУТ ЗАКОНЧИЛ
        //TODO
    }
}
