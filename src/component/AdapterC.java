package component;

import model.Misurazione;
import sensori.C;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.text.DecimalFormat;

@SuppressWarnings("deprecation")
public class AdapterC extends Observable implements Component {
    private final C sensoreC;
    private final String tipologia = "pressione";
    private Double ultimaMisuraValore = null;
    private static final DecimalFormat df = new DecimalFormat("0.0");

    public AdapterC(C sensoreC) {
        this.sensoreC = sensoreC;
    }

    @Override
    public List<Double> ottieniMisura() {
        double misura = sensoreC.measure();
        ultimaMisuraValore = misura;
        return Collections.singletonList(misura);
    }

    @Override
    public void notificaMisura() {
        double misuraCorrente = sensoreC.measure();

        // Calcola variazione percentuale se disponibile
        String variazioneInfo = "";
        if (ultimaMisuraValore != null) {
            double variazione = ((misuraCorrente - ultimaMisuraValore) / Math.abs(ultimaMisuraValore)) * 100;
            variazioneInfo = " (variazione: " + df.format(variazione) + "%)";
        }

        String unitaMisura = "hPa";
        Misurazione misurazione = new Misurazione(
                tipologia,
                sensoreC.getNome(),
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
        return sensoreC.getNome() + " (" + tipologia + ")";
    }

    // Per test
    public C getSensoreC() {
        return sensoreC;
    }

    public Double getUltimaMisuraValore() {
        return ultimaMisuraValore;
    }
}
