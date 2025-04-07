package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sensori.B;

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
    void testMisuraInizialeInRange() {
        double misura = sensore.misura();
        assertTrue(misura >= 40.0 && misura <= 60.0);
    }

    @Test
    void testMisuraVariazioneConsistente() {
        // Impostiamo un valore noto
        sensore.setUltimaMisura(50.0);

        // Prendiamo diverse misure consecutive e verifichiamo che non varino troppo
        double prima = sensore.misura();
        double max = prima + 1.5; // Considerando la variazione massima di 1.0 + un margine
        double min = prima - 1.5;

        // Facciamo diverse letture per confermare consistenza
        for (int i = 0; i < 5; i++) {
            double next = sensore.misura();
            assertTrue(next >= min && next <= max,
                    "La misura " + next + " è fuori dall'intervallo previsto [" + min + ", " + max + "]");
        }
    }

    @Test
    void testSetUltimaMisuraGetUltimaMisura() {
        sensore.setUltimaMisura(45.5);
        assertEquals(45.5, sensore.getUltimaMisura());
    }
}
