package it.evermine.chesselite.chess.images;

import it.evermine.chesselite.ChessEliteCore;
import it.evermine.chesselite.ChessEliteHelper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PieceImage {
    @Getter
    private String path;
    @Getter
    private Image image;

    public PieceImage(String path) {
        this.path = path;
        load();
    }

    private void load() {

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        image = SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public void updatePosition(int x, int y) {
        ChessEliteHelper.getImageViewFromPosition(x, y).setImage(image);
    }
}