package ewm.comment.controller;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;
import ewm.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;
    @PostMapping("/{eventId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@PathVariable(name = "eventId") Long eventId,
                                 @PathVariable(name = "userId") Long authorId,
                                 @Valid @RequestBody InputCommentDto inputCommentDto){
        return commentService.addComment(eventId, authorId, inputCommentDto);
    }
}
