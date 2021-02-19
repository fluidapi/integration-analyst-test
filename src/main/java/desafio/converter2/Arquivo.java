package desafio.converter2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.underscore.lodash.U;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {
    public static void ler() {
        List<Covid> covid = readCsvFile("/home/lucas/Applications/integration-analyst-test/src/main/resources/input.csv");
        String jsonString = imprimirParaJson(covid);
        System.out.println(covid);
        System.out.println(U.formatJson(jsonString));
    }

       private static List<Covid> readCsvFile(String filePath) {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;

        List<Covid> covid = new ArrayList<Covid>();

        try {
            fileReader = new BufferedReader(new FileReader(filePath));
            csvParser = new CSVParser(fileReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Covid Covid = new Covid(
                        csvRecord.get("date"),
                        Integer.parseInt(csvRecord.get("cases")),
                        Integer.parseInt(csvRecord.get("deaths")),
                        csvRecord.get("uf"),
                        csvRecord.get("time"));



                covid.add(Covid);
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler CSV!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Fechando fileReader / csvParser Erro!");
                e.printStackTrace();
            }
        }

        return covid;
    }

    private static String imprimirParaJson(List<Covid> covid) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(covid);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
