package io.fluid.pedrazzani;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;

public class DataTranslator {

    private static final int ZERO_INT = 0;

    public List<JsonData> toJson(List<CsvData> dataList) {
        List<JsonData> jsonDataList = new ArrayList<>();

        Map<String, List<CsvData>> groupCsvData = dataList.stream().collect(groupingBy(this::groupByYearMounth));
        groupCsvData.forEach((month, csvData) -> jsonDataList.add(mapToJsonDataList(month, csvData)));
        jsonDataList.sort(this::sort);

        return jsonDataList;
    }

    private String groupByYearMounth(CsvData csvData) {
        LocalDate date = LocalDate.parse(csvData.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return format("%s-%02d", date.getYear(), date.getMonthValue());
    }

    private JsonData mapToJsonDataList(String month, List<CsvData> csvDataList) {
        int cases = csvDataList.stream().mapToInt(csvData -> toInteger(csvData.getCases())).sum();
        int deaths = csvDataList.stream().mapToInt(csvData -> toInteger(csvData.getDeaths())).sum();

        return JsonData.builder()
                .month(month)
                .cases(cases)
                .deaths(deaths)
                .build();
    }

    private int toInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return ZERO_INT;
        }
    }

    private int sort(JsonData jsonData, JsonData jsonData1) {
        return jsonData.getMonth().compareTo(jsonData1.getMonth());
    }

}
