package com.echecs.pieces;

import com.echecs.Position;

public class Queen extends Piece {
    public Queen(char couleur) {
        super(couleur);
    }

    @Override
    public boolean peutSeDeplacer(Position pos1, Position pos2, Piece[][] echiquier) {
        return false;
    }
}
