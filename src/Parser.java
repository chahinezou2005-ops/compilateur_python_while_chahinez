import java.util.List;
import java.util.Set;

public class Parser {
    private final List<Token> tokens;
    private int i = 0;
    private boolean ER = false;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

   
    public void Z() {
        // Cas  : fichier vide 
        if (getCourant().type == TokenType.FIN_FICHIER) {
            System.out.println("Fichier vide – rien à analyser syntaxiquement");
            return;
        }

        // Sinon on fait l'analyse normale
        
        programme();

        if (!ER && getCourant().type == TokenType.FIN_FICHIER) {
            System.out.println("\nProgramme syntaxiquement correct");
        } else {
            System.out.println("\nProgramme syntaxiquement incorrect");
        }
        GestionErreurs.bilanSyntaxique();
    }
    // programme → instruction+
    private void programme() {
        while (getCourant().type != TokenType.FIN_FICHIER) {
            instruction();
        }
    }

    // instruction → affectation | boucle_while | pass | ligne_vide | instruction_ignorée
    private void instruction() {
        Token t = getCourant();

        if (t.type == TokenType.FIN_FICHIER) return;

        // ligne_vide : on ignore les sauts de ligne
        if (t.type == TokenType.NOUVELLE_LIGNE) {
            i++;
            return;
        }

        // affectation : x = ...
        if (t.type == TokenType.IDENTIFIANT) {
            affectation();
            return;
        }

        // pass
        if (t.type == TokenType.MOT_CLE && "pass".equals(t.lexeme)) {
            i++;
            return;
        }

        // while ...
        if (t.type == TokenType.MOT_CLE && "while".equals(t.lexeme)) {
            boucle_while();
            return;
        }

        // instruction_ignorée : if, for, def, print, etc. → on saute toute la ligne
        if (t.type == TokenType.MOT_CLE) {
            String lex = t.lexeme;
            if (Set.of("if","in","for","def","class","print","return","break","continue").contains(lex)) {
                ignorer_instruction();
                return;
            }
        }

        // Tout le reste = erreur
        GestionErreurs.syntaxique(t.ligne, t.colonne, "Instruction non autorisée : " + t.lexeme);
        ER = true;
        i++; // on avance pour ne pas boucler
    }

    // affectation → VARIABLE "=" expression
    private void affectation() {
        consommer(TokenType.IDENTIFIANT, "Variable attendue");
        consommer(TokenType.EGAL, "'=' attendu");
        expression(); // on accepte n'importe quoi comme expression (simplifié)
    }

    // boucle_while → "while" condition ":" bloc_simple  ("else" ":" bloc_simple)?
    private void boucle_while() {
        consommer(TokenType.MOT_CLE, "while", "mot-clé 'while' attendu");
        condition();
        consommer(TokenType.DEUX_POINTS, "':' attendu après la condition");
        
        // On saute les nouvelles lignes éventuelles (indentation)
        while (getCourant().type == TokenType.NOUVELLE_LIGNE) {
            i++;
        }
        
        bloc_simple();

        // else optionnel
        while (getCourant().type == TokenType.NOUVELLE_LIGNE) {
            i++;
        }
        
        if (getCourant().type == TokenType.MOT_CLE && "else".equals(getCourant().lexeme)) {
            i++;
            consommer(TokenType.DEUX_POINTS, "':' attendu après else");
            
            while (getCourant().type == TokenType.NOUVELLE_LIGNE) {
                i++;
            }
            
            bloc_simple();
        }

        // On sort proprement du while (même si tout est sur une ligne)
        while (getCourant().type == TokenType.NOUVELLE_LIGNE) {
            i++;
        }
    }
    


    // bloc_simple → (affectation | pass)
    private void bloc_simple() {
        Token t = getCourant();

        if (t.type == TokenType.IDENTIFIANT) {
            affectation();
        }
        else if (t.type == TokenType.MOT_CLE && "pass".equals(t.lexeme)) {
            i++;
        }
        else {
            GestionErreurs.syntaxique(t.ligne, t.colonne, 
                "Instruction simple attendue après ':' (affectation ou pass)");
            ER = true;
            // on avance jusqu'à la fin de ligne pour récupérer
            while (getCourant().type != TokenType.NOUVELLE_LIGNE && 
                   getCourant().type != TokenType.FIN_FICHIER) {
                i++;
            }
        }
    }

    private void condition() {
        expression();
    }

    // expression → tout jusqu'à ':' ou fin de ligne (simplifié, car pas besoin de parser vraiment)
    private void expression() {
        while (true) {
            Token t = getCourant();
            if (t.type == TokenType.DEUX_POINTS || 
                t.type == TokenType.NOUVELLE_LIGNE || 
                t.type == TokenType.FIN_FICHIER) {
                break;
            }
            i++;
        }
    }

    // Ignorer toute la ligne d'une instruction interdite
    private void ignorer_instruction() {
        while (getCourant().type != TokenType.NOUVELLE_LIGNE && 
               getCourant().type != TokenType.FIN_FICHIER) {
            i++;
        }
        if (getCourant().type == TokenType.NOUVELLE_LIGNE) i++;
    }

   
    private Token getCourant() {
        if (i < tokens.size()) return tokens.get(i);
        return new Token(TokenType.FIN_FICHIER, "", -1, -1);
    }

    private void consommer(TokenType type, String message) {
        if (getCourant().type == type) {
            i++;
        } else {
            GestionErreurs.syntaxique(getCourant().ligne, getCourant().colonne, message);
            ER = true;
        }
    }

    private void consommer(TokenType type, String attendu, String message) {
        if (getCourant().type == type && attendu.equals(getCourant().lexeme)) {
            i++;
        } else {
            GestionErreurs.syntaxique(getCourant().ligne, getCourant().colonne, message);
            ER = true;
        }
    }
}