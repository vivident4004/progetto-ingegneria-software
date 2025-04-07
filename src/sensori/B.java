package sensori;

import java.util.Random;

public class B {
    private final String nome;
    private final Random random = new Random();
    private double ultimaMisura = 50.0; // Valore iniziale per umidità
    private long ultimoAggiornamento = 0;

    public B(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Simula la lettura dell'umidità con variazioni realistiche
     */
    public double misura() {
        long tempoCorrente = System.currentTimeMillis();
        long tempoPassato = tempoCorrente - ultimoAggiornamento;

        // Se è la prima lettura o è passato molto tempo (>10s), maggiore variazione
        double maxVariazione;
        if (ultimoAggiornamento == 0 || tempoPassato > 10000) {
            maxVariazione = 5.0;
        } else {
            maxVariazione = 0.5;
        }

        double variazione = random.nextDouble() * maxVariazione * 2 - maxVariazione;
        double nuovaMisura = ultimaMisura + variazione;

        // Memorizza lo stato per la prossima lettura
        ultimaMisura = nuovaMisura;
        ultimoAggiornamento = tempoCorrente;

        return nuovaMisura;
    }

    // Per test
    public double getUltimaMisura() {
        return ultimaMisura;
    }

    public void setUltimaMisura(double valore) {
        this.ultimaMisura = valore;
    }
}
