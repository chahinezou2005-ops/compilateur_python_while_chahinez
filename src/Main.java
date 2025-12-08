import java.nio.file.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nom du fichier à tester (ex: test1_correct.txt) : ");
        String fichier = sc.nextLine().trim();

        try {
           


            String code = Files.readString(Paths.get("tests", fichier));

            System.out.println("\n=== ANALYSE LEXICALE ===\n");
            Lexer lexer = new Lexer(code);
            lexer.analyser();
            lexer.afficher();

            System.out.println("\n=== ANALYSE SYNTAXIQUE ===");
            Parser parser = new Parser(lexer.getTokens());
            parser.Z();

        } catch (Exception e) {
            System.out.println("ERREUR : Fichier non trouvé → tests/" + fichier);
            System.out.println("Vérifie que le dossier 'tests' est bien à côté du .jar");
        }
        sc.close();
    }
}
