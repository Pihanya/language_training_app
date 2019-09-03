package ru.shestakova.repository.text;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.text.AssessmentMark;

public interface AssessmentMarkRepository extends JpaRepository<AssessmentMark, Short> {

}
