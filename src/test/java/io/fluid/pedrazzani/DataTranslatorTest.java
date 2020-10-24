package io.fluid.pedrazzani;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import io.fluid.pedrazzani.service.DataTranslator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DataTranslatorTest {

    private static final String JANUARY = "2020-01-30";
    private static final String OCTUBER = "2020-10-30";
    private static final String TIME = "16:00";
    private static final String MONTH = "2020-01";
    private static final String CASES = "2";
    private static final String DEATHS = "1";

    @InjectMocks
    private DataTranslator dataTranslator;

    @Test
    public void shouldTranslate() {
        List<JsonData> jsonData = dataTranslator.toJson(createDataList());
        assertThat(jsonData).containsExactly(
                JsonData.builder().month(MONTH).cases(8).deaths(4).build(),
                JsonData.builder().month("2020-10").cases(8).deaths(4).build());
    }

    @Test
    public void shouldNotTranslateWhenDateIsBlank() {
        List<CsvData> csvDataList = Collections.singletonList(CsvData.builder().date("").cases(CASES).deaths(DEATHS).uf("CE").time(TIME).build());
        List<JsonData> jsonData = dataTranslator.toJson(csvDataList);
        assertThat(jsonData).isEmpty();
    }

    @Test
    public void shouldNotTranslateWhenDateWrongFormat() {
        List<CsvData> csvDataList = Collections.singletonList(CsvData.builder().date("20-10-2020").cases(CASES).deaths(DEATHS).uf("CE").time(TIME).build());
        List<JsonData> jsonData = dataTranslator.toJson(csvDataList);
        assertThat(jsonData).isEmpty();
    }

    @Test
    public void shouldTranslateWhenCasesOrDeathsIsNotInteger() {
        List<CsvData> csvDataList = Collections.singletonList(CsvData.builder().date(JANUARY).cases("2.1").deaths("").uf("CE").time(TIME).build());
        List<JsonData> jsonData = dataTranslator.toJson(csvDataList);
        assertThat(jsonData).containsExactly(JsonData.builder().month(MONTH).cases(0).deaths(0).build());
    }

    @Test
    public void shouldTranslateWhenTimeIsEmpty() {
        List<CsvData> csvDataList = Collections.singletonList(CsvData.builder().date(JANUARY).cases(DEATHS).deaths(DEATHS).uf("CE").build());
        List<JsonData> jsonData = dataTranslator.toJson(csvDataList);
        assertThat(jsonData).containsExactly(JsonData.builder().month(MONTH).cases(1).deaths(1).build());
    }

    @Test
    public void shouldTranslateWhenUfIsEmpty() {
        List<CsvData> csvDataList = Collections.singletonList(CsvData.builder().date(JANUARY).cases(DEATHS).deaths(DEATHS).time(TIME).build());
        List<JsonData> jsonData = dataTranslator.toJson(csvDataList);
        assertThat(jsonData).containsExactly(JsonData.builder().month(MONTH).cases(1).deaths(1).build());
    }

    List<CsvData> createDataList() {
        return Arrays.asList(
                CsvData.builder().date(JANUARY).cases(CASES).deaths(DEATHS).uf("CE").time(TIME).build(),
                CsvData.builder().date(JANUARY).cases(CASES).deaths(DEATHS).uf("MG").time(TIME).build(),
                CsvData.builder().date(JANUARY).cases(CASES).deaths(DEATHS).uf("RJ").time(TIME).build(),
                CsvData.builder().date(JANUARY).cases(CASES).deaths(DEATHS).uf("SP").time(TIME).build(),
                CsvData.builder().date(OCTUBER).cases(CASES).deaths(DEATHS).uf("CE").time(TIME).build(),
                CsvData.builder().date(OCTUBER).cases(CASES).deaths(DEATHS).uf("MG").time(TIME).build(),
                CsvData.builder().date(OCTUBER).cases(CASES).deaths(DEATHS).uf("RJ").time(TIME).build(),
                CsvData.builder().date(OCTUBER).cases(CASES).deaths(DEATHS).uf("SP").time(TIME).build()
        );
    }

}