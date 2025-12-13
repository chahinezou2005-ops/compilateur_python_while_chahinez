import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== MINI COMPILATEUR (while en phython) ===");
        System.out.println("Entrez le  code puis appuyez sur Entrée :");
        System.out.println("(Terminez avec Ctrl + Z puis Entrée)");
        System.out.print("> ");

StringBuilder codeBuilder = new StringBuilder();

while (sc.hasNextLine()) {
    codeBuilder.append(sc.nextLine()).append("\n");
}

String code = codeBuilder.toString().trim();
        try {
            System.out.println("\n=== ANALYSE LEXICALE ===\n");
            Lexer lexer = new Lexer(code);
            lexer.analyser();
            lexer.afficher();

            System.out.println("\n=== ANALYSE SYNTAXIQUE ===");
            Parser parser = new Parser(lexer.getTokens());
            parser.Z();

        } catch (Exception e) {
            System.out.println("ERREUR lors de l'analyse !");
            System.out.println("Détail : " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}