package it.evermine.chesselite;

import it.evermine.chesselite.chess.AvailableMoves;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import javafx.scene.layout.GridPane;
import lombok.Getter;

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
        if(previousMoves != null)
            clearMoves(previousMoves);
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

    private void runMatrixBiFunction(BiConsumer<Integer, Integer> function) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                function.accept(i, j);
            }
        }
    }

    private void runMatrixFunction(Consumer<Square> function) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                function.accept(mainMatrix[i][j]);
            }
        }
    }

    public Square isAvailableMove(Square square) {
        if(previousMoves != null && previousMoves.containsMove(square.getId())) {
            return previousMoves.getStartingSquare();
        }

        return null;
    }

    public void clearPreviousAvailableMoves() {
        previousMoves = null;
    }
}
