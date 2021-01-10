package io.github.arkanjoms.service;

import com.opencsv.exceptions.CsvException;
import io.github.arkanjoms.model.SummarizedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProcessDataServiceTest {

    FileService fileService;

    FileTransformerService fileTransformerService;

    ProcessDataService testedObject;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
        fileTransformerService = new FileTransformerService();
        testedObject = new ProcessDataService(fileService, fileTransformerService);
    }

    @Test
    void processInputData() throws IOException, CsvException {
        byte[] bytes = "date,cases,deaths,uf,time\n2020-01-30,10,2,CE,16:00\n2020-01-30,20,1,MG,16:00\n2020-02-02,100,5,SC,16:00".getBytes();
        MultipartFile multipartFile = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, bytes);
        List<SummarizedData> result = testedObject.processInputData(multipartFile);
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
