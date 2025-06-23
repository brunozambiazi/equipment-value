package com.company.data;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentValue {

  private final BigDecimal marketValue;
  private final BigDecimal auctionValue;
}
