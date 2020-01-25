package it.evermine.chesselite.chess;

import it.evermine.chesselite.chess.images.PieceImage;
import it.evermine.chesselite.chess.pieces.Pieces;

public interface Piece {
    boolean canMoves(int x, int y);

    Integer[][] getMoves(int x, int y);

    PieceImage getImage();

    boolean isWhite();

    boolean isBlack();

    Pieces getType();

    String getName();

    String getSingularName();

    String getPluralName();

    default boolean isPawn() {
        return isSomething(Pieces.PAWN);
    }

    default boolean isKnight() {
        return isSomething(Pieces.KNIGHT);
    }

    default boolean isBishop() {
        return isSomething(Pieces.BISHOP);
    }

    default boolean isRook() {
        return isSomething(Pieces.ROOK);
    }

    default boolean isQueen() {
        return isSomething(Pieces.QUEEN);
    }

    default boolean isKing() {
        return isSomething(Pieces.KING);
    }

    default boolean isSomething(Pieces piece) {
        return getName().equals(piece.getName());
    }
}
