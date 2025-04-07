package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import composite.Composite;
import composite.Component;
import java.util.List;

class CompositeTest {
    private Composite composite;
    private TestComponent component1;
    private TestComponent component2;

    private static class TestComponent implements Component {
        private final String nome;
        private final double misura;
        private boolean notificato = false;

        public TestComponent(String nome, double misura) {
            this.nome = nome;
            this.misura = misura;
        }

        @Override
        public List<Double> ottieniMisura() {
            return List.of(misura);
        }

        @Override
        public void notificaMisura() {
            notificato = true;
        }

        @Override
        public String toString() {
            return nome;
        }

        public boolean isNotificato() {
            return notificato;
        }
    }

    @BeforeEach
    void setUp() {
        composite = new Composite("Test Composite");
        component1 = new TestComponent("Component1", 10.0);
        component2 = new TestComponent("Component2", 20.0);
    }

    @Test
    void testAddComponent() {
        composite.add(component1);
        assertEquals(1, composite.getChildren().size());
        assertEquals(component1, composite.getChildren().get(0));
    }

    @Test
    void testRemoveComponent() {
        composite.add(component1);
        composite.add(component2);
        assertEquals(2, composite.getChildren().size());

        composite.remove(component1);
        assertEquals(1, composite.getChildren().size());
        assertEquals(component2, composite.getChildren().get(0));
    }

    @Test
    void testOttieniMisuraAggregaDatiDaTuttiIComponenti() {
        composite.add(component1);
        composite.add(component2);

        List<Double> misure = composite.ottieniMisura();

        assertEquals(2, misure.size());
        assertTrue(misure.contains(10.0));
        assertTrue(misure.contains(20.0));
    }

    @Test
    void testOttieniMisuraConCompositeVuoto() {
        List<Double> misure = composite.ottieniMisura();
        assertTrue(misure.isEmpty());
    }

    @Test
    void testNotificaMisuraPropagaATuttiIComponenti() {
        composite.add(component1);
        composite.add(component2);

        // Verifico che all'inizio nessun componente sia stato notificato
        assertFalse(component1.isNotificato());
        assertFalse(component2.isNotificato());

        composite.notificaMisura();

        // Verifico che tutti i componenti siano stati notificati
        assertTrue(component1.isNotificato());
        assertTrue(component2.isNotificato());
    }

    @Test
    void testCompositeNidificati() {
        Composite subComposite = new Composite("Sub Composite");
        TestComponent component3 = new TestComponent("Component3", 30.0);

        subComposite.add(component3);
        composite.add(component1);
        composite.add(subComposite);

        // Verifico che ottieniMisura aggreghi correttamente dai compositi annidati
        List<Double> misure = composite.ottieniMisura();
        assertEquals(2, misure.size());
        assertTrue(misure.contains(10.0));
        assertTrue(misure.contains(30.0));

        // Verifichiamo che notificaMisura propaghi ai compositi annidati
        composite.notificaMisura();
        assertTrue(component1.isNotificato());
        assertTrue(component3.isNotificato());
    }

    @Test
    void testToString() {
        assertEquals("Test Composite [Gruppo di 0 sensori]", composite.toString());

        composite.add(component1);
        composite.add(component2);

        assertEquals("Test Composite [Gruppo di 2 sensori]", composite.toString());
    }
}
