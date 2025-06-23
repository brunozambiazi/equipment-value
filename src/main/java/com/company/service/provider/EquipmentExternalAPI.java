package com.company.service.provider;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import com.company.data.Equipment;
import com.company.data.Equipments;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EquipmentExternalAPI implements EquipmentProvider {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public Equipments findAll() {
    try {
      InputStream jsonStream = getClass().getResourceAsStream("/mock/api-response.json");
      String json = new String(jsonStream.readAllBytes());
      return objectMapper.readValue(json, Equipments.class);
    } catch (Exception ex) {
      log.error("Error fetching all equipments: {}", ex.getMessage(), ex);
      return new Equipments();
    }
  }

  @Override
  public Optional<Equipment> findById(Long id) {
    try {
      return ofNullable(findAll()
          .getEquipments()
          .get(id));
    } catch (Exception ex) {
      log.error("Error fetching equipment by id [{}]: {}", id, ex.getMessage(), ex);
      return empty();
    }
  }
}
