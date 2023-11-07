package com.echecs.pieces;

import com.echecs.Position;

public class Bishop extends Piece{
    public Bishop(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}
