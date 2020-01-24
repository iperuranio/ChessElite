package it.evermine.chesselite.chess.pieces;

import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.images.PieceImage;
import lombok.Getter;

public class Knight implements Piece {
    @Getter
    private final Pieces type = Pieces.KNIGHT;
    @Getter
    private PieceImage image;
    @Getter
    private boolean white;

    public Knight(PieceImage image, boolean white) {
        this.image = image;
    }

    @Override
    public boolean isBlack() {
        return !isWhite();
    }

    @Override
    public String getName() {
        return type.getName();
    }

    @Override
    public String getSingularName() {
        return type.getSingularName();
    }

    @Override
    public String getPluralName() {
        return type.getPluralName();
    }
}
