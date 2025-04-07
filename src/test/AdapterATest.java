package test; // Assicurati che il package sia corretto

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import adapter.AdapterA;
import sensori.A;
import composite.Component;
import model.Misurazione;

import java.util.List;
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("deprecation")
class AdapterATest {

    private AdapterA adapter;
    private A sensore;

    private static class MockSensoreA extends A {
        private double nextValue = 20.0;

        public MockSensoreA(String nome) {
            super(nome);
        }

        @Override
        public double getMeasure() {
            return nextValue;
        }

        public void setNextValue(double value) {
            this.nextValue = value;
        }
    }

    @BeforeEach
    void setUp() {
        sensore = new MockSensoreA("TestAdapterA");
        adapter = new AdapterA(sensore);
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
        ((MockSensoreA) sensore).setNextValue(19.8);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");
        List<Double> misure = adapter.ottieniMisura();
        assertNotNull(misure);
        assertEquals(1, misure.size(), "Deve restituire una lista con un solo elemento.");
        assertEquals(19.8, misure.get(0), 0.001, "Il valore nella lista non corrisponde a quello del sensore.");
        assertEquals(19.8, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato.");
    }

    @Test
    @DisplayName("notificaMisura: notifica observer con Misurazione e aggiorna ultima misura")
    void testNotificaMisura() {
        ((MockSensoreA) sensore).setNextValue(21.5); // Imposta valore sensore
        TestObserver observer = new TestObserver();
        adapter.addObserver(observer);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");

        adapter.notificaMisura();

        assertEquals(1, observer.getUpdateCount(), "L'observer deve essere notificato una volta.");
        assertNotNull(observer.getLastArg(), "L'argomento della notifica non può essere null.");
        assertInstanceOf(Misurazione.class, observer.getLastArg(), "L'argomento deve essere di tipo Misurazione.");

        Misurazione notifica = (Misurazione) observer.getLastArg();
        assertEquals("temperatura", notifica.getTipologia(), "La tipologia nella notifica non è corretta.");
        assertEquals("TestAdapterA", notifica.getNomeSensore(), "Il nome sensore nella notifica non è corretto.");
        assertEquals(21.5, notifica.getValore(), 0.001, "Il valore nella notifica non è corretto.");
        assertEquals("°C", notifica.getUnitaMisura(), "L'unità di misura nella notifica non è corretta.");
        assertEquals(21.5, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato dopo la notifica.");
    }

    @Test
    @DisplayName("Deve contenere il sensore corretto")
    void testSensoreCorretto() {
        assertSame(sensore, adapter.getSensoreA());
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
