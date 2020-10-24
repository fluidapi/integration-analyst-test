package io.fluid.pedrazzani;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import io.fluid.pedrazzani.model.CsvData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CsvDataReader {

    public List<CsvData> readCsv(File csvFile) {
        try {
            CsvMapper csvMapper = new CsvMapper();
            CsvSchema schema = csvMapper.schemaFor(CsvData.class).withSkipFirstDataRow(true);
            MappingIterator<CsvData> objectMappingIterator = csvMapper
                    .readerWithTypedSchemaFor(CsvData.class)
                    .with(schema)
                    .readValues(csvFile);
            return objectMappingIterator.readAll();
        } catch (IOException e) {
            log.error("Error on read file. Message: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

}
