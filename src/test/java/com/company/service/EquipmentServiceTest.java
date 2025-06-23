package com.company.service;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.company.data.Equipment;
import com.company.data.EquipmentValue;
import com.company.data.SaleDetails;
import com.company.data.Schedule;
import com.company.data.ScheduleYear;
import com.company.service.provider.EquipmentProvider;
import com.company.utils.error.BadRequestException;
import com.company.utils.error.InvalidDataException;
import com.company.utils.error.NotFoundException;
import java.math.BigDecimal;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

  @Mock
  private EquipmentProvider provider;

  @InjectMocks
  private EquipmentService service;

  @Test
  public void calculateEquipmentValue_happyPath_withNoDefaults() {
    // given valid input
    Long equipmentId = 123L;
    Integer year = 2025;

    // and valid equipment mock
    Equipment mockEquipment = buildEquipment(year);
    when(provider.findById(equipmentId)).thenReturn(of(mockEquipment));

    // when calculate is called
    EquipmentValue equipmentValue = service.calculateEquipmentValue(equipmentId, year);

    // then result is valid
    assertNotNull(equipmentValue);
    assertEquals(equipmentValue.getAuctionValue(), mockEquipment.getCost().multiply(mockEquipment.getSchedule().getYear(year).getAuctionRatio()));
    assertEquals(equipmentValue.getMarketValue(), mockEquipment.getCost().multiply(mockEquipment.getSchedule().getYear(year).getMarketRatio()));
  }

  @Test
  public void calculateEquipmentValue_happyPath_withDefaults() {
    // given valid input
    Long equipmentId = 123L;
    Integer year = 2025;

    // and valid equipment mock
    Equipment mockEquipment = buildEquipment(year);
    mockEquipment.getSchedule().getYear(year).setAuctionRatio(null);
    mockEquipment.getSchedule().getYear(year).setMarketRatio(null);
    when(provider.findById(equipmentId)).thenReturn(of(mockEquipment));

    // when calculate is called
    EquipmentValue equipmentValue = service.calculateEquipmentValue(equipmentId, year);

    // then result is valid
    assertNotNull(equipmentValue);
    assertEquals(equipmentValue.getAuctionValue(), mockEquipment.getCost().multiply(mockEquipment.getSchedule().getDefaultAuctionRatio()));
    assertEquals(equipmentValue.getMarketValue(), mockEquipment.getCost().multiply(mockEquipment.getSchedule().getDefaultMarketRatio()));
  }

  @Test
  public void calculateEquipmentValue_invalidEquipmentId() {
    // given invalid input
    Long equipmentId = null;
    Integer year = 2025;

    // when calculate is called
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> service.calculateEquipmentValue(equipmentId, year));

    // then exception is thrown
    assertNotNull(exception);
    assertTrue(exception.getMessage().toLowerCase().contains("equipment id"));
  }

  @Test
  public void calculateEquipmentValue_invalidYear() {
    // given invalid input
    Long equipmentId = 123L;
    Integer year = null;

    // when calculate is called
    BadRequestException exception = assertThrows(BadRequestException.class,
        () -> service.calculateEquipmentValue(equipmentId, year));

    // then exception is thrown
    assertNotNull(exception);
    assertTrue(exception.getMessage().toLowerCase().contains("year"));
  }

  @Test
  public void calculateEquipmentValue_equipmentNotFound() {
    // given valid input
    Long equipmentId = 123L;
    Integer year = 2025;

    // and no equipments found
    when(provider.findById(equipmentId)).thenReturn(empty());

    // when calculate is called
    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> service.calculateEquipmentValue(equipmentId, year));

    // then exception is thrown
    assertNotNull(exception);
  }

  @Test
  public void calculateEquipmentValue_equipmentWithNoCost() {
    // given valid input
    Long equipmentId = 123L;
    Integer year = 2025;

    // and equipment with no cost value
    Equipment mockEquipment = buildEquipment(year);
    mockEquipment.getSaleDetails().setCost(null);
    when(provider.findById(equipmentId)).thenReturn(of(mockEquipment));

    // when calculate is called
    InvalidDataException exception = assertThrows(InvalidDataException.class,
        () -> service.calculateEquipmentValue(equipmentId, year));

    // then exception is thrown
    assertNotNull(exception);
    assertTrue(exception.getMessage().toLowerCase().contains("cost"));
  }

  private static Equipment buildEquipment(int year) {
    return Equipment.builder()
        .schedule(Schedule.builder()
            .defaultAuctionRatio(new BigDecimal("0.5"))
            .defaultMarketRatio(new BigDecimal("1.5"))
            .years(Map.of(year, ScheduleYear.builder()
                .auctionRatio(new BigDecimal("0.9")).marketRatio(new BigDecimal("1.2"))
                .build()))
            .build())
        .saleDetails(SaleDetails.builder().cost(new BigDecimal("9.9")).build())
        .build();
  }
}