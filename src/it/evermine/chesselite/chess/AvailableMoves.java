package it.evermine.chesselite.chess;

import it.evermine.chesselite.ChessEliteCore;
import it.evermine.chesselite.ChessEliteHelper;
import it.evermine.chesselite.chess.pieces.Pawn;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AvailableMoves {
    @Getter @Setter
    private List<Square> moves;
    @Getter
    private Square startingSquare;
    @Getter
    private boolean check;
    @Getter
    private Square checkSquare;

    public AvailableMoves() {

    }

    public AvailableMoves(List<Square> moves) {
        this.moves = moves;
    }

    public void generateMoves(Square startingSquare, boolean needToCheck) {
//        System.out.println(startingSquare.getPiece().getName()+" "+startingSquare.getX()+" "+startingSquare.getY());
        this.startingSquare = startingSquare;

        if(moves != null && needToCheck) {
            List<Square> correctSquares = new ArrayList<>();

            for (Square move : moves) {
                if(move.canMove(startingSquare)) {
                    if(!move.isEmpty() && move.getPiece().isKing() && !startingSquare.getPiece().isKing()) {
                        this.checkSquare = move;
                        continue;
                    }
                    correctSquares.add(move);
                }
            }

            moves = new ArrayList<>(correctSquares);

            if(startingSquare.getPiece().isKing()) {
                int kingX = startingSquare.getX();
                int kingY = startingSquare.getY();

                for(Piece piece : ChessEliteCore.getPieces()) {
                    if(!piece.isDead() && startingSquare.areEnemies(piece.getSquare())) {
                        int pieceX = piece.getSquare().getX();
                        int pieceY = piece.getSquare().getY();

                        if(piece.isPawn()) { //controllo distanza e poi mosse
                            System.out.println("Esaminando il pedone in "+piece.getSquare().getX()+" "+piece.getSquare().getY());
                            System.out.println(piece.getSquare().getPiece() != null);
                            removeCommonSquares(((Pawn) piece).getEnemyEatingSquares(piece.getSquare()));
                        } else if(piece.isKing()) {
                            removeCommonSquares(ChessEliteHelper.getKing(piece.getSquare()));
                        } else {
                            removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
                        }
//                        else if(piece.isBishop()) {
//                            removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//
//                            if(ChessEliteHelper.areOnTheSameDiagonal(kingX, kingY, pieceX, pieceY)) {
//                                removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//                            }
//                        } else if(piece.isRook()) {
//                            if(ChessEliteHelper.areOnTheSameAxes(kingX, kingY, pieceX, pieceY)) {
//                                removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//                            }
//                        } else if(piece.isKnight()) {
//                            if(ChessEliteHelper.areOnTheSameHorse(kingX, kingY, pieceX, pieceY)) {
//                                removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//                            }
//                        } else if(piece.isQueen()) {
//                            if(ChessEliteHelper.areOnTheSameDiagonal(kingX, kingY, pieceX, pieceY) || ChessEliteHelper.areOnTheSameAxes(kingX, kingY, pieceX, pieceY)) {
//                                removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//                            }
//                        } else if(piece.isKing()) {
//                            if(ChessEliteHelper.areOnTheSameKing(kingX, kingY, pieceX, pieceY)) {
//                                removeCommonSquares(piece.getMoves(piece.getSquare()).getMoves());
//                            }
//                        }
                    }
                }
            }
        }
    }

    public void showMoves() {
        if(moves == null)
            return;
        for (Square move : moves) {
            move.color();
        }
    }

    public void clearMoves() {
        if(moves == null)
            return;
        for (Square move : moves) {
            move.removeColor();
        }
    }

    public void removeCommonSquares(List<Square> second) {
//        List<Square> union = new ArrayList<Square>(moves);
//        union.addAll(second);
//
//        List<Square> intersection = new ArrayList<Square>(moves);
//        intersection.retainAll(second);
//
//        union.removeAll(intersection);
//
        moves.removeAll(second);
    }

    public boolean containsMove(long id) {
        for (Square move : moves) {
            if(move.getId() == id)
                return true;
        }

        return false;
    }

    public boolean isCheck() {
        return checkSquare != null;
    }
}
