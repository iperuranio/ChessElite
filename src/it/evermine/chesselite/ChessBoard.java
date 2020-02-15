package it.evermine.chesselite;

import it.evermine.chesselite.chess.AvailableMoves;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import it.evermine.chesselite.chess.images.PieceImage;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;

import java.io.Console;
import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChessBoard {
    @Getter
    private GridPane mainBoard;
    @Getter
    private Square[][] mainMatrix;
    @Getter
    private AvailableMoves previousMoves;
    @Getter @Setter
    private boolean whiteCheck;
    @Getter @Setter
    private boolean blackCheck;

    public ChessBoard(GridPane mainBoard, Square[][] mainMatrix) {
        this.mainBoard = mainBoard;
        this.mainMatrix = mainMatrix;
    }

    public void drawMoves(AvailableMoves moves) {
        clearPreviousMoves();

        moves.showMoves();
        previousMoves = moves;
    }

    public void clearPreviousMoves() {
        if(existsPreviousMoves())
            clearMoves(previousMoves);
    }

    public void clearAndRemoveFromScreenMoves() {
        clearPreviousMoves();
        clearPreviousAvailableMoves();
    }

    public void clearMoves(AvailableMoves moves) {
        moves.clearMoves();
    }

    public void loadSquare(Square s) {
        mainMatrix[s.getX()][s.getY()] = s;
    }

    public void loadSquares(Collection<Square> o) {
        for (Square square : o) {
            loadSquare(square);
        }
    }

    public void updateMainMatrix() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = mainMatrix[i][j];

                if (square != null && !square.isEmpty()) {
                    square.getPiece().getImage().updatePosition(i, j);
                    square.updateCoordinates(i, j);
                    square.updatePiece();
                    if(square.isCheck()) {
                        PieceImage.checkGround(i, j);
                    }
                } else {
                    PieceImage.clearImage(i, j);
                    PieceImage.resetGround(i, j);
                }
            }
        }
    }

    public void fillSquares() {
        runMatrixBiFunction((x, y) -> {
            if(mainMatrix[x][y] == null)
                mainMatrix[x][y] = new Square(x, y);
        });
    }

    public void runMatrixBiFunction(BiConsumer<Integer, Integer> function) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                function.accept(i, j);
            }
        }
    }

    public void runMatrixFunction(Consumer<Square> function) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                function.accept(mainMatrix[i][j]);
            }
        }
    }

    public void runArrayFunction(Consumer<Integer> function) {
        for (int i = 0; i < 8; i++) {
            function.accept(i);
        }
    }

    public void runCrescentForWithMinusFunction(int starting, int exit, Consumer<Integer> function) {
        for (int i = starting; i < exit; i++) {
            function.accept(i);
        }
    }

    public void runDecrescentForWithEqualsFunction(int starting, int exit, Consumer<Integer> function) {
        for (int i = starting; i == exit; i++) {
            function.accept(i);
        }
    }

    public Square isAvailableMove(Square square) {
        if(existsPreviousMoves() && previousMoves.containsMove(square.getId())) {
            return previousMoves.getStartingSquare();
        }

        return null;
    }

    public void clearPreviousAvailableMoves() {
        previousMoves = null;
    }

    public boolean existsPreviousMoves() {
        return previousMoves != null;
    }

    public boolean isInternal(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public void flip() {
        int totalX = mainMatrix[0].length;
        int totalY = mainMatrix[1].length;

        for (int x = 0; x < totalX; x++) {
            for (int y = 0; y < totalY/2; y++) {
                Square tmp = mainMatrix[x][totalY - y - 1];

                if(mainMatrix[x][y].isCheck()) {
                    PieceImage.resetGround(x, y);
                    PieceImage.checkGround(x, totalY - y - 1);
                }

                mainMatrix[x][totalY - y - 1] = mainMatrix[x][y];
                mainMatrix[x][y] = tmp;
            }
        }
    }

    public void updateSquares() {
        runMatrixBiFunction((x, y) -> {
            ChessEliteHelper.getSquare(x, y).updateCoordinates(x, y);
        });
    }
}
