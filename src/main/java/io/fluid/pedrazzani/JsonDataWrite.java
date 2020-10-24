package io.fluid.pedrazzani;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.fluid.pedrazzani.model.JsonData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JsonDataWrite {

    public void write(File output, List<JsonData> jsonDataList) {
        try {
            new ObjectMapper().writeValue(output, jsonDataList);
        } catch (IOException e) {
            log.error("Erro ao salvar json. Message: {}", e.getMessage());
        }
    }

}
