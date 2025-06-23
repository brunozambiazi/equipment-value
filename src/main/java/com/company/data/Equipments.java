package com.company.data;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.util.Map;
import java.util.TreeMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Equipments {

  @JsonAnyGetter // https://www.baeldung.com/jackson-annotations#1-jsonanygetter
  @JsonAnySetter // https://www.baeldung.com/jackson-annotations#3-jsonanysetter
  private Map<Long, Equipment> equipments = new TreeMap<>();
}
