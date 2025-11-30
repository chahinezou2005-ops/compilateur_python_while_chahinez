public class Main {
    public static void main(String[] args) {
        String code = """
            age = 25$
"salut
CHAHINEZ = vrai
            """;


          /*   String code = """
            chahinez = 20
            age = 18.5
            while age > 0:
                print("Bonjour Chahinez")
                age = age - 1
            else:
                print("Terminé")
            # commentaire ignoré
            """;*/


        
        Lexer lexer = new Lexer(code);
        lexer.analyser();
        lexer.afficher();
    }
}