package ewm.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputCommentDto {
    @NotNull(message = "Айди события не может быть пустым")
    private Long eventId;
    @NotBlank(message = "Комментарий не может быть пустым")
    @Size(min = 1, max = 7000)
    private String text;
}
