package ewm.comment.mapper;

import ewm.comment.dto.InputCommentDto;
import ewm.event.model.Event;
import ewm.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import javax.xml.stream.events.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "author")
    @Mapping(target = "event", source = "event")
    @Mapping(target = "created", ignore = true)
    Comment toComment(InputCommentDto inputCommentDto, User author, Event event);
}
