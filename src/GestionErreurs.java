public class GestionErreurs {
    private static int nbLexicales = 0;
    private static int nbSyntaxiques = 0;

    public static void lexicale(int ligne, int colonne, String message) {
        System.out.printf("ERREUR LEXICALE [L%02d:C%02d] : %s%n", ligne, colonne, message);
        nbLexicales++;
    }

    public static void syntaxique(int ligne, int colonne, String message) {
        System.out.printf("ERREUR SYNTAXIQUE [L%02d:C%02d] : %s%n", ligne, colonne, message);
        nbSyntaxiques++;
    }

    // Appelé à la fin de l’analyse lexicale
    public static void bilanLexical() {
        System.out.println(); // ligne vide
        if (nbLexicales == 0) {
            System.out.println("Aucune erreur détectée");
        } else {
            System.out.printf("=== %d erreur(s) lexicale(s) détectée(s) ===%n%n", nbLexicales);
        }
    }

    // Appelé à la fin de l’analyse syntaxique
    public static void bilanSyntaxique() {
        if (nbSyntaxiques > 0) {
            System.out.printf("=== %d erreur(s) syntaxique(s) détectée(s) ===%n", nbSyntaxiques);
        }
    }

    public static void reset() {
        nbLexicales = 0;
        nbSyntaxiques = 0;
    }
}