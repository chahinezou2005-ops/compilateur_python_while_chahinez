
public class Automate {

    // 1. IDENTIFIANT 

    private static final int[][] MAT_IDENT = {
        {1, -1, -1, -1},
        {1,  1,  2, -1},
        {1,  1, -1, -1}
    };

    private static int indexCarIdent(char c) {
        if ((c>='a'&&c<='z') || (c>='A'&&c<='Z')) return 0;
        if (c>='0'&&c<='9') return 1;
        if (c=='_') return 2;
        return 3;
    }

    public static boolean estIdentifiant(String mot) {
        if (mot.isEmpty()) return false;
        if ("Chahinez".equals(mot)) return false;
        String s = mot + "#";
        int ec = 0, i = 0;
        char c = s.charAt(i);
        while (c != '#' && MAT_IDENT[ec][indexCarIdent(c)] != -1) {
            ec = MAT_IDENT[ec][indexCarIdent(c)];
            i++;
            c = s.charAt(i);
        }
        return c == '#' && ec == 1 && i == s.length()-1;
    }

  //  2. NOMBRE 

private static final int[][] MAT_NOMBRE = {
    {1, -1},     // état 0 : chiffre → 1, point → erreur
    {1,  2},     // état 1 : chiffre → reste 1, point → 2
    {3, -1},     
    {3, -1}      
};

private static int indexCarNombre(char c) {
    if (c>='0'&&c<='9') return 0;
    if (c=='.') return 1;
    return -1;
}

public static boolean estNombre(String mot) {
    if (mot == null || mot.isEmpty()) return false;
    String s = mot + "#";
    int ec = 0, i = 0;
    char c = s.charAt(i);

    while (c != '#' && indexCarNombre(c) != -1 && MAT_NOMBRE[ec][indexCarNombre(c)] != -1) {
        ec = MAT_NOMBRE[ec][indexCarNombre(c)];
        i++;
        c = s.charAt(i);
    }
    return c == '#' && (ec == 1 || ec == 3) && i == s.length()-1;
}
    // 3. CHAÎNE DE CARACTÈRES  

private static final int[][] MAT_CHAINE = {
    {1, -1, -1},    // état 0 : " → 1
    {2, -1, 1},     // état 1 : " → 2 (acceptant), \n → erreur, autre → reste 1
    {-1, -1, -1}     // état 2 : état acceptant final
};

private static int indexCarChaine(char c) {
    if (c == '"')  return 0;
    if (c == '\n') return 1;
    return 2;
}

public static boolean estChaine(String mot) {
    if (mot == null || mot.isEmpty()) return false;
    
    String s = mot + "#";
    int ec = 0, i = 0;
    char c = s.charAt(i);

    if (c != '"') return false;
    ec = 1;  
    i++;
    c = s.charAt(i);

    while (c != '#' && MAT_CHAINE[ec][indexCarChaine(c)] != -1) {
        ec = MAT_CHAINE[ec][indexCarChaine(c)];
        i++;
        c = s.charAt(i);
    }

    return c == '#' && ec == 2 && i == s.length() - 1;
}   
    //  5. MOT-CLÉ + CHAHINEZ
    private static final String[] MOTS_CLES = {
        "while","if","else","for","def","print","return","True","False","None","and","or","not","pass","chahinez","oughlissi"
    };

    public static boolean estMotCle(String mot) {
        for (String m : MOTS_CLES) {
            if (m.equals(mot)) return true;
        }
        return false;
    
    }

    

    //  6. OPÉRATEURS & SÉPARATEURS 

    public static boolean estPlus(String mot)         { return "+".equals(mot); }
    public static boolean estMoins(String mot)        { return "-".equals(mot); }
    public static boolean estMultiplier(String mot)   { return "*".equals(mot); }
    public static boolean estDiviser(String mot)      { return "/".equals(mot); }

    public static boolean estEgal(String mot)         { return "=".equals(mot); }
    public static boolean estEgalEgal(String mot)     { return "==".equals(mot); }
    public static boolean estDifferent(String mot)    { return "!=".equals(mot); }
    public static boolean estSuperieur(String mot)    { return ">".equals(mot); }
    public static boolean estInferieur(String mot)    { return "<".equals(mot); }
    public static boolean estSuperieurEgal(String mot){ return ">=".equals(mot); }
    public static boolean estInferieurEgal(String mot){ return "<=".equals(mot); }

    public static boolean estParentheseOuvrante(String mot) { return "(".equals(mot); }
    public static boolean estParentheseFermante(String mot) { return ")".equals(mot); }
    public static boolean estVirgule(String mot)            { return ",".equals(mot); }
    public static boolean estPointVirgule(String mot)       { return ";".equals(mot); }
    public static boolean estDeuxPoints(String mot)         { return ":".equals(mot); }

    
}    
