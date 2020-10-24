package io.fluid.pedrazzani.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonPropertyOrder({"date", "cases", "deaths", "uf", "time"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CsvData {

    private String date;
    private String cases;
    private String deaths;
    private String uf;
    private String time;

}
