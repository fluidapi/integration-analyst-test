package io.fluid.pedrazzani.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.fluid.pedrazzani.model.CsvData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class DataReader {

    public List<CsvData> readCsv(File csvFile) throws IOException {
        log.info("Reading data from csv file.");
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = csvMapper.schemaFor(CsvData.class).withSkipFirstDataRow(true);
        MappingIterator<CsvData> objectMappingIterator = csvMapper
                .readerWithTypedSchemaFor(CsvData.class)
                .with(schema)
                .readValues(csvFile);
        return objectMappingIterator.readAll();
    }

}
