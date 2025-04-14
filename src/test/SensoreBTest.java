package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sensors.B;

class SensoreBTest {
    private B sensore;

    @BeforeEach
    void setUp() {
        sensore = new B("TestSensoreB");
    }

    @Test
    void testGetNome() {
        assertEquals("TestSensoreB", sensore.getNome());
    }

    @Test
    void testMisuraIniziale() {
        double misura = sensore.misura();
        assertTrue(misura >= 40.0 && misura <= 60.0);
    }

    @Test
    @DisplayName("Verifica variazioni consecutive entro limiti")
    void testVariazioniConsecutive() {
        double misuraPrecedente = sensore.misura();
        for (int i = 0; i < 5; i++) {
            double nuovaMisura = sensore.misura();
            double variazione = Math.abs(nuovaMisura - misuraPrecedente);
            double limiteVariazione = 1.0 + 0.001;

            assertTrue(variazione <= limiteVariazione,
                    "Variazione eccessiva: " + variazione +
                            " tra " + misuraPrecedente + " e " + nuovaMisura +
                            " (limite: " + limiteVariazione + ")");

            misuraPrecedente = nuovaMisura;
        }
    }
}
