package sensori;

import java.util.Random;

public class C {
    private final String nome;
    private final Random random = new Random();
    private double ultimaMisura = 1010.0; // Valore iniziale per pressione
    private long ultimoAggiornamento = 0;

    public C(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    /**
     * Simula la lettura della pressione con variazioni realistiche
     */
    public double measure() {
        long tempoCorrente = System.currentTimeMillis();
        long tempoPassato = tempoCorrente - ultimoAggiornamento;

        // Se è la prima lettura o è passato molto tempo (>10s), maggiore variazione
        double maxVariazione;
        if (ultimoAggiornamento == 0 || tempoPassato > 10000) {
            maxVariazione = 5.0; // Variazione maggiore dopo tempo lungo
        } else {
            // Variazione piccola per letture frequenti
            maxVariazione = 0.5;
        }

        // Genera una variazione casuale limitata
        double variazione = random.nextDouble() * maxVariazione * 2 - maxVariazione;
        double nuovaMisura = ultimaMisura + variazione;

        // Assicurarsi che rimanga nell'intervallo desiderato (1000-1020 hPa)
//        if (nuovaMisura < 1000.0) nuovaMisura = 1000.0;
//        if (nuovaMisura > 1020.0) nuovaMisura = 1020.0;

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
