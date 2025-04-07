package model;

import java.text.DecimalFormat;

/**
 * Classe che rappresenta una misurazione completa con valore e metadati
 */
public class Misurazione {
    private final String tipologia;
    private final String nomeSensore;
    private final double valore;
    private final String unitaMisura;
    private final String infoVariazione;
    private static final DecimalFormat formatter = new DecimalFormat("0.0");

    public Misurazione(String tipologia, String nomeSensore, double valore, String unitaMisura) {
        this(tipologia, nomeSensore, valore, unitaMisura, "");
    }

    public Misurazione(String tipologia, String nomeSensore, double valore,
                       String unitaMisura, String infoVariazione) {
        this.tipologia = tipologia;
        this.nomeSensore = nomeSensore;
        this.valore = valore;
        this.unitaMisura = unitaMisura;
        this.infoVariazione = infoVariazione;
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

    public String getInfoVariazione() {
        return infoVariazione;
    }

    @Override
    public String toString() {
        return nomeSensore + " (" + tipologia + "): " + getValoreFormattato() +
                " " + unitaMisura + infoVariazione;
    }
}
