package com.company.data;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetails {

  private BigDecimal cost;

  private Long retailSaleCount;

  private Long auctionSaleCount;
}