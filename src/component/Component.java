package component;

import java.util.List;

public interface Component {
    List<Double> ottieniMisura();
    void notificaMisura();
    String toString(); // Per visualizzazione nell'interfaccia utente
}
