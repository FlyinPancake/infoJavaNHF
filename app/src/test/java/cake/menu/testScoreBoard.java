package cake.menu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class testScoreBoard {
    public ScoreBoard scoreBoard;

    @Before
    public void setUp(){
        scoreBoard = new ScoreBoard();
        scoreBoard.resetHiScores();
    }

    @Test
    public void testDefaultList(){
        for (Score sc :
                scoreBoard.getScoreTable()) {
            Assert.assertEquals(new Score("FPC", 100),sc);
        }
    }

    @Test
    public void testAddHighscore(){
        scoreBoard.addHiScore("FPC", 1000);
        Assert.assertEquals(1000,scoreBoard.getScoreTable().get(4).getHiScore());
    }

    @Test
    public void testTableFunctions() {
        Assert.assertEquals(2,scoreBoard.getColumnCount());
        Assert.assertEquals(5,scoreBoard.getRowCount());
    }
}
