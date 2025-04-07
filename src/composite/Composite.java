package composite;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
//        return children.stream()
//                .flatMap(component -> component.ottieniMisura().stream())
//                .collect(Collectors.toList());
        List<Double> misureAggregate = new ArrayList<>();
        for (Component child : children) {
            misureAggregate.addAll(child.ottieniMisura()); // Aggrega le liste
        }
        //System.out.println("Composite '" + nome + "': Misure aggregate: " + misureAggregate);
        return misureAggregate;
    }

    /**
     * Delega la richiesta di notifica a tutti i componenti figli.
     */
//    @Override
//    public void notificaMisura() {
//        System.out.println("Composite '" + nome + "': Inoltro notifica ai figli...");
//        for (Component child : children) {
//            child.notificaMisura();
//        }
//    }

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
