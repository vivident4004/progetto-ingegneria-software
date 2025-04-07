package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import observer.Centralina;
import model.Misurazione;
import java.util.Observable;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

class CentralinaTest {
    private Centralina centralina;
    private TestObservable observable;

    // Una classe Observable di test
    private static class TestObservable extends Observable {
        public void triggerNotify(Object arg) {
            setChanged();
            notifyObservers(arg);
        }
    }

    @BeforeEach
    void setUp() {
        centralina = new Centralina("TestCentralina");
        observable = new TestObservable();
        observable.addObserver(centralina);
    }

    @Test
    void testGetNome() {
        assertEquals("TestCentralina", centralina.getNome());
    }

    @Test
    void testUpdateConMisurazioneValida() {
        // Creiamo una misurazione di test
        Misurazione misurazione = new Misurazione("test", "sensoreTest", 25.0, "unità");

        // La centralina non ha misure inizialmente
        centralina.mostraMisurazioni(); // Solo per verificare che non ci siano eccezioni

        // Notifichiamo la centralina
        observable.triggerNotify(misurazione);

        // Verifichiamo che la misurazione sia ricevuta e gestita correttamente
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        centralina.mostraMisurazioni();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("sensoreTest (test): 25,0 unità"));
    }

    @Test
    void testUpdateConDatiNonValidi() {
        // Notifichiamo con dati non validi
        observable.triggerNotify("dati non validi");

        // La centralina dovrebbe gestire correttamente anche dati non validi
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        centralina.mostraMisurazioni();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("Nessuna misurazione disponibile"));
    }

    @Test
    void testAggiornamentoMisuraEsistente() {
        // Prima misurazione
        Misurazione misura1 = new Misurazione("test", "sensoreTest", 25.0, "unità");
        observable.triggerNotify(misura1);

        // Seconda misurazione dallo stesso sensore
        Misurazione misura2 = new Misurazione("test", "sensoreTest", 26.0, "unità");
        observable.triggerNotify(misura2);

        // Verifichiamo che la seconda misurazione abbia sostituito la prima
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        centralina.mostraMisurazioni();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("26,0 unità"));
        assertFalse(output.contains("25,0 unità"));
    }

    @Test
    void testMultipleMisurazioniDaSensoriDiversi() {
        Misurazione misura1 = new Misurazione("temperatura", "sensore1", 25.0, "°C");
        Misurazione misura2 = new Misurazione("umidità", "sensore2", 50.0, "%");

        observable.triggerNotify(misura1);
        observable.triggerNotify(misura2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        centralina.mostraMisurazioni();

        System.setOut(originalOut);
        String output = outputStream.toString();

        assertTrue(output.contains("sensore1 (temperatura): 25,0 °C"));
        assertTrue(output.contains("sensore2 (umidità): 50,0 %"));
    }
}
