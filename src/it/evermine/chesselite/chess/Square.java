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
    @Getter @Setter
    private int X;
    @Getter @Setter
    private int Y;
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
    @Getter @Setter
    private boolean check;

    public Square(int X, int Y) {
        this.X = X;
        this.Y = Y;

        initialize();
    }

    public Square(Piece piece, int X, int Y) {
        this.piece = piece;
        this.X = X;
        this.Y = Y;

        updatePiece();

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
        System.out.println(X+" "+Y+" CHECK "+(piece != null)+"\n");
        return piece.isWhite();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj instanceof Square) {
            return ((Square) obj).getId() == id;
        } else return false;
    }

    public void showPieceMoves() {
        if(ChessEliteCore.getChessBoard().existsPreviousMoves() && ChessEliteCore.getChessBoard().getPreviousMoves().getStartingSquare().equals(this)) {
            ChessEliteCore.getChessBoard().clearAndRemoveFromScreenMoves();
        } else ChessEliteCore.getChessBoard().drawMoves(piece.getMoves(this));
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
            if(s.getPiece().isPawn()) {
                if(isPawnSided(s)) {
                    return false;
                } else if(verticalXRayCheck(s)) {
                    return false;
                }
            }

            return true;
        } else {
            if(areEnemies(s)) {
                if(piece.isKing()) {
                    return true;
                } else if(s.getPiece().isPawn()) {
                    if(s.getX() == X) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean horizontalCheck(Square s) {
        return true;
    }

    public boolean verticalXRayCheck(Square s) {
        if(!verticalCheck(s) || s.getY() == Y) {
            return false;
        }
//        System.out.println("\n");
//        System.out.println("Punto che si vuole muovere: "+s.getX()+" "+s.getY()+" | Punto di arrivo: "+X+" "+Y);
        int yMin = s.getY() < Y ? s.getY() : Y;

//        System.out.println("y = "+yMin+" y < "+(Math.abs(s.getY() - Y) + (yMin + (s.isWhite() ? + 0 : -1)))+" y++");
        for(int i = yMin; i < Math.abs(s.getY() - Y) + yMin; i++) {
            if(i > 7) {
//                System.out.println("La casella "+X+" "+i+" è non è interna");
                break;
            }

//            System.out.println("La casella "+X+" "+i+" è piena? "+!getSquare(X, i).isEmpty());
            if(!getSquare(X, i).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    private Square getSquare(int x, int y) {
        return ChessEliteHelper.getSquare(x, y);
    }

    public boolean verticalCheck(Square s) {
        return X == s.getX();
    }

    public boolean verticalPawnCheck(Square s) {
        return Y < s.getY();
    }

    public boolean isPawnSided(Square s) {
        return s.getX() != X && verticalPawnCheck(s);
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
            PieceImage.clearImage(X, Y);
        else
            PieceImage.resetGround(X, Y);
    }

    public void updatePiece() {
        this.piece.setSquare(this);
    }

    public synchronized void move(Square requestedSquare) {
        PieceImage.clearImage(requestedSquare.getX(), requestedSquare.getY());
        PieceImage.resetGround(requestedSquare.getX(), requestedSquare.getY());

        if(!isEmpty()) {
            piece.setDead(true);
            piece.setSquare(null);
            PieceImage.clearImage(X, Y);
        }

        this.setPiece(requestedSquare.getPiece());
        updatePiece();

        requestedSquare.setPiece(null);

        piece.getImage().updatePosition(X, Y);

        if(!piece.isUsed()) {
            piece.setUsed(true);
        }

        AvailableMoves moves = piece.getMoves(this);

        if(moves.isCheck()) {
            Square checkSquare = moves.getCheckSquare();

            if(checkSquare.isWhite()) {
                ChessEliteCore.getChessBoard().setWhiteCheck(true);
            } else {
                ChessEliteCore.getChessBoard().setBlackCheck(true);
            }

            checkSquare.check();
        }
    }

    private void check() {
        this.setCheck(true);
        PieceImage.checkGround(X, Y);
    }

    public void updateCoordinates(int x, int y) {
        setX(x); setY(y);
    }
}
