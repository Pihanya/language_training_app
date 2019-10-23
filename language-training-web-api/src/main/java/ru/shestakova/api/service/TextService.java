package ru.shestakova.api.service;

import java.util.Optional;
import ru.shestakova.api.model.filter.BankTextFilter;
import ru.shestakova.api.model.text.BankText;
import ru.shestakova.api.request.text.CreateTextRequest;
import ru.shestakova.api.request.text.EditTextRequest;
import ru.shestakova.api.response.text.CreateTextResponse;
import ru.shestakova.api.response.text.DeleteTextResponse;
import ru.shestakova.api.response.text.EditTextResponse;
import ru.shestakova.api.response.text.GetTextsResponse;

public interface TextService {

  CreateTextResponse createText(Long initiatorId, CreateTextRequest request);

  Optional<BankText> findTextById(Integer textId);

  GetTextsResponse findTextsByFilter(BankTextFilter filter);

  EditTextResponse editText(Long initiatorId, Integer textId, EditTextRequest request);

  DeleteTextResponse deleteText(Long initiatorId, Integer textId);
}
