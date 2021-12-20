import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Parser {
    private final String filename;

    public Parser(String filename) {this.filename = filename;}

    public ArrayList<char[]> getNumbers() {
        ArrayList<char[]> numbers = new ArrayList<>();

        try {
            FileInputStream stream = new FileInputStream(this.filename);
            Scanner sc             = new Scanner(stream);

            while (sc.hasNext()) {
                numbers.add(sc.next().toCharArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numbers;
    }
}