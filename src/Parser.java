import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Parser {
    private String filename;

    public Parser(String filename) {this.filename = filename; }

    public ArrayList<Pair> parse() {
        ArrayList<Pair> pairs  = new ArrayList<>();

        try {
            File f            = new File(this.filename);
            FileReader fr     = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;

            while ((line = br.readLine()) != null) {
                pairs.add(constructPair(line, null));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pairs;
    }

    private Pair constructPair(String line, Pair parent) {
        int splitIndex = 0;
        int counter    = 0;
        Pair node      = new Pair();
        String digit   = "[0-9]+";
        node.parent    = parent;

        for (int i=0; i < line.length(); ++i) {
            if (line.charAt(i) == '[') {
                counter++;
            } else if (line.charAt(i) == ']') {
                counter--;
            } else if ((line.charAt(i) == ',') && (counter == 1)) {
                splitIndex = i;
            }
        }

        String left  = line.substring(1, splitIndex);
        String right = line.substring(splitIndex+1, line.length()-1);

        if (left.matches(digit)) {
            node.leftValue = Integer.parseInt(left);
        } else {
            node.left = constructPair(left, node);
        }

        if (right.matches(digit)) {
            node.rightValue = Integer.parseInt(right);
        } else {
            node.right = constructPair(right, node);
        }

        return node;
    }
}