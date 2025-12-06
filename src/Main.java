public class Main {
    public static void main(String[] args) {
        String code = """
            
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