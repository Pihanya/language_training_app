package ru.shestakova.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.forum.ForumMessage;
import ru.shestakova.model.forum.ForumMessage.ForumMessageKey;

public interface ForumMessageRepository extends JpaRepository<ForumMessage, ForumMessageKey> {

}
