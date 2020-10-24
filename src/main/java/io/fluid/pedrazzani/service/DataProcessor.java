package io.fluid.pedrazzani.service;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.List;

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
            List<CsvData> csvData = dataReader.readCsv(Paths.get(path, input).toFile());
            List<JsonData> jsonDataList = dataTranslator.toJson(csvData);
            dataWrite.writeJson(Paths.get(path, output).toFile(), jsonDataList);
            log.info("done.");
        } catch (Exception e) {
            log.error("Fail csv to json file translate. Detail: {}", e.getMessage());
        }
    }

}
