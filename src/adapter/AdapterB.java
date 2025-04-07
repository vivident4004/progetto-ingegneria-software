package adapter;

import composite.Component;
import model.Misurazione;
import sensori.B;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.text.DecimalFormat;

@SuppressWarnings("deprecation")
public class AdapterB extends Observable implements Component {
    private final B sensoreB;
    private final String tipologia = "umidità";
    private Double ultimaMisuraValore = null;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public AdapterB(B sensoreB) {
        this.sensoreB = sensoreB;
    }

    @Override
    public List<Double> ottieniMisura() {
        double misura = sensoreB.misura();
        ultimaMisuraValore = misura;
        return Collections.singletonList(misura);
    }

    @Override
    public void notificaMisura() {
        double misuraCorrente = sensoreB.misura();

        // Calcola variazione percentuale se disponibile
        String variazioneInfo = "";
        if (ultimaMisuraValore != null) {
            double variazione = ((misuraCorrente - ultimaMisuraValore) / Math.abs(ultimaMisuraValore)) * 100;
            variazioneInfo = " (variazione: " + df.format(variazione) + "%)";
        }

        String unitaMisura = "%";
        Misurazione misurazione = new Misurazione(
                tipologia,
                sensoreB.getNome(),
                misuraCorrente,
                unitaMisura,
                variazioneInfo
        );

        // Aggiorna l'ultima misura
        ultimaMisuraValore = misuraCorrente;

        setChanged();
        notifyObservers(misurazione);
    }

    @Override
    public String toString() {
        return sensoreB.getNome() + " (" + tipologia + ")";
    }

    // Per test
    public B getSensoreB() {
        return sensoreB;
    }

    public Double getUltimaMisuraValore() {
        return ultimaMisuraValore;
    }
}
