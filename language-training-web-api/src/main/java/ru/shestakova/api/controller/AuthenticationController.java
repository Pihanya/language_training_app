package ru.shestakova.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shestakova.api.request.RegistrationRequest;
import ru.shestakova.api.response.forum.RegistrationResponse;
import ru.shestakova.api.response.user.AuthenticationResponse;
import ru.shestakova.api.service.AuthenticationService;

@RestController
@RequestMapping(
    path = "/auth",
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
@AllArgsConstructor
public class AuthenticationController {

  private AuthenticationService authService;

  @GetMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<AuthenticationResponse> authenticateUser(
      @RequestHeader(name = "Username") String username,
      @RequestHeader(name = "Password") String password
  ) {
    var response = authService.authenticateUser(username, password);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case USER_NOT_FOUND:
        return ResponseEntity.notFound().build();
      case WRONG_CREDENTIALS:
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<RegistrationResponse> registrarUser(
      @RequestBody RegistrationRequest request) {
    var response = authService.registrarUser(request);
    switch (response.getStatus()) {
      case SUCCESS:
        return ResponseEntity.ok(response);
      case USERNAME_ALREADY_EXISTS:
        return ResponseEntity.badRequest().body(response);
      default:
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
