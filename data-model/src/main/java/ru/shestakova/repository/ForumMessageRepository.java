package ru.shestakova.repository;

import com.querydsl.core.types.dsl.Expressions;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.shestakova.model.ForumMessage;
import ru.shestakova.model.QForumMessage;
import ru.shestakova.repository.filter.ForumMessageFilter;

public interface ForumMessageRepository extends JpaRepository<ForumMessage, Long>,
    QuerydslPredicateExecutor<ForumMessage> {

  private static int pageSize() {
    return 50;
  }

  Optional<ForumMessage> findByMessageIdAndThemeThemeId(Long messageId, Integer themeId);

  List<ForumMessage> findByThemeThemeId(Integer themeId);

  default List<ForumMessage> findMessagesByFilter(ForumMessageFilter filter) {
    var message = QForumMessage.forumMessage;
    var expression = Expressions.asBoolean(true).isTrue();

    if (filter.getThemeId() != null) {
      expression = expression.and(message.theme.themeId.eq(filter.getThemeId()));
    }

    if (filter.getAuthorId() != null) {
      expression = expression.and(message.author.userId.eq(filter.getAuthorId()));
    }

    if (filter.getCreatedFrom() != null) {
      expression = expression.and(message.createDate.goe(filter.getCreatedFrom()));
    }

    if (filter.getCreatedTo() != null) {
      expression = expression.and(message.createDate.loe(filter.getCreatedTo()));
    }

    if (filter.getEditedFrom() != null) {
      expression = expression.and(message.editDate.goe(filter.getEditedFrom()));
    }

    if (filter.getEditedTo() != null) {
      expression = expression.and(message.editDate.loe(filter.getEditedTo()));
    }

    if (filter.getDeletedFrom() != null) {
      expression = expression.and(message.deleteDate.goe(filter.getDeletedFrom()));
    }

    if (filter.getDeletedTo() != null) {
      expression = expression.and(message.deleteDate.loe(filter.getDeletedTo()));
    }

    if (filter.getTerminationStatuses() != null && !filter.getTerminationStatuses().isEmpty()) {
      expression = expression.and(message.terminationStatus.in(filter.getTerminationStatuses()));
    }

    if(filter.getCount() > pageSize()) {
      filter.setCount(pageSize());
    }

    int pageIndex = filter.getFrom() / pageSize();
    int skip = filter.getFrom() % pageSize();
    var page = findAll(expression, PageRequest.of(pageIndex, pageSize()));

    var content = page.getContent();
    return content.subList(skip, content.size());
  }
}
