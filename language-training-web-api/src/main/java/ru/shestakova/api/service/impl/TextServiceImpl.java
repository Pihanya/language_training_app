package ru.shestakova.api.service.impl;

import static ru.shestakova.api.service.impl.Mappers.mapFrom;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shestakova.api.model.filter.BankTextFilter;
import ru.shestakova.api.model.text.BankText;
import ru.shestakova.api.model.user.UserRole;
import ru.shestakova.api.request.text.CreateTextRequest;
import ru.shestakova.api.request.text.EditTextRequest;
import ru.shestakova.api.response.text.CreateTextResponse;
import ru.shestakova.api.response.text.DeleteTextResponse;
import ru.shestakova.api.response.text.EditTextResponse;
import ru.shestakova.api.response.text.GetTextsResponse;
import ru.shestakova.api.service.TextService;
import ru.shestakova.api.service.exception.PermissionException;
import ru.shestakova.repository.BankTextRepository;
import ru.shestakova.repository.ServiceUserRepository;

@Service
@AllArgsConstructor
public class TextServiceImpl implements TextService {

  private ServiceUserRepository userRepository;
  private BankTextRepository textRepository;

  @Override public CreateTextResponse createText(Long initiatorId, CreateTextRequest request) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new CreateTextResponse().setStatus(CreateTextResponse.Status.INITIATOR_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());

    if (!userRole.isCanCreateTexts()) {
      throw new PermissionException();
    }

    var text = textRepository.save(
        new ru.shestakova.model.BankText()
            .setTitle(request.getTitle())
            .setText(request.getText())
            .setAuthor(user)
    );

    return new CreateTextResponse()
        .setStatus(CreateTextResponse.Status.SUCCESS)
        .setText(mapFrom(text));
  }

  @Override public Optional<BankText> findTextById(Integer textId) {
    return textRepository.findById(textId).map(Mappers::mapFrom);
  }

  @Override public GetTextsResponse findTextsByFilter(BankTextFilter filter) {
    var texts = textRepository.findAllByFilter(mapFrom(filter))
                              .stream()
                              .map(Mappers::mapFrom)
                              .collect(Collectors.toUnmodifiableList());
    return new GetTextsResponse()
        .setLength(texts.size())
        .setTexts(texts);
  }

  @Override
  public EditTextResponse editText(Long initiatorId, Integer textId, EditTextRequest request) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new EditTextResponse().setStatus(EditTextResponse.Status.INITIATOR_NOT_FOUND);
    }

    var textOptional = textRepository.findById(textId);
    if (textOptional.isEmpty()) {
      return new EditTextResponse().setStatus(EditTextResponse.Status.TEXT_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());
    var text = textOptional.get();

    if (!user.getUserId().equals(text.getAuthor().getUserId())) {
      var otherRole = UserRole.fromId(text.getAuthor().getRole());
      if (userRole.compare(otherRole) <= 0 || !userRole.isCanEditOthersTexts()) {
        throw new PermissionException();
      }
    } else if (!userRole.isCanEditHisTexts()) {
      throw new PermissionException();
    }

    if (request.getTitle() != null) {
      text.setTitle(request.getTitle());
    }
    if (request.getText() != null) {
      text.setText(request.getText());
    }

    return new EditTextResponse()
        .setStatus(EditTextResponse.Status.SUCCESS)
        .setText(mapFrom(text));
  }

  @Override public DeleteTextResponse deleteText(Long initiatorId, Integer textId) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new DeleteTextResponse().setStatus(DeleteTextResponse.Status.INITIATOR_NOT_FOUND);
    }

    var textOptional = textRepository.findById(textId);
    if (textOptional.isEmpty()) {
      return new DeleteTextResponse().setStatus(DeleteTextResponse.Status.TEXT_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());
    var text = textOptional.get();

    if (!user.getUserId().equals(text.getAuthor().getUserId())) {
      var otherRole = UserRole.fromId(text.getAuthor().getRole());
      if (userRole.compare(otherRole) <= 0 || !userRole.isCanDeleteOtherTexts()) {
        throw new PermissionException();
      }
    } else if (!userRole.isCanDeleteHisTexts()) {
      throw new PermissionException();
    }

    textRepository.delete(text);

    return new DeleteTextResponse()
        .setStatus(DeleteTextResponse.Status.SUCCESS)
        .setText(mapFrom(text));
  }
}
