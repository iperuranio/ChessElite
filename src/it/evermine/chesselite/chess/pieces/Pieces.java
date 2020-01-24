package it.evermine.chesselite.chess.pieces;

import lombok.Getter;

public enum Pieces {
    PAWN(8, "Pedone", "Pedoni", 1),
    KNIGHT(2, "Cavallo", "Cavalli", 3),
    BISHOP(2, "Alfiere", "Alfieri", 3),
    ROOK(2, "Torre", "Torri", 5),
    QUEEN(1, "Donna", "Donne", 9),
    KING(1, "Re", "Re", Integer.MAX_VALUE);

    @Getter
    private int numOnChessboard;
    @Getter
    private final String singularName;
    @Getter
    private final String pluralName;
    @Getter
    private int value;

    Pieces(int num, String singular, String plural, int value) {
        this.numOnChessboard = num;
        this.singularName = singular;
        this.pluralName = plural;
        this.value = value;
    }

    /**
     * Returns the name of this enum constant, as contained in the
     * declaration.  This method may be overridden, though it typically
     * isn't necessary or desirable.  An enum type should override this
     * method when a more "programmer-friendly" string form exists.
     *
     * @return the name of this enum constant
     */
    @Override
    public String toString() {
        return getSingularName();
    }

    public String getName() {
        return getSingularName();
    }
}
