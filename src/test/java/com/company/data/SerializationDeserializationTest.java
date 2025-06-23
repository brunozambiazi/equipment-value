package com.company.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

public class SerializationDeserializationTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void equipments_serialization() throws Exception {
    // given some equipments
    Equipments equipments = buildEquipments();

    // and an expected response
    String expectedJson = getJsonFileContent();

    // when equipments object is serialized
    String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(equipments);

    // then result is equal to expected response
    Map<Object, Object> resultJsonMap = objectMapper.readValue(resultJson, new TypeReference<>() {});
    Map<Object, Object> expectedJsonMap = objectMapper.readValue(expectedJson, new TypeReference<>() {});
    assertEquals(resultJsonMap, expectedJsonMap);
  }

  @Test
  public void equipments_deserialization() throws Exception {
    // given a json with equipment data
    String json = getJsonFileContent();

    // and an expected equipments object
    Equipments expectedEquipments = buildEquipments();

    // when json is deserialized into an equipments object
    Equipments equipments = objectMapper.readValue(json, Equipments.class);

    // then equipments object is equal to expected object
    assertEquals(expectedEquipments, equipments);
  }

  private static Equipments buildEquipments() {
    Equipment equipment1 = Equipment.builder()
        .schedule(Schedule.builder()
            .years(new TreeMap<>(Map.of(
                2006, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.311276"))
                    .auctionRatio(new BigDecimal("0.181383"))
                    .build(),
                2007, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.317628"))
                    .auctionRatio(new BigDecimal("0.185085"))
                    .build(),
                2008, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.324111"))
                    .auctionRatio(new BigDecimal("0.188862"))
                    .build(),
                2009, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.330725"))
                    .auctionRatio(new BigDecimal("0.192716"))
                    .build(),
                2010, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.363179"))
                    .auctionRatio(new BigDecimal("0.198498"))
                    .build(),
                2011, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.374074"))
                    .auctionRatio(new BigDecimal("0.206337"))
                    .build(),
                2012, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.431321"))
                    .auctionRatio(new BigDecimal("0.213178"))
                    .build()
            )))
            .defaultMarketRatio(new BigDecimal("0.02"))
            .defaultAuctionRatio(new BigDecimal("0.02"))
            .build())
        .saleDetails(SaleDetails.builder()
            .cost(new BigDecimal("681252"))
            .retailSaleCount(122L)
            .auctionSaleCount(17L)
            .build())
        .classification(Classification.builder()
            .category("Earthmoving Equipment")
            .subcategory("Dozers")
            .make("Caterpillar")
            .model("D8T")
            .build())
        .build();

    Equipment equipment2 = Equipment.builder()
        .schedule(Schedule.builder()
            .years(new TreeMap<>(Map.of(
                2016, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.613292"))
                    .auctionRatio(new BigDecimal("0.417468"))
                    .build(),
                2017, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.692965"))
                    .auctionRatio(new BigDecimal("0.473205"))
                    .build(),
                2018, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("0.980485"))
                    .auctionRatio(new BigDecimal("0.684991"))
                    .build(),
                2019, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("1.041526"))
                    .auctionRatio(new BigDecimal("0.727636"))
                    .build(),
                2020, ScheduleYear.builder()
                    .marketRatio(new BigDecimal("1.106366"))
                    .auctionRatio(new BigDecimal("0.772935"))
                    .build()
            )))
            .defaultMarketRatio(new BigDecimal("0.06"))
            .defaultAuctionRatio(new BigDecimal("0.06"))
            .build())
        .saleDetails(SaleDetails.builder()
            .cost(new BigDecimal("48929"))
            .retailSaleCount(12L)
            .auctionSaleCount(127L)
            .build())
        .classification(Classification.builder()
            .category("Aerial Equipment")
            .subcategory("Boom Lifts")
            .make("JLG")
            .model("340AJ")
            .build())
        .build();

    return Equipments.builder()
        .equipments(new TreeMap<>(Map.of(
            67352L, equipment1,
            87390L, equipment2
        )))
        .build();
  }

  private static String getJsonFileContent() throws Exception {
    InputStream responseStream = Equipments.class.getResourceAsStream("/mock/api-response.json");
    return new String(responseStream.readAllBytes());
  }
}
