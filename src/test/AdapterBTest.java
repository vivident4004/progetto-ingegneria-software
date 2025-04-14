package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import domain.AdapterB;
import sensori.B;
import domain.Component;
import data.Misurazione;

import java.util.List;
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("deprecation")
class AdapterBTest {

    private AdapterB adapter;
    private B sensore;

    private static class MockSensoreB extends B {
        private double nextValue = 50.0;

        public MockSensoreB(String nome) {
            super(nome);
        }

        @Override
        public double misura() {
            return nextValue;
        }

        public void setNextValue(double value) {
            this.nextValue = value;
        }
    }

    @BeforeEach
    void setUp() {
        sensore = new MockSensoreB("TestAdapterB");
        adapter = new AdapterB(sensore);
    }

    @Test
    @DisplayName("Deve implementare Component")
    void testImplementaComponent() {
        assertTrue(adapter instanceof Component);
    }

    @Test
    @DisplayName("Deve estendere Observable")
    void testEstendeObservable() {
        assertTrue(adapter instanceof Observable);
    }

    @Test
    @DisplayName("ottieniMisura: restituisce lista singola e aggiorna ultima misura")
    void testOttieniMisura() {
        ((MockSensoreB) sensore).setNextValue(48.5);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");
        List<Double> misure = adapter.ottieniMisura();
        assertNotNull(misure);
        assertEquals(1, misure.size(), "Deve restituire una lista con un solo elemento.");
        assertEquals(48.5, misure.get(0), 0.001, "Il valore nella lista non corrisponde a quello del sensore.");
        assertEquals(48.5, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato.");
    }

    @Test
    @DisplayName("notificaMisura: notifica observer con Misurazione e aggiorna ultima misura")
    void testNotificaMisura() {
        ((MockSensoreB) sensore).setNextValue(52.3); // Imposta valore sensore
        TestObserver observer = new TestObserver();
        adapter.addObserver(observer);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");

        adapter.notificaMisura();

        assertEquals(1, observer.getUpdateCount(), "L'observer deve essere notificato una volta.");
        assertNotNull(observer.getLastArg(), "L'argomento della notifica non può essere null.");
        assertInstanceOf(Misurazione.class, observer.getLastArg(), "L'argomento deve essere di tipo Misurazione.");

        Misurazione notifica = (Misurazione) observer.getLastArg();
        assertEquals("umidità", notifica.getTipologia(), "La tipologia nella notifica non è corretta.");
        assertEquals("TestAdapterB", notifica.getNomeSensore(), "Il nome sensore nella notifica non è corretto.");
        assertEquals(52.3, notifica.getValore(), 0.001, "Il valore nella notifica non è corretto.");
        assertEquals("%", notifica.getUnitaMisura(), "L'unità di misura nella notifica non è corretta.");
        assertEquals(52.3, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato dopo la notifica.");
    }

    @Test
    @DisplayName("Deve contenere il sensore corretto")
    void testSensoreCorretto() {
        assertSame(sensore, adapter.getSensoreB());
    }

    private static class TestObserver implements Observer {
        private int updateCount = 0;
        private Object lastArg = null;

        @Override
        public void update(Observable o, Object arg) {
            updateCount++;
            lastArg = arg;
        }
        public int getUpdateCount() { return updateCount; }
        public Object getLastArg() { return lastArg; }
    }
}
