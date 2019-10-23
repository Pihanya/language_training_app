package ru.shestakova.api.service.impl;

import static ru.shestakova.api.service.impl.Mappers.mapFrom;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shestakova.api.model.filter.TextWorkFilter;
import ru.shestakova.api.model.text.TextWork;
import ru.shestakova.api.model.user.UserRole;
import ru.shestakova.api.request.text.CreateWorkRequest;
import ru.shestakova.api.request.text.EditWorkRequest;
import ru.shestakova.api.response.text.CreateWorkResponse;
import ru.shestakova.api.response.text.DeleteWorkResponse;
import ru.shestakova.api.response.text.EditWorkResponse;
import ru.shestakova.api.response.text.GetWorksResponse;
import ru.shestakova.api.service.TextWorkService;
import ru.shestakova.api.service.exception.PermissionException;
import ru.shestakova.repository.BankTextRepository;
import ru.shestakova.repository.ServiceUserRepository;
import ru.shestakova.repository.TextWorkRepository;

@Service
@AllArgsConstructor
public class TextWorkServiceImpl implements TextWorkService {

  private ServiceUserRepository userRepository;
  private BankTextRepository textRepository;
  private TextWorkRepository workRepository;

  @Override public CreateWorkResponse createWork(Long initiatorId, CreateWorkRequest request) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new CreateWorkResponse().setStatus(CreateWorkResponse.Status.INITIATOR_NOT_FOUND);
    }

    var textOptional = textRepository.findById(request.getTextId());
    if (textOptional.isEmpty()) {
      return new CreateWorkResponse().setStatus(CreateWorkResponse.Status.TEXT_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());
    var text = textOptional.get();

    if (!userRole.isCanCreateWorks()) {
      throw new PermissionException();
    }

    var title = request.getTitle();
    var textStr = request.getText();
    var workType = request.getType();

    if (
        workRepository.findByTextTextIdAndAuthorUserIdAndType(
            text.getTextId(), user.getUserId(), workType.getValue()
        ).isPresent()
    ) {
      return new CreateWorkResponse().setStatus(CreateWorkResponse.Status.WORK_ALREADY_EXISTS);
    }

    var work = workRepository.save(
        new ru.shestakova.model.TextWork()
            .setWorkTitle(title)
            .setWorkText(textStr)
            .setRating(0)
            .setText(text)
            .setAuthor(user)
            .setType(workType.getValue())
    );

    return new CreateWorkResponse()
        .setStatus(CreateWorkResponse.Status.SUCCESS)
        .setWork(mapFrom(work));
  }

  @Override public Optional<TextWork> findWorkById(Long workId) {
    return workRepository.findById(workId).map(Mappers::mapFrom);
  }

  @Override public GetWorksResponse findWorksByFilter(TextWorkFilter filter) {
    var works = workRepository.findAllByFilter(mapFrom(filter))
                              .stream()
                              .map(Mappers::mapFrom)
                              .collect(Collectors.toUnmodifiableList());
    return new GetWorksResponse()
        .setLength(works.size())
        .setWorks(works);
  }

  @Override
  public EditWorkResponse editWork(Long initiatorId, Long workId, EditWorkRequest request) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new EditWorkResponse().setStatus(EditWorkResponse.Status.INITIATOR_NOT_FOUND);
    }

    var workOptional = workRepository.findById(workId);
    if (workOptional.isEmpty()) {
      return new EditWorkResponse().setStatus(EditWorkResponse.Status.WORK_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());
    var work = workOptional.get();

    if (!user.getUserId().equals(work.getAuthor().getUserId())) {
      var otherRole = UserRole.fromId(work.getAuthor().getRole());
      if (userRole.compare(otherRole) <= 0 || !userRole.isCanEditOthersWorks()) {
        throw new PermissionException();
      }
    } else if (!userRole.isCanEditHisWorks()) {
      throw new PermissionException();
    }

    if (request.getTitle() != null) {
      work.setWorkTitle(request.getTitle());
    }
    if (request.getText() != null) {
      work.setWorkText(request.getText());
    }
    if (request.getType() != null) {
      work.setType(request.getType().getValue());
    }

    return new EditWorkResponse()
        .setStatus(EditWorkResponse.Status.SUCCESS)
        .setWork(mapFrom(work));
  }

  @Override public DeleteWorkResponse deleteWork(Long initiatorId, Long workId) {
    var userOptional = userRepository.findById(initiatorId);
    if (userOptional.isEmpty()) {
      return new DeleteWorkResponse().setStatus(DeleteWorkResponse.Status.INITIATOR_NOT_FOUND);
    }

    var workOptional = workRepository.findById(workId);
    if (workOptional.isEmpty()) {
      return new DeleteWorkResponse().setStatus(DeleteWorkResponse.Status.WORK_NOT_FOUND);
    }

    var user = userOptional.get();
    var userRole = UserRole.fromId(user.getRole());
    var work = workOptional.get();

    if (!user.getUserId().equals(work.getAuthor().getUserId())) {
      var otherRole = UserRole.fromId(work.getAuthor().getRole());
      if (userRole.compare(otherRole) <= 0 || !userRole.isCanDeleteOthersWorks()) {
        throw new PermissionException();
      }
    } else if (!userRole.isCanDeleteHisWorks()) {
      throw new PermissionException();
    }

    workRepository.delete(work);

    return new DeleteWorkResponse()
        .setStatus(DeleteWorkResponse.Status.SUCCESS)
        .setWork(mapFrom(work));
  }
}
