package desafio.converter2;

import java.util.ArrayList;

public class Covid {
    public   static  ArrayList<Covid> covid =  new ArrayList();
    public  static  ArrayList<Covid> consolidado =  new ArrayList();
    private String mes;
    private int casos;
    private int mortes;
    private String uf;
    private String hora;

    public void setCovid(Covid covid) {
        this.covid.add(covid);
    }
    public Covid(String date, int cases, int mortes, String uf, String time) {
        this.mes = date;
        this.casos = cases;
        this.mortes = mortes;
        this.uf = uf;
        this.hora = time;

    }


    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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

    public void setMortes(int mortes) {
        this.mortes = mortes;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }


    @Override
    public String toString() {
        return "Customer [mes=" + mes + ", casos=" + casos + ", mortes=" + mortes + ", uf=" + uf + ",hora=" + hora +"]";
    }
    public static  void processarConsolidado(){

          for(Covid c: covid) {
            String mesAno = c.getMes().substring(0,7);
            switch (mesAno){
                case "2020-01":
                    consolidado.get(0).setHora(mesAno);
                    consolidado.get(0).setCasos(c.getCasos() + consolidado.get(0).getCasos());
                    consolidado.get(0).setMortes(c.getMortes() + consolidado.get(0).getMortes());
                    break;
                case "2020-02":
                    consolidado.get(1).setHora(mesAno);
                    consolidado.get(1).setCasos(c.getCasos() + consolidado.get(1).getCasos());
                    consolidado.get(1).setMortes(c.getMortes() + consolidado.get(1).getMortes());
                    break;
                case "2020-03":
                    consolidado.get(2).setHora(mesAno);
                    consolidado.get(2).setCasos(c.getCasos() + consolidado.get(2).getCasos());
                    consolidado.get(2).setMortes(c.getMortes() + consolidado.get(2).getMortes());
                    break;
            }

        }

    }

}
