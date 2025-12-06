public class Main {
    public static void main(String[] args) {
        // String code = """
        //     age = 25
        //     while age > 0
        //     age = age - 1
        //     CHAHINEZ = True
        //     pass
        //     """;


            //  String code = """
            // chahinez = 20
            // age = 18.5
            // while age > 0
            //     print("Bonjour Chahinez")
            //     age = age - 1
            // else:
            //     print("Terminé")
            // # commentaire ignoré
            // """;



        // String code = """
        // x = 5 
        // while x < 10: 
        
        
        //      """;

        // String code = """
        // toto 42
        // while toto > 0:
        // pass 
    
            
        //     """;

        String code = """
        a=1
for i in range(10):  
    pass
while a<5:
    a=a+1
       """;

        
        Lexer lexer = new Lexer(code);
        lexer.analyser();
        lexer.afficher();

        
System.out.println("\n=== ANALYSE SYNTAXIQUE ===");
        Parser parser = new Parser(lexer.getTokens());
        parser.Z(); 
}
}