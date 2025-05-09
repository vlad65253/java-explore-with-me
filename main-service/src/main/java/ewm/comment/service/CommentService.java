package ewm.comment.service;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;

public interface CommentService {
    CommentDto addComment(Long eventId, Long authorId, InputCommentDto inputCommentDto);
}
