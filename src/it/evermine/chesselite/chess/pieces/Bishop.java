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
import java.util.stream.StreamSupport;

public class Bishop implements Piece {
    @Getter
    private Constructor<?> constructor;
    @Getter
    private long ID = Piece.generateID();
    @Getter
    private final Pieces type = Pieces.BISHOP;
    @Getter
    private PieceImage image;
    @Getter
    private boolean white;
    @Getter @Setter
    private boolean used; //da aggiornare

    public Bishop(PieceImage image, boolean white) {
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
//        List<Square> list = new ArrayList<>();
//
//        int squareX = square.getX();
//        int squareY = square.getY();
//        boolean isWhiteSquare = ChessEliteHelper.isWhiteSquare(squareX, squareY);
//
//        for(int i = 0; i < 8; i++) {
//            for(int j = 0; j < 8; j++) {
//                if((isWhiteSquare ? ChessEliteHelper.isWhiteSquare(i, j) : !ChessEliteHelper.isWhiteSquare(i, j)) && i != squareX && j != squareY)  {
//                    if(Math.abs(squareX - i) == Math.abs(squareY - j)) {
//                        list.add(ChessEliteHelper.getSquare(i, j));
//                    }
//                }
//            }
//        }

        moves.setMoves(ChessEliteHelper.getDiagonals(square));
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
