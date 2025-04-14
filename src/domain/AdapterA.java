package domain;

import data.Misurazione;
import sensors.A;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.text.DecimalFormat;

@SuppressWarnings("deprecation")
public class AdapterA extends Observable implements Component {
    private final A sensoreA;
    private final String tipologia = "temperatura";
    private Double ultimaMisuraValore = null;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public AdapterA(A sensoreA) {
        this.sensoreA = sensoreA;
    }

    @Override
    public List<Double> ottieniMisura() {
        double misura = sensoreA.getMeasure();
        ultimaMisuraValore = misura;
        // Le foglie ritornano una lista con un solo elemento
        return Collections.singletonList(misura);
    }

    @Override
    public void notificaMisura() {
        double misuraCorrente = sensoreA.getMeasure();

        // Calcola variazione percentuale se disponibile
        String variazioneInfo = "";
        if (ultimaMisuraValore != null) {
            double variazione = ((misuraCorrente - ultimaMisuraValore) / Math.abs(ultimaMisuraValore)) * 100;
            variazioneInfo = " (variazione: " + df.format(variazione) + "%)";
        }

        String unitaMisura = "°C";
        Misurazione misurazione = new Misurazione(
                tipologia,
                sensoreA.getNome(),
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
        return sensoreA.getNome() + " (" + tipologia + ")";
    }

    // Per test
    public A getSensoreA() {
        return sensoreA;
    }

    public Double getUltimaMisuraValore() {
        return ultimaMisuraValore;
    }
}
