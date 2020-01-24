package it.evermine.chesselite;

import it.evermine.chesselite.ChessEliteHelper.GUIS;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import it.evermine.chesselite.chess.images.PieceImage;
import it.evermine.chesselite.chess.pieces.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ChessEliteCore extends Application {
    @Getter
    private static Square[][] mainMatrix = new Square[8][8];
    @Getter
    private static Scene mainScene;
    @Getter
    private static GridPane guiChessBoard;
    @Getter
    private static Parent parentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentRoot = FXMLLoader.load(GUIS.MAIN_GUI.getURL());
        mainScene = new Scene(parentRoot);
        primaryStage.setTitle(GUIS.MAIN_GUI.getTitle());
        primaryStage.setScene(mainScene);

        primaryStage.show();

        guiChessBoard = (GridPane) mainScene.lookup("#chessboard");

        loadChessBoard();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    private static void loadChessBoard() {
//        Piece wpawn = new Pawn(new PieceImage("white_pawn.png"), true);
        Piece WHITE_PAWN = new Pawn(new PieceImage("white_pawn.png"), true);
        Piece BLACK_PAWN = new Pawn(new PieceImage("black_pawn.png"), false);

        Piece WHITE_KNIGHT = new Knight(new PieceImage("white_horse.png"), true);
        Piece BLACK_KNIGHT = new Knight(new PieceImage("black_horse.png"), false);

        Piece WHITE_BISHOP = new Bishop(new PieceImage("white_bishop.png"), true);
        Piece BLACK_BISHOP = new Bishop(new PieceImage("black_bishop.png"), false);

        Piece WHITE_ROOK = new Rook(new PieceImage("white_rook.png"), true);
        Piece BLACK_ROOK = new Rook(new PieceImage("black_rook.png"), false);

        Piece WHITE_QUEEN = new Queen(new PieceImage("white_queen.png"), true);
        Piece BLACK_QUEEN = new Queen(new PieceImage("black_queen.png"), false);

        Piece WHITE_KING = new Pawn(new PieceImage("white_king.png"), true);
        Piece BLACK_KING = new Pawn(new PieceImage("black_king.png"), false);


        mainMatrix[0][0] = new Square(BLACK_ROOK);
        mainMatrix[1][0] = new Square(BLACK_KNIGHT);
        mainMatrix[2][0] = new Square(BLACK_BISHOP);
        mainMatrix[3][0] = new Square(BLACK_QUEEN);
        mainMatrix[4][0] = new Square(BLACK_KING);
        mainMatrix[5][0] = new Square(BLACK_BISHOP);
        mainMatrix[6][0] = new Square(BLACK_KNIGHT);
        mainMatrix[7][0] = new Square(BLACK_ROOK);

        mainMatrix[0][1] = new Square(BLACK_PAWN);
        mainMatrix[1][1] = new Square(BLACK_PAWN);
        mainMatrix[2][1] = new Square(BLACK_PAWN);
        mainMatrix[3][1] = new Square(BLACK_PAWN);
        mainMatrix[4][1] = new Square(BLACK_PAWN);
        mainMatrix[5][1] = new Square(BLACK_PAWN);
        mainMatrix[6][1] = new Square(BLACK_PAWN);
        mainMatrix[7][1] = new Square(BLACK_PAWN);

        for (int i = 0; i < 8; i++) { //fare funzione di update
            for (int j = 0; j < 8; j++) {
                Square square = mainMatrix[i][j];

                if (square != null) {
                    square.getPiece().getImage().updatePosition(i, j);
                }
            }
        }
    }
}
