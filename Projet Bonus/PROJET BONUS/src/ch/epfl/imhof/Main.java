package ch.epfl.imhof;

import ch.epfl.imhof.bonus.Flipper;

/**
 * Le Main du programme
 * 
 * @author Magaly Abboud (249344)
 * @author David Resin (225452)
 */
public class Main {
	
    /**
     * La methode main, lancant l'application de simlation physique appelee Flipper
     * 
     * @param args
     *              Les arguments, contenant dans l'ordre :
     *                 1) Le nom (chemin) d'un fichier OSM compresse avec gzip contenant les donnees de la carte a dessiner
     *                 2) Le nom (chemin) d'un fichier HGT couvrant la totalite de la zone de la carte a dessiner, zone tampon inclue
     *                 3) La longitude du point bas-gauche de la carte, en degres
     *                 4) La latitude du point bas-gauche de la carte, en degres
     *                 5) La longitude du point haut-droite de la carte, en degres
     *                 6) La latitude du point haut-droite de la carte, en degres
     *                 7) La resolution de l'image à dessiner, en points par pouce (dpi)
     *                 8) Le nom (chemin) du fichier PNG a generer
     *                 9) La constante gravitationelle
     *                 10) Le facteur de rebond sur les bords de la simulation
     *                 11) Le facteur d'accentuation des pentes
     *                 12) Le facteur de friction
     *                 13) La largeur de la fenetre
     *                 14) Un boolean indiquant si les vecteurs de vitesse (vert) et d'acceleration (bleu) doivent apparaitre
     *                 15) Un boolean indiquant s'il faut remplacer la carte topographique par une carte en niveau de gris representant l'altitude a chaque point
     *                 
     * @throws Exception 
     *              Si un argument est invalide
     */
    public static void main(String[] args) throws Exception {
        Flipper.launch(Flipper.class, args);
    }
}
