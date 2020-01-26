package it.evermine.chesselite.chess;

import it.evermine.chesselite.ChessEliteCore;
import it.evermine.chesselite.ChessEliteHelper;
import it.evermine.chesselite.chess.images.PieceImage;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.html.ImageView;
import java.util.Random;

public class Square {
    private static PieceImage image;

    @Getter
    public long id;
    @Getter
    private final int X;
    @Getter
    private final int Y;
    @Getter @Setter
    private Piece piece;
    @Getter
    private boolean colored;
    @Getter
    private boolean leftCorner;
    @Getter
    private boolean rightCorner;
    @Getter
    private boolean topCorner;
    @Getter
    private boolean botCorner;
    @Getter
    private boolean isAngle;

    public Square(int X, int Y) {
        this.X = X;
        this.Y = Y;

        initialize();
    }

    public Square(Piece piece, int X, int Y) {
        this.piece = piece;
        this.X = X;
        this.Y = Y;

        initialize();
    }

    private void initialize() {
        id = Piece.generateID();

        leftCorner = X == 0;
        rightCorner = X == 7;
        topCorner = Y == 0;
        botCorner = Y == 7;

        isAngle = (leftCorner && topCorner) || (rightCorner && topCorner) || (rightCorner && botCorner) || (leftCorner && botCorner);

        image = new PieceImage("blue_dot.png");
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isWhite() {
        return piece.isWhite();
    }

    public void showPieceMoves() {
        ChessEliteCore.getChessBoard().drawMoves(piece.getMoves(this));
    }

    public void color() {
        setImage();
    }

    public void removeColor() {
        removeImage();
    }

    public boolean areEnemies(Square s) {
        return s.isWhite() != isWhite();
    }

    public boolean canMove(Square s) { //casella s -> qui
        if(isEmpty()) {
            if(s.getPiece().isPawn() && isPawnSided(s)) {
                return false;
            } else {
                return true;
            }
        } else {
            if(areEnemies(s) && !piece.isKing()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isPawnSided(Square s) {
        return s.getX() != X && (s.isWhite() ? Y < s.getY() : Y > s.getY());
    }

    public Pane getElement() {
        return ChessEliteHelper.getPaneFromPosition(X, Y);
    }

    private void setImage() {
        if(isEmpty())
            image.updatePosition(X, Y);
        else
            image.blueGround(getElement());
    }

    private void removeImage() {
        if(isEmpty())
            image.clearImage(X, Y);
        else
            image.resetGround(X, Y);
    }

    public void move(Square requestedSquare) {
        requestedSquare.getPiece().getImage().clearImage(requestedSquare.getX(), requestedSquare.getY());
        if(!isEmpty())
            piece.getImage().clearImage(X, Y);

        this.setPiece(requestedSquare.getPiece());
        requestedSquare.setPiece(null);

        piece.getImage().updatePosition(X, Y);

        if(!piece.isUsed()) {
            piece.setUsed(true);
        }
    }
}
