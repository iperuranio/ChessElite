package it.evermine.chesselite;

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
    private Integer[][] previousMoves;

    public ChessBoard(GridPane mainBoard, Square[][] mainMatrix) {
        this.mainBoard = mainBoard;
        this.mainMatrix = mainMatrix;
    }

    public void drawMoves(Integer[][] dim) {
        for (int i = 0; i < dim[0].length; i++) {
            for(int j = 0; j < dim[1].length; j++) {
                mainMatrix[i][j].color();
            }
        }
    }

    public void clearMoves(Integer[][] dim) {
        for (int i = 0; i < dim[0].length; i++) {
            for(int j = 0; j < dim[1].length; j++) {
                mainMatrix[i][j].removeColor();
            }
        }
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

    private void runMatrixSquare(Consumer<Square> function) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                function.accept(mainMatrix[i][j]);
            }
        }
    }
}
