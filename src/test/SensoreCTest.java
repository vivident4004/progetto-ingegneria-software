package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sensori.C;

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
    void testMeasureInizialeInRange() {
        double misura = sensore.measure();
        assertTrue(misura >= 1000.0 && misura <= 1020.0);
    }

    @Test
    void testMeasureVariazioneConsistente() {
        // Impostiamo un valore noto
        sensore.setUltimaMisura(1010.0);

        // Prendiamo diverse misure consecutive e verifichiamo che non varino troppo
        double prima = sensore.measure();
        double max = prima + 1.0; // Considerando la variazione massima di 0.5 + un margine
        double min = prima - 1.0;

        // Facciamo diverse letture per confermare consistenza
        for (int i = 0; i < 5; i++) {
            double next = sensore.measure();
            assertTrue(next >= min && next <= max,
                    "La misura " + next + " è fuori dall'intervallo previsto [" + min + ", " + max + "]");
        }
    }

    @Test
    void testSetUltimaMisuraGetUltimaMisura() {
        sensore.setUltimaMisura(1015.5);
        assertEquals(1015.5, sensore.getUltimaMisura());
    }
}
