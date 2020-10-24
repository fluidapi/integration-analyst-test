package io.fluid.pedrazzani;

import io.fluid.pedrazzani.service.DataProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("${input}")
    private String input;

    @Value("${output}")
    private String output;

    @Value("${path}")
    private String path;

    private final DataProcessor dataProcessor;

    public Application(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        dataProcessor.process(path, input, output);
    }
}
