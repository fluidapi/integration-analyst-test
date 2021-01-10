package io.github.arkanjoms.controller;

import com.opencsv.exceptions.CsvException;
import io.github.arkanjoms.model.SummarizedData;
import io.github.arkanjoms.service.ProcessDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/covid-report")
public class CovidReportController {

    private final ProcessDataService processDataService;

    public CovidReportController(ProcessDataService processDataService) {
        this.processDataService = processDataService;
    }

    @PostMapping
    public ResponseEntity<List<SummarizedData>> resume(@RequestParam MultipartFile file) throws IOException, CsvException {
        return ResponseEntity.ok(processDataService.processInputData(file));
    }
}
