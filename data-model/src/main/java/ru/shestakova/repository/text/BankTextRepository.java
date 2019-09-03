package ru.shestakova.repository.text;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shestakova.model.text.BankText;

public interface BankTextRepository extends JpaRepository<BankText, Integer> {

}
