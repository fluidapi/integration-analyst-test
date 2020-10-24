package io.fluid.pedrazzani.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class JsonData {
    String month;
    Integer cases;
    Integer deaths;
}
