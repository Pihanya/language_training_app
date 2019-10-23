package ru.shestakova.api.service;

import java.util.Optional;
import ru.shestakova.api.model.filter.TextWorkFilter;
import ru.shestakova.api.model.text.TextWork;
import ru.shestakova.api.request.text.CreateWorkRequest;
import ru.shestakova.api.request.text.EditWorkRequest;
import ru.shestakova.api.response.text.CreateWorkResponse;
import ru.shestakova.api.response.text.DeleteWorkResponse;
import ru.shestakova.api.response.text.EditWorkResponse;
import ru.shestakova.api.response.text.GetWorksResponse;

public interface TextWorkService {

  CreateWorkResponse createWork(Long initiatorId, CreateWorkRequest request);

  Optional<TextWork> findWorkById(Long workId);

  GetWorksResponse findWorksByFilter(TextWorkFilter filter);

  EditWorkResponse editWork(Long initiatorId, Long workId, EditWorkRequest request);

  DeleteWorkResponse deleteWork(Long initiatorId, Long workId);
}
