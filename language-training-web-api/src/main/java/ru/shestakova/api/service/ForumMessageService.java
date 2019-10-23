package ru.shestakova.api.service;

import java.util.Optional;
import ru.shestakova.api.model.filter.ForumMessageFilter;
import ru.shestakova.api.model.forum.ForumMessage;
import ru.shestakova.api.request.forum.CreateMessageRequest;
import ru.shestakova.api.response.forum.CreateMessageResponse;
import ru.shestakova.api.response.forum.EditMessageResponse;
import ru.shestakova.api.response.forum.GetMessagesResponse;
import ru.shestakova.api.response.forum.TerminateMessageResponse;

public interface ForumMessageService {

 CreateMessageResponse createMessage(Long initiatorId, Integer themeId, CreateMessageRequest request);

  Optional<ForumMessage> getMessage(Integer themeId, Long messageId);

  GetMessagesResponse getMessagesByFilter(ForumMessageFilter filter);

  TerminateMessageResponse deleteMessage(Long initiatorId, Integer themeId, Long messageId);

  EditMessageResponse editMessage(Long initiatorId, Integer themeId, Long messageId, String text);
}
