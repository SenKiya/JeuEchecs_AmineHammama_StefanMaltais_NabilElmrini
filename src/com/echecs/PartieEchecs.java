package com.echecs;

import com.echecs.pieces.*;
import com.echecs.util.EchecsUtil;

import java.util.Vector;
//import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Représente une partie de jeu d'échecs. Orcheste le déroulement d'une partie :
 * déplacement des pièces, vérification d'échec, d'échec et mat,...
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class    PartieEchecs {
    /**
     * Grille du jeu d'échecs. La ligne 0 de la grille correspond à la ligne
     * 8 de l'échiquier. La colonne 0 de la grille correspond à la colonne a
     * de l'échiquier.
     */
    private Piece[][] echiquier;

    private Vector<Piece> liste = new Vector<>();
    private String aliasJoueur1, aliasJoueur2;
    private char couleurJoueur1, couleurJoueur2;

    /**
     * La couleur de celui à qui c'est le tour de jouer (n ou b).
     */
    private char tour = 'b'; //Les blancs commencent toujours

    /**
     * Crée un échiquier de jeu d'échecs avec les pièces dans leurs positions
     * initiales de début de partie.
     * Répartit au hasard les couleurs n et b entre les 2 joueurs.
     */
    public PartieEchecs() {
        echiquier = new Piece[8][8];
        //Placement des pièces :
        echiquier[0][0] = new Rook('n');
        echiquier[0][1] = new Horse('n');
        echiquier[0][2] = new Bishop('n');
        echiquier[0][3] = new King('n');
        echiquier[0][4] = new Queen('n');
        echiquier[0][5] = new Bishop('n');
        echiquier[0][6] = new Horse('n');
        echiquier[0][7] = new Rook('n');

        for (int i = 0; i < 8; i++) {
            echiquier[1][i] = new Pawn('n');
        }

        echiquier[7][0] = new Rook('b');
        echiquier[7][1] = new Horse('b');
        echiquier[7][2] = new Bishop('b');
        echiquier[7][3] = new King('b');
        echiquier[7][4] = new Queen('b');
        echiquier[7][5] = new Bishop('b');
        echiquier[7][6] = new Horse('b');
        echiquier[7][7] = new Rook('b');

        for (int i = 0; i < 8; i++) {
            echiquier[6][i] = new Pawn('b');

        }


    }

    /**
     * Change la main du jeu (de n à b ou de b à n).
     */
    public void changerTour() {
        if (tour == 'b')
            tour = 'n';
        else
            tour = 'b';
    }

    /**
     * Tente de déplacer une pièce d'une position à une autre sur l'échiquier.
     * Le déplacement peut échouer pour plusieurs raisons, selon les règles du
     * jeu d'échecs. Par exemples :
     * Une des positions n'existe pas;
     * Il n'y a pas de pièce à la position initiale;
     * La pièce de la position initiale ne peut pas faire le mouvement;
     * Le déplacement met en échec le roi de la même couleur que la pièce.
     *
     * @param initiale Position la position initiale
     * @param finale   Position la position finale
     * @return boolean true, si le déplacement a été effectué avec succès, false sinon
     */
    public boolean deplace(Position initiale, Position finale) {
        Piece a = echiquier[EchecsUtil.indiceLigne(initiale)][EchecsUtil.indiceColonne(initiale)];
        Piece b = echiquier[EchecsUtil.indiceLigne(finale)][EchecsUtil.indiceColonne(finale)];
        if (a.equals(b)) {
            return false;
        }
        if (a.equals(null)) {
            return false;
        }
        if (EchecsUtil.indiceLigne(initiale) > 8 || EchecsUtil.indiceLigne(initiale) < 0 || EchecsUtil.indiceLigne(finale) > 8 || EchecsUtil.indiceLigne(finale) < 0) {
            return false;
        }
        if (tour != (a.getCouleur())) {
            return false;
        }
        if (b.getCouleur() == 'b' || b.getCouleur() == 'n') {
            if (b.getCouleur() == a.getCouleur()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si un roi est en échec et, si oui, retourne sa couleur sous forme
     * d'un caractère n ou b.
     * Si la couleur du roi en échec est la même que celle de la dernière pièce
     * déplacée, le dernier déplacement doit être annulé.
     * Les 2 rois peuvent être en échec en même temps. Dans ce cas, la méthode doit
     * retourner la couleur de la pièce qui a été déplacée en dernier car ce
     * déplacement doit être annulé.
     *
     * @return char Le caractère n, si le roi noir est en échec, le caractère b,
     * si le roi blanc est en échec, tout autre caractère, sinon.
     */
    public char estEnEchec() {
        // creer une liste chainee avec toutes les pieces; looper pour verifier si on peut appeler
        //    deplacer(position de la piece iterer, position du roi opposee) si on peut, return echec
        Position roiPosb = null;
        Position roiPosn = null;
        Position piecePos;
        char roicouleur;
        char result = 'x';
        Vector<Position> positions = new Vector<Position>();
        boolean b = false;
        boolean n = false;
        Piece p;
        for (int i = 0; i < echiquier.length; i++) {
            for (int j = 0; j < echiquier[i].length; j++) {
                if (echiquier[i][j] != null) {
                    if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'b') {
                        roiPosb = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                    } else if (echiquier[i][j] instanceof King && echiquier[i][j].getCouleur() == 'n') {
                        roiPosn = new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i));
                    } else {
                        positions.add(new Position(EchecsUtil.getColonne((byte) j), EchecsUtil.getLigne((byte) i)));
                    }

                }
            }
        }

        var iterateur = positions.listIterator();
        while (iterateur.hasNext()) {
            Position temp = iterateur.next();
            p = echiquier[temp.getLigne()][temp.getColonne()];
            if (p.peutSeDeplacer(temp, roiPosn, echiquier)) {
                n = true;

            } else if (p.peutSeDeplacer(temp, roiPosb, echiquier)) {
                b = true;
            }

        }
        if (b && n) {
            result = tour;
        } else if (b) {
            result = 'b';
        } else if (n) {
            result = 'n';
        }
            return result;
        } /**
         * Retourne la couleur n ou b du joueur qui a la main.
         *
         * @return char la couleur du joueur à qui c'est le tour de jouer.
         */
        public char getTour () {
            return tour;
        }
        /**
         * Retourne l'alias du premier joueur.
         * @return String alias du premier joueur.
         */
        public String getAliasJoueur1 () {
            return aliasJoueur1;
        }
        /**
         * Modifie l'alias du premier joueur.
         * @param aliasJoueur1 String nouvel alias du premier joueur.
         */
        public void setAliasJoueur1 (String aliasJoueur1){
            this.aliasJoueur1 = aliasJoueur1;
        }
        /**
         * Retourne l'alias du deuxième joueur.
         * @return String alias du deuxième joueur.
         */
        public String getAliasJoueur2 () {
            return aliasJoueur2;
        }
        /**
         * Modifie l'alias du deuxième joueur.
         * @param aliasJoueur2 String nouvel alias du deuxième joueur.
         */
        public void setAliasJoueur2 (String aliasJoueur2){
            this.aliasJoueur2 = aliasJoueur2;
        }
        /**
         * Retourne la couleur n ou b du premier joueur.
         * @return char couleur du premier joueur.
         */
        public char getCouleurJoueur1 () {
            return couleurJoueur1;
        }
        /**
         * Retourne la couleur n ou b du deuxième joueur.
         * @return char couleur du deuxième joueur.
         */
        public char getCouleurJoueur2 () {
            return couleurJoueur2;
        }
    }
