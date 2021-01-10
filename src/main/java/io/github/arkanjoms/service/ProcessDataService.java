package io.github.arkanjoms.service;

import com.opencsv.exceptions.CsvException;
import io.github.arkanjoms.model.CovidDailyDataCSV;
import io.github.arkanjoms.model.SummarizedData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProcessDataService {

    private final FileService fileService;
    private final FileTransformerService fileTransformerService;

    public ProcessDataService(FileService fileService, FileTransformerService fileTransformerService) {
        this.fileService = fileService;
        this.fileTransformerService = fileTransformerService;
    }

    public List<SummarizedData> processInputData(MultipartFile multipartFile) throws IOException, CsvException {
        File file = fileService.transformerMultipartToFile(multipartFile);
        List<CovidDailyDataCSV> transformedData = fileTransformerService.transform(file);
        return summarizeByMonth(transformedData);
    }

    private List<SummarizedData> summarizeByMonth(List<CovidDailyDataCSV> dailyData) {
        log.debug("summarizing data by month");
        Map<YearMonth, SummarizedData> monthSummarized = new HashMap<>();

        for (CovidDailyDataCSV day : dailyData) {
            if (monthSummarized.containsKey(day.getYearMonth())) {
                sumDailyData(monthSummarized, day);
            } else {
                monthSummarized.put(day.getYearMonth(), createMonthDate(day));
            }
        }

        return new ArrayList<>(monthSummarized.values());
    }

    private void sumDailyData(Map<YearMonth, SummarizedData> monthSummarize, CovidDailyDataCSV day) {
        SummarizedData monthData = monthSummarize.get(day.getYearMonth());
        monthData.setCases(monthData.getCases() + day.getCases());
        monthData.setDeaths(monthData.getDeaths() + day.getDeaths());
    }

    private SummarizedData createMonthDate(CovidDailyDataCSV day) {
        return SummarizedData.builder()
                .month(day.getYearMonth().toString())
                .cases(day.getCases())
                .deaths(day.getDeaths())
                .build();
    }
}
