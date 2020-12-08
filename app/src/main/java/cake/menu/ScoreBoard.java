package cake.menu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import javax.swing.table.AbstractTableModel;
import java.io.*;
import java.util.*;

/**
 * Score-ok listáját tárolja és eléri, hogy táblázatban megjeleníthetők legyenek
 */
public class ScoreBoard extends AbstractTableModel {
    private final ArrayList<Score> scoreTable;
    private final int scoreTableSize;

    public ScoreBoard() {
        scoreTableSize = 5;
        scoreTable = new ArrayList<>();
        readScoreBoard();
    }

    /**
     * Visszaállítja a toplistát, 5 db 100 pontos értékre.
     * (A klasszikus játékok előtt tisztelegve 5 highscore van, de ez tetszőlegesen,
     * a scoreTableSize változó módosításával növelhető.)
     */
    public void resetHiScores() {
        scoreTable.clear();
        for (int i = 0; i < scoreTableSize; i++) {
            scoreTable.add(new Score("FPC", 100));
        }
        writeScoreBoard();
    }

    /**
     * Beolvassa a toplistát egy JSON fileból
     */
    private void readScoreBoard() {
        try {
            FileReader fr = new FileReader(new File("hiscores.json"));
            Gson gson = new Gson();
            Score[] scoreTableArray = gson.fromJson(fr, Score[].class);
            scoreTable.clear();
            scoreTable.addAll(Arrays.asList(scoreTableArray));
            fr.close();
            Collections.sort(scoreTable);
            Collections.reverse(scoreTable);

        } catch (FileNotFoundException | JsonSyntaxException e) {
            resetHiScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kap egy monogramot és egy pontszámot és ha magasabb mint a legalacsonyabb pontszám,
     * hozzáadja a toplistához. Ekkor a legalacsonyabb pontszámú eredményt törli
     * @param name A pontszerző neve
     * @param score A pont
     */
    public void addHiScore(String name, int score) {
        if(scoreTable.get(scoreTable.size() - 1).getHiScore() < score) {
            scoreTable.remove(scoreTable.size()-1);
            scoreTable.add(new Score(name,score));
            Collections.sort(scoreTable);
            Collections.reverse(scoreTable);
            writeScoreBoard();
        }
    }

    /**
     * Kiírja az aktuális toplistát
     */
    private void writeScoreBoard() {
        File scoreFile = new File("hiscores.json");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try {
            FileWriter fw = new FileWriter(scoreFile);
            String jsonString = gson.toJson(scoreTable.toArray());
            fw.write(jsonString);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Score> getScoreTable() {
        return scoreTable;
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return scoreTable.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Score cScore = scoreTable.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cScore.getName();
            case 1 -> cScore.getHiScore();
            default -> null;
        };
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> String.class;
            case 1 -> int.class;
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "Initials";
            case 1 -> "Score";
            default -> null;
        };
    }
}
