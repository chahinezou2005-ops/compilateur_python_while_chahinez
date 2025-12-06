public class Main {
    public static void main(String[] args) {
        String code = """
            x=1; while x<4 x=x+1; pass
            """;


           



        


       
                System.out.println("\n=== ANALYSE LEXICALE  ===\n");

        Lexer lexer = new Lexer(code);
        lexer.analyser();
        lexer.afficher();
        

        
System.out.println("\n=== ANALYSE SYNTAXIQUE ===");
        Parser parser = new Parser(lexer.getTokens());
        parser.Z(); 
}
}