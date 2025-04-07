package sensori;

import java.util.Random;

public class A {
    private final String nome;
    private final Random random = new Random();
    private double ultimaMisura = 25.0; // Valore iniziale
    private long ultimoAggiornamento = 0;

    public A(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Simula la lettura della temperatura con variazioni realistiche
     */
    public double getMeasure() {
        long tempoCorrente = System.currentTimeMillis();
        long tempoPassato = tempoCorrente - ultimoAggiornamento;

        // Se è la prima lettura o è passato molto tempo (>10s), maggiore variazione
        double maxVariazione;
        if (ultimoAggiornamento == 0 || tempoPassato > 10000) {
            maxVariazione = 2.0; // Variazione maggiore dopo tempo lungo
        } else {
            maxVariazione = 0.2;
        }

        // Genera una variazione casuale limitata
        double variazione = random.nextDouble() * maxVariazione * 2 - maxVariazione;
        double nuovaMisura = ultimaMisura + variazione;

        // Memorizza lo stato per la prossima lettura
        ultimaMisura = nuovaMisura;
        ultimoAggiornamento = tempoCorrente;

        return nuovaMisura;
    }

    // Per scopi di testing
    public double getUltimaMisura() {
        return ultimaMisura;
    }

    public void setUltimaMisura(double valore) {
        this.ultimaMisura = valore;
    }
}
