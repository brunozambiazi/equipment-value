package com.company.data;

import static java.util.Optional.ofNullable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

  private Schedule schedule;

  private SaleDetails saleDetails;

  private Classification classification;

  @JsonIgnore
  public BigDecimal getCost() {
    return saleDetails == null ? null : saleDetails.getCost();
  }

  @JsonIgnore
  public BigDecimal getYearOrDefaultAuctionRatio(Integer year) {
    return schedule == null ? null : ofNullable(schedule.getYear(year))
        .map(ScheduleYear::getAuctionRatio)
        .orElseGet(() -> schedule.getDefaultAuctionRatio());
  }

  @JsonIgnore
  public BigDecimal getYearOrDefaultMarketRatio(Integer year) {
    return schedule == null ? null : ofNullable(schedule.getYear(year))
        .map(ScheduleYear::getMarketRatio)
        .orElseGet(() -> schedule.getDefaultMarketRatio());
  }
}
