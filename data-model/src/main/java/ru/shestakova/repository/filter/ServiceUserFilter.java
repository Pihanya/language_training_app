package ru.shestakova.repository.filter;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class ServiceUserFilter {

  private Integer fromRating = 0;
  private Integer toRating = Integer.MAX_VALUE;

  private Date registeredFrom = Date.from(new Date().toInstant().minus(7, ChronoUnit.DAYS));
  private Date registeredTo = new Date(Long.MAX_VALUE);
}
