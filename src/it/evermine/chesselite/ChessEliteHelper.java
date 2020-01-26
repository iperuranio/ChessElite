package it.evermine.chesselite;

import it.evermine.chesselite.chess.AvailableMoves;
import it.evermine.chesselite.chess.Piece;
import it.evermine.chesselite.chess.Square;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.net.MalformedURLException;
import java.net.URL;

public class ChessEliteHelper {
    public enum GUIS {
        MAIN_GUI("main_gui.fxml", "ChessElite v1.0", 800, 800);

        private String url;
        @Getter
        private String title;
        @Getter
        private int x;
        @Getter
        private int y;

        GUIS(String url) {
            this.url = url;
        }

        GUIS(String url, String title) {
            this.url = url;
            this.title = title;
        }

        GUIS(String url, String title, int x, int y) {
            this.url = url;
            this.title = title;
            this.x = x;
            this.y = y;
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
            return url;
        }

        public URL getURL() {
            return ChessEliteHelper.getGUI(url);
        }
    }

    public static URL getGUI(String name) {
        return ChessEliteHelper.class.getResource(name);
    }

    public static Pane getPaneFromPosition(int x, int y) {
        final String toSearch = "square_"+x+"_"+y;

        for (Node child : ChessEliteCore.getChessBoard().getMainBoard().getChildren()) {
            if(child.getId() != null && child.getId().equals(toSearch))
                return (Pane) child;
        }

        return null;
    }

    public static ImageView getImageViewFromPane(Pane pane) {
        return (ImageView) pane.getChildren().get(0);
    }

    public static ImageView getImageViewFromPosition(int x, int y) {
        return getImageViewFromPane(getPaneFromPosition(x, y));
    }

    public static AvailableMoves getMoves(Square square) {
        if(square.isEmpty())
            return null;

        return square.getPiece().getMoves(square);
    }

    public static boolean isCorrectSquare(String id) {
        return id.startsWith("square_");
    }

    public static Square getSquareFromPane(String id, int[] dim) {
        return ChessEliteCore.getChessBoard().getMainMatrix()[dim[0]][dim[1]];
    }

    public static Square getSquareFromPane(String id) {
        return getSquareFromPane(id, getIDFromPane(id));
    }

    public static int[] getIDFromPane(String id) {//square_1_1
        String[] splitted = id.split("_");
        return new int[]{Integer.parseInt(splitted[1]), Integer.parseInt(splitted[2])};
    }

    public static Square getSquare(int i, int j) {
        return ChessEliteCore.getChessBoard().getMainMatrix()[i][j];
    }

    public static boolean isWhiteSquare(int x, int y) { //2 6
        if(x == y)
            return true;

        if(y == 0 || y % 2 == 0) {
            if(x == 0 || x % 2 == 0) {
                return true;
            }
        } else {
            if(x != 0 && x % 2 != 0) {
                return true;
            }
        }

        return false;
    }
}
