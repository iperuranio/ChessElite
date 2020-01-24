package it.evermine.chesselite;

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
        final String toSearch = "pane_"+x+"_"+y;

        for (Node child : ChessEliteCore.getGuiChessBoard().getChildren()) {
            if(child.getId().equals(toSearch))
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
}
