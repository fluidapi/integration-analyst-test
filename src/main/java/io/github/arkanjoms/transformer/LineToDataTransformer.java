package io.github.arkanjoms.transformer;

import io.github.arkanjoms.model.CovidDailyDataCSV;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDate;
import java.time.LocalTime;

public class LineToDataTransformer {

    private LineToDataTransformer() {
        // Private Constructor will prevent the instantiation of this class directly.
    }

    public static CovidDailyDataCSV toDTO(String[] line) {
        return CovidDailyDataCSV.builder()
                .date(lineToLocalDate(line[0]))
                .cases(lineToInteger(line[1]))
                .deaths(lineToInteger(line[2]))
                .federationUnity(line[3])
                .hour(lineToLocalTime(line))
                .build();
    }

    private static LocalTime lineToLocalTime(String[] line) {
        return Strings.isNotEmpty(line[4]) ? LocalTime.parse(line[4]) : null;
    }

    private static int lineToInteger(String text) {
        return Integer.parseInt(text);
    }

    private static LocalDate lineToLocalDate(String text) {
        return LocalDate.parse(text);
    }
}
