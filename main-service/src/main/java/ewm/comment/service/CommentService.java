package ewm.comment.service;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;
import ewm.comment.dto.UpdateCommentDto;

import java.util.List;

public interface CommentService {
    CommentDto privateAddComment(Long authorId, InputCommentDto inputCommentDto);

    void privateDeleteComment(Long commentId, Long authorId);

    CommentDto privatePatchComment(Long authorId, UpdateCommentDto updateCommentDto);

    List<CommentDto> findCommentsByEventIdAndUserId(Long eventId, Long userId, Integer from, Integer size);

    List<CommentDto> findCommentsByUserId(Long userId, Integer from, Integer size);

    void adminDelete(long id);

    CommentDto adminUpdate(long id, UpdateCommentDto updateCommentDto);

    List<CommentDto> findCommentsByEventId(Long eventId, Integer from, Integer size);

    CommentDto findCommentById(Long commentId);
}
