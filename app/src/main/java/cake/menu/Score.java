package cake.menu;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * Egy nevet és egy legmagasabb pontszámot tárol
 */
public class Score  implements Serializable, Comparable<Score>{
    private final int  hiScore;
    private final String  name;

    public Score(String name, int hiScore) {
        this.hiScore = hiScore;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return hiScore == score.hiScore &&
                Objects.equals(name, score.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hiScore, name);
    }

    public int getHiScore() {
        return hiScore;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Score o) {
        return this.hiScore - o.getHiScore();
    }
}