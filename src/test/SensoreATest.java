package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sensori.A;

class SensoreATest {
    private A sensore;

    @BeforeEach
    void setUp() {
        sensore = new A("TestSensoreA");
    }

    @Test
    void testGetNome() {
        assertEquals("TestSensoreA", sensore.getNome());
    }

    @Test
    void testGetMeasureInizialeInRange() {
        double misura = sensore.getMeasure();
        assertTrue(misura >= 20.0 && misura <= 30.0);
    }

    @Test
    @DisplayName("Verifica variazioni consecutive entro limiti")
    void testVariazioniConsecutive() {
        sensore.setUltimaMisura(25.0);
        double misuraPrecedente = sensore.getMeasure();
        for (int i = 0; i < 5; i++) {
            double nuovaMisura = sensore.getMeasure();
            double variazione = Math.abs(nuovaMisura - misuraPrecedente);
            double limiteVariazione = 0.4 + 0.001;

            assertTrue(variazione <= limiteVariazione,
                    "Variazione eccessiva: " + variazione +
                            " tra " + misuraPrecedente + " e " + nuovaMisura +
                            " (limite: " + limiteVariazione + ")");

            misuraPrecedente = nuovaMisura;
        }
    }


    @Test
    void testSetUltimaMisuraGetUltimaMisura() {
        sensore.setUltimaMisura(22.5);
        assertEquals(22.5, sensore.getUltimaMisura());
    }
}
