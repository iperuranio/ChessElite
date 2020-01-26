package it.evermine.chesselite.chess;

import it.evermine.chesselite.ChessEliteCore;
import it.evermine.chesselite.ChessEliteHelper;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class AvailableMoves {
    @Getter @Setter
    private List<Square> moves;
    @Getter
    private Square startingSquare;

    public AvailableMoves() {

    }

    public AvailableMoves(List<Square> moves) {
        this.moves = moves;
    }

    public void generateMoves(Square startingSquare, boolean needToCheck) {
        this.startingSquare = startingSquare;

        if(moves != null && needToCheck) {
            List<Square> correctSquares = new ArrayList<>();

            for (Square move : moves) {
                if(move.canMove(startingSquare)) {
                    correctSquares.add(move);
                }
            }

            moves = new ArrayList<>(correctSquares);
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

    public boolean containsMove(long id) {
        for (Square move : moves) {
            if(move.getId() == id)
                return true;
        }

        return false;
    }
}
