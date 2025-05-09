package ewm.comment.service;

import ewm.comment.dto.CommentDto;
import ewm.comment.dto.InputCommentDto;
import ewm.comment.dto.UpdateCommentDto;
import ewm.comment.mapper.CommentMapper;
import ewm.comment.model.Comment;
import ewm.comment.repository.CommentRepository;
import ewm.event.model.Event;
import ewm.event.repository.EventRepository;
import ewm.exception.EntityNotFoundException;
import ewm.exception.InitiatorRequestException;
import ewm.exception.ValidationException;
import ewm.requests.repository.RequestRepository;
import ewm.user.model.User;
import ewm.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;
    private final RequestRepository requestRepository;

    @Override
    public CommentDto privateAddComment(Long eventId, Long authorId, InputCommentDto inputCommentDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "Событие с ID " + eventId + " не найдено"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Пользователь с ID - " + authorId + " не найден"));
        if (event.getInitiator().equals(author)) {
            throw new ValidationException(Comment.class, "Нельзя оставить комментарий к своему событию");
        }
        if (requestRepository.findByRequesterIdAndEventId(authorId, eventId).isEmpty()) {
            throw new ValidationException(Comment.class, "Пользователь с ID - " + authorId + " не заявился на событие с ID - " + eventId);
        }
        Comment comment = commentMapper.toComment(inputCommentDto, author, event);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void privateDeleteComment(Long commentId, Long authorId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "Комментария с ID " + commentId + " не найдено"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Пользователь с ID - " + authorId + " не найден"));
        if (!comment.getAuthor().equals(author)) {
            throw new InitiatorRequestException("Нельзя удалить чужой комментарий");
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto privatePatchComment(Long commentId, Long authorId, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "Комментария с ID " + commentId + " не найдено"));

        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Пользователь с ID - " + authorId + " не найден"));
        if (!comment.getAuthor().equals(author)) {
            throw new InitiatorRequestException("Нельзя изменить чужой комментарий");
        }
        comment.setText(updateCommentDto.getText());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> findCommentsByEventIdAndUserId(Long eventId, Long userId, Integer from, Integer size) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "Событие с ID " + eventId + " не найдено"));

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Пользователь с ID - " + userId + " не найден"));
        Pageable pageable = PageRequest.of(from, size);
        return commentMapper.toCommentDtos(commentRepository.findAllByEventIdAndAuthorId(eventId, userId, pageable));
    }

    @Override
    public List<CommentDto> findCommentsByUserId(Long userId, Integer from, Integer size) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "Пользователь с ID - " + userId + " не найден"));
        Pageable pageable = PageRequest.of(from, size);
        return commentMapper.toCommentDtos(commentRepository.findAllByAuthorId(userId, pageable));
    }

    @Override
    public void adminDelete(long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto adminUpdate(long id, UpdateCommentDto updateCommentDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, "Комментарий c ID - " + id + ", не найден."));

        if (updateCommentDto.getText() != null) {
            comment.setText(updateCommentDto.getText());
        }
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> findCommentsByEventId(Long eventId, Integer from, Integer size) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(Event.class, "Событие с ID " + eventId + " не найдено"));
        Pageable pageable = PageRequest.of(from, size);
        return commentMapper.toCommentDtos(commentRepository.findAllByEventId(eventId, pageable));
    }

    @Override
    public CommentDto findCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(Comment.class, "Комментарий c ID - " + commentId + ", не найден."));
        return commentMapper.toCommentDto(comment);
    }

}
