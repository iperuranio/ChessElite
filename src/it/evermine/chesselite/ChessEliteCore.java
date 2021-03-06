package it.evermine.chesselite;

import it.evermine.chesselite.ChessEliteHelper.GUIS;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import it.evermine.chesselite.chess.images.PieceImage;
import it.evermine.chesselite.chess.pieces.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lombok.Getter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ChessEliteCore extends Application {
    @Getter
    private static List<Piece> pieces = new ArrayList<>();
    @Getter
    private static boolean whiteTourn = true;
    @Getter
    private static ChessBoard chessBoard;
    @Getter
    private static Scene mainScene;
    @Getter
    private static Parent parentRoot;

    @Override
    public void start(Stage primaryStage) throws Exception {
        parentRoot = FXMLLoader.load(GUIS.MAIN_GUI.getURL());
        mainScene = new Scene(parentRoot);
        primaryStage.setTitle(GUIS.MAIN_GUI.getTitle());
        primaryStage.setScene(mainScene);

        primaryStage.show();

        chessBoard = new ChessBoard(((GridPane) mainScene.lookup("#chessboard")), new Square[8][8]);

        registerEvents();
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

        Piece WHITE_KING = new King(new PieceImage("white_king.png"), true);
        Piece BLACK_KING = new King(new PieceImage("black_king.png"), false);

        List<Square> list = new ArrayList<>();

        list.addAll(loadListOfSquare(0, BLACK_ROOK, BLACK_KNIGHT, BLACK_BISHOP, BLACK_QUEEN, BLACK_KING, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK));
        list.addAll(loadListOfSquare(1, BLACK_PAWN));

        list.addAll(loadListOfSquare(7, WHITE_ROOK, WHITE_KNIGHT, WHITE_BISHOP, WHITE_QUEEN, WHITE_KING, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK));
        list.addAll(loadListOfSquare(6, WHITE_PAWN));

        chessBoard.loadSquares(list);
        chessBoard.fillSquares();
        chessBoard.updateMainMatrix();
    }

    private static List<Square> loadListOfSquare(int max, Piece... pieces) {
        List<Square> list = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            Piece dummy = (pieces.length == 1 ? pieces[0] : pieces[i]).cloneObject();
            Square sq = new Square(dummy, i, max);
            dummy.setSquare(sq);

            ChessEliteCore.getPieces().add(dummy);
            list.add(sq);
        }

        return list;
    }

    private void registerEvents() {
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(e.getSource() instanceof Pane) {
                    Pane pane = (Pane) e.getSource();

                    if(!ChessEliteHelper.isCorrectSquare(pane.getId()))
                        return;

                    int[] dim = ChessEliteHelper.getIDFromPane(pane.getId());
                    Square clickedSquare = ChessEliteHelper.getSquareFromPane(pane.getId(), dim);

                    Piece piece = clickedSquare.getPiece();
                    if(piece == null)
                        System.out.println(">----- "+clickedSquare.getX()+" "+clickedSquare.getY()+" VUOTA -----<");
                    else if(piece.getSquare() != null) {
                        System.out.println(">----- "+clickedSquare.getX()+" "+clickedSquare.getY()+" "+piece.getName()+" "+piece.getSquare().getX()+" "+piece.getSquare().getY()+" -----<");
                    } else {
                        System.out.println(">----- "+clickedSquare.getX()+" "+clickedSquare.getY()+" "+piece.getName()+" -----<");
                    }



                    if(clickedSquare.isEmpty()) {
                        makeMove(clickedSquare);
                    } else {
                        checkOnPiece(clickedSquare);
                    }

//                    ((ImageView)pane.getChildren().get(0)).setImage(new PieceImage("white_king.png").getImage());
                }
            }
        };

        for (Node node : getChessBoard().getMainBoard().getChildren()) {
            node.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }
    }

    public static void changeTourn() {
        whiteTourn = !whiteTourn;

        chessBoard.flip();
        chessBoard.updateSquares();
        chessBoard.updateMainMatrix();
    }

    private void makeMove(Square clickedSquare) {
        Square requestedSquare = getChessBoard().isAvailableMove(clickedSquare);

        getChessBoard().clearAndRemoveFromScreenMoves();

        if(requestedSquare != null) {
            Task<Void> sleeper = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {

                    }
                    return null;
                }
            };

            sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    ChessEliteCore.changeTourn();
                }
            });
            sleeper.setOnRunning(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    clickedSquare.move(requestedSquare);
                }
            });

            new Thread(sleeper).start();
        }
    }

    private void checkOnPiece(Square clickedSquare) {
        if(clickedSquare.isWhite()) {
            if(isWhiteTourn())
                clickedSquare.showPieceMoves();
            else
                makeMove(clickedSquare);
        } else {
            if(isWhiteTourn())
                makeMove(clickedSquare);
            else
                clickedSquare.showPieceMoves();
        }
    }
}
