
public class GestionErreurs {
    private static int compteurErreurs = 0;

    // Pour les erreurs lexicales
    public static void lexicale(int ligne, int colonne, String message) {
        System.out.printf("ERREUR LEXICALE [L%02d:C%02d] : %s%n", ligne, colonne, message);
        compteurErreurs++;
    }

    

    // Affiché à la fin
    public static void bilan() {
        if (compteurErreurs == 0) {
            System.out.println("Aucune erreur détectée");
        } else {
            System.out.printf("%n=== %d erreur(s) détectée(s)  ===%n%n", compteurErreurs);
        }
    }

    public static int getNombreErreurs() {
        return compteurErreurs;
    }
}