package desafio.converter1Test;

import org.junit.Test;
import sun.invoke.empty.Empty;

public class CovidTeste {

    @Test(expected = IndexOutOfBoundsException.class)
    public void deveEstarVazioConsolidado() {
        desafio.converter1.Covid covid = new desafio.converter1.Covid();
        covid.consolidado.get(0);

    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void deveEstarVazioCovid() {
        desafio.converter1.Covid covid = new desafio.converter1.Covid();
        covid.covid.get(0);

    }

    @Test()
    public void deveValidarListaConsolidado() {
        desafio.converter1.Covid covid = new desafio.converter1.Covid();
        covid.covid.add(0,covid);
        covid.covid.get(0);

    }



}



