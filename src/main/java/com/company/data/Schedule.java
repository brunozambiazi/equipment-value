package com.company.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

  private Map<Integer, ScheduleYear> years;

  private BigDecimal defaultMarketRatio;

  private BigDecimal defaultAuctionRatio;

  @JsonIgnore
  public ScheduleYear getYear(Integer year) {
    return years == null ? null : years.get(year);
  }
}