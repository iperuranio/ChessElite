package it.evermine.chesselite.chess;

import lombok.Getter;
import lombok.Setter;

public class Square {
    @Getter @Setter
    private Piece piece;

    public Square() {

    }

    public Square(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
