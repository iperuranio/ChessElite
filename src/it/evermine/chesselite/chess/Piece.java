package it.evermine.chesselite.chess;

import it.evermine.chesselite.chess.images.PieceImage;
import it.evermine.chesselite.chess.pieces.Pieces;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public interface Piece {
    default Piece cloneObject() {
        try {
            return (Piece) getConstructor().newInstance(getImage(), isWhite());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    Constructor<?> getConstructor();

    static long generateID() {
        return new Random().nextLong();
    }

    long getID();

    void setUsed(boolean b);

    boolean isUsed();

    boolean canMoves(int x, int y);

    AvailableMoves getMoves(Square square);

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

    boolean isDead();

    void setDead(boolean bool);

    Square getSquare();

    void setSquare(Square square);
}
