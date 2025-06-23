package com.company;

import com.company.service.EquipmentService;
import com.company.service.provider.EquipmentExternalAPI;
import java.util.LinkedHashMap;
import java.util.Map;

public class App {

  public static void main(String[] args) {
      EquipmentService service = new EquipmentService(new EquipmentExternalAPI());

      Map<Long, Integer> input = new LinkedHashMap<>();
      input.put(67352L, 2007);
      input.put(87964L, 2011);

      input.forEach((equipmentId, year) -> {
          try {
              System.out.println(service.calculateEquipmentValue(equipmentId, year));
              System.out.println("---");
          } catch (Exception ex) {
              System.err.printf("Error calculating value for equipment %d in year %d: %s%n", equipmentId, year, ex.getMessage());
          }
      });
  }
}
