package mdc.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Identify the corresponding block type in "map.txt" and record the coordinates
 */
public class TextReader {
    private static final File file = new File("src/main/resources/text/map.txt");

    /**
     * @param name  - Characters corresponding to the block
     * @return      - A two-dimensional array of all such block coordinates
     */
    public static ArrayList<int[]> getComponents(char name) {
        ArrayList<int[]> components = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int i = 0;
            String temp = reader.readLine();
            while (!(temp == null)) {
                char[] chars = temp.toCharArray();
                for (int j = 0; j < chars.length; j++) {
                    if (chars[j] == name) {
                        int[] pos = new int[2];
                        pos[0] = j; pos[1] = i;
                        components.add(pos);
                    }
                }
                temp = reader.readLine();
                i++;
            }
            return components;
        } catch (IOException e) {
            System.out.println("Please put \"map.txt\" under the folder \"Assignment\" (the same level as \"score.txt\")");
            e.printStackTrace();
            return null;
        }
    }
}
