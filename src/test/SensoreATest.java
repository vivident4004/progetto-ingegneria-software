package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
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
    void testGetMeasureVariazioneConsistente() {
        // Impostiamo un valore noto
        sensore.setUltimaMisura(25.0);

        // Prendiamo diverse misure consecutive e verifichiamo che non varino troppo
        double prima = sensore.getMeasure();
        double max = prima + 0.5; // Considerando la variazione massima di 0.3 + un margine
        double min = prima - 0.5;

        // Facciamo diverse letture per confermare consistenza
        for (int i = 0; i < 5; i++) {
            double next = sensore.getMeasure();
            assertTrue(next >= min && next <= max,
                    "La misura " + next + " è fuori dall'intervallo previsto [" + min + ", " + max + "]");
        }
    }

    @Test
    void testSetUltimaMisuraGetUltimaMisura() {
        // Test getters e setters aggiunti per testing
        sensore.setUltimaMisura(22.5);
        assertEquals(22.5, sensore.getUltimaMisura());
    }
}
