package data;

import java.text.DecimalFormat;

/**
 * Classe che rappresenta una misurazione completa con valore e metadati
 */
public class Misurazione {
    private final String tipologia;
    private final String nomeSensore;
    private final double valore;
    private final String unitaMisura;
    private final String variazione;
    private static final DecimalFormat formatter = new DecimalFormat("0.0");

    public Misurazione(String tipologia, String nomeSensore, double valore,
                       String unitaMisura, String variazione) {
        this.tipologia = tipologia;
        this.nomeSensore = nomeSensore;
        this.valore = valore;
        this.unitaMisura = unitaMisura;
        this.variazione = variazione;
    }

    public String getTipologia() {
        return tipologia;
    }

    public String getNomeSensore() {
        return nomeSensore;
    }

    public double getValore() {
        return valore;
    }

    public String getValoreFormattato() {
        return formatter.format(valore);
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    @Override
    public String toString() {
        return nomeSensore + " (" + tipologia + "): " + getValoreFormattato() +
                " " + unitaMisura + variazione;
    }
}
