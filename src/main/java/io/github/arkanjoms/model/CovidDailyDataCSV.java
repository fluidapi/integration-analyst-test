package io.github.arkanjoms.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

@Getter
@Setter
@Builder
public class CovidDailyDataCSV implements Serializable {

    private static final long serialVersionUID = 4328132535821340850L;

    private LocalDate date;
    private Integer cases;
    private Integer deaths;
    private String federationUnity;
    private LocalTime hour;

    public YearMonth getYearMonth() {
        return YearMonth.from(date);
    }
}
