package com.echecs.pieces;

import com.echecs.Position;
import com.echecs.util.EchecsUtil;

public class Pawn extends Piece{
    public Pawn(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        byte ligne1 = EchecsUtil.indiceLigne(pos1);
        byte colonne1 = EchecsUtil.indiceColonne(pos1);
        byte ligne2 = EchecsUtil.indiceLigne(pos2);
        byte colonne2 = EchecsUtil.indiceColonne(pos2);

        /*
        Pour avancer de 1
         */
        if(pos1.estSurLaMemeColonneQue(pos2) && pos1.estVoisineDe(pos2)){
            if(echiquier[colonne1][ligne1].getCouleur()=='b' && (ligne1<ligne2) && echiquier[colonne2][ligne2]==null){
                return true;
            }
            else if(echiquier[colonne1][ligne1].getCouleur()=='n' && (ligne1>ligne2) && echiquier[colonne2][ligne2]==null) {
                return true;
            }
        }

        /*
        Pour prendre piece de l'autre cote
         */
        if(pos1.estSurLaMemeDiagonaleQue(pos2) && pos1.estVoisineDe(pos2) && echiquier[colonne2][ligne2]!=null){
            if(echiquier[colonne1][ligne1].getCouleur()=='b' && (ligne1<ligne2) && echiquier[colonne2][ligne2].getCouleur()=='n'){
                return true;
            }
            else if(echiquier[colonne1][ligne1].getCouleur()=='n' && (ligne1>ligne2) && echiquier[colonne2][ligne2].getCouleur()=='b') {
                return true;
            }
        }
        return false;
    }
}
