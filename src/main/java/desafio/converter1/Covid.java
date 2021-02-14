package desafio.converter1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Covid {

    public   static  ArrayList<Covid> covid =  new ArrayList();
    public   static  ArrayList<Covid> consolidado =  new ArrayList();
    private String mes;
    private int casos;
    private int mortes;
    private String UF;
    private  String hora;

    public void setCovid(Covid covid) {
        this.covid.add(covid);
    }

    public String getData() {
        return mes;
    }

    public void setData(String data) {
        this.mes = data;
    }

    public int getCasos() {
        return casos;
    }

    public void setCasos(int casos) {
        this.casos = casos;
    }

    public int getMortes() {
        return mortes;
    }

    public void setMortes(int numeroMortes) {
        this.mortes = numeroMortes;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public static  void processarConsolidado(){
        consolidado.add(0, new Covid());
        consolidado.add(1, new Covid());
        consolidado.add(2, new Covid());
        for(Covid c: covid) {
            String mesAno = c.getData().substring(0,7);
            switch (mesAno){
                case "2020-01":
                    consolidado.get(0).setData(mesAno);
                    consolidado.get(0).setCasos(c.getCasos() + consolidado.get(0).getCasos());
                    consolidado.get(0).setMortes(c.getMortes() + consolidado.get(0).getMortes());
                    break;
                case "2020-02":
                    consolidado.get(1).setData(mesAno);
                    consolidado.get(1).setCasos(c.getCasos() + consolidado.get(1).getCasos());
                    consolidado.get(1).setMortes(c.getMortes() + consolidado.get(1).getMortes());
                    break;
                case "2020-03":
                    consolidado.get(2).setData(mesAno);
                    consolidado.get(2).setCasos(c.getCasos() + consolidado.get(2).getCasos());
                    consolidado.get(2).setMortes(c.getMortes() + consolidado.get(2).getMortes());
                    break;
            }

        }

    }

    public static void imprimirSaidaJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(consolidado);
        System.out.println(json);

    }
}

