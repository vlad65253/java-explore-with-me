package ewm.event.dto;

import ewm.event.model.Category;
import ewm.event.model.EventState;
import ewm.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class EventFullDto extends EventBaseDto{
    private LocalDateTime createdOn;
    private String description;
    private LocationDto location;
    private LocalDateTime participantLimit;
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventState state;
    private Long views;
}
