package io.github.arkanjoms.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.arkanjoms.model.SummarizedData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class CovidReportControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void resume_ok() throws Exception {
        byte[] bytes = "date,cases,deaths,uf,time\n2020-01-30,10,5,CE,16:00\n2020-01-30,20,3,MG,16:00".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, bytes);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.multipart("/covid-report").file(file)).andReturn();

        assertNotNull(mvcResult);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        ObjectMapper mapper = new ObjectMapper();
        List<SummarizedData> result = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<SummarizedData>>() {
        });

        assertEquals(1, result.size());
        Optional<SummarizedData> month = result.stream().filter(r -> r.getMonth().equals("2020-01")).findFirst();
        assertTrue(month.isPresent());
        assertEquals("2020-01", month.get().getMonth());
        assertEquals(30, month.get().getCases());
        assertEquals(8, month.get().getDeaths());

        Optional<SummarizedData> monthEmpty = result.stream().filter(r -> r.getMonth().equals("2020-02")).findFirst();
        assertTrue(monthEmpty.isEmpty());
    }

    @Test
    void resume_invalidFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:HAL9000.png");
        byte[] bytes = resource.getInputStream().readAllBytes();
        MockMultipartFile file = new MockMultipartFile("file", "HAL9000.png", MediaType.IMAGE_PNG_VALUE, bytes);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.multipart("/covid-report").file(file)).andReturn();

        assertNotNull(mvcResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), mvcResult.getResponse().getStatus());
        assertEquals("invalid type, only csv is permitted", mvcResult.getResponse().getErrorMessage());
    }
}
