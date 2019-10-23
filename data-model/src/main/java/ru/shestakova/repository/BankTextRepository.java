package ru.shestakova.repository;

import java.util.Collections;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.BankText;
import ru.shestakova.repository.filter.BankTextFilter;

public interface BankTextRepository extends JpaRepository<BankText, Integer> {

  default List<BankText> findAllByFilter(BankTextFilter filter) {
    return Collections.emptyList();
  }
}
