package domain;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
    private final String nome;
    private final List<Component> children = new ArrayList<>();

    public Composite(String nome) {
        this.nome = nome;
    }

    public void add(Component component) {
        children.add(component);
    }

    public void remove(Component component) {
        children.remove(component);
    }

    // Metodo getter per i figli per i test
    public List<Component> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * Ottiene le misure aggregando quelle di tutti i figli.
     */
    @Override
    public List<Double> ottieniMisura() {
        List<Double> misureAggregate = new ArrayList<>();
        for (Component child : children) {
            misureAggregate.addAll(child.ottieniMisura()); // Aggrega le liste
        }
        return misureAggregate;
    }

    /**
     * Delega la richiesta di notifica a tutti i componenti figli.
     */
    @Override
    public void notificaMisura() {
        for (Component component : children) {
            component.notificaMisura();
        }
    }

    @Override
    public String toString() {
        return nome + " [Gruppo di " + children.size() + " sensori]";
    }
}
