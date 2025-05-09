package ewm.event.service;

import ewm.event.dto.*;

import java.util.List;

public interface EventService {

    List<EventShortDto> getAllEvents(ReqParam reqParam);

    List<EventFullDto> getAllEvents(AdminEventParams params);

    EventFullDto publicGetEvent(long id);

    EventFullDto create(Long userId, NewEventDto newEventDto);

    List<EventShortDto> findUserEvents(Long userId, Integer from, Integer size);

    EventFullDto findUserEventById(Long userId, Long eventId);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    EventFullDto update(Long eventId, UpdateEventAdminRequest updateEventAdminRequest);

}