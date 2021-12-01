package server.utility;

/**
 * @author Josue Lubaki & Ismael Coulibaly
 * @version 1.0
 */
public class utils {

    // Methode qui permet de mettre le mot en capitalize (premiere lettre en
    // majuscule)
    public static String capitalize(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
