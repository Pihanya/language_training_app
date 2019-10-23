package ru.shestakova.api.service;

import java.util.UUID;
import ru.shestakova.api.request.RegistrationRequest;
import ru.shestakova.api.response.forum.RegistrationResponse;
import ru.shestakova.api.response.user.AuthenticationResponse;

public interface AuthenticationService {

  RegistrationResponse registrarUser(RegistrationRequest request);

  AuthenticationResponse authenticateUser(String username, String password);

  AuthenticationResponse authenticateUserByToken(String username, UUID token);
}
