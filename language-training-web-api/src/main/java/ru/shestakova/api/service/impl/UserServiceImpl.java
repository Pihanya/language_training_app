package ru.shestakova.api.service.impl;


import static ru.shestakova.api.service.impl.Mappers.mapFrom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shestakova.api.response.user.GetDetailedUserResponse;
import ru.shestakova.api.response.user.GetServiceUserResponse;
import ru.shestakova.api.service.UserService;
import ru.shestakova.repository.ServiceUserRepository;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private ServiceUserRepository userRepository;

  @Override
  public GetServiceUserResponse getServiceUser(Long userId) {
    var optional = userRepository.findById(userId);
    if (optional.isEmpty()) {
      return new GetServiceUserResponse().setStatus(GetServiceUserResponse.Status.USER_NOT_FOUND);
    } else {
      return new GetServiceUserResponse()
          .setStatus(GetServiceUserResponse.Status.SUCCESS)
          .setUser(mapFrom(optional.get()));
    }
  }

  @Override public GetServiceUserResponse getServiceUser(String username) {
    var optional = userRepository.findByUsername(username);
    if (optional.isEmpty()) {
      return new GetServiceUserResponse().setStatus(GetServiceUserResponse.Status.USER_NOT_FOUND);
    } else {
      return new GetServiceUserResponse()
          .setStatus(GetServiceUserResponse.Status.SUCCESS)
          .setUser(mapFrom(optional.get()));
    }
  }

  @Override public GetDetailedUserResponse getDetailedUser(Long userId) {
    var optional = userRepository.findById(userId);
    if (optional.isEmpty()) {
      return new GetDetailedUserResponse().setStatus(GetDetailedUserResponse.Status.USER_NOT_FOUND);
    } else {
      return new GetDetailedUserResponse()
          .setStatus(GetDetailedUserResponse.Status.SUCCESS)
          .setUser(mapFrom(optional.get()))
          .setDetails(mapFrom(optional.get().getDetails()));
    }
  }

  @Override public GetDetailedUserResponse getDetailedUser(String username) {
    var optional = userRepository.findByUsername(username);
    if (optional.isEmpty()) {
      return new GetDetailedUserResponse().setStatus(GetDetailedUserResponse.Status.USER_NOT_FOUND);
    } else {
      return new GetDetailedUserResponse()
          .setStatus(GetDetailedUserResponse.Status.SUCCESS)
          .setUser(mapFrom(optional.get()))
          .setDetails(mapFrom(optional.get().getDetails()));
    }
  }
}
