package mdc.tools;

public final class Score implements Comparable<Score> {
    private String name;
    private int value;

    public Score(String n, int s) {
        this.name = n;
        this.value = s;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.value;
    }

    public int compareTo(Score o) {
        return Integer.compare(o.getScore(), this.getScore());
    }
}
