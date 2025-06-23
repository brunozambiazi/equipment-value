package com.company.service;

import static java.util.Optional.ofNullable;

import com.company.data.Equipment;
import com.company.data.EquipmentValue;
import com.company.service.provider.EquipmentProvider;
import com.company.utils.error.BadRequestException;
import com.company.utils.error.InvalidDataException;
import com.company.utils.error.NotFoundException;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EquipmentService {

  private final EquipmentProvider equipmentProvider;

  public EquipmentValue calculateEquipmentValue(Long equipmentId, Integer year) {
    log.info("Calculating equipment value for id [{}] and year [{}]", equipmentId, year);
    validateInput(equipmentId, year);

    Equipment equipment = equipmentProvider.findById(equipmentId)
        .orElseThrow(() -> new NotFoundException("Equipment %s not found".formatted(equipmentId)));
    log.info("Found equipment: [{}]", equipment);

    BigDecimal equipmentCost = ofNullable(equipment.getCost())
        .orElseThrow(() -> new InvalidDataException("Equipment does not have cost"));

    EquipmentValue value = EquipmentValue.builder()
        .marketValue(equipmentCost.multiply(equipment.getYearOrDefaultMarketRatio(year)))
        .auctionValue(equipmentCost.multiply(equipment.getYearOrDefaultAuctionRatio(year)))
        .build();
    log.info("Calculated equipment value: [{}]", value);
    return value;
  }

  private static void validateInput(Long equipmentId, Integer year) {
    if (equipmentId == null) {
      throw new BadRequestException("Invalid equipment id");
    }

    if (year == null) {
      throw new BadRequestException("Invalid year");
    }
  }
}
