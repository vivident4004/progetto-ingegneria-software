package test;

import model.Misurazione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MisurazioneTest {

    @Test
    void testCostruttoreSenzaVariazione() {
        Misurazione misurazione = new Misurazione("temperatura", "sensoreA", 25.5, "°C");

        assertEquals("temperatura", misurazione.getTipologia());
        assertEquals("sensoreA", misurazione.getNomeSensore());
        assertEquals(25.5, misurazione.getValore());
        assertEquals("°C", misurazione.getUnitaMisura());
        assertEquals("", misurazione.getInfoVariazione());
    }

    @Test
    void testCostruttoreConVariazione() {
        Misurazione misurazione = new Misurazione("temperatura", "sensoreA", 25.5, "°C", " (variazione: 2.0%)");

        assertEquals("temperatura", misurazione.getTipologia());
        assertEquals("sensoreA", misurazione.getNomeSensore());
        assertEquals(25.5, misurazione.getValore());
        assertEquals("°C", misurazione.getUnitaMisura());
        assertEquals(" (variazione: 2.0%)", misurazione.getInfoVariazione());
    }

    @Test
    void testGetValoreFormattato() {
        Misurazione misurazione1 = new Misurazione("temperatura", "sensoreA", 25.5, "°C");
        assertEquals("25,5", misurazione1.getValoreFormattato());

        Misurazione misurazione2 = new Misurazione("temperatura", "sensoreA", 25.0, "°C");
        assertEquals("25,0", misurazione2.getValoreFormattato());

        Misurazione misurazione3 = new Misurazione("temperatura", "sensoreA", 25.56789, "°C");
        assertEquals("25,6", misurazione3.getValoreFormattato());
    }

    @Test
    void testToStringSenzaVariazione() {
        Misurazione misurazione = new Misurazione("temperatura", "sensoreA", 25.5, "°C");
        assertEquals("sensoreA (temperatura): 25,5 °C", misurazione.toString());
    }

    @Test
    void testToStringConVariazione() {
        Misurazione misurazione = new Misurazione("temperatura", "sensoreA", 25.5, "°C", " (variazione: 2,0%)");
        assertEquals("sensoreA (temperatura): 25,5 °C (variazione: 2,0%)", misurazione.toString());
    }
}
