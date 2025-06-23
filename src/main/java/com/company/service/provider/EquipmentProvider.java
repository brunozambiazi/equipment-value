package com.company.service.provider;

import com.company.data.Equipment;
import com.company.data.Equipments;
import java.util.Optional;

public interface EquipmentProvider {

  Equipments findAll();

  Optional<Equipment> findById(Long id);
}
