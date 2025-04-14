import domain.*;
import observer.Centralina;
import sensori.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

@SuppressWarnings("deprecation")
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Component> sensoriDisponibili = new ArrayList<>();
        Centralina centralina;
        Composite stazione;

        // Inizializzazione del sistema
        System.out.println("=== SISTEMA DI MONITORAGGIO SENSORI ===");
        //System.out.println("Inizializzazione...");

        // Creazione sensori e adapter
        sensoriDisponibili.add(new AdapterA(new A("termometro esterno")));
        sensoriDisponibili.add(new AdapterA(new A("termometro interno")));
        sensoriDisponibili.add(new AdapterB(new B("igrometro")));
        sensoriDisponibili.add(new AdapterC(new C("barometro")));

        // Creazione centralina
        centralina = new Centralina("Centralina");

        // Creazione stazione meteo (radice del composite)
        stazione = new Composite("Stazione di monitoraggio");

        // Registrazione di tutti i sensori alla centralina e aggiunta alla stazione
        for (Component sensore : sensoriDisponibili) {
            if (sensore instanceof Observable) {
                ((Observable) sensore).addObserver(centralina);
            }
            stazione.add(sensore);
        }

        //System.out.println("Sistema inizializzato con successo!\n");

        boolean continua = true;
        while (continua) {
            System.out.println("\n=== MENÚ PRINCIPALE ===");
            System.out.println("1. Mostra le ultime misure ricevute dalla centralina");
            System.out.println("2. Forza l'aggiornamento e la notifica di un sensore specifico");
            System.out.println("3. Forza l'aggiornamento e la notifica di tutti i sensori"); // observer push
            System.out.println("0. Esci");
            System.out.print("\nScegli un'opzione: ");

            int scelta;
            try {
                scelta = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Selezione non valida. Inserisci un numero.");
                continue;
            }

            switch (scelta) {
                case 0:
                    continua = false;
                    System.out.println("Chiusura del sistema...");
                    break;

                case 1:
                    centralina.mostraMisurazioni();
                    break;

                case 2:
                    mostraSensoriEAggiorna(sensoriDisponibili, scanner);
                    break;

                case 3:
                    System.out.println("Aggiornamento di tutti i sensori...");
                    stazione.notificaMisura();
                    System.out.println("Aggiornamento completato.");
                    break;

                default:
                    System.out.println("Opzione non valida. Riprova.");
            }
        }

        scanner.close();
        System.out.println("Sistema terminato.");
    }

    private static void mostraSensoriEAggiorna(List<Component> sensori, Scanner scanner) {
        System.out.println("\n=== SENSORI DISPONIBILI ===");
        for (int i = 0; i < sensori.size(); i++) {
            System.out.println((i+1) + ". " + sensori.get(i));
        }
        System.out.println("0. Annulla");

        System.out.print("\nSeleziona un sensore da aggiornare: ");
        try {
            int scelta = Integer.parseInt(scanner.nextLine());

            if (scelta == 0) {
                return;
            }

            if (scelta >= 1 && scelta <= sensori.size()) {
                Component sensore = sensori.get(scelta-1);
                System.out.println("Lettura di " + sensore + "...");
                sensore.notificaMisura();
                System.out.println("Lettura aggiornata.");
            } else {
                System.out.println("Selezione non valida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Selezione non valida. Inserisci un numero.");
        }
    }
}
