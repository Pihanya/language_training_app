package ru.shestakova.api.controller;

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
import org.springframework.web.bind.annotation.RestController;
import ru.shestakova.api.model.text.BankText;
import ru.shestakova.api.request.text.CreateTextRequest;
import ru.shestakova.api.request.text.EditTextRequest;
import ru.shestakova.api.response.text.GetTextsResponse;
import ru.shestakova.api.service.TextService;

@RestController
@RequestMapping(
    path = "/texts",
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@AllArgsConstructor
public class TextController {

  private TextService textService;

  @PostMapping
  ResponseEntity<BankText> createText(
      @RequestAttribute("UserId") Long userId,
      @RequestBody CreateTextRequest request
  ) {
    var response = textService.createText(userId, request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response.getText());
      case INITIATOR_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping(path = "{textId}", consumes = MediaType.ALL_VALUE)
  ResponseEntity<BankText> findTextById(@PathVariable(name = "textId") Integer textId) {
    return textService.findTextById(textId)
                      .map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(consumes = MediaType.ALL_VALUE)
  ResponseEntity<GetTextsResponse> findTextsByFilter() {
    return ResponseEntity.badRequest().build();
  }

  @PatchMapping(path = "{textId}")
  ResponseEntity<BankText> editText(
      @RequestAttribute("UserId") Long userId,
      @PathVariable(name = "textId") Integer textId,
      @RequestBody EditTextRequest request
  ) {
    var response = textService.editText(userId, textId, request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response.getText());
      case INITIATOR_NOT_FOUND:
      case TEXT_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @DeleteMapping(path = "{textId}", consumes = MediaType.ALL_VALUE)
  ResponseEntity<BankText> deleteText(
      @RequestAttribute("UserId") Long userId,
      @PathVariable(name = "textId") Integer textId
  ) {
    var response = textService.deleteText(userId, textId);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response.getText());
      case INITIATOR_NOT_FOUND:
      case TEXT_NOT_FOUND:
        return ResponseEntity.notFound().build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
