package io.fluid.pedrazzani.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;
import io.fluid.pedrazzani.model.CsvData;
import io.fluid.pedrazzani.model.JsonData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIOException;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.slf4j.Logger.ROOT_LOGGER_NAME;

@RunWith(MockitoJUnitRunner.class)
public class DataProcessorTest {

    private static final String PATH = "path";
    private static final String INPUT = "input";
    private static final String OUTPUT = "output";
    private static final String ROOT_LOGGER_NAME = org.slf4j.Logger.ROOT_LOGGER_NAME;
    private static final String MONTH = "2020-01";

    @Mock
    private Appender<ILoggingEvent> mockAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;
    @Mock
    private DataReader dataReader;
    @Mock
    private DataWrite dataWrite;
    @Mock
    private DataTranslator dataTranslator;
    @InjectMocks
    private DataProcessor dataProcessor;

    @Before
    public void setup() {
        Logger root = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        root.addAppender(mockAppender);
    }

    @After
    public void teardown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void shouldProcess() throws IOException {

        CsvData csvData = CsvData.builder().date("2020-01-01").cases("1").deaths("0").build();
        JsonData jsonData = JsonData.builder().month(MONTH).cases(1).deaths(0).build();
        File csvFile = Paths.get(PATH, INPUT).toFile();

        List<CsvData> csvDataList = Collections.singletonList(csvData);
        when(dataReader.readCsv(csvFile)).thenReturn(csvDataList);
        when(dataTranslator.toJson(csvDataList)).thenReturn(Collections.singletonList(jsonData));

        dataProcessor.process(PATH, INPUT, OUTPUT);

        verify(dataWrite).writeJson(Paths.get(PATH, OUTPUT).toFile(), Collections.singletonList(jsonData));
    }

    @Test
    public void shouldNotProcessWhenDataReadThrowException() throws IOException {
        when(dataReader.readCsv(Paths.get(PATH, INPUT).toFile())).thenThrow(new IOException());
        dataProcessor.process(PATH, INPUT, OUTPUT);
        JsonData jsonData = JsonData.builder().month(MONTH).cases(1).deaths(0).build();
        verify(dataWrite, never()).writeJson(Paths.get(PATH, OUTPUT).toFile(), Collections.singletonList(jsonData));
    }

    @Test
    public void shouldNotProcessWhenDataWriteThrowException() throws IOException {
        CsvData csvData = CsvData.builder().date("2020-01-01").cases("1").deaths("0").build();
        JsonData jsonData = JsonData.builder().month(MONTH).cases(1).deaths(0).build();
        File csvFile = Paths.get(PATH, INPUT).toFile();

        List<CsvData> csvDataList = Collections.singletonList(csvData);
        when(dataReader.readCsv(csvFile)).thenReturn(csvDataList);
        when(dataTranslator.toJson(csvDataList)).thenReturn(Collections.singletonList(jsonData));
        doThrow(new IOException("")).when(dataWrite).writeJson(Paths.get(PATH, OUTPUT).toFile(), Collections.singletonList(jsonData));

        dataProcessor.process(PATH, INPUT, OUTPUT);

        verify(mockAppender, times(2)).doAppend(captorLoggingEvent.capture());
        final LoggingEvent loggingEvent = captorLoggingEvent.getValue();
        assertThat(loggingEvent.getLevel()).isEqualTo(Level.ERROR);
        assertThat(loggingEvent.getFormattedMessage()).isEqualTo("Fail csv to json file translate. Detail: ");

    }

}