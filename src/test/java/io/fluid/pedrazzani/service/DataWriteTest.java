package io.fluid.pedrazzani.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fluid.pedrazzani.model.JsonData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DataWriteTest {

    @InjectMocks
    private DataWrite dataWrite;

    private Path tempFile;

    @Before
    public void setUp() throws IOException {
        tempFile = Files.createTempFile("output", ".json");
    }

    @After
    public void tearDown() throws IOException {
        Files.delete(tempFile);
    }

    @Test
    public void shouldWriteJson() throws IOException {
        JsonData jsonData = JsonData.builder().month("2020-10").cases(0).deaths(0).build();
        dataWrite.writeJson(tempFile.toFile(), Collections.singletonList(jsonData));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonData[] jsonDataList = objectMapper.readValue(tempFile.toFile(), JsonData[].class);
        assertThat(jsonDataList).containsExactly(jsonData);
    }

}