package observer;

import model.Misurazione;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Centralina implements Observer {
    private final String nome;
    private final List<Misurazione> ultimeMisure = new ArrayList<>();

    public Centralina(String nome) {
        this.nome = nome;
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (arg instanceof Misurazione misurazione) {
            // Aggiorna o aggiungi la misurazione alla lista
            boolean aggiornata = false;
            for (int i = 0; i < ultimeMisure.size(); i++) {
                if (ultimeMisure.get(i).getNomeSensore().equals(misurazione.getNomeSensore())) {
                    ultimeMisure.set(i, misurazione);
                    aggiornata = true;
                    break;
                }
            }

            if (!aggiornata) {
                ultimeMisure.add(misurazione);
            }

            System.out.println(nome + ": ricevuta misurazione da " + misurazione);
        } else {
            System.out.println(nome + ": dato non riconosciuto da " + arg);
        }
    }

    public void mostraMisurazioni() {
        System.out.println("\n=== STATO ATTUALE CENTRALINA (" + nome + ") ===");
        if (ultimeMisure.isEmpty()) {
            System.out.println("Nessuna misurazione disponibile");
        } else {
            for (Misurazione m : ultimeMisure) {
                System.out.println("- " + m);
            }
        }
    }

    public String getNome() {
        return nome;
    }
}
