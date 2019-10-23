package ru.shestakova.api.controller;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shestakova.api.model.filter.ForumMessageFilter;
import ru.shestakova.api.model.forum.ForumMessage;
import ru.shestakova.api.model.forum.MessageTerminationStatus;
import ru.shestakova.api.request.forum.CreateMessageRequest;
import ru.shestakova.api.request.forum.EditMessageRequest;
import ru.shestakova.api.response.forum.CreateMessageResponse;
import ru.shestakova.api.response.forum.EditMessageResponse;
import ru.shestakova.api.response.forum.GetMessagesResponse;
import ru.shestakova.api.response.forum.TerminateMessageResponse;
import ru.shestakova.api.service.ForumMessageService;

@RestController
@RequestMapping(
    path = "/forum/messages",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@AllArgsConstructor
public class ForumMessageController {

  private ForumMessageService messageService;

  @PostMapping(path = "{themeId}")
  public ResponseEntity<CreateMessageResponse> createMessage(
      @RequestAttribute("UserId") Long userId,
      @PathVariable(name = "themeId") Integer themeId,
      @RequestBody CreateMessageRequest request
  ) {
    var response = messageService.createMessage(userId, themeId, request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case INITIATOR_NOT_FOUND:
      case THEME_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(path = "{themeId}/{messageId}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ForumMessage> getMessage(
      @PathVariable(name = "themeId") Integer themeId,
      @PathVariable(name = "messageId") Long messageId
  ) {
    return messageService.getMessage(themeId, messageId)
                         .map(ResponseEntity::ok)
                         .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<GetMessagesResponse> getMessagesByFilter(
      @RequestParam(name = "themeId", required = false) Integer themeId,
      @RequestParam(name = "authorId", required = false) Long authorId,
      @RequestParam(name = "from", required = false) Integer from,
      @RequestParam(name = "count", required = false) Integer count,
      @RequestParam(name = "createdFrom", required = false) Long createdFrom,
      @RequestParam(name = "createdTo", required = false) Long createdTo,
      @RequestParam(name = "editedFrom", required = false) Long editedFrom,
      @RequestParam(name = "editedTo", required = false) Long editedTo,
      @RequestParam(name = "deletedFrom", required = false) Long deletedFrom,
      @RequestParam(name = "deletedTo", required = false) Long deletedTo,
      @RequestParam(name = "status[]", required = false) List<Integer> intStatuses
  ) {
    var filter = new ForumMessageFilter();

    if (themeId != null) {
      filter.setThemeId(themeId);
    }

    if (authorId != null) {
      filter.setAuthorId(authorId);
    }

    if (from != null && from >= 0) {
      filter.setFrom(from);
    }

    if (count != null && count > 0) {
      filter.setCount(count);
    }

    if (createdFrom != null && createdFrom >= 0) {
      filter.setCreatedFrom(Instant.ofEpochMilli(createdFrom));
    }

    if (createdTo != null) {
      filter.setCreatedTo(Instant.ofEpochMilli(createdTo));
    }

    if (editedFrom != null && editedFrom >= 0) {
      filter.setEditedFrom(Instant.ofEpochMilli(editedFrom));
    }

    if (editedTo != null) {
      filter.setEditedTo(Instant.ofEpochMilli(editedTo));
    }

    if (deletedFrom != null && deletedFrom >= 0) {
      filter.setDeletedFrom(Instant.ofEpochMilli(deletedFrom));
    }

    if (deletedTo != null) {
      filter.setDeletedTo(Instant.ofEpochMilli(deletedTo));
    }

    if (intStatuses != null && !intStatuses.isEmpty()) {
      filter.setTerminationStatuses(
          intStatuses.stream()
                     .map(MessageTerminationStatus::fromNumeric)
                     .collect(Collectors.toUnmodifiableList())
      );
    }

    var response = messageService.getMessagesByFilter(filter);
    if (response.getLength() == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(response);
    }
  }

  @PatchMapping(path = "{themeId}/{messageId}")
  ResponseEntity<EditMessageResponse> editMessage(
      @RequestAttribute(name = "UserId") Long userId,
      @PathVariable(name = "themeId") Integer themeId,
      @PathVariable(name = "messageId") Long messageId,
      @RequestBody EditMessageRequest request
  ) {
    var response = messageService.editMessage(userId, themeId, messageId, request.getText());
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case INITIATOR_NOT_FOUND:
      case THEME_NOT_FOUND:
      case MESSAGE_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping(path = "{themeId}/{messageId}/delete", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<TerminateMessageResponse> deleteMessage(
      @RequestAttribute(name = "UserId") Long userId,
      @PathVariable(name = "themeId") Integer themeId,
      @PathVariable(name = "messageId") Long messageId
  ) {
    var response = messageService.deleteMessage(userId, themeId, messageId);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case INITIATOR_NOT_FOUND:
      case THEME_NOT_FOUND:
      case MESSAGE_NOT_FOUND:
        return ResponseEntity.notFound().build();
      case FORBIDDEN_TRANSITION:
        return ResponseEntity.badRequest().body(response);
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
