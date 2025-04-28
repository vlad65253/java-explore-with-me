package ewm.event.dto;

import ewm.event.model.Category;
import ewm.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventBaseDto {
    private String annotation;
    private Category category;
    private Long confirmedRequests;
    private LocalDateTime eventDate;
    private Long id;
    private User initiator;
    private LocalDateTime paid;
    private String title;
}
