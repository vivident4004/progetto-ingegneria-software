package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sensors.C;

class SensoreCTest {
    private C sensore;

    @BeforeEach
    void setUp() {
        sensore = new C("TestSensoreC");
    }

    @Test
    void testGetNome() {
        assertEquals("TestSensoreC", sensore.getNome());
    }

    @Test
    void testMisuraIniziale() {
        double misura = sensore.measure();
        assertTrue(misura >= 1000.0 && misura <= 1020.0);
    }

    @Test
    @DisplayName("Verifica variazioni consecutive entro limiti")
    void testVariazioniConsecutive() {
        double misuraPrecedente = sensore.measure();
        for (int i = 0; i < 5; i++) {
            double nuovaMisura = sensore.measure();
            double variazione = Math.abs(nuovaMisura - misuraPrecedente);

            assertTrue(variazione <= 2.0 + 0.001,
                    "Variazione eccessiva: " + variazione +
                            " tra " + misuraPrecedente + " e " + nuovaMisura);

            misuraPrecedente = nuovaMisura;
        }
    }
}
