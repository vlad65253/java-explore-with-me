package ewm.comment.controller;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;
import ewm.comment.dto.UpdateCommentDto;
import ewm.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    private final CommentService commentService;
    @PostMapping("/{eventId}/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto privateAddComment(@PathVariable(name = "eventId") Long eventId,
                                 @PathVariable(name = "userId") Long authorId,
                                 @Valid @RequestBody InputCommentDto inputCommentDto){
        return commentService.privateAddComment(eventId, authorId, inputCommentDto);
    }
    @DeleteMapping("/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void privateDeleteComment(@PathVariable(name = "commentId") Long commentId,
                                        @PathVariable(name = "userId") Long authorId){
        commentService.privateDeleteComment(commentId, authorId);
    }
    @PatchMapping("/{commentId}/{userId}")
    public CommentDto privatePatchComment(@PathVariable(name = "commentId") Long commentId,
                                    @PathVariable(name = "userId") Long authorId,
                                    @Valid @RequestBody UpdateCommentDto updateCommentDto){
        return commentService.privatePatchComment(commentId, authorId, updateCommentDto);
    }
    @GetMapping("/{eventId}/{userId}")
    public List<CommentDto> findCommentsByEventIdAndUserId(@PathVariable(name = "eventId") Long eventId,
                                                           @PathVariable(name = "userId") Long userId,
                                                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                           @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.findCommentsByEventIdAndUserId(eventId, userId, from, size);
    }
    @GetMapping("/users/{userId}")
    public List<CommentDto> findCommentsByUserId(@PathVariable(name = "userId") Long userId,
                                                 @RequestParam(name = "from", defaultValue = "0") Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return commentService.findCommentsByUserId(userId, from, size);
    }
}
