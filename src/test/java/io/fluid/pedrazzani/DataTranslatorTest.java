package io.fluid.pedrazzani;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTranslatorTest {

    @Test
    public void should_map_data_csv_to_json() {
        List<JsonData> jsonData = new DataTranslator().toJson(createDataList());
        assertThat(jsonData).containsExactly(
                JsonData.builder().month("2020-01").cases(8).deaths(4).build(),
                JsonData.builder().month("2020-10").cases(8).deaths(4).build());
    }

    List<CsvData> createDataList() {
        return Arrays.asList(
                CsvData.builder().date("2020-01-30").cases("2").deaths("1").uf("CE").time("16:00").build(),
                CsvData.builder().date("2020-01-30").cases("2").deaths("1").uf("MG").time("16:00").build(),
                CsvData.builder().date("2020-01-30").cases("2").deaths("1").uf("RJ").time("16:00").build(),
                CsvData.builder().date("2020-01-30").cases("2").deaths("1").uf("SP").time("16:00").build(),
                CsvData.builder().date("2020-10-30").cases("2").deaths("1").uf("CE").time("16:00").build(),
                CsvData.builder().date("2020-10-30").cases("2").deaths("1").uf("MG").time("16:00").build(),
                CsvData.builder().date("2020-10-30").cases("2").deaths("1").uf("RJ").time("16:00").build(),
                CsvData.builder().date("2020-10-30").cases("2").deaths("1").uf("SP").build()
        );
    }

}