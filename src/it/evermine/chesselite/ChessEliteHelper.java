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
import java.util.ArrayList;
import java.util.List;

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
        if(!ChessEliteCore.getChessBoard().isInternal(i, j))
            return null;
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

    public static List<Square> getKnight(Square square) {
        List<Square> list = new ArrayList<>();

        int squareX = square.getX();
        int squareY = square.getY();

        int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for(int i = 0; i < X.length; i++) {
            Square squareAt = ChessEliteHelper.getSquare(squareX + X[i], squareY + Y[i]);

            if(squareAt != null) {
                list.add(squareAt);
            }
        }

        return list;
    }

    public static List<Square> getAxes(Square square) {
        List<Square> list = new ArrayList<>();

        final int squareX = square.getX();
        final int squareY = square.getY();

        if(squareX != 7) {
            //System.out.println("\nPRIMO FOR\n>-------------------------------------< ");
            for (int i = squareX; i < 8; i++) {
                if(i != squareX) {
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+squareY);
                    Square sq = getSquare(i, squareY);

                    if(!sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+i+" "+squareY);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+squareY);

                    list.add(sq);
                }
            }

            //System.out.println(">-------------------------------------<");
        }

        if(squareX != 0) {
            //System.out.println("\nSECONDO FOR\n>-------------------------------------< ");

            int i = squareX;

            do {
                if(i != squareX) {
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+squareY);
                    Square sq = getSquare(i, squareY);

                    if(!sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+squareX+" "+i);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+squareX+" "+i);

                    list.add(sq);
                }

                i--;

            } while(i >= 0);

            //System.out.println(">-------------------------------------<");
        }

        if(squareY != 7) {
            //System.out.println("\nTERZO FOR\n>-------------------------------------<");
            for (int i = squareY; i < 8; i++) {
                if(i != squareY) {
                                        //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+squareX+" "+i);
                    Square sq = getSquare(squareX, i);

                    if(!sq.isEmpty()) {
                        list.add(sq);
                                                //System.out.println("--- La casella è piena, aggiunta e uscendo: "+squareX+" "+i);
                        break;
                    }
                                        //System.out.println("-- La casella non è piena, aggiunta: "+squareX+" "+i);
                    list.add(sq);
                }
            }

            //System.out.println(">-------------------------------------<");
        }

        if(squareY != 0) {
            //System.out.println("\nQUARTO FOR\n>-------------------------------------<");
            int i = squareY;

            do {
                if(i != squareY) {
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+squareX+" "+i);
                    Square sq = getSquare(squareX, i);

                    if(!sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+squareX+" "+i);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+squareX+" "+i);

                    list.add(sq);
                }

                i--;

            } while(i >= 0);

            //System.out.println(">-------------------------------------<");
        }

        //System.out.println("\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        return list;
    }

    public static List<Square> getDiagonals(Square square) {
        List<Square> list = new ArrayList<>();

        final int squareX = square.getX();
        final int squareY = square.getY();

        //System.out.println(squareX+" "+squareY);

        if(squareX != 7) {
            int squareYDecrementing = squareY;
            //System.out.println("\nPRIMO FOR\n>-------------------------------------< ");
            for (int i = squareX; i < 8; i++) {
                if(i != squareX) {
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+squareY);
                    Square sq = getSquare(i, --squareYDecrementing);

                    if(sq != null && !sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+i+" "+squareY);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+squareY);

                    if(sq != null)
                        list.add(sq);
                }
            }

            //System.out.println(">-------------------------------------<");
        }

        if(squareX != 7) {
            int squareYDecrementing = squareY;
            //System.out.println("\nSECONDO FOR\n>-------------------------------------< ");
            for (int i = squareX; i < 8; i++) {
                if (i != squareX) {
                    int a = ++squareYDecrementing;
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+a);
                    Square sq = getSquare(i, a);

                    if (sq != null && !sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+i+" "+a);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+a);

                    if (sq != null)
                        list.add(sq);
                }
            }

            //System.out.println(">-------------------------------------<");
        }

        if(squareX != 0) {
            //System.out.println("\nTERZO FOR\n>-------------------------------------< ");
            int squareYDecrementing = squareY;
            int i = squareX;

            do {
                if(i != squareX) {
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+squareY);
                    Square sq = getSquare(i, --squareYDecrementing);

                    if(sq != null && !sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+i+" "+squareY);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+squareY);

                    if(sq != null)
                     list.add(sq);
                }

                i--;

            } while(i >= 0);

            //System.out.println(">-------------------------------------<");
        }

        if(squareX != 0) {
            //System.out.println("\nQUARTO FOR\n>-------------------------------------< ");
            int squareYDecrementing = squareY;
            int i = squareX;

            do {
                if(i != squareX) {
                    int a = ++squareYDecrementing;
                    //System.out.println("\nGiro: "+i);
                    //System.out.println("- Scorrendo casella: "+i+" "+a);
                    Square sq = getSquare(i, a);

                    if(sq != null && !sq.isEmpty()) {
                        list.add(sq);
                        //System.out.println("--- La casella è piena, aggiunta e uscendo: "+i+" "+a);
                        break;
                    }

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+a);

                    if(sq != null)
                        list.add(sq);

                    //System.out.println("-- La casella non è piena, aggiunta: "+i+" "+a);
                    if(sq != null)
                        list.add(sq);
                }

                i--;

            } while(i >= 0);

            //System.out.println(">-------------------------------------<");
        }

        //System.out.println("\nAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        return list;
    }

    public static boolean areOnTheSameDiagonal(int firstX, int firstY, int secondX, int secondY) {
        return Math.abs(firstX - secondX) == Math.abs(firstY - secondY);
    }

    public static boolean areOnTheSameAxes(int firstX, int firstY, int secondX, int secondY) {
        return firstX == secondX || firstY == secondY;
    }

    public static boolean areOnTheSameHorse(int firstX, int firstY, int secondX, int secondY) {
        int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for(int i = 0; i < X.length; i++) {
            Square squareAt = ChessEliteHelper.getSquare(firstX + X[i], firstY + Y[i]);

            if(squareAt != null && squareAt.getX() == secondX && squareAt.getY() == secondY) {
                return true;
            }
        }

        return false;
    }

    public static boolean areOnTheSameKing(int firstX, int firstY, int secondX, int secondY) {
        int X[] = { 0, 0, -1, -1, -1, 1, 1, 1 };
        int Y[] = { -1, +1, -1, 1, 0, 1, -1, 0 };

        for(int i = 0; i < X.length; i++) {
            Square squareAt = ChessEliteHelper.getSquare(firstX + X[i], firstY + Y[i]);

            if(squareAt != null && squareAt.getX() == secondX && squareAt.getY() == secondY) {
                return true;
            }
        }

        return false;
    }

    public static List<Square> getCheck(Square square) {
        List<Square> toReturn = new ArrayList<>();
        List<Square> list = new ArrayList<>();

        list.addAll(getDiagonals(square));
        list.addAll(getAxes(square));
        list.addAll(getKnight(square));

        boolean isWhite = square.getPiece().isWhite();

        for(Square sq : list) {
            if(!sq.isEmpty() && sq.getPiece().isWhite() != isWhite) {
                toReturn.add(square);
            }
        }

        return toReturn;
    }

    public static List<Square> getKing(Square square) {
        List<Square> list = new ArrayList<>();

        int squareX = square.getX();
        int squareY = square.getY();

        int X[] = { 0, 0, -1, -1, -1, 1, 1, 1 };
        int Y[] = { -1, +1, -1, 1, 0, 1, -1, 0 };

        for(int i = 0; i < X.length; i++) {
            Square squareAt = ChessEliteHelper.getSquare(squareX + X[i], squareY + Y[i]);

            if(squareAt != null) {
                list.add(squareAt);
            }
        }

        return list;
    }
}
