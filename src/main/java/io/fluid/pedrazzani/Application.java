package io.fluid.pedrazzani;

import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${input}")
    private String csvPath;

    @Value("${output}")
    private String jsonPath;

    @Value("${path}")
    private String path;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<CsvData> csvData = new CsvDataReader().readCsv(Paths.get(path, csvPath).toFile());
        List<JsonData> jsonDataList = new DataTranslator().toJson(csvData);
        new JsonDataWrite().write(Paths.get(path, jsonPath).toFile(), jsonDataList);
    }
}
