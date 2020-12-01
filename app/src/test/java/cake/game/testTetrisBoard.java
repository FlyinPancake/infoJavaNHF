package cake.game;
import static org.junit.Assert.*;

import cake.menu.PieceShower;
import cake.graphics.TetrisScreen;
import cake.menu.TetrisStatusbar;
import org.junit.Before;
import org.junit.Test;


import cake.game.GamePiece.Tetromino;

public class testTetrisBoard {
    private PieceShower next;
    private PieceShower hold;
    private TetrisScreen tetrisScreen;
    private TetrisBoard tb;

    @Before
    public void setUp() {
        next = new PieceShower();
        hold = new PieceShower();
        tetrisScreen = new TetrisScreen(next, hold,new TetrisStatusbar());
        tb = new TetrisBoard(next,hold,tetrisScreen);
    }

    @Test
    public void testNewBoard(){
        for (int xx = 0; xx < tb.getTETRIS_WIDTH(); xx++) {
            for (int yy = 0; yy < tb.getTETRIS_HEIGHT(); yy++) {
                assertEquals(Tetromino.NoShape, tb.shapeAt(xx, yy));
            }
        }
    }
}
