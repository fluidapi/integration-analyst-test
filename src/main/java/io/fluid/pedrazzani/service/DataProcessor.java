package io.fluid.pedrazzani.service;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import lombok.Lombok;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

import static lombok.Lombok.checkNotNull;

@Slf4j
@Service
public class DataProcessor {

    private final DataReader dataReader;
    private final DataWrite dataWrite;
    private final DataTranslator dataTranslator;

    public DataProcessor(DataReader dataReader, DataWrite dataWrite, DataTranslator dataTranslator) {
        this.dataReader = dataReader;
        this.dataWrite = dataWrite;
        this.dataTranslator = dataTranslator;
    }

    public void process(String path, String input, String output) {
        try {
            log.info("Start processing ...");
            checkArguments(path, input, output);
            List<CsvData> csvData = dataReader.readCsv(Paths.get(path, input).toFile());
            List<JsonData> jsonDataList = dataTranslator.toJson(csvData);
            dataWrite.writeJson(Paths.get(path, output).toFile(), jsonDataList);
            log.info("done.");
        } catch (Exception e) {
            log.error("Fail csv to json file translate. Detail: {}", e.getMessage());
        }
    }

    private void checkArguments(String path, String input, String output) {
        checkNotNull(path, "Data Directory must not be null.");
        checkNotNull(input, "Data Input name must not be null.");
        checkNotNull(output, "Data Output name must not be null.");
    }

}
