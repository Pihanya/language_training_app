package ru.shestakova.repository.text;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.text.WorkType;

public interface WorkTypeRepository extends JpaRepository<WorkType, Short> {

}
