
public class GestionErreurs {
    private static int compteurErreurs = 0;

    // Pour les erreurs lexicales
    public static void lexicale(int ligne, int colonne, String message) {
        System.out.printf("ERREUR LEXICALE [L%02d:C%02d] : %s%n", ligne, colonne, message);
        compteurErreurs++;
    }


    // Pour les erreurs syntaxiques
    public static void syntaxique(int ligne, int colonne, String message) {
    System.out.printf("ERREUR SYNTAXIQUE [L%02d:C%02d] : %s%n", ligne, colonne, message);
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
    // Afficher les nombres d'erreurs
    public static int getNombreErreurs() {
        return compteurErreurs;
    }

    
}