package ru.shestakova.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.forum.ForumTheme;

public interface ForumThemeRepository extends JpaRepository<ForumTheme, Integer> {

}
