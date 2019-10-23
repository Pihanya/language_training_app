package ru.shestakova.api.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.shestakova.api.model.filter.ForumThemeFilter;
import ru.shestakova.api.model.forum.ForumTheme;
import ru.shestakova.api.model.forum.ThemeTerminationStatus;
import ru.shestakova.api.request.forum.CreateThemeRequest;
import ru.shestakova.api.request.forum.EditThemeRequest;
import ru.shestakova.api.response.forum.GetThemesResponse;
import ru.shestakova.api.response.forum.TerminateThemeResponse;
import ru.shestakova.api.service.ForumThemeService;

@RestController
@RequestMapping(
    value = "/forum/topics",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@AllArgsConstructor
public class ForumThemeController {

  private ForumThemeService themeService;

  @PostMapping
  public ResponseEntity<ForumTheme> createTheme(
      @RequestAttribute(name = "UserId") Long userId,
      @RequestBody CreateThemeRequest request
  ) {
    var response = themeService.createTheme(userId, request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response.getTheme());
      case INITIATOR_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(path = "/{themeId}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<ForumTheme> getTheme(@PathVariable(name = "themeId") Integer themeId) {
    return themeService.getTheme(themeId)
                       .map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(consumes = MediaType.ALL_VALUE)
  public ResponseEntity<GetThemesResponse> getThemes(
      @RequestParam(name = "authorId", required = false) Long authorId,
      @RequestParam(name = "from", required = false) Integer from,
      @RequestParam(name = "count", required = false) Integer count,
      @RequestParam(name = "createFrom", required = false) Long createdFrom,
      @RequestParam(name = "createTo", required = false) Long createdTo,
      @RequestParam(name = "editedFrom", required = false) Long editedFrom,
      @RequestParam(name = "editedTo", required = false) Long editedTo,
      @RequestParam(name = "lastMessageFrom", required = false) Long lastMessageFrom,
      @RequestParam(name = "lastMessageTo", required = false) Long lastMessageTo,
      @RequestParam(name = "totalMessagesFrom", required = false) Integer totalMessagesFrom,
      @RequestParam(name = "totalMessagesTo", required = false) Integer totalMessagesTo,
      @RequestParam(name = "status[]", required = false) List<Integer> intStatuses
  ) {
    var filter = new ForumThemeFilter();
    if (authorId != null) {
      filter.setAuthorId(authorId);
    }

    if (from != null && from > 0) {
      filter.setFrom(from);
    }

    if (count != null && count > 0) {
      filter.setCount(count);
    }

    if (createdFrom != null) {
      filter.setCreatedFrom(Instant.ofEpochMilli(createdFrom));
    }

    if (createdTo != null) {
      filter.setCreatedTo(Instant.ofEpochMilli(createdTo));
    }

    if (editedFrom != null) {
      filter.setEditedFrom(Instant.ofEpochMilli(editedFrom));
    }

    if (editedTo != null) {
      filter.setEditedTo(Instant.ofEpochMilli(editedTo));
    }

    if (lastMessageFrom != null) {
      filter.setLastMessageFrom(Instant.ofEpochMilli(lastMessageFrom));
    }

    if (lastMessageTo != null) {
      filter.setLastMessageTo(Instant.ofEpochMilli(lastMessageTo));
    }

    if (totalMessagesFrom != null && totalMessagesFrom > 0) {
      filter.setTotalMessagesFrom(totalMessagesFrom);
    }

    if (totalMessagesTo != null) {
      filter.setTotalMessagesFrom(totalMessagesTo);
    }

    if (intStatuses != null && !intStatuses.isEmpty()) {
      var statuses = new ArrayList<ThemeTerminationStatus>(intStatuses.size());
      for (var intStatus : intStatuses) {
        if (ThemeTerminationStatus.isValid(intStatus)) {
          statuses.add(ThemeTerminationStatus.fromNumeric(intStatus));
        } else {
          return ResponseEntity.badRequest().build();
        }
      }
      filter.setTerminationStatuses(statuses);
    }

    var response = themeService.findThemesByFilter(filter);
    if (response.getLength() == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(response);
    }
  }

  @PatchMapping(path = "/{themeId}")
  public ResponseEntity<ForumTheme> editTheme(
      @RequestAttribute(name = "UserId") Long userId,
      @PathVariable(name = "themeId") Integer themeId,
      @RequestBody EditThemeRequest request
  ) {
    var response = themeService.editTheme(userId, themeId, request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response.getTheme());
      case INITIATOR_NOT_FOUND:
      case THEME_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(path = "/{themeId}/{terminationTitle}", consumes = MediaType.ALL_VALUE)
  public ResponseEntity<TerminateThemeResponse> terminateTheme(
      @RequestAttribute(name = "UserId") Long userId,
      @PathVariable(name = "themeId") Integer themeId,
      @PathVariable(name = "terminationTitle") String terminationTitle
  ) {
    ThemeTerminationStatus status;
    switch (terminationTitle) {
      case "archive":
        status = ThemeTerminationStatus.ARCHIVED;
        break;
      case "close":
        status = ThemeTerminationStatus.CLOSED;
        break;
      case "delete":
        status = ThemeTerminationStatus.DELETED;
        break;
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    var response = themeService.terminateTheme(userId, themeId, status);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case INITIATOR_NOT_FOUND:
      case THEME_NOT_FOUND:
        return ResponseEntity.notFound().build();
      case FORBIDDEN_TRANSITION:
        return ResponseEntity.badRequest().body(response);
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
