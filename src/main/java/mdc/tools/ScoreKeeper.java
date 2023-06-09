package mdc.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ScoreKeeper {
    private List<Score> scores = new ArrayList();
    private final String fileName;

    public ScoreKeeper(String filename) {
        this.fileName = filename;
        this.loadFile();
    }

    public void loadFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.fileName));

            try {
                String line = br.readLine();
                int i = 0;

                while(line != null && i < 10) {
                    Scanner s = new Scanner(line);
                    int score = s.nextInt();
                    String name = s.nextLine().trim();
                    this.scores.add(new Score(name, score));
                    line = br.readLine();
                    s.close();
                }
            } catch (Throwable var11) {
                try {
                    br.close();
                } catch (Throwable var10) {
                    var11.addSuppressed(var10);
                }

                throw var11;
            }

            br.close();
        } catch (FileNotFoundException var12) {
            try {
                FileWriter fw = new FileWriter(this.fileName);

                try {
                    fw.write("");
                } catch (Throwable var8) {
                    try {
                        fw.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }

                    throw var8;
                }

                fw.close();
            } catch (IOException var9) {
                System.err.println("Error creating score file");
            }

            System.err.println("Empty score file created");
        } catch (IOException var13) {
            System.err.println("Error reading score file");
        }

    }

    public void addScore(String name, int score) {
        this.scores.add(new Score(name, score));
        this.sortScores();
    }

    private void sortScores() {
        Collections.sort(this.scores);
    }

    public void saveScores() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName));

            try {
                for(int i = 0; i < this.scores.size() && i < 10; ++i) {
                    int var10001 = ((Score)this.scores.get(i)).getScore();
                    bw.write("" + var10001 + " " + ((Score)this.scores.get(i)).getName() + "\n");
                }
            } catch (Throwable var5) {
                try {
                    bw.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }

            bw.close();
        } catch (IOException var6) {
            System.err.println("Error writing score file");
        }

    }

    public Score[] getScores() {
        Score[] scs = new Score[10];

        for(int i = 0; i < scs.length; ++i) {
            scs[i] = (Score)this.scores.get(i);
        }

        return scs;
    }

    public int getLowestScore() {
        this.sortScores();
        return ((Score)this.scores.get(9)).getScore();
    }
}
