package test;

import data.Misurazione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MisurazioneTest {

    @Test
    void testGetValoreFormattato() {
        Misurazione misurazione1 = new Misurazione("temperatura", "sensoreA", 25.56789, "°C", "");
        assertEquals("25,6", misurazione1.getValoreFormattato());

        Misurazione misurazione2 = new Misurazione("umidità", "sensoreB", 50.0, "%", "");
        assertEquals("50,0", misurazione2.getValoreFormattato());

        Misurazione misurazione3 = new Misurazione("pressione", "sensoreC", 1010.0, "hPa", "");
        assertEquals("1010,0", misurazione3.getValoreFormattato());
    }

    @Test
    void testToStringConVariazione() {
        Misurazione misurazione = new Misurazione("temperatura", "sensoreA", 25.5, "°C", "");
        assertEquals("sensoreA (temperatura): 25,5 °C", misurazione.toString());
    }
}
