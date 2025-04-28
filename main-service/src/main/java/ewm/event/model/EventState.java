package ewm.event.model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public enum EventState {
    PUBLISHED,
    PENDING,
    CANCELED;
    public static Set<EventState> from(List<String> states) {
        return states.stream()
                .map(state -> EventState.valueOf(state.toUpperCase()))
                .collect(Collectors.toSet());
    }

    }
