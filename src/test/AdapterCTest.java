package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import adapter.AdapterC;
import sensori.C;
import composite.Component;
import model.Misurazione;

import java.util.List;
import java.util.Observer;
import java.util.Observable;

@SuppressWarnings("deprecation")
class AdapterCTest {

    private AdapterC adapter;
    private C sensore;

    private static class MockSensoreC extends C {
        private double nextValue = 1010.0;

        public MockSensoreC(String nome) {
            super(nome);
        }

        @Override
        public double measure() {
            return nextValue;
        }

        public void setNextValue(double value) {
            this.nextValue = value;
        }
    }

    @BeforeEach
    void setUp() {
        sensore = new MockSensoreC("TestAdapterC");
        adapter = new AdapterC(sensore);
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
        ((MockSensoreC) sensore).setNextValue(1012.5);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");
        List<Double> misure = adapter.ottieniMisura();
        assertNotNull(misure);
        assertEquals(1, misure.size(), "Deve restituire una lista con un solo elemento.");
        assertEquals(1012.5, misure.get(0), 0.001, "Il valore nella lista non corrisponde a quello del sensore.");
        assertEquals(1012.5, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato.");
    }

    @Test
    @DisplayName("notificaMisura: notifica observer con Misurazione e aggiorna ultima misura")
    void testNotificaMisura() {
        ((MockSensoreC) sensore).setNextValue(1008.7); // Imposta valore sensore
        TestObserver observer = new TestObserver();
        adapter.addObserver(observer);
        assertNull(adapter.getUltimaMisuraValore(), "Precondizione: ultimaMisuraValore deve essere null.");

        adapter.notificaMisura();

        assertEquals(1, observer.getUpdateCount(), "L'observer deve essere notificato una volta.");
        assertNotNull(observer.getLastArg(), "L'argomento della notifica non può essere null.");
        assertInstanceOf(Misurazione.class, observer.getLastArg(), "L'argomento deve essere di tipo Misurazione.");

        Misurazione notifica = (Misurazione) observer.getLastArg();
        assertEquals("pressione", notifica.getTipologia(), "La tipologia nella notifica non è corretta.");
        assertEquals("TestAdapterC", notifica.getNomeSensore(), "Il nome sensore nella notifica non è corretto.");
        assertEquals(1008.7, notifica.getValore(), 0.001, "Il valore nella notifica non è corretto.");
        assertEquals("hPa", notifica.getUnitaMisura(), "L'unità di misura nella notifica non è corretta.");
        assertEquals(1008.7, adapter.getUltimaMisuraValore(), 0.001, "ultimaMisuraValore non è stato aggiornato dopo la notifica.");
    }

    @Test
    @DisplayName("Deve contenere il sensore corretto")
    void testSensoreCorretto() {
        assertSame(sensore, adapter.getSensoreC());
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
