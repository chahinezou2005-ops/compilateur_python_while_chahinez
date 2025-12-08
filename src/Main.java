import java.nio.file.*;
import java.util.Scanner;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nom du fichier à tester (ex: test1_correct.txt | tests/test2_for.txt | test3_incorrect.txt | test5_myname.txt | test_vide.txt) : ");
        String fichier = sc.nextLine().trim();

        try {
            // obtient le dossier contenant le .class/.jar
            String jarPath = new File(Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getParent();

            Path testFile = Paths.get(jarPath, "tests", fichier);

            String code = Files.readString(testFile);

            System.out.println("\n=== ANALYSE LEXICALE ===\n");
            Lexer lexer = new Lexer(code);
            lexer.analyser();
            lexer.afficher();

            System.out.println("\n=== ANALYSE SYNTAXIQUE ===");
            Parser parser = new Parser(lexer.getTokens());
            parser.Z();

        } catch (Exception e) {
            System.out.println("ERREUR : Fichier non trouvé ou autre erreur !");
            System.out.println("Détail : " + e.getClass().getSimpleName() + " -> " + e.getMessage());
            System.out.println("Assure-toi que le dossier 'tests' est dans le même dossier que le .jar");
        } finally {
            sc.close();
        }
    }
}