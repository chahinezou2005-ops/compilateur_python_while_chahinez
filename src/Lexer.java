

import java.util.*;

public class Lexer {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    
    private int position = 0;
    private int ligne = 1;
    private int colonne = 1;

    public Lexer(String source) {
        this.source = source + "#"; 
    }

    public void analyser() {
        char c = source.charAt(position);

        while (c != '#') {
            if (c == ' ' || c == '\t') {
                position++; colonne++;
            }
            else if (c == '\n') {
                ligne++; colonne = 1; position++;
            }
            else if (c == '#') {
                ignorerCommentaire();
            }
            else if (c == '"') {
                lireChaine();
            }
            else if (Character.isLetter(c) || c == '_') {
                lireIdentifiantOuMotCle();
            }
            else if (Character.isDigit(c)) {
                lireNombre();
            }
            else if ("=+-*/><!().,;:".indexOf(c) != -1) {
                lireOperateurOuSeparateur();
            }
            else {
                // ERREUR LEXICALE : caractère inconnu → on signale mais on continue !
                GestionErreurs.lexicale(ligne, colonne, 
                    "Caractère interdit ou inconnu : '" + c + "'");
                position++; 
                colonne++;
            }

            c = source.charAt(position);
        }
        ajouter(TokenType.FIN_FICHIER, "");
    }

    private void lireOperateurOuSeparateur() {
        char c1 = source.charAt(position);
        char c2 = source.charAt(position + 1);

        if (c1 == '=' && c2 == '=') { ajouter(TokenType.EGAL_EGAL, "=="); position += 2; colonne += 2; }
        else if (c1 == '!' && c2 == '=') { ajouter(TokenType.DIFFERENT, "!="); position += 2; colonne += 2; }
        else if (c1 == '>' && c2 == '=') { ajouter(TokenType.SUPERIEUR_EGAL, ">="); position += 2; colonne += 2; }
        else if (c1 == '<' && c2 == '=') { ajouter(TokenType.INFERIEUR_EGAL, "<="); position += 2; colonne += 2; }
        else if (c1 == '+') { ajouter(TokenType.PLUS, "+"); position++; colonne++; }
        else if (c1 == '-') { ajouter(TokenType.MOINS, "-"); position++; colonne++; }
        else if (c1 == '*') { ajouter(TokenType.MULTIPLIER, "*"); position++; colonne++; }
        else if (c1 == '/') { ajouter(TokenType.DIVISER, "/"); position++; colonne++; }
        else if (c1 == '>') { ajouter(TokenType.SUPERIEUR, ">"); position++; colonne++; }
        else if (c1 == '<') { ajouter(TokenType.INFERIEUR, "<"); position++; colonne++; }
        else if (c1 == '=') { ajouter(TokenType.EGAL, "="); position++; colonne++; }
        else if (c1 == '(') { ajouter(TokenType.PARENTHESE_OUVRANTE, "("); position++; colonne++; }
        else if (c1 == ')') { ajouter(TokenType.PARENTHESE_FERMANT, ")"); position++; colonne++; }
        else if (c1 == ',') { ajouter(TokenType.VIRGULE, ","); position++; colonne++; }
        else if (c1 == ';') { ajouter(TokenType.POINT_VIRGULE, ";"); position++; colonne++; }
        else if (c1 == ':') { ajouter(TokenType.DEUX_POINTS, ":"); position++; colonne++; }
    }

    private void ignorerCommentaire() {
        char c = source.charAt(position);
        while (c != '\n' && c != '#') {
            position++; colonne++;
            c = source.charAt(position);
        }
    }

    private void lireChaine() {
        position++; colonne++; 
        int debut = position;
        int ligneDebut = ligne;
        int colonneDebut = colonne;
        char c = source.charAt(position);

        while (c != '"' && c != '\n' && c != '#') {
            position++; colonne++;
            c = source.charAt(position);
        }

        String contenu = source.substring(debut, position);
        if (c == '"') {
            ajouter(TokenType.CHAINE, "\"" + contenu + "\"");
            position++; colonne++;
        } else {
            // ERREUR : chaîne non fermée → on signale mais on continue
            GestionErreurs.lexicale(ligneDebut, colonneDebut, "Chaîne non fermée");
            ajouter(TokenType.CHAINE, "\"" + contenu); // on l'ajoute quand même
        }
    }

    private void lireIdentifiantOuMotCle() {
        int debut = position;
        char c = source.charAt(position);

        while (Character.isLetterOrDigit(c) || c == '_') {
            position++; colonne++;
            c = source.charAt(position);
        }

        String mot = source.substring(debut, position);

        if (Automate.estMotCle(mot)) {
            ajouter(TokenType.MOT_CLE, mot);
        } else {
            ajouter(TokenType.IDENTIFIANT, mot);
        }
    }

    private void lireNombre() {
        int debut = position;
        char c = source.charAt(position);

        while (Character.isDigit(c) || c == '.') {
            position++; colonne++;
            c = source.charAt(position);
        }

        ajouter(TokenType.NOMBRE, source.substring(debut, position));
    }

    private void ajouter(TokenType type, String lexeme) {
        int col = colonne - lexeme.length();
        tokens.add(new Token(type, lexeme, ligne, col < 1 ? 1 : col));
    }

    public void afficher() {
        System.out.println("\n=== ANALYSE LEXICALE – CHAHINEZ – ÉTAPE 3 ===\n");
        for (Token t : tokens) {
            System.out.println(t);
        }
        GestionErreurs.bilan(); //  Pour Affiche le nombre total d'erreurs
    }

    public List<Token> getTokens() {
        return new ArrayList<>(tokens);
    }
}