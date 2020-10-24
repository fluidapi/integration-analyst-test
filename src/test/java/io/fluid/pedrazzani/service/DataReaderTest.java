package io.fluid.pedrazzani.service;

import io.fluid.pedrazzani.model.CsvData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DataReaderTest {

    @InjectMocks
    private DataReader dataReader;

    private Path tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("input", ".csv");
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(tempFile);
    }

    @Test
    public void shouldReadCsv() throws IOException {
        String csvString = "date,cases,deaths,uf,time\n" +
                "2020-01-30,0,0,CE,16:00\n" +
                "2020-01-30,0,0,MG,16:00";

        Path write = Files.write(tempFile, csvString.getBytes(), StandardOpenOption.CREATE);
        List<CsvData> csvData = dataReader.readCsv(write.toFile());

        assertThat(csvData).containsExactly(
                CsvData.builder().date("2020-01-30").cases("0").deaths("0").uf("CE").time("16:00").build(),
                CsvData.builder().date("2020-01-30").cases("0").deaths("0").uf("MG").time("16:00").build());
    }

}