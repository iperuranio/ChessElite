package it.evermine.chesselite.chess;

import it.evermine.chesselite.ChessEliteCore;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

public class Square {
    @Getter
    public final long id;
    @Getter
    private final int X;
    @Getter
    private final int Y;
    @Getter @Setter
    private Piece piece;
    @Getter
    private boolean colored;

    public Square(int X, int Y) {
        this.X = X;
        this.Y = Y;
        id = new Random().nextLong();
    }

    public Square(Piece piece, int X, int Y) {
        this.piece = piece;
        this.X = X;
        this.Y = Y;
        id = new Random().nextLong();
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isWhite() {
        return piece.isWhite();
    }

    public void showMoves(int x, int y) {
        piece.getMoves(x, y);
    }

    public void color() {

    }

    public void removeColor() {

    }
}
