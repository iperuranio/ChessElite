package it.evermine.chesselite.chess.pieces;

import it.evermine.chesselite.ChessEliteHelper;
import it.evermine.chesselite.chess.AvailableMoves;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import it.evermine.chesselite.chess.images.PieceImage;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pawn implements Piece {
    @Getter
    private Constructor<?> constructor;
    @Getter
    private long ID = Piece.generateID();
    @Getter
    private final Pieces type = Pieces.PAWN;
    @Getter
    private PieceImage image;
    @Getter
    private boolean white;
    @Getter @Setter
    private boolean used; //da aggiornare
    @Getter @Setter
    private boolean dead;
    @Getter @Setter
    private Square square;

    public Pawn(PieceImage image, boolean white) {
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

    public List<Square> getEnemyEatingSquares(Square square) {
        AvailableMoves moves = new AvailableMoves();
        List<Square> squares = new ArrayList<>();

        System.out.println(square.getX()+" "+square.getY());

        if(!square.isRightCorner()) {
            Square sq = ChessEliteHelper.getSquare(square.getX() + 1, square.getY() +1);

            System.out.println(sq.getX()+" "+sq.getY());

            if(sq.isEmpty()) {
                squares.add(sq);
            } else if(sq.areEnemies(square)) {
                squares.add(sq);
            }
        }

        if(!square.isLeftCorner()) {
            Square sq = ChessEliteHelper.getSquare(square.getX() - 1, square.getY() +1);

            System.out.println(sq.getX()+" "+sq.getY());

            if(sq.isEmpty()) {
                squares.add(sq);
            } else if(sq.areEnemies(square)) {
                squares.add(sq);
            }
        }

        moves.setMoves(squares);
        moves.generateMoves(square, true);

        return moves.getMoves();
    }

    @Override
    public AvailableMoves getMoves(Square square) {
        AvailableMoves moves = new AvailableMoves();
        List<Square> squares = new ArrayList<>();

        if(!square.isRightCorner()) {
            squares.add(ChessEliteHelper.getSquare(square.getX() + 1, square.getY() -1));
        }

        if(!square.isLeftCorner()) {
            squares.add(ChessEliteHelper.getSquare(square.getX() - 1, square.getY() -1));
        }

        if(!isUsed()) {
            squares.add(ChessEliteHelper.getSquare(square.getX(), square.getY() -2));
        }

        squares.add(ChessEliteHelper.getSquare(square.getX(), square.getY() -1));

        moves.setMoves(squares);
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
