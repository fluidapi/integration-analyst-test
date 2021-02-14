package desafio.converter1;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Arquivo {
    public static void ler() {

        Scanner leitor = null;
        try {
            leitor = new Scanner(new FileReader("C:/Users/guilhermem/Desktop/ProjectModelo/desafio/src/main/resources/input.csv"));
            while (leitor.hasNextLine()) {
                String linha = leitor.nextLine().trim();
                if(!linha.contains("date")){
                    String[] tratamentoArquivo = linha.split(",");
                    Covid covid = new Covid();
                    covid.setData(tratamentoArquivo[0]);
                    covid.setCasos(Integer.parseInt(tratamentoArquivo[1]));
                    covid.setMortes(Integer.parseInt(tratamentoArquivo[2]));
                    covid.setCovid(covid);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("Problema ao ler arquivo texto!");
            e.printStackTrace();
        } finally {
            leitor.close();
        }

    }

}
