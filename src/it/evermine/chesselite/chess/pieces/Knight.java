package it.evermine.chesselite.chess.pieces;

import it.evermine.chesselite.ChessEliteCore;
import it.evermine.chesselite.ChessEliteHelper;
import it.evermine.chesselite.chess.AvailableMoves;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import it.evermine.chesselite.chess.images.PieceImage;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Knight implements Piece {
    @Getter
    private Constructor<?> constructor;
    @Getter
    private long ID = Piece.generateID();
    @Getter
    private final Pieces type = Pieces.KNIGHT;
    @Getter
    private PieceImage image;
    @Getter
    private boolean white; //da aggiornare
    @Getter @Setter
    private boolean used; //da aggiornare
    @Getter @Setter
    private boolean dead;
    @Getter @Setter
    private Square square;

    public Knight(PieceImage image, boolean white) {
        this.image = image;
        this.white = white;
        try {
            constructor = getClass().getConstructor(PieceImage.class, boolean.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean canMoves(int x, int y) {
        return false;
    }

    @Override
    public AvailableMoves getMoves(Square square) {
        AvailableMoves moves = new AvailableMoves();

        moves.setMoves(ChessEliteHelper.getKnight(square));
        moves.generateMoves(square, true);

        return moves;
    }

    @Override
    public boolean isBlack() {
        return !isWhite();
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getSingularName() {
        return type.getSingularName();
    }

    @Override
    public String getPluralName() {
        return type.getPluralName();
    }
}
