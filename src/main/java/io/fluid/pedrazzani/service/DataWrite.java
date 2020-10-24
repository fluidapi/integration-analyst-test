package io.fluid.pedrazzani.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fluid.pedrazzani.model.JsonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class DataWrite {

    public void writeJson(File output, List<JsonData> jsonDataList) throws IOException {
        log.info("Writing data into json file.");
        new ObjectMapper().writeValue(output, jsonDataList);
    }

}
