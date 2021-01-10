package io.github.arkanjoms.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.github.arkanjoms.model.CovidDailyDataCSV;
import io.github.arkanjoms.transformer.LineToDataTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class FileTransformerService {

    public List<CovidDailyDataCSV> transform(File file) throws IOException, CsvException {
        log.debug("transforming data");
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            reader.skip(1);
            List<String[]> lines = reader.readAll();
            return lines.stream().map(LineToDataTransformer::toDTO).collect(toList());
        }
    }
}
